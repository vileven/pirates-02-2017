package api.mechanics;

import api.mechanics.avatar.GameUser;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Vileven on 01.06.17.
 */
public class GameSession {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameSession.class);
    @NotNull
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    @NotNull
    private final Long sessionId;

    @NotNull
    private final GameUser first;
    @NotNull
    private final GameUser second;

    private final long startTime;

    public GameSession(@NotNull GameUser first, @NotNull GameUser second, long startTime) {
        this.sessionId = ID_GENERATOR.getAndIncrement();;
        this.first = first;
        this.second = second;
        this.startTime = startTime;
    }

    @NotNull
    public GameUser getEnemy(@NotNull GameUser user) {
        if (Objects.equals(user, first)) {
            return second;
        }

        if (Objects.equals(user, second)) {
            return first;
        }

        throw new IllegalArgumentException("Requested enemy for game but user not participant");
    }


    @NotNull
    public GameUser getSelf(long userId) {
        if (first.getUser().getId().equals(userId)) {
            return first;
        }
        if (second.getUser().getId().equals(userId)) {
            return second;
        }
        throw new IllegalArgumentException("Request self for game but user not participate it");
    }

    @NotNull
    public GameUser getFirst() {
        return first;
    }

    @NotNull
    public GameUser getSecond() {
        return second;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameSession that = (GameSession) o;

        return sessionId.equals(that.sessionId);
    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }
}
