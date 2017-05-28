package api.mechanics;

import api.utils.TimeHelper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Vileven on 28.05.17.
 */
@Service
public class MechanicsExecutor implements Runnable {
    private static final long STEP_TIME = 30;

    @NotNull
    private final GameMechanics gameMechanics;

    @NotNull
    private final Clock clock = Clock.systemDefaultZone();

    private final Executor tickExecutor = Executors.newSingleThreadExecutor();

    @Autowired
    public MechanicsExecutor(@NotNull GameMechanics gameMechanics) {
        this.gameMechanics = gameMechanics;
    }

    @PostConstruct
    public void initAfterStartup() {
        tickExecutor.execute(this);
    }

    @Override
    public void run() {
        long lastFrameMillis = STEP_TIME;
        while (true) {
            final long before = clock.millis();

            gameMechanics.gmStep(lastFrameMillis);

            final long after = clock.millis();
            TimeHelper.sleep(STEP_TIME - (after - before));

            if (Thread.currentThread().isInterrupted()) {
                gameMechanics.reset();
                return;
            }

            final long afterSleep = clock.millis();
            lastFrameMillis = afterSleep - before;
        }
    }
}
