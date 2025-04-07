package com.tvd.jsonstorge;

import java.util.Arrays;

class Animal {
    int id;
    int age;
    String name;
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Class<Animal> clazz = Animal.class;
        System.out.println(Arrays.toString(clazz.getDeclaredFields()));
    }
}