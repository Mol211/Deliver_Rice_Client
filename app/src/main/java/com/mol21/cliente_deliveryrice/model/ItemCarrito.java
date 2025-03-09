package com.mol21.cliente_deliveryrice.model;

import java.math.BigDecimal;

public class ItemCarrito {
    private long id;
    private int cantidad;
    private BigDecimal subTotal;
    private Carrito carrito;
    private Producto producto;
    public ItemCarrito(int cantidad, Carrito carrito, Producto producto) {
        this.cantidad=cantidad;
        this.producto= producto;
        this.carrito = carrito;
        this.subTotal = producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
    }
    public ItemCarrito() {
    }
    public long getItem_id() {
        return id;
    }
    public void setItem_id(long id) {
        this.id = id;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subTotal= producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
    }
    public BigDecimal getSubTotal() {
        return subTotal;
    }
    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    public Carrito getCarrito() {
        return carrito;
    }
    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
