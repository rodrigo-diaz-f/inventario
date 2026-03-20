package com.ittalent.desafio.inventario.util;

public enum ErroresEnum {
    PRODUCTO_NO_ENCONTRADO("PRODUCTO_NO_ENCONTRADO"),
    STOCK_INSUFICIENTE("STOCK_INSUFICIENTE"),
    LIMITE_PECITIONES_EXCEDIDO("LIMITE_PECITIONES_EXCEDIDO"),
    CONFLICTO_ACTUALIZACION_EN_CURSO("CONFLICTO_ACTUALIZACION_EN_CURSO"),
    REQUEST_INVALIDO("REQUEST_INVALIDO"),
    ERROR_GENERAL("ERROR_GENERAL");

    private String mensaje;

    private ErroresEnum(String mensaje) {}
    public String getMensaje() {
        return this.mensaje;
    }
}
