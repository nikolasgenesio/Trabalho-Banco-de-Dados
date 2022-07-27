package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.AdocoesDAO;

import java.util.ArrayList;
import java.util.List;

public class Listar_AnimaisAdocoes extends AppCompatActivity {

    private ListView listView1, listView2;
    private AdocoesDAO adocoesDAO;
    private Funcionario funcionario;

    private List<Adocoes> adocoesList;
    private List<Adocoes> adocoesListFiltrados = new ArrayList<>();
    private List<Animal> animalList;

    private AdocaoDeferidaAdapter adocaoDeferidaAdapter;
    private AnimalListAdapter animalListAdapter;

    private Conexao_Banco banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_animais_adocoes);

        getSupportActionBar().setTitle("Relatório - Animais");

        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        banco = new Conexao_Banco(this);

        listView1 = findViewById(R.id.lista_animaisAdotados);
        listView2 = findViewById(R.id.lista_animaisNaoAdotados);
        adocoesDAO = new AdocoesDAO(this);
        adocoesList = adocoesDAO.obterTodosAnimaisAdotados();
        adocoesListFiltrados.addAll(adocoesList);
        animalList = banco.animaisAdocao();

        adocaoDeferidaAdapter = new AdocaoDeferidaAdapter(this, R.layout.list_view_animal, adocoesList, funcionario);

        //exibir lista
        //ArrayAdapter<Adocoes> adocoesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adocoesList);
        listView1.setAdapter(adocaoDeferidaAdapter);
        getListViewSize(listView1);

        animalListAdapter = new AnimalListAdapter(this, R.layout.list_view_animal, animalList, funcionario);
        listView2.setAdapter(animalListAdapter);
        getListViewSize(listView2);
    }

    public void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
    }

}