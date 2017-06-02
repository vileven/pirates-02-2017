package api.mechanics.avatar;

import api.model.User;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Здесь класс игрока, здесь будут в полях вся информация о нем, координаты, очки, хп и т.д.
 */
public class GameUser {
    @NotNull
    private final User user;

    public Queue<String> getStates() {
        return states;
    }

    private final Queue<String> states = new ConcurrentLinkedQueue<>();

    public GameUser(@NotNull User user) {
        this.user = user;
    }

    @NotNull
    public User getUser() {
        return user;
    }
}
