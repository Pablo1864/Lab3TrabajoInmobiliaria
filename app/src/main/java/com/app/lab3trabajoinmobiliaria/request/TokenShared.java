package com.app.lab3trabajoinmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.app.lab3trabajoinmobiliaria.model.Propietario;

public class TokenShared {

    private static SharedPreferences sp;
    private static final String TOKEN_KEY = "auth_token";
    private static final String NOMBRE_KEY = "propietario_nombre";
    private static final String APELLIDO_KEY = "propietario_apellido";
    private static final String EMAIL_KEY = "propietario_email";
    private static final String AVATAR_KEY = "propietario_avatar"; // Si manejas avatares

    // Método para conectar con SharedPreferences
    private static SharedPreferences conectar(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        }
        return sp;
    }

    // Guardar el token
    public static void guardar(Context context, String token) {
        SharedPreferences sp = conectar(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TOKEN_KEY, "Bearer " + token);
        editor.apply();
    }

    // Obtener el token
    public static String obtenerToken(Context context) {
        SharedPreferences sp = conectar(context);
        return sp.getString(TOKEN_KEY, null);
    }

    public static void guardarPropietario(Context context, Propietario propietario) {
        SharedPreferences sp = conectar(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", propietario.getId());
        editor.putString("nombre", propietario.getNombre());
        editor.putString("apellido", propietario.getApellido());
        editor.putString("email", propietario.getEmail());
        editor.putString("telefono", propietario.getTelefono());
        editor.putString("domicilio", propietario.getDomicilio());
        editor.putString("dni", propietario.getDni());

        editor.apply(); // Usar apply para guardar de forma asíncrona

        // Log para verificar los datos guardados
        Log.d("TokenShared", "Propietario guardado: " + propietario.toString());
    }


    public static Propietario leerPropietario(Context context) {
        SharedPreferences sp = conectar(context);

        // Asegúrate de que las claves coincidan con las que usas en guardarPropietario
        int id = sp.getInt("id", 0);
        String nombre = sp.getString("nombre", null);
        String apellido = sp.getString("apellido", null);
        String email = sp.getString("email", null);
        String telefono = sp.getString("telefono", null);
        String domicilio = sp.getString("domicilio", null);
        String dni = sp.getString("dni", null);

        // Verificar si los valores son null
        if (nombre == null || apellido == null || email == null || telefono == null || domicilio == null || dni == null) {
            Log.d("TokenShared", "Algunos datos del propietario no se encontraron en SharedPreferences.");
        }

        Propietario propietario = new Propietario(id, nombre, apellido, email, telefono, domicilio, dni);

        // Log para verificar los datos leídos
        Log.d("TokenShared", "Propietario leído: " + propietario.toString());

        return propietario;
    }


    // Eliminar todos los datos (logout)
    public static void eliminarDatos(Context context) {
        SharedPreferences sp = conectar(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(TOKEN_KEY);
        editor.remove(NOMBRE_KEY);
        editor.remove(APELLIDO_KEY);
        editor.remove(EMAIL_KEY);
        editor.remove(AVATAR_KEY); // Si guardas un avatar
        editor.apply();
    }
    public static void limpiarDatos(Context context) {
        SharedPreferences sp = conectar(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();  // Limpia todos los datos almacenados
        editor.apply();
    }


}

