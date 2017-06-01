package api.mechanics.internal;

import api.mechanics.GameSession;
import api.websocket.RemotePointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * Created by Vileven on 01.06.17.
 */
@Service
public class ServerSnapshotService {
    @NotNull
    private final RemotePointService remotePointService;

    @NotNull
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ServerSnapshotService(@NotNull RemotePointService remotePointService) {
        this.remotePointService = remotePointService;
    }

    public void sendSnapshotsFor(@NotNull GameSession gameSession, long frameTime) {
    }
}
