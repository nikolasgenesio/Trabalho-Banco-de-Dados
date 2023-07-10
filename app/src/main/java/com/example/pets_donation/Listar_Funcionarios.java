package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pets_donation.Models.FuncionarioDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Listar_Funcionarios extends AppCompatActivity {

    //declaracao das variaveis
    private TextView textView;
    private ListView listView;
    private FuncionarioDAO funcionarioDAO;
    private List<Funcionario> funcionarioList;
    private List<Funcionario> funcionarioListFiltrados = new ArrayList<>();
    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_funcionarios);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Funcionários");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        //inicializando as variaveis
        textView = findViewById(R.id.msgFuncionarios);
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

        if (funcionarioList != null && funcionarioList.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
        } else {
            //exibir lista
            ArrayAdapter<Funcionario> adotanteArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, funcionarioList);
            listView.setAdapter(adotanteArrayAdapter);
            getListViewSize(listView);

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

    /**
     * Funcao para adaptar lista
     * @param myListView Lista de funcionarios
     */
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}