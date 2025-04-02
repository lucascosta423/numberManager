package com.main.numberManager.utils.responseApi;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String status;
}
