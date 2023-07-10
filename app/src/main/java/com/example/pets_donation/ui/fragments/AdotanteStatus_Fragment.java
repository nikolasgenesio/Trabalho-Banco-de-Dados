package com.example.pets_donation.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pets_donation.Adocao_View;
import com.example.pets_donation.Adotante;
import com.example.pets_donation.AnimalAdocaoListAdapter;
import com.example.pets_donation.Models.ProcessoAdocaoDAO;
import com.example.pets_donation.ProcessoAdocao;
import com.example.pets_donation.R;

import java.util.List;

public class AdotanteStatus_Fragment extends Fragment {

    //declaracao das variaveis
    private Adotante adotante;
    private ListView listView;
    private ProcessoAdocaoDAO processoAdocaoDAO;
    private List<Adocao_View> processoAdocaoList;
    private AnimalAdocaoListAdapter animalAdocaoListAdapter;

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.adotante = (Adotante) getActivity().getIntent().getSerializableExtra("adotante");
        Log.i("Tarefa 1 - status Status", "NOME: " + adotante.getNome());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adotante_status_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.lista_adocoes);
        processoAdocaoDAO = new ProcessoAdocaoDAO(getActivity());
        processoAdocaoList = processoAdocaoDAO.retornaStatusAdocaoAdotante(adotante.getCpf());
        animalAdocaoListAdapter = new AnimalAdocaoListAdapter(getActivity(), R.layout.list_view_adocoes, processoAdocaoList);
        //ArrayAdapter<ProcessoAdocao> processoAdocaoArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, processoAdocaoList);
        //exibir a lista adaptada
        listView.setAdapter(animalAdocaoListAdapter);
    }
}