package com.ruirua.sampleguideapp.model.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruirua.sampleguideapp.model.Point;

import java.lang.reflect.Type;
import java.util.List;

public class PointsTypeConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static List<Point> fromRelPointListString(String value) {
        Type listType = new TypeToken<List<Point>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String toRelPointListString(List<Point> list) {
        return gson.toJson(list);
    }
}
