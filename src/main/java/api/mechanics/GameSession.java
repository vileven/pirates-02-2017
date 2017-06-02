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
    private final GameUser leader;
    @NotNull
    private final GameUser slave;

    private final long startTime;

    public GameSession(@NotNull GameUser leader, @NotNull GameUser slave, long startTime) {
        this.sessionId = ID_GENERATOR.getAndIncrement();
        this.leader = leader;
        this.slave = slave;
        this.startTime = startTime;
    }


    @NotNull
    public GameUser getEnemy(@NotNull GameUser user) {
        if (Objects.equals(user, leader)) {
            return slave;
        }

        if (Objects.equals(user, slave)) {
            return leader;
        }

        throw new IllegalArgumentException("Requested enemy for game but user not participant");
    }


    @NotNull
    public GameUser getSelf(long userId) {
        if (leader.getUser().getId().equals(userId)) {
            return leader;
        }
        if (slave.getUser().getId().equals(userId)) {
            return slave;
        }
        throw new IllegalArgumentException("Request self for game but user not participate it");
    }

    @NotNull
    public GameUser getLeader() {
        return leader;
    }

    @NotNull
    public GameUser getSlave() {
        return slave;
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
