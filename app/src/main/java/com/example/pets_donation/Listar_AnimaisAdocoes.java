package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pets_donation.Models.AdocoesDAO;

import java.util.ArrayList;
import java.util.List;

public class Listar_AnimaisAdocoes extends AppCompatActivity {

    private ListView listView;
    private AdocoesDAO adocoesDAO;
    private List<Adocoes> adocoesList;
    private List<Adocoes> adocoesListFiltrados = new ArrayList<>();
    private Funcionario funcionario;
    private AnimalListAdapter animalListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_animais_adocoes);

        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        listView = findViewById(R.id.lista_animais);
        adocoesDAO = new AdocoesDAO(this);
        adocoesList = adocoesDAO.obterTodosAnimaisAdotados();
        adocoesListFiltrados.addAll(adocoesList);

        //exibir lista
        ArrayAdapter<Adocoes> adocoesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adocoesList);
        listView.setAdapter(adocoesArrayAdapter);
    }
}