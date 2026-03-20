package com.ittalent.desafio.inventario.service;

import com.ittalent.desafio.inventario.dto.ProductoResponse;

public interface ProductoService {

    ProductoResponse getProducto(Long productoId);

    ProductoResponse disminuirStock(Long productoId, int cantidad);
}
