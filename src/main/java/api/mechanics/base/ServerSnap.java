package api.mechanics.base;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Ответ сервера, здесь для каждого пользователя будут результаты и timestamp (Может еще что??)
 */
public class ServerSnap {

    @NotNull
    List<ServerPlayerSnap> players;
    private long serverFrameTime;

    @NotNull
    public List<ServerPlayerSnap> getPlayers() {
        return players;
    }

    public void setPlayers(@NotNull List<ServerPlayerSnap> players) {
        this.players = players;
    }

    public long getServerFrameTime() {
        return serverFrameTime;
    }

    public void setServerFrameTime(long serverFrameTime) {
        this.serverFrameTime = serverFrameTime;
    }
}

