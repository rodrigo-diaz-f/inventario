package com.ittalent.desafio.inventario.repository;

import com.ittalent.desafio.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
