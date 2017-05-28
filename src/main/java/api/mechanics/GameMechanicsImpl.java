package api.mechanics;

import api.mechanics.base.ClientSnap;
import api.services.AccountService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Vileven on 28.05.17.
 */
@Service
public class GameMechanicsImpl implements GameMechanics {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanicsImpl.class);

    @NotNull
    private final AccountService accountService;

    public GameMechanicsImpl(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void addClientSnapshot(@NotNull Long userId, @NotNull ClientSnap gameSnap) {

    }

    @Override
    public void addUser(@NotNull Long user) {

    }

    @Override
    public void gmStep(long frameTime) {

    }

    @Override
    public void reset() {

    }
}
