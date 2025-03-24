package com.main.numberManager.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern DIACRITICS = Pattern.compile("\\p{M}");
    private static final Pattern SPECIAL_CHARS = Pattern.compile("[^a-zA-Z0-9; ]"); // Mantém espaços

    /**
     * Remove acentos de uma string.
     */
    public static String removeAccents(String input) {
        if (input == null) {
            return null;
        }
        // Normaliza para decompor acentos
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Remove apenas os acentos, mantendo as letras corretamente
        return normalized.replaceAll("\\p{M}", "").replaceAll("́", "");
    }

    /**
     * Remove acentos e caracteres especiais, mantendo letras, números, espaços e ";".
     */
    public static String cleanText(String input) {
        if (input == null) {
            return null;
        }
        return SPECIAL_CHARS.matcher(removeAccents(input)).replaceAll("");
    }

    /**
     * Limpa uma linha de texto separada por ";", removendo acentos e caracteres especiais.
     */
    public static String cleanLineKeepingSeparator(String line) {
        if (line == null) {
            return null;
        }
        StringBuilder cleaned = new StringBuilder();
        String[] parts = line.split(";");

        for (int i = 0; i < parts.length; i++) {
            if (i > 0) cleaned.append(";"); // Mantém os separadores corretamente
            cleaned.append(cleanText(parts[i].trim()));
        }

        return cleaned.toString();
    }
}
