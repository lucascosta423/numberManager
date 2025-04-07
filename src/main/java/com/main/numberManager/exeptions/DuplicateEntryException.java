package com.main.numberManager.exeptions;

import lombok.Getter;

@Getter
public class DuplicateEntryException extends RuntimeException {
    // Getters
    private final String fieldName;
    private final String fieldValue;

    public DuplicateEntryException(String fieldName, String fieldValue) {
        super(String.format("Já existe um registro com %s='%s'", fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
