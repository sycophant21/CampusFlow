package com.helper.filters;

import java.util.List;

public class AndFilter implements ComplexFilter {
    private final List<Filter> filters;

    public AndFilter(List<Filter> filters) {
        this.filters = filters;
    }

    public List<Filter> getFilters() {
        return filters;
    }
}
