package api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Vileven on 02.06.17.
 */
public class Score {

    public final Long score;

    public final Long kills;

    @JsonProperty("max_combo")
    public final Long maxCombo;

    @JsonProperty("login")
    public final String login;


    @JsonCreator
    public Score(@JsonProperty("score") Long score, @JsonProperty("kills") Long kills,
                 @JsonProperty("max_combo")Long maxCombo,
                 @JsonProperty(value = "login", required = false) @Nullable String login) {
        this.score = score;
        this.kills = kills;
        this.maxCombo = maxCombo;
        this.login = login;
    }
}
