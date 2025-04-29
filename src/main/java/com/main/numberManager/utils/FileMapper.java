package com.main.numberManager.utils;

@FunctionalInterface
public interface FileMapper<T> {
    void map(T instance, String header,String values);
}
