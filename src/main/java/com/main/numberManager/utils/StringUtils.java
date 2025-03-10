package com.main.numberManager.utils;

import java.text.Normalizer;

public class StringUtils {

    public static String removeAccents(String input) {
        if (input == null) {
            return null;
        }
        // Normaliza a string para decompor os acentos
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Remove apenas os acentos, mantendo todas as letras
        return normalized.replaceAll("\\p{M}", "");
    }

    public static String cleanText(String input) {
        if (input == null) {
            return null;
        }
        // Remove acentos corretamente sem perder letras
        String withoutAccents = removeAccents(input);
        // Mantém letras, números, espaços e ";", removendo apenas caracteres especiais
        return withoutAccents.replaceAll("[^a-zA-Z0-9 ;]", "");
    }

    public static String cleanLineKeepingSeparator(String line) {
        if (line == null) {
            return null;
        }
        // Divide a linha pelo ";"
        String[] parts = line.split(";");
        // Processa cada parte removendo acentos e caracteres especiais
        for (int i = 0; i < parts.length; i++) {
            parts[i] = cleanText(parts[i].trim()); // Trim para remover espaços extras
        }
        // Junta de volta com ";"
        return String.join(";", parts);
    }
}
