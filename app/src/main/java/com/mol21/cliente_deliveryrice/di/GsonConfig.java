package com.mol21.cliente_deliveryrice.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mol21.cliente_deliveryrice.utils.LocalDateTimeAdapter;

import java.time.LocalDateTime;

public class GsonConfig {

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }
}
