package com.ittalent.desafio.inventario.service;

import com.ittalent.desafio.inventario.dto.ProductoResponse;
import com.ittalent.desafio.inventario.entity.Producto;
import com.ittalent.desafio.inventario.exception.StockInsuficienteException;
import com.ittalent.desafio.inventario.exception.ProductoNoEncontradoException;
import com.ittalent.desafio.inventario.repository.ProductoRepository;
import com.ittalent.desafio.inventario.util.AppConstantes;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public ProductoResponse getProducto(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));
        return ProductoResponse.from(producto);
    }

    @Override
    @Transactional
    public ProductoResponse disminuirStock(Long productoId, int cantidad) {
        if (cantidad < 1) {
            throw new IllegalArgumentException("cantidad a disminuir debe ser al menos 1, pero es: " + cantidad);
        }

        int intentos = 0;
        while (true) {
            try {
                Producto producto = productoRepository.findById(productoId)
                        .orElseThrow(() -> new ProductoNoEncontradoException(productoId));

                if (producto.getStock() < cantidad) {
                    throw new StockInsuficienteException(producto.getStock(), cantidad);
                }

                producto.setStock(producto.getStock() - cantidad);

                // Usando el metodo "saveAndFlush" forzamos a chequear la version dentro de la
                // transacción misma, lo cual lanza la excepción ObjectOptimisticLockingFailureException
                // si hay un conflicto de concurrencia
                Producto productoActualizado = productoRepository.saveAndFlush(producto);
                return ProductoResponse.from(productoActualizado);

            } catch (ObjectOptimisticLockingFailureException ex) {
                if (++intentos >= AppConstantes.MAXIMOS_INTENTOS) {
                    throw ex;
                }
                try {
                    Thread.sleep(50L * intentos);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrumpido al reintentar", ie);
                }
            }
        }
    }
}
