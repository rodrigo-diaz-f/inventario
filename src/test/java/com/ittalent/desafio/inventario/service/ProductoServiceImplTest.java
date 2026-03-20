package com.ittalent.desafio.inventario.service;

import com.ittalent.desafio.inventario.dto.ProductoResponse;
import com.ittalent.desafio.inventario.entity.Producto;
import com.ittalent.desafio.inventario.exception.ProductoNoEncontradoException;
import com.ittalent.desafio.inventario.exception.StockInsuficienteException;
import com.ittalent.desafio.inventario.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto buildProducto(int stock) {
        return Producto.builder()
                .id(1L)
                .nombre("Producto de prueba")
                .codigo("SKU-PRODPRUEBA")
                .stock(stock)
                .precio(999)
                .build();
    }

    // ── decreaseStock tests ──────────────────────────────────────────────────

    @Test
    void cuandoDisminuirStockEsOk_devolverStockActualizado() {
        Producto producto = buildProducto(10);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.saveAndFlush(any())).thenAnswer(inv -> inv.getArgument(0));

        ProductoResponse response = productoService.disminuirStock(1L, 3);

        assertThat(response.getStock()).isEqualTo(7);
        verify(productoRepository).saveAndFlush(argThat(p -> p.getStock() == 7));
    }

    @Test
    void cuandoDisminuirElStockExistenteEnLaMismaCantidad_devolverStockEnCero() {
        Producto producto = buildProducto(5);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.saveAndFlush(any())).thenAnswer(inv -> inv.getArgument(0));

        ProductoResponse response = productoService.disminuirStock(1L, 5);

        assertThat(response.getStock()).isEqualTo(0);
        verify(productoRepository).saveAndFlush(argThat(p -> p.getStock() == 0));
    }

    @Test
    void cuandoDisminuirStockDeProductoInexistente_lanzarProductoNoEncontradoException() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoService.disminuirStock(99L, 1))
                .isInstanceOf(ProductoNoEncontradoException.class)
                .hasMessageContaining("99");

        verify(productoRepository, never()).saveAndFlush(any());
    }

    @Test
    void cuandoDisminuirStockEnCantidadMayorAExistente_lanzarStockInsuficienteException() {
        Producto producto = buildProducto(3);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        assertThatThrownBy(() -> productoService.disminuirStock(1L, 10))
                .isInstanceOf(StockInsuficienteException.class)
                .hasMessageContaining("disponible = 3")
                .hasMessageContaining("requerido = 10");

        verify(productoRepository, never()).saveAndFlush(any());
    }

    @Test
    void cuandoDisminuirStockConCantidadCero_lanzarIllegalArgumentException() {
        assertThatThrownBy(() -> productoService.disminuirStock(1L, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cantidad a disminuir debe ser al menos 1");

        verify(productoRepository, never()).findById(any());
    }

    // ── getProduct tests ─────────────────────────────────────────────────────

    @Test
    void cuandoGetProductoOK_devolverProductoResponse() {
        Producto producto = buildProducto(789);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        ProductoResponse response = productoService.getProducto(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNombre()).isEqualTo("Producto de prueba");
        assertThat(response.getCodigo()).isEqualTo("SKU-PRODPRUEBA");
        assertThat(response.getStock()).isEqualTo(789);
        assertThat(response.getPrecio()).isEqualByComparingTo(999);
    }

    @Test
    void cuandoGetProductoNoEsEncontrado_lanzarProductoNoEncontradoException() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoService.getProducto(99L))
                .isInstanceOf(ProductoNoEncontradoException.class)
                .hasMessageContaining("99");
    }
}
