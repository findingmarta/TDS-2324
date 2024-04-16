package com.ruirua.sampleguideapp.model.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruirua.sampleguideapp.model.Trail;

import java.lang.reflect.Type;
import java.util.List;

public class TrailsTypeConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static List<Trail> fromRelTrailListString(String value) {
        Type listType = new TypeToken<List<Trail>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String toRelTrailListString(List<Trail> list) {
        return gson.toJson(list);
    }
}
