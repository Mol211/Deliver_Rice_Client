package com.mol21.cliente_deliveryrice.mvvm.model;



import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Usuario {

    private long id;
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private String telefono;
    LocalDateTime fechaCreacion;
    private Rol rol;


    private Set<Direccion> direccionesUsuario = new HashSet<>();

    private Set<Pedido> pedidos = new HashSet<>();

    public long get_id() {
        return id;
    }

    public void set_id(long id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<Direccion> getDireccionesUsuario() {
        return direccionesUsuario;
    }

    public void setDireccionesUsuario(Set<Direccion> direccionesUsuario) {
        this.direccionesUsuario = direccionesUsuario;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
