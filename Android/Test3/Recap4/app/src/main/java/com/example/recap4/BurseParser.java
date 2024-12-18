package com.example.recap4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BurseParser {
    private static final String DEN ="den";
    private static final String SUMA ="suma";
    private static final String TIP ="tip";


    public static List<Bursa> parseJson(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parseArr(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Bursa> parseArr(JSONArray jsonArray) throws JSONException {
        List<Bursa> lista = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            lista.add(parseObj(jsonArray.getJSONObject(i)));
        }
        return lista;
    }

    private static Bursa parseObj(JSONObject jsonObject) throws JSONException {
        String den = jsonObject.getString(DEN);
        int suma = jsonObject.getInt(SUMA);
        String tip = jsonObject.getString(TIP);

        return new Bursa(den,suma,tip);
    }
}
