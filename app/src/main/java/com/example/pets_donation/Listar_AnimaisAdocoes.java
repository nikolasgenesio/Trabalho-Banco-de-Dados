package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pets_donation.Models.AdocoesDAO;

import java.util.ArrayList;
import java.util.List;

public class Listar_AnimaisAdocoes extends AppCompatActivity {

    private ListView listView1, listView2;
    private AdocoesDAO adocoesDAO;
    private List<Adocoes> adocoesList;
    private List<Adocoes> adocoesListFiltrados = new ArrayList<>();
    private Funcionario funcionario;
    private AdocaoDeferidaAdapter adocaoDeferidaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_animais_adocoes);

        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        listView1 = findViewById(R.id.lista_animaisAdotados);
        listView2 = findViewById(R.id.lista_animaisNaoAdotados);
        adocoesDAO = new AdocoesDAO(this);
        adocoesList = adocoesDAO.obterTodosAnimaisAdotados();
        adocoesListFiltrados.addAll(adocoesList);

        adocaoDeferidaAdapter = new AdocaoDeferidaAdapter(this, R.layout.list_view_animal, adocoesList, funcionario);

        //exibir lista
        //ArrayAdapter<Adocoes> adocoesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adocoesList);
        listView1.setAdapter(adocaoDeferidaAdapter);

    }
}