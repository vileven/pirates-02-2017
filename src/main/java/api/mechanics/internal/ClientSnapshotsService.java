package api.mechanics.internal;

import api.mechanics.GameSession;
import api.mechanics.base.ClientSnap;
import api.services.AccountService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vileven on 01.06.17.
 */
@Service
public class ClientSnapshotsService {

    @NotNull
    private final Map<Long, List<ClientSnap>> snaps = new HashMap<>();

    @NotNull
    private final AccountService accountService;

    @Autowired
    public ClientSnapshotsService(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    public synchronized void pushClientSnap(long user, @NotNull ClientSnap snap) {
        final List<ClientSnap> userSnaps = snaps.computeIfAbsent(user, u -> new ArrayList<>());
        userSnaps.add(snap);
    }

    @Nullable
    public synchronized List<ClientSnap> getSnapsForUser(long user) {
        return snaps.get(user);
    }

    public void processSnapshotsFor(GameSession gameSession) {

    }

    public void clear() {
        snaps.clear();
    }

}
