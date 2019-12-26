package com.cch.gsyvideoplayer.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类作用及功能说明
 */
public class JsonUtil {


    public static String toJson(Object object) {
        if (object == null) {
            return "";
        }
        Gson g = new GsonBuilder().create();
        String json = g.toJson(object, object.getClass());
        return json;
    }

    /**
     * JSON转成指定对象
     *
     * @param json
     */
    public static <T> T toObj(String json, Class<T> clazz) {
        Gson gson = new Gson();

        return gson.fromJson(json, clazz);
    }

    /**
     * JSON转成指定List
     *
     * @param json
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {

//将likeList转化成List集合

        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
        /*
        以下是错误的
        Gson gson = new Gson();
		List<T> list=gson.fromJson(json, new TypeToken<List<T>>(){}.getType());

		return list;*/
    }
}
