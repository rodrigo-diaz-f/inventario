package com.ittalent.desafio.inventario.exception;

public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Long id) {
        super("Producto no encontrado con el id: " + id);
    }
}
