package com.mol21.cliente_deliveryrice.model;



import java.time.LocalDateTime;
import java.util.List;

public class Direccion {
    private Long id;
    private String calle;
    private String numero;
    private String codPostal;
    private String ciudad;
    private boolean esPrincipal;
    LocalDateTime fechaCreacion;
    private Usuario usuario;
    private List<Pedido> lista_pedidos;
    public Long getDireccion_id() {
        return id;
    }
    public void setDireccion_id(Long id) {
        this.id = id;
    }
    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getCodPostal() {
        return codPostal;
    }
    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public boolean isEsPrincipal() {
        return esPrincipal;
    }
    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
