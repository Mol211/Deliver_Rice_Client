package com.mol21.cliente_deliveryrice.ui.listener;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.MetodoPago;

public interface OnItemSelectedListener {
    void onDireccionSeleccionada(DireccionDTO direccion);
    void onMetodoSeleccionado(MetodoPago metodo);
}
