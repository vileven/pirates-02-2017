package api.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Vileven on 02.06.17.
 */
public class InitServerSnap {
    private boolean leader;

    @JsonProperty("rival_login")
    private String rivalLogin;


}
