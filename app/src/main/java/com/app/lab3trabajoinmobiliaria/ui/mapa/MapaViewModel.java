package com.app.lab3trabajoinmobiliaria.ui.mapa;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaViewModel extends AndroidViewModel {
    private MutableLiveData<Mapa> mMap;

    public MapaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Mapa> getMMap(){
        if(mMap == null){
            mMap = new MutableLiveData<>();
        }
        return mMap;
    }

    public void obtenerMapa(){
        Mapa m = new Mapa();
        mMap.setValue(m);
    }

    public class Mapa implements OnMapReadyCallback {

        LatLng Inmobiliaria = new LatLng(-33.150720, -66.306864);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


            googleMap.addMarker(new MarkerOptions().position(Inmobiliaria).title("Inmobiliaria").snippet("La mejor universidad"));


            float zoomLevel = 15.0f;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Inmobiliaria, zoomLevel));
        }
    }
}
