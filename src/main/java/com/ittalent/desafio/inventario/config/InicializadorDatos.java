package com.ittalent.desafio.inventario.config;

import com.ittalent.desafio.inventario.entity.Producto;
import com.ittalent.desafio.inventario.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InicializadorDatos implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) {
        if (productoRepository.count() > 0) {
            return;
        }

        productoRepository.saveAll(List.of(
                Producto.builder()
                        .nombre("Mesa de escritorio blanca")
                        .codigo("SKU-MESCRB")
                        .stock(50)
                        .precio(250000)
                        .build(),
                Producto.builder()
                        .nombre("Cama de 2 plazas gris")
                        .codigo("SKU-CAM2PG")
                        .stock(200)
                        .precio(400000)
                        .build(),
                Producto.builder()
                        .nombre("Sofa 3 cuerpos de felpa")
                        .codigo("SKU-SOFA3CF")
                        .stock(0)
                        .precio(700000)
                        .build()
        ));
    }
}
