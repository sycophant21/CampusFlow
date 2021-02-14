package com.helper;

import com.google.gson.Gson;

public class Serializer {

    private final Gson gson;

    public Serializer(Gson gson) {

        this.gson = gson;
    }

    public String serialize(Object object) {
        return gson.toJson(object);
    }

    public Object deserialize(String document, Class clazz) {
        return gson.fromJson(document, clazz);
    }
}
