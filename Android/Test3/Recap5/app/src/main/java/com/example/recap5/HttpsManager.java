package com.example.recap5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
            return getJsonHttps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            terminate();
        }
    }

    private static void terminate() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connection.disconnect();
    }

    private static String getJsonHttps() throws IOException {
        connection = (HttpsURLConnection) new URL(url).openConnection();
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder builder = new StringBuilder();
        String line;

        while ((line=reader.readLine())!=null){
            builder.append(line);
        }

        return builder.toString();
    }
}
