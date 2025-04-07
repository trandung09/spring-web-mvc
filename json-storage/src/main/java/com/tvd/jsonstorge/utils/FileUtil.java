package com.tvd.jsonstorge.utils;

import com.google.gson.*;
import com.tvd.jsonstorge.constant.DateTimeConstant;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil<T> implements DataWritable<T>, DataReadable<T> {

    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.format(DateTimeConstant.DATE_FORMATTER)))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) -> LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeConstant.DATE_FORMATTER))
            .create();

    @Override
    public void writeDataToFile(List<T> data, String fileName) {
        if (StringUtil.isNullOrEmpty(fileName) || DataUtil.isNullOrEmpty(data)) {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            String rs = gson.toJson(data);
            fileWriter.write(rs);

        } catch (IOException e) {
            System.out.println("File util - write: " + e.getMessage());
        }
    }

    @Override
    public List<T> readDataFromFile(String fileName, Class<T[]> clazz) {
        if (StringUtil.isNullOrEmpty(fileName)) {
            return null;
        }
        try (FileReader reader = new FileReader(fileName)) {
            T[] dataArr = gson.fromJson(reader, clazz);
            return dataArr == null ? null : new ArrayList<>(Arrays.asList(dataArr));
        } catch (IOException e) {
            System.out.println("File util - read: " + e.getMessage());
        }
        return null;
    }
}