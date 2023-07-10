package com.example.pets_donation.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pets_donation.Adocao.VPAdapter;
import com.example.pets_donation.Adotante;
import com.example.pets_donation.R;
import com.google.android.material.tabs.TabLayout;

public class AdotanteAdocao_Fragment extends Fragment {

    //declaracao das variaveis
    private Adotante adotante;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private VPAdapter vpAdapter;

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.adotante = (Adotante) getActivity().getIntent().getSerializableExtra("adotante");
        Log.i("Tarefa 1 - status Adocao", "NOME: " + adotante.getNome());

        return inflater.inflate(R.layout.fragment_adotante_adocao_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewPager);
        vpAdapter = new VPAdapter(this);

        //adaptacao do TabLayout
        viewPager2.setAdapter(vpAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
        });

    }
}