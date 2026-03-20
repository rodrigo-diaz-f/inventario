package com.ittalent.desafio.inventario.dto;

import com.ittalent.desafio.inventario.entity.Producto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProductoResponse {

    private Long id;
    private String nombre;
    private String codigo;
    private Integer stock;
    private Integer precio;

    public static ProductoResponse from(Producto producto) {
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .codigo(producto.getCodigo())
                .stock(producto.getStock())
                .precio(producto.getPrecio())
                .build();
    }
}
