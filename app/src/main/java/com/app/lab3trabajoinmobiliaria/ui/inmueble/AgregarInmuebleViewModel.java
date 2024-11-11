package com.app.lab3trabajoinmobiliaria.ui.inmueble;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.app.lab3trabajoinmobiliaria.R;
import com.app.lab3trabajoinmobiliaria.model.Inmueble;
import com.app.lab3trabajoinmobiliaria.model.LoginResponse;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AgregarInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mResultOk = new MutableLiveData<>();
    private final Context context;
    private Uri uri; // URI de la imagen seleccionada
    private MutableLiveData<Uri> Mavatar;

    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Uri> getUriMutable() {
        if (Mavatar == null) {
            Mavatar = new MutableLiveData<>();
        }
        return Mavatar;
    }
    public LiveData<Boolean> getMResult() {
        if ( mResultOk== null) {
            mResultOk = new MutableLiveData<>();
        }
        return mResultOk;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            uri = data.getData();
            if (uri == null) {
                Log.e("AgregarInmuebleViewModel", "URI de la imagen es nula");
            } else {
                Log.d("AgregarInmuebleViewModel", "URI de la imagen recibida: " + uri.toString());
            }
            Mavatar.setValue(uri);
        } else {
            Log.e("AgregarInmuebleViewModel", "Error al recibir la foto, resultCode: " + result.getResultCode());
        }
    }

    // Método para obtener la ruta real del archivo desde la URI
    public String getRealPathFromURI() {
        String result = null;
        if (uri != null) {
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        result = cursor.getString(column_index);
                        Log.d("AgregarInmuebleViewModel", "Ruta de la imagen: " + result);
                    } else {
                        Log.e("AgregarInmuebleViewModel", "No se pudo mover el cursor al primer resultado");
                    }
                    cursor.close();
                } else {
                    Log.e("AgregarInmuebleViewModel", "El cursor es nulo al consultar la URI");
                }
            } catch (Exception e) {
                Log.e("AgregarInmuebleViewModel", "Error al obtener la ruta desde la URI", e);
            }
        } else {
            Log.e("AgregarInmuebleViewModel", "URI es nula");
        }
        return result;
    }

    // Método para mapear el índice del tipo de uso al valor de uso correspondiente
    private String obtenerUsoPorIndice(int tipoIndice) {
        switch (tipoIndice) {
            case 0:
                return "Departamento";
            case 1:
                return "Casa";
            case 2:
                return "Depósito";
            case 3:
                return "Local";
            default:
                return "Otro";
        }
    }

    // Método para agregar inmueble, usando el índice para determinar el tipo de uso
    public void agregarInmueble(String direccion, String ambientes, int tipoIndice, double precio) {
        String uso = obtenerUsoPorIndice(tipoIndice);
        agregarInmueble(direccion, ambientes, tipoIndice + 1, precio, uso);
    }

    // Método privado que contiene la lógica de subida de datos a la API
    private void agregarInmueble(String direccion, String ambientes, int tipo, double precio, String uso) {
        String imagePath = getRealPathFromURI();
        ApiClient.InmobiliariaService api = ApiClient.getApi();

        if (direccion.isEmpty() || ambientes.isEmpty() || uso.isEmpty() || precio == 0 || tipo == 0) {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            Log.e("AgregarInmuebleViewModel", "Campos incompletos");
            return;
        }

        MultipartBody.Part imagen = null;
        if (imagePath != null) {
            File file = new File(imagePath);
            if (!file.exists()) {
                Log.e("AgregarInmuebleViewModel", "El archivo de imagen no existe: " + imagePath);
            } else {
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
                imagen = MultipartBody.Part.createFormData("imagen", file.getName(), requestFile);
            }
        } else {
            Log.w("AgregarInmuebleViewModel", "La ruta de la imagen es nula. El inmueble será creado sin imagen.");
        }

        RequestBody direccionBody = RequestBody.create(MediaType.parse("text/plain"), direccion);
        RequestBody ambientesBody = RequestBody.create(MediaType.parse("text/plain"), ambientes);
        RequestBody tipoBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tipo));
        RequestBody precioBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(precio));
        RequestBody usoBody = RequestBody.create(MediaType.parse("text/plain"), uso);

        Call<ResponseBody> call = api.agregarInmueble(
                TokenShared.obtenerToken(context),
                direccionBody, ambientesBody, tipoBody, precioBody, usoBody, imagen
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Inmueble creado con éxito", Toast.LENGTH_SHORT).show();
                    Log.d("AgregarInmuebleViewModel", "Inmueble creado exitosamente: " + response.body());

                    mResultOk.postValue(true);
                } else {
                    String errorMessage = "Error al crear el inmueble: " + response.message() + ", código: " + response.code();
                    Log.e("AgregarInmuebleViewModel", errorMessage);
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                    mResultOk.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("AgregarInmuebleViewModel", "Fallo al conectar con el servidor", t);
                Toast.makeText(context, "Error al conectar con el servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                mResultOk.postValue(false);
            }
        });
    }
}








