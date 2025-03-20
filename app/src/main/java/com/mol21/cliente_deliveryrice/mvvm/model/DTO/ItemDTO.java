package com.mol21.cliente_deliveryrice.mvvm.model.DTO;



import com.mol21.cliente_deliveryrice.mvvm.model.ItemCarrito;

import java.math.BigDecimal;

public class ItemDTO {
    //Información del ItemCarrito
    private long id;
    private int cantidad;
    private BigDecimal subTotal;

    //Información del Producto
    private long idProducto;
    private String nombreProducto;
    private BigDecimal precioUnitario;
    private String imagenUrl;
    private int stockDisponible;


    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", subTotal=" + subTotal +
                ", idProducto=" + idProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", imagenUrl='" + imagenUrl + '\'' +
                ", stockDisponible=" + stockDisponible +
                '}';
    }

    public ItemDTO() {
    }

    public ItemDTO (ItemCarrito item){
        this.id = item.getItem_id();
        this.nombreProducto = item.getProducto().getNombre();
        this.cantidad = item.getCantidad();
        this.subTotal = item.getSubTotal();
        this.precioUnitario = item.getProducto().getPrecio();
        this.imagenUrl = item.getProducto().getImagenUrl();
        this.idProducto = item.getProducto().getId_product();
        this.stockDisponible = item.getProducto().getStock()-cantidad;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subTotal= precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
