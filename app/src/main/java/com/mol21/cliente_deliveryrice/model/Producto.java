package com.mol21.cliente_deliveryrice.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Producto {
    private long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;
    private String imagenUrl;
    private CategoriaProducto categoriaProducto;

    private Set<ItemCarrito> itemsCarrito = new HashSet<>();

    private Set<DetallePedido> detallesPedidos = new HashSet<>();

    public long getId_product() {
        return id;
    }

    public void setId_product(long id_product) {
        this.id = id_product;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public Set<ItemCarrito> getItemsCarrito() {
        return itemsCarrito;
    }

    public void setItemsCarrito(Set<ItemCarrito> itemsCarrito) {
        this.itemsCarrito = itemsCarrito;
    }

    public Set<DetallePedido> getDetallesPedidos() {
        return detallesPedidos;
    }

    public void setDetallesPedidos(Set<DetallePedido> detallesPedidos) {
        this.detallesPedidos = detallesPedidos;
    }
}
