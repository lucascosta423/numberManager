package com.main.numberManager.utils;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    public static Stream<String> readerFile(MultipartFile file) throws IOException {
        return new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.ISO_8859_1))
                .lines()
                .skip(1);
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

            if (parts.length < 3) { // Ajuste conforme o número de colunas esperado
                throw new IllegalArgumentException("Linha inválida: " + line + " (Esperado >= 3 colunas, encontrado " + parts.length + ")");
            }

            T model = type.getDeclaredConstructor().newInstance();
            Field[] fields = type.getDeclaredFields();
            int partIndex = 0;

            for (Field field : fields) {
                field.setAccessible(true);

                // Ignora campos anotados com @Id ou @GeneratedValue
                if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(GeneratedValue.class)) {
                    continue;
                }

                if (partIndex < parts.length) {
                    String value = parts[partIndex].trim();

                    if (!value.isEmpty()) {
                        // Verifica o tipo do campo e converte adequadamente
                        if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                            field.set(model, Integer.parseInt(value));
                        } else {
                            field.set(model, value); // Mantém como String
                        }
                    }
                }
                partIndex++;
            }
            return model;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear linha para " + type.getSimpleName(), e);
        }
    }

    public static <T> T mapLineToModel(String line, String[] headers, Supplier<T> modelSupplier, FileMapper<T> mapper) {
        try {
            String[] parts = line.split(";");
            T model = modelSupplier.get();

            for (int i = 0; i < headers.length; i++) {
                String header = headers[i].trim().toLowerCase();
                String value = i < parts.length ? parts[i].trim() : "";
                mapper.map(model, header, value);
            }

            return model;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear linha: " + line, e);
        }
    }
}
