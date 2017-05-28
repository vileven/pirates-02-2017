package api.mechanics;

import api.mechanics.base.ClientSnap;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Vileven on 28.05.17.
 */
public interface GameMechanics {
    void addClientSnapshot(@NotNull Long userId, @NotNull ClientSnap gameSnap);

    void addUser(@NotNull Long user);

    void gmStep(long frameTime);

    @SuppressWarnings("EmptyMethod")
    void reset();
}
