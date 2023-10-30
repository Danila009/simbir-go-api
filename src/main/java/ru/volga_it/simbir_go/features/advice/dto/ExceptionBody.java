package ru.volga_it.simbir_go.features.advice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionBody {

    private String message;
    private String code;
}
