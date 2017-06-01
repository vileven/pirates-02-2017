package api.mechanics.internal;

import api.mechanics.GameSession;
import api.mechanics.requests.InitGame;
import api.websocket.RemotePointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Vileven on 01.06.17.
 */
public class GameInitService {
    @NotNull
    private final RemotePointService remotePointService;
    @NotNull
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GameInitService(@NotNull RemotePointService remotePointService) {
        this.remotePointService = remotePointService;

    }

    public void initGameFor(@NotNull GameSession gameSession) {

    }

    private InitGame.Request createInitMessageFor(@NotNull GameSession gameSession, @NotNull Long userId) {
        return null;
    }

}
