package com.app.lab3trabajoinmobiliaria.request;


import com.app.lab3trabajoinmobiliaria.model.CambiarClave;
import com.app.lab3trabajoinmobiliaria.model.Contrato;
import com.app.lab3trabajoinmobiliaria.model.Inmueble;
import com.app.lab3trabajoinmobiliaria.model.Inquilino;
import com.app.lab3trabajoinmobiliaria.model.Login;
import com.app.lab3trabajoinmobiliaria.model.LoginResponse;
import com.app.lab3trabajoinmobiliaria.model.Pagos;
import com.app.lab3trabajoinmobiliaria.model.Propietario;
import com.app.lab3trabajoinmobiliaria.model.RecuperarContrasena;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {

    public static final String API_BASE_URL = "http://192.168.1.4:5000/api/";
    public static final String URLBASEIMG = "http://192.168.1.4:5000/";

    public static InmobiliariaService getApi(){
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(InmobiliariaService.class);
    }

    public interface InmobiliariaService {
        @POST("Propietarios/login")
        Call<LoginResponse> login(@Body Login login);
        @PUT("Propietarios/editarPerfil")
        Call<Propietario> updatePropietario(@Header("Authorization") String token, @Body Propietario propietario);
        @PUT("Propietarios/cambiarClave")
        Call<ResponseBody> cambiarClave(@Header("Authorization") String token,@Body CambiarClave request);
        @FormUrlEncoded
        @POST("Propietarios/solicitarRecuperacion")
        Call<ResponseBody> enviarCorreoRecuperacion(@Field("email") String email);
        @POST("Propietarios/restablecerContrasena")
        Call<ResponseBody> restablecerContrasena(@Body RecuperarContrasena request);

        @GET("inmuebles/misInmuebles")
        Call<ArrayList<Inmueble>> getMisInmuebles(@Header("Authorization") String token);
        @PUT("Inmuebles/actualizarEstado/{idInmueble}")
        Call<ResponseBody> actualizarEstado(@Header("Authorization") String token,@Path("idInmueble") int idInmueble, @Body String nuevoEstado);

        @POST("inmuebles/agregarInmueble")
        @Multipart
        Call<ResponseBody> agregarInmueble(
                @Header("Authorization") String token,
                @Part("direccion") RequestBody direccion,
                @Part("ambientes") RequestBody ambientes,
                @Part("idTipoInmueble") RequestBody idTipoInmueble,
                @Part("precio") RequestBody precio,
                @Part("uso") RequestBody uso,
                @Part MultipartBody.Part imagen
        );
        @GET("Inquilinos/alquilados")
        Call<ArrayList<Inmueble>> getInmueblesAlquilados(@Header("Authorization") String token);

        @GET("Inquilinos/{idInmueble}")
        Call<Inquilino> getInquilino(@Header("Authorization") String token, @Path("idInmueble") int idInmueble);

        @GET("Contratos/inmuebles-alquilados")
        Call<ArrayList<Contrato>> obtenerInmueblesAlquilados(@Header("Authorization") String token
        );

        // Endpoint para obtener el detalle de un contrato específico
        @GET("Contratos/contrato-detalle/{id}")
        Call<Contrato> obtenerContratoDetalle(@Header("Authorization") String token, @Path("id") int contratoId
        );
        @GET("Pagos/ListarPagos/{idContrato}")
        Call<ArrayList<Pagos>> obtenerPagos(@Header("Authorization") String token, @Path("idContrato") int contratoId);



    }

    }



