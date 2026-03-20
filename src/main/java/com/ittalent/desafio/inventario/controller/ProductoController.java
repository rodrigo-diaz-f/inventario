package com.ittalent.desafio.inventario.controller;

import com.ittalent.desafio.inventario.dto.DisminuirStockRequest;
import com.ittalent.desafio.inventario.dto.ProductoResponse;
import com.ittalent.desafio.inventario.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> getProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProducto(id));
    }

    @PostMapping("/{id}/disminuir-stock")
    public ResponseEntity<ProductoResponse> disminuirStock(
            @PathVariable Long id,
            @RequestBody DisminuirStockRequest request) {
        return ResponseEntity.ok(productoService.disminuirStock(id, request.getCantidad()));
    }
}
