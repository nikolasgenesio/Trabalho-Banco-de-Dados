package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Listar_Adotantes extends AppCompatActivity {

    //declarando as variaveis
    private ListView listView;
    private AdotanteDAO adotanteDAO;
    private List<Adotante> adotanteList;
    private List<Adotante> adotanteListFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_adotantes);

        listView = findViewById(R.id.lista_adotantes);
        adotanteDAO = new AdotanteDAO(this);
        adotanteList = adotanteDAO.obterTodosAdotantes();
        adotanteListFiltrados.addAll(adotanteList);

        //exibir lista
        ArrayAdapter<Adotante> adotanteArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adotanteList);
        listView.setAdapter(adotanteArrayAdapter);

    }
}