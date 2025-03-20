package com.mol21.cliente_deliveryrice.mvvm.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private long id;
    private LocalDateTime fechaCreacion;
    private BigDecimal totalPrecio;
    private int totalProductos;
    private Usuario usuario;
    private List<ItemCarrito> items = new ArrayList<>();
    private boolean procesado ;
    private Pedido pedido; // Relación con Pedido
    public boolean isProcesado() {
        return procesado;
    }
    public void setProcesado(boolean procesado) {
        this.procesado = procesado;
    }
    public long getCarrito_id() {
        return id;
    }
    public void setCarrito_id(long id) {
        this.id = id;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public BigDecimal getTotalPrecio() {
        return totalPrecio;
    }
    public void setTotalPrecio(BigDecimal totalPrecio) {
        this.totalPrecio = totalPrecio;
    }
    public int getTotalProductos() {
        return totalProductos;
    }
    public void setTotalProductos(int totalProductos) {
        this.totalProductos = totalProductos;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public List<ItemCarrito> Items() {
        return items;
    }
    public void setItemCarritoList(List<ItemCarrito> items) {
        this.items = items;
    }
}
