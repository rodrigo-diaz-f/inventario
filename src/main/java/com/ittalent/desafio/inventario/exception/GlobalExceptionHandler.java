package com.ittalent.desafio.inventario.exception;

import com.ittalent.desafio.inventario.dto.ErrorResponse;
import com.ittalent.desafio.inventario.util.ErroresEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleNoEncontrado(ProductoNoEncontradoException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ErroresEnum.PRODUCTO_NO_ENCONTRADO.getMensaje(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<ErrorResponse> handleStockInsuficiente(StockInsuficienteException ex, HttpServletRequest request) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ErroresEnum.STOCK_INSUFICIENTE.getMensaje(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(LimitePeticionesExcedidoException.class)
    public ResponseEntity<ErrorResponse> handleLimitePeticionesExcedido(LimitePeticionesExcedidoException ex, HttpServletRequest request) {
        return build(HttpStatus.TOO_MANY_REQUESTS, ErroresEnum.LIMITE_PECITIONES_EXCEDIDO.getMensaje(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLock(ObjectOptimisticLockingFailureException ex, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, ErroresEnum.CONFLICTO_ACTUALIZACION_EN_CURSO.getMensaje(),
                "La peticion no pudo ser completada debido a una actualización ya en curso. Intente nuevamente.", request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, ErroresEnum.REQUEST_INVALIDO.getMensaje(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ErroresEnum.ERROR_GENERAL.getMensaje(),
                "Un error inesperado ha ocurrido.", request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String error, String mensaje, String uri) {
        ErrorResponse body = ErrorResponse.builder()
                .status(status.value())
                .error(error)
                .mensaje(mensaje)
                .uri(uri)
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
