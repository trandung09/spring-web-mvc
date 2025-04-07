package com.tvd.jsonstorge.utils;

import java.util.List;

public interface DataReadable<T> {

    List<T> readDataFromFile(String fileName, Class<T[]> clazz);
}
