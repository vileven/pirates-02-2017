package api.mechanics.internal;

import api.mechanics.GameSession;
import api.mechanics.avatar.GameUser;
import api.mechanics.base.ClientSnap;
import api.websocket.Message;
import api.websocket.RemotePointService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

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

    public void sendSnapshotsFor(@NotNull GameSession gameSession, long frameTime)  {
        final GameUser leader = gameSession.getLeader();
        final GameUser slave = gameSession.getSlave();



        final Queue<String> leaderStates = slave.getStates();
        final Queue<String> slaveStates = leader.getStates();
        try {
            if (leaderStates != null && !leaderStates.isEmpty()) {
                remotePointService.sendMessageToUser(leader.getUser().getId(), new Message("server-snap",
                        objectMapper.writeValueAsString(leaderStates)));
                leaderStates.clear();
            }

            if (slaveStates != null && !slaveStates.isEmpty()) {
                remotePointService.sendMessageToUser(slave.getUser().getId(), new Message("server-snap",
                        objectMapper.writeValueAsString(slaveStates)));
                slaveStates.clear();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed sending snapshot", ex);
        }
    }
}
