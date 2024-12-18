package com.example.recap5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudParser {
    private final static String NUME = "nume";
    private final static String MEDIE = "medie";
    private final static String SPECIALIZARE = "specializare";

    public static List<Student> parseJson(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parseArr(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Student> parseArr(JSONArray jsonArray) throws JSONException {
        List<Student> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(parseObj(jsonArray.getJSONObject(i)));
        }

        return list;
    }

    private static Student parseObj(JSONObject jsonObject) throws JSONException {
        String nume = jsonObject.getString(NUME);
        float medie = Float.parseFloat(jsonObject.getString(MEDIE));
        String spec = jsonObject.getString(SPECIALIZARE);

        return new Student(nume,medie,spec);
    }
}
