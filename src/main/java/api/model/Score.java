package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Vileven on 02.06.17.
 */
public class Score {

    @JsonProperty("user_login")
    public final String userLogin;

    public final Long score;


    public Score(String userLogin, Long score) {
        this.userLogin = userLogin;
        this.score = score;
    }
}
