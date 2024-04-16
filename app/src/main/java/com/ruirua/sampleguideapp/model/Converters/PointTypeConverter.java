package com.ruirua.sampleguideapp.model.Converters;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruirua.sampleguideapp.model.Point;

import java.lang.reflect.Type;

public class PointTypeConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static Point fromPointString(String pointJson) {
        Type listType = new TypeToken<Point>() {}.getType();
        return gson.fromJson(pointJson, listType);
    }

    @TypeConverter
    public static String toPointString(Point point) {
        return gson.toJson(point);
    }
}
