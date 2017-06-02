package api.mechanics.internal;

import api.mechanics.GameSession;
import api.mechanics.requests.InitGame;
import api.websocket.Message;
import api.websocket.RemotePointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Vileven on 01.06.17.
 */
@Service
public class GameInitService {
    @NotNull
    private final RemotePointService remotePointService;
    @NotNull
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GameInitService(@NotNull RemotePointService remotePointService) {
        this.remotePointService = remotePointService;

    }

    public void initGameFor(@NotNull GameSession gameSession) {
        try {
            remotePointService.sendMessageToUser(gameSession.getLeader().getUser().getId(), new Message("init-game",
                    "{\"leader\": true, \"rival_login\":\""+gameSession.getSlave().getUser().getLogin()+"\"}"));

            remotePointService.sendMessageToUser(gameSession.getSlave().getUser().getId(), new Message("init-game",
                    "{\"leader\": false, \"rival_login\":\""+gameSession.getLeader().getUser().getLogin()+"\"}"));

        } catch (IOException e) {
            remotePointService.cutDownConnection(gameSession.getLeader().getUser().getId());
            remotePointService.cutDownConnection(gameSession.getSlave().getUser().getId());
        }
    }


}
