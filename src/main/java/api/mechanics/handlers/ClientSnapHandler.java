package api.mechanics.handlers;

import api.mechanics.GameMechanics;
import api.mechanics.base.ClientSnap;
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
public class ClientSnapHandler extends MessageHandler<ClientSnap> {
    @NotNull
    private GameMechanics gameMechanics;
    @NotNull
    private MessageHandlerContainer messageHandlerContainer;

    public ClientSnapHandler(@NotNull GameMechanics gameMechanics,
                             @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(ClientSnap.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(ClientSnap.class, this);
    }

    @Override
    public void handle(@NotNull ClientSnap message, @NotNull long forUser) throws HandleException {
        gameMechanics.addClientSnapshot(forUser, message);
    }
}
