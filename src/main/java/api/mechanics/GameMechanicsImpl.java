package api.mechanics;

import api.mechanics.base.ClientSnap;
import api.mechanics.internal.ClientSnapshotsService;
import api.mechanics.internal.GameSessionService;
import api.mechanics.internal.ServerSnapshotService;
import api.model.User;
import api.services.AccountService;
import api.websocket.RemotePointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Vileven on 28.05.17.
 */
@Service
public class GameMechanicsImpl implements GameMechanics {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanicsImpl.class);

    @NotNull
    private final AccountService accountService;
    @NotNull
    private final ClientSnapshotsService clientSnapshotsService;
    @NotNull
    private final ServerSnapshotService serverSnapshotService;
    @NotNull
    private final RemotePointService remotePointService;
    @NotNull
    private final GameSessionService gameSessionService;

    @NotNull
    private final ConcurrentLinkedQueue<Long> waiters = new ConcurrentLinkedQueue<>();

    @NotNull
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    @NotNull
    private final ObjectMapper objectMapper;

    public GameMechanicsImpl(@NotNull AccountService accountService, @NotNull ClientSnapshotsService clientSnapshotsService, @NotNull ServerSnapshotService serverSnapshotService, @NotNull RemotePointService remotePointService, @NotNull GameSessionService gameSessionService, @NotNull ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.clientSnapshotsService = clientSnapshotsService;
        this.serverSnapshotService = serverSnapshotService;
        this.remotePointService = remotePointService;
        this.gameSessionService = gameSessionService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void addClientSnapshot(@NotNull Long userId, @NotNull ClientSnap gameSnap) {
        tasks.add(() -> clientSnapshotsService.pushClientSnap(userId, gameSnap));
    }

    @Override
    public void addUser(@NotNull Long user) {
        if (gameSessionService.isPlaying(user)) {
            return;
        }
        waiters.add(user);
    }

    @Override
    public void gmStep(long frameTime) {

        while (!tasks.isEmpty()) {
            final Runnable nextTask = tasks.poll();
            try {
                nextTask.run();
            } catch (RuntimeException ex) {
                LOGGER.error("Cant handle game task", ex);
            }
        }

        for (GameSession session : gameSessionService.getSessions()) {
            clientSnapshotsService.processSnapshotsFor(session);
        }


        final Iterator<GameSession> iterator = gameSessionService.getSessions().iterator();
        final Collection<GameSession> sessionsToTerminate = new ArrayList<>();
        while (iterator.hasNext()) {
            final GameSession session = iterator.next();
            try {
                serverSnapshotService.sendSnapshotsFor(session, frameTime);
            } catch (RuntimeException ex) {
                sessionsToTerminate.add(session);
                LOGGER.error("Session was terminated!");
            }
            sessionsToTerminate.forEach(gameSessionService::notifyGameIsOver);
        }

        tryStartGames();
        clientSnapshotsService.clear();
    }

    private void tryStartGames() {
        final Set<User> matchedPlayers = new LinkedHashSet<>();

        while (waiters.size() >= 2 || waiters.size() >= 1 && matchedPlayers.size() >= 1) {
            final long candidate = waiters.poll();
            if (!insureCandidate(candidate)) {
                continue;
            }
            matchedPlayers.add(accountService.getUserById(candidate));
            if(matchedPlayers.size() == 2) {
                final Iterator<User> iterator = matchedPlayers.iterator();
                gameSessionService.startGame(iterator.next(), iterator.next());
                matchedPlayers.clear();
            }
        }
        matchedPlayers.stream().map(User::getId).forEach(waiters::add);
    }

    private boolean insureCandidate(long candidate) {
        return remotePointService.isConnected(candidate) &&
                accountService.getUserById(candidate) != null;
    }

    @Override
    public void reset() {

    }
}
