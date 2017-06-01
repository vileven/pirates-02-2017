package api.mechanics.internal;

import api.mechanics.GameSession;
import api.model.User;
import api.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vileven on 01.06.17.
 */
@Service
public class GameSessionService {
    @NotNull
    private final Map<Long, GameSession> usersMap = new HashMap<>();
    @NotNull
    private final Set<GameSession> gameSessions = new LinkedHashSet<>();

    @NotNull
    private final RemotePointService remotePointService;

    @NotNull
    private final GameInitService gameInitService;

    public GameSessionService(@NotNull RemotePointService remotePointService, @NotNull GameInitService gameInitService) {
        this.remotePointService = remotePointService;
        this.gameInitService = gameInitService;
    }

    public Set<GameSession> getSessions() {
        return gameSessions;
    }

    @Nullable
    public GameSession getSessionForUser(@NotNull Long userId) {
        return usersMap.get(userId);
    }

    public boolean isPlaying(@NotNull Long userId) {
        return usersMap.containsKey(userId);
    }

    public void notifyGameIsOver(@NotNull GameSession gameSession) {

    }

    public void startGame(@NotNull User first, @NotNull User second) {
    }
}

