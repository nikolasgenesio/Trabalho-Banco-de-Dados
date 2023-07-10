package com.example.pets_donation.Adocao;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pets_donation.Adotante;
import com.example.pets_donation.Animal;
import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.AnimalDAO;
import com.example.pets_donation.R;
import com.example.pets_donation.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class fragment1Adocao extends Fragment {

    //declaracao das variaveis
    private Adotante adotante;
    private RecyclerView recyclerView;
    private List<Animal> animalList;
    private AnimalDAO animalDAO;
    private List<Animal> animalListFiltrados = new ArrayList<>();
    private Conexao_Banco banco;
    private TextView textView;

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        this.adotante = (Adotante) getActivity().getIntent().getSerializableExtra("adotante");
        Log.i("Tarefa 1 - status Adocao", "NOME: " + adotante.getNome());

        return inflater.inflate(R.layout.fragment_fragment1_adocao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        textView = view.findViewById(R.id.msgCachorros);

        banco = new Conexao_Banco(getActivity());
        animalList = banco.animaisAdocao();

        //exibir somente cachorros
        for (Animal animal : animalList) {
            if (animal.getTipo().equals("Cachorro")) {
                animalListFiltrados.add(animal);
            }
        }

        if (animalListFiltrados != null && animalListFiltrados.isEmpty())
            textView.setVisibility(View.VISIBLE);
        else
            initRecyclerView();

    }

    /**
     * Funcao para exibir os cachorros em uma lista adaptada
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), animalListFiltrados, adotante);
        recyclerView.setAdapter(adapter);
    }
}