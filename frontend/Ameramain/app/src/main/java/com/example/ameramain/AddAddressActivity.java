package com.example.ameramain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ameramain.R; // не забудь проверить импорт
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddAddressActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker currentMarker; // будем хранить маркер, чтобы перемещать его

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaddress); // Замени на название твоего layout

        // Получаем фрагмент карты
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment); // ID из XML

        if (mapFragment != null) {
            mapFragment.getMapAsync(this); // Подключаем слушателя готовности карты
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Установим начальную точку (например, Алматы)
        LatLng initialLocation = new LatLng(43.238949, 76.889709);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 16f));

        // Добавим начальный маркер
        currentMarker = mMap.addMarker(new MarkerOptions().position(initialLocation).title("Selected Location"));

        // Обработка кликов по карте
        mMap.setOnMapClickListener(latLng -> {
            // Переместим маркер на новую точку
            if (currentMarker != null) {
                currentMarker.setPosition(latLng);
            } else {
                currentMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("New Location"));
            }
        });
    }
}
