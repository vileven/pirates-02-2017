package api.utils.response;

import java.util.List;

/**
 * Created by Vileven on 16.05.17.
 */
public class SelectBody {
    public final List<?> entries;
    public final Integer total;

    public SelectBody(List<?> entries, Integer total) {
        this.entries = entries;
        this.total = total;
    }
}
