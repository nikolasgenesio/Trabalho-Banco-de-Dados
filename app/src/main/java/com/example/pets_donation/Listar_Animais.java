package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pets_donation.Models.AnimalDAO;

import java.util.ArrayList;
import java.util.List;

public class Listar_Animais extends AppCompatActivity {

    //declarando as variaveis
    private ListView listView;
    private AnimalDAO animalDAO;
    private List<Animal> animalList;
    private List<Animal> animalListFiltrados = new ArrayList<>();
    private Funcionario funcionario;
    private AnimalListAdapter animalListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_animais);

        getSupportActionBar().setTitle("Animais");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        listView = findViewById(R.id.lista_animais);
        animalDAO = new AnimalDAO(this);
        animalList = animalDAO.obterTodosAnimais();
        animalListFiltrados.addAll(animalList);

        animalListAdapter = new AnimalListAdapter(this, R.layout.list_view_animal, animalList, funcionario);

        //exibir lista
        ArrayAdapter<Animal> animalArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, animalList);
        listView.setAdapter(animalListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animal animal = (Animal) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), FuncionarioGerencia_Animal.class);
                intent.putExtra("funcionario", funcionario);
                intent.putExtra("animal", animal);
                startActivity(intent);
                finish();
            }
        });

    }


}