package com.example.recap6;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParserMancare {
    private static final String DEN ="den";
    private static final String KCAL ="kcal";
    private static final String DATAEXP ="dataExp";


    public static List<Mancare> parseJson(String json){

        try {
            JSONArray jsonArray = new JSONArray(json);
            return parseArr(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Mancare> parseArr(JSONArray jsonArray) throws JSONException {
        List<Mancare> lista = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            lista.add(parseObj(jsonArray.getJSONObject(i)));
        }

        return lista;
    }

    private static Mancare parseObj(JSONObject jsonObject) throws JSONException {
        String den = jsonObject.getString(DEN);
        double kcal = jsonObject.getDouble(KCAL);
        String exp = jsonObject.getString(DATAEXP);

        Date data;
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
        try {
            data=sdf.parse(exp);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new Mancare(den,kcal,data);
    }
}
