package api.utils.info;

import api.utils.pair.Pair;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vileven on 14.05.17.
 */
public class SelectParametersInfo {
    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public List<Pair<String, String>> getOrders() {
        return orders;
    }

    private final Integer limit;
    private final Integer offset;

    private final List<Pair<String, String>> orders;
    private final List<Pair<String, String>> filters;

    @JsonCreator
    public SelectParametersInfo(@JsonProperty("limit") Integer limit, @JsonProperty("offset") Integer offset,
                                @JsonProperty(value = "orders") List<List<String>> orders,
                                @JsonProperty("filters") List<List<String>> filters) {
        this.limit = limit;
        this.offset = offset;
        this.orders = orders.stream().map(el -> new Pair<>(el.get(0), el.get(1))).collect(Collectors.toList());
        this.filters = filters.stream().map(el -> new Pair<>(el.get(0), el.get(1))).collect(Collectors.toList());
    }

    public List<Pair<String, String>> getFilters() {
        return filters;
    }
}
