package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pets_donation.Models.FuncionarioDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Listar_Funcionarios extends AppCompatActivity {

    private ListView listView;
    private FuncionarioDAO funcionarioDAO;
    private List<Funcionario> funcionarioList;
    private List<Funcionario> funcionarioListFiltrados = new ArrayList<>();
    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_funcionarios);

        getSupportActionBar().setTitle("Funcionários");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        listView = findViewById(R.id.lista_adotantes);
        funcionarioDAO = new FuncionarioDAO(this);
        funcionarioList = funcionarioDAO.obterTodosFuncionarios();
        funcionarioListFiltrados.addAll(funcionarioList);

        Iterator<Funcionario> fc = funcionarioList.iterator();
        while (fc.hasNext()) {
            Funcionario funcionario1 = fc.next();
            if (funcionario1.getNome().equals(funcionario.getNome())) {
                fc.remove();
                break;
            }
        }

        //exibir lista
        ArrayAdapter<Funcionario> adotanteArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, funcionarioList);
        listView.setAdapter(adotanteArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Funcionario funcionario1 = (Funcionario) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), GerenciaFuncionarios_Dados.class);
                intent.putExtra("adm", funcionario);
                intent.putExtra("funcionario", funcionario1);
                startActivity(intent);
                finish();
            }
        });
    }
}