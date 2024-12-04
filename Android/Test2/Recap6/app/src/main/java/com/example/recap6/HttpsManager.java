package com.example.recap6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsManager {
    private static HttpsURLConnection connection;
    private static BufferedReader reader;
    private static String url;

    public HttpsManager(String url) {
        this.url = url;
    }

    public static String procesare(){
        try {
            return getJsonFromHttps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            inchidere();
        }
    }

    private static void inchidere() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
        connection.disconnect();
    }

    private static String getJsonFromHttps() throws IOException {
        connection = (HttpsURLConnection) new URL(url).openConnection();
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;

        while((line=reader.readLine())!=null){
            builder.append(line);
        }

        return builder.toString();
    }
}
