package com.helper.filters;

import java.util.List;

public class OrFilter implements ComplexFilter {
    private final List<Filter> filters;

    public OrFilter(List<Filter> filters) {

        this.filters = filters;
    }

    public List<Filter> getFilters() {
        return filters;
    }
}
