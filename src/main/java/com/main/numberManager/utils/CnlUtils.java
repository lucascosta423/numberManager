package com.main.numberManager.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CnlUtils{

    public static List<String> readerFile(MultipartFile file) throws IOException {
        return new BufferedReader(new InputStreamReader(file.getInputStream()))
                .lines()
                .toList();
    }

    public static <T> List<T> parseLines(List<String> lines, Function<String, T> mapper) {
        return lines.stream()
                .map(line -> {
                    if (line.isEmpty()) {
                        return null;
                    }
                    return mapper.apply(line);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <T> T mapToModel(String line, Class<T> type) {
        try {
            String[] parts = line.split(";");

            if (parts.length < 13) { // Ajuste conforme necessário para outros modelos
                throw new IllegalArgumentException("Linha inválida: " + line + " (Esperado >= 13 colunas, encontrado " + parts.length + ")");
            }
            T model = type.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(parts, model);
            return model;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear linha para " + type.getSimpleName(), e);
        }
    }
}
