package com.mol21.cliente_deliveryrice.mvvm.model.DTO;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.mol21.cliente_deliveryrice.mvvm.model.Direccion;

public class DireccionDTO implements Parcelable {
    private long id;
    private String calle;
    private String numero;
    private String codPostal;
    private String ciudad;
    private boolean esPrincipal;
    private String emailUsuario;
    private boolean esActiva;

    protected DireccionDTO(Parcel in) {
        id = in.readLong();
        calle = in.readString();
        numero = in.readString();
        codPostal = in.readString();
        ciudad = in.readString();
        esPrincipal = in.readByte() != 0;
        emailUsuario = in.readString();
        esActiva = in.readByte() != 0;
    }

    public static final Creator<DireccionDTO> CREATOR = new Creator<DireccionDTO>() {
        @Override
        public DireccionDTO createFromParcel(Parcel in) {
            return new DireccionDTO(in);
        }

        @Override
        public DireccionDTO[] newArray(int size) {
            return new DireccionDTO[size];
        }
    };

    public boolean isEsActiva() {
        return esActiva;
    }

    public void setEsActiva(boolean esActiva) {
        this.esActiva = esActiva;
    }
//De momento dejo el email, pero no me sirve para nada


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String email) {
        this.emailUsuario = email;
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

    public DireccionDTO() {
    }

    public DireccionDTO(Direccion direccion) {
        this.id = direccion.getDireccion_id();
        this.calle = direccion.getCalle();
        this.numero = direccion.getNumero();
        this.ciudad = direccion.getCiudad();
        this.codPostal = direccion.getCodPostal();
        this.esPrincipal = direccion.isEsPrincipal();
        this.esActiva = true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(calle);
        parcel.writeString(numero);
        parcel.writeString(codPostal);
        parcel.writeString(ciudad);
        parcel.writeByte((byte) (esPrincipal ? 1 : 0));
        parcel.writeString(emailUsuario);
        parcel.writeByte((byte) (esActiva ? 1 : 0));
    }
}
