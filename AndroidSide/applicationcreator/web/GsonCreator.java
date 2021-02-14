package com.example.applicationcreator.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GsonCreator {
    private static GsonCreator gsonCreator = null;
    private static Gson gson = new Gson();

    private static void createJsonSnD() {
        JsonSerializer<Date> ser = new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };

        JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                try {
                    String jsonAsString = json.toString();
                    Date date = new SimpleDateFormat("MMM dd, yyyy, hh:mm:ss", Locale.getDefault()).parse(jsonAsString.substring(1,jsonAsString.length()-1));
                    return new SimpleDateFormat("DD/mm/yyyy",Locale.ENGLISH).parse(new SimpleDateFormat("DD/mm/yyyy",Locale.ENGLISH).format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, ser)
                .registerTypeAdapter(Date.class, deser).create();

    }
    public static GsonCreator getInstance() {
        if (gsonCreator == null) {
            createJsonSnD();
            gsonCreator = new GsonCreator();
        }
        return gsonCreator;
    }

    public Gson getGson() {
        return gson;
    }
}
