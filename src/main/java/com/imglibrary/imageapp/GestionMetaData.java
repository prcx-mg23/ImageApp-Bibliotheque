package com.imglibrary.imageapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestionMetaData {
    private static final String FILE_NAME = "metadata.json";

    public static void save(List<ImageMetaData> metadataList) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(metadataList, writer);
        }
    }

    public static List<ImageMetaData> load() throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<List<ImageMetaData>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
