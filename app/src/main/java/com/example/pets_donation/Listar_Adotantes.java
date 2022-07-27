package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pets_donation.Models.AdotanteDAO;

import java.util.ArrayList;
import java.util.List;

public class Listar_Adotantes extends AppCompatActivity {

    //declarando as variaveis
    private ListView listView;
    private AdotanteDAO adotanteDAO;
    private List<Adotante> adotanteList;
    private List<Adotante> adotanteListFiltrados = new ArrayList<>();
    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_adotantes);

        getSupportActionBar().setTitle("Adotantes");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        listView = findViewById(R.id.lista_adotantes);
        adotanteDAO = new AdotanteDAO(this);
        adotanteList = adotanteDAO.obterTodosAdotantes();
        adotanteListFiltrados.addAll(adotanteList);

        //exibir lista
        ArrayAdapter<Adotante> adotanteArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adotanteList);
        listView.setAdapter(adotanteArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Adotante adotante = (Adotante) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), GerenciaAdotantes_Dados.class);
                intent.putExtra("funcionario", funcionario);
                intent.putExtra("adotante", adotante);
                startActivity(intent);
                finish();
            }
        });
    }
}