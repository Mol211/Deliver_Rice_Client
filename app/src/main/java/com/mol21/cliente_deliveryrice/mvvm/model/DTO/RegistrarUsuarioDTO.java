package com.mol21.cliente_deliveryrice.mvvm.model.DTO;

import com.mol21.cliente_deliveryrice.mvvm.model.Direccion;
import com.mol21.cliente_deliveryrice.mvvm.model.Usuario;

public class RegistrarUsuarioDTO {
    private Usuario u;
    private Direccion d;

    public RegistrarUsuarioDTO(Usuario u, Direccion d) {
        this.u = u;
        this.d = d;
    }

    public Usuario getU() {
        return u;
    }

    public void setU(Usuario u) {
        this.u = u;
    }

    public Direccion getD() {
        return d;
    }

    public void setD(Direccion d) {
        this.d = d;
    }
}
