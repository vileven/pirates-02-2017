package api.mechanics.base;

import org.jetbrains.annotations.NotNull;

/**
 * Данные об одном пользователе от сервера
 */
public class ServerPlayerSnap {
    @NotNull
    private Long userId;

    @NotNull
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@NotNull Long userId) {
        this.userId = userId;
    }
}
