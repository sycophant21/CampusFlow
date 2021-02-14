package com.helper.filters;

import com.helper.Operator;

public class SimpleFilter implements Filter {
    private final Operator operator;
    private final String fieldPath;
    private final Object fieldValue;

    public SimpleFilter(Operator operator, String fieldPath, Object fieldValue) {
        this.operator = operator;
        this.fieldPath = fieldPath;
        this.fieldValue = fieldValue;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public String getFieldPath() {
        return fieldPath;
    }
}
