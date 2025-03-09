package com.mol21.cliente_deliveryrice.di;

import com.mol21.cliente_deliveryrice.api.ProductoApi;
import com.mol21.cliente_deliveryrice.api.UsuarioApi;
import com.mol21.cliente_deliveryrice.repository.ProductoRepository;
import com.mol21.cliente_deliveryrice.repository.UsuarioRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    public static OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder.build();
    }

    @Provides
    public static Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9090/")
                .addConverterFactory(GsonConverterFactory.create(GsonConfig.getGson()))
                .client(okHttpClient)
                .build();
    }


    @Provides
    public static UsuarioApi provideUsuarioApi(Retrofit retrofit){
        return retrofit.create(UsuarioApi.class);
    }

    @Provides
    public UsuarioRepository provideUsuarioRepository(UsuarioApi usuarioApi) {
        return new UsuarioRepository(usuarioApi);
    }
    @Provides
    public static ProductoApi provideProductoApi(Retrofit retrofit){
        return retrofit.create(ProductoApi.class);
    }
    @Provides
    public ProductoRepository provideProductoRepository(ProductoApi productoApi) {
        return new ProductoRepository(productoApi);
    }


    private class Builder {
    }
}
