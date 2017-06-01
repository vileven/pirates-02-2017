package api.websocket;

import api.model.User;
import api.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.naming.AuthenticationException;
import java.io.IOException;

import static api.controllers.SessionController.USER_ID;

/**
 * Created by Vileven on 28.05.17.
 */
public class GameSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameSocketHandler.class);

    @NotNull
    private final AccountService accountService;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;
    @NotNull
    private final RemotePointService remotePointService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GameSocketHandler(@NotNull AccountService accountService,
                             @NotNull MessageHandlerContainer messageHandlerContainer,
                             @NotNull RemotePointService remotePointService) {
        this.accountService = accountService;
        this.messageHandlerContainer = messageHandlerContainer;
        this.remotePointService = remotePointService;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws AuthenticationException {
        final Long userId = (Long) webSocketSession.getAttributes().get("userId");
        if (userId == null || accountService.getUserById(userId) == null) {
            throw new AuthenticationException("Only authenticated users allowed to play a game");
        }
        remotePointService.registerUser(userId, webSocketSession);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws AuthenticationException {
        final Long userId = (Long) session.getAttributes().get(USER_ID);
        final User user;
        if (userId == null || (user = accountService.getUserById(userId)) == null) {
            throw new AuthenticationException("Only authenticated users allowed to play a game");
        }
        handleMessage(user, message);
    }

    private void handleMessage(User user, TextMessage text) {
        final Message message;
        try {
            message = objectMapper.readValue(text.getPayload(), Message.class);
        } catch (IOException ex) {
            LOGGER.error("wrong json format at ping response", ex);
            return;
        }
        try {
            messageHandlerContainer.handle(message, user.getId());
        } catch (HandleException e) {
            LOGGER.error("Can't handle message of type " + message.getType() + " with content: " + message.getContent(), e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        LOGGER.warn("Websocket transport problem", throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        final Long userId = (Long) webSocketSession.getAttributes().get("userId");
        if (userId == null) {
            LOGGER.warn("User disconnected but his session was not found (closeStatus=" + closeStatus + ')');
            return;
        }
        remotePointService.removeUser(userId);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
