package com.kihron.keymod.client.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.kihron.keymod.client.KeyMod;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JSONReader {

    public static Map<String, Map<Integer, Game>> readMap(String link) throws IOException {
        Map<String, Map<Integer, Game>> result = new HashMap<>();

        URL url = new URL(link);
        JsonReader jr = new JsonReader(new InputStreamReader(url.openStream()));
        JsonElement je = new JsonParser().parse(jr);
        JsonObject jo = je.getAsJsonObject();
        JsonArray ja = jo.get("keys").getAsJsonArray();
        for (JsonElement e : ja) {
            JsonObject o = e.getAsJsonObject();
            String category = o.get("category").getAsString();
            JsonArray a = o.get("values").getAsJsonArray();
            Map<Integer, Game> sub = new HashMap<>();
            for (int i = 0; i < a.size(); i++) {
                String gameString = a.get(i).getAsJsonObject().get("gameString").getAsString();
                String translateString = a.get(i).getAsJsonObject().get("translateString").getAsString();
                sub.put(i + 1, new Game(gameString, translateString));
            }
            result.put(category, sub);
        }
        return result;
    }

    public static String[] readCategories(String link) throws IOException {

        String[] result = null;

        URL url = new URL(link);
        JsonReader jr = new JsonReader(new InputStreamReader(url.openStream()));
        JsonElement je = new JsonParser().parse(jr);
        JsonObject jo = je.getAsJsonObject();
        JsonArray ja = jo.get("categories").getAsJsonArray();
        result = new String[ja.size()];
        for (int i = 0; i < ja.size(); i++) {
            result[i] = ja.get(i).getAsString();
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println(Arrays.toString(readCategories("https://raw.githubusercontent.com/Kihron/Electritech/master/keys.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
