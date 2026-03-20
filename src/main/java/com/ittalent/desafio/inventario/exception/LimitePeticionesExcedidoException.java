package com.ittalent.desafio.inventario.exception;

import com.ittalent.desafio.inventario.util.AppConstantes;

public class LimitePeticionesExcedidoException extends RuntimeException {

    public LimitePeticionesExcedidoException(String ip) {
        super("Limite de peticiones excedido para la ip: " + ip + ". Máximo de " + AppConstantes.MAXIMAS_PETICIONES + " peticiones por minuto");
    }
}
