package com.ittalent.desafio.inventario.exception;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(int cantidadDisponible, int cantidadRequerida) {
        super("Insufficient stock: disponible = " + cantidadDisponible + ", requerido = " + cantidadRequerida);
    }
}
