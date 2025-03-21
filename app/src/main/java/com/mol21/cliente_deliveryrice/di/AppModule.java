package com.mol21.cliente_deliveryrice.di;

import com.mol21.cliente_deliveryrice.mvvm.api.CarritoApi;
import com.mol21.cliente_deliveryrice.mvvm.api.DireccionApi;
import com.mol21.cliente_deliveryrice.mvvm.api.PedidoApi;
import com.mol21.cliente_deliveryrice.mvvm.api.ProductoApi;
import com.mol21.cliente_deliveryrice.mvvm.api.UsuarioApi;
import com.mol21.cliente_deliveryrice.mvvm.repository.CarritoRepository;
import com.mol21.cliente_deliveryrice.mvvm.repository.DireccionRepository;
import com.mol21.cliente_deliveryrice.mvvm.repository.PedidoRepository;
import com.mol21.cliente_deliveryrice.mvvm.repository.ProductoRepository;
import com.mol21.cliente_deliveryrice.mvvm.repository.UsuarioRepository;

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

    @Provides
    public static CarritoApi provideCarritoApi(Retrofit retrofit){
        return retrofit.create(CarritoApi.class);
    }
    @Provides
    public CarritoRepository provideCarritoRepository(CarritoApi carritoApi) {
        return new CarritoRepository(carritoApi);
    }
    @Provides
    public static DireccionApi provideDireccionApi(Retrofit retrofit){
        return retrofit.create(DireccionApi.class);
    }
    @Provides
    public DireccionRepository provideDireccionRepository(DireccionApi direccionApi) {
        return new DireccionRepository(direccionApi);
    }
    @Provides
    public static PedidoApi providePedidoApi(Retrofit retrofit){
        return retrofit.create(PedidoApi.class);
    }
    @Provides
    public PedidoRepository providePedidoRepository(PedidoApi pedidoApi){
        return new PedidoRepository(pedidoApi);
    }


    private class Builder {
    }
}
