package com.mol21.cliente_deliveryrice.ui.listener;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;

public interface OnDireccionClickListener {
    void onEditarClick(DireccionDTO direccion);
    void onEliminarClick(DireccionDTO direccion);
    void onMarcarComoPrincipalClick(DireccionDTO direccion);
}
