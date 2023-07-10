package com.example.pets_donation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pets_donation.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AdotanteAbrigos_Fragment extends Fragment {

    //declaracao da variavel
    GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_adotante_abrigos_, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
        getChildFragmentManager().findFragmentById(R.id.map);

        //aparecer no Google Maps
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                map = googleMap;

                LatLng ongSJPA = new LatLng(-21.75065, -43.44451);
                map.addMarker(new MarkerOptions().position(ongSJPA).title("ONG SJPA\n"));

                CameraPosition cameraPosition = new CameraPosition.Builder().zoom(12).target(ongSJPA).build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                LatLng ongSJPA1 = new LatLng(-21.74113, -43.44601);
                map.addMarker(new MarkerOptions().position(ongSJPA1).title("Canil Da Sociedade Protetora Dos Animais De Juiz De Fora (SJPA)\n"));

                LatLng canilMunicipal = new LatLng(-21.69956, -43.44675);
                map.addMarker(new MarkerOptions().position(canilMunicipal).title("Canil Municipal de Juiz de Fora\n\n"));
            }
        });

        return view;
    }

}