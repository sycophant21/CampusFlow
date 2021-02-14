package com.mongo;

import com.helper.filters.AndFilter;
import com.helper.filters.Filter;
import com.helper.filters.OrFilter;
import com.helper.filters.SimpleFilter;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class FilterToBSONConverter {
    public Bson filterToBsonConverter(Filter filter) {
        Bson bson = null;
        if (filter.getClass() == SimpleFilter.class) {
            switch (((SimpleFilter) filter).getOperator()) {
                case EQ: {
                    bson = eq(((SimpleFilter) filter).getFieldPath(), ((SimpleFilter) filter).getFieldValue());
                    break;
                }
                case NE: {
                    bson = ne(((SimpleFilter) filter).getFieldPath(), ((SimpleFilter) filter).getFieldValue());
                    break;
                }
                case GT: {
                    bson = gt(((SimpleFilter) filter).getFieldPath(), ((SimpleFilter) filter).getFieldValue());
                    break;
                }
                case LT: {
                    bson = lt(((SimpleFilter) filter).getFieldPath(), ((SimpleFilter) filter).getFieldValue());
                    break;
                }
                case GTE: {
                    bson = gte(((SimpleFilter) filter).getFieldPath(), ((SimpleFilter) filter).getFieldValue());
                    break;
                }
                case LTE: {
                    bson = lte(((SimpleFilter) filter).getFieldPath(), ((SimpleFilter) filter).getFieldValue());
                    break;
                }
            }
        }
        else if (filter.getClass() == AndFilter.class) {
            List<Bson> bsonList = new ArrayList<>();
            for (Filter filter1 : ((AndFilter) filter).getFilters()) {
                bsonList.add(filterToBsonConverter(filter1));
            }
            return and(bsonList);
        }
        else if (filter.getClass() == OrFilter.class) {
            List<Bson> bsonList = new ArrayList<>();
            for (Filter filter1 : ((OrFilter) filter).getFilters()) {
                bsonList.add(filterToBsonConverter(filter1));
            }
            return or(bsonList);
        }
        return bson;
    }
}
