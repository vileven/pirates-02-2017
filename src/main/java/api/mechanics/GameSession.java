package api.mechanics;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
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



    private final long startTime;

    public GameSession(@NotNull Long sessionId, long startTime) {
        this.sessionId = sessionId;
        this.startTime = startTime;
    }
}
