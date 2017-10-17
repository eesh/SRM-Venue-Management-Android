package com.srmuniv.srmvenuemanagementtool.Network;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;


/**
 * Created by eesh on 10/16/17.
 */

class CustomDeserializer<T> implements JsonDeserializer<T> {


    String fieldName;

    public CustomDeserializer(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        // Get the "content" element from the parsed JSON
        JsonElement content;
        if(je.isJsonArray()) {
            content = je.getAsJsonArray();
        } else {
            content = je.getAsJsonObject().get(fieldName);
        }

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(content, type);

    }
}