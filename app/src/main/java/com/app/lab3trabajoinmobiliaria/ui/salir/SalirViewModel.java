package com.app.lab3trabajoinmobiliaria.ui.salir;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SalirViewModel extends AndroidViewModel {
    public SalirViewModel(@NonNull Application application) {
        super(application);
    }


    public static void Salir(Activity activity){

        new AlertDialog.Builder(activity)
                .setTitle("Salir de la aplicacion")
                .setMessage("Esta seguro que quiere salir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SharedPreferences shared = activity.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared.edit();

                        editor.putString("token", "");
                        editor.commit();
                        activity.finishAndRemoveTask();
                        activity.finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(activity,"Continua la aplicacion", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}