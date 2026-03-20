package com.ittalent.desafio.inventario.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ErrorResponse {

    private int status;
    private String error;
    private String mensaje;
    private String uri;
}
