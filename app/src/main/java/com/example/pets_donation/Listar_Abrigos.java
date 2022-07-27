package com.example.pets_donation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pets_donation.Models.AbrigoDAO;

import java.util.ArrayList;
import java.util.List;

public class Listar_Abrigos extends AppCompatActivity {

    //declarando as variaveis
    private ListView listView;
    private AbrigoDAO abrigoDAO;
    private List<Abrigo> abrigoList;
    private List<Abrigo> abrigoListFiltrados = new ArrayList<>();

    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_abrigos);

        getSupportActionBar().setTitle("Abrigos");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        listView = findViewById(R.id.lista_abrigos);
        abrigoDAO = new AbrigoDAO(this);
        abrigoList = abrigoDAO.obterTodosAbrigos();
        abrigoListFiltrados.addAll(abrigoList);

        //exibir lista
        ArrayAdapter<Abrigo> abrigoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, abrigoList);
        listView.setAdapter(abrigoArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Abrigo abrigo = (Abrigo) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), FuncionarioGerencia_Abrigos.class);
                intent.putExtra("funcionario", funcionario);
                intent.putExtra("abrigo", abrigo);
                startActivity(intent);
                finish();
                //Log.i("Abrigo", "NOME2: " + abrigo.getNome());
                //Log.i("Abrigo", "ID: " + abrigo.getID());
            }
        });

        //registerForContextMenu(listView);
    }


    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Abrigo abrigo = abrigoListFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir>")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        abrigoListFiltrados.remove(abrigo);
                        abrigoList.remove(abrigo);
                        abrigoDAO.excluir(abrigo);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        abrigoList = abrigoDAO.obterTodosAbrigos();
        abrigoListFiltrados.clear();
        abrigoListFiltrados.addAll(abrigoList);
        listView.invalidateViews();
    }

    public void OnResume() {
        super.onResume();
        abrigoList = abrigoDAO.obterTodosAbrigos();
        abrigoListFiltrados.clear();
        abrigoListFiltrados.addAll(abrigoList);
        listView.invalidateViews();
    }

}