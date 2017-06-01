package api.mechanics.handlers;

import api.mechanics.GameMechanics;
import api.mechanics.requests.JoinGame;
import api.websocket.HandleException;
import api.websocket.MessageHandler;
import api.websocket.MessageHandlerContainer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Vileven on 01.06.17.
 */
@Component
public class JoinGameHandler extends MessageHandler<JoinGame.Request> {
    @NotNull
    private GameMechanics gameMechanics;
    @NotNull
    private MessageHandlerContainer messageHandlerContainer;

    public JoinGameHandler(@NotNull GameMechanics gameMechanics, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(JoinGame.Request.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(JoinGame.Request.class, this);
    }

    @Override
    public void handle(@NotNull JoinGame.Request message, long forUser) throws HandleException {
        gameMechanics.addUser(forUser);
    }
}

