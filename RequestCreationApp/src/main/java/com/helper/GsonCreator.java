package com.helper;

import com.google.gson.*;


public class GsonCreator {
    private final Gson gson;

    public GsonCreator(Gson gson) {
        this.gson = gson;
        //createJsonSnD();
    }
    public Gson getGson() {
        return gson;
    }
}
