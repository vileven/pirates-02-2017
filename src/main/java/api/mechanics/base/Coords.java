package api.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Vileven on 01.06.17.
 */
@SuppressWarnings("InstanceVariableNamingConvention")
public class Coords {

    public Coords(@JsonProperty("x") double x, @JsonProperty("y") double y, @JsonProperty("z") double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final double x;
    public final double y;
    public final double z;

    @NotNull
    public Coords add(@NotNull Coords addition) {
        return new Coords(x + addition.x, y + addition.y,
                z + addition.z);
    }

}