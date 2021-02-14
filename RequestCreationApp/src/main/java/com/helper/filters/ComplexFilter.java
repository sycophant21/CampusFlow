package com.helper.filters;

import java.util.List;

public interface ComplexFilter extends Filter{

    List<Filter> getFilters();

}
