package api.websocket;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Vileven on 28.05.17.
 */
public interface MessageHandlerContainer {

    void handle(@NotNull Message message, long forUser) throws HandleException;

    <T> void registerHandler(@NotNull Class<T> clazz, MessageHandler<T> handler);
}
