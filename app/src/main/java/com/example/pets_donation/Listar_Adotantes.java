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

import com.example.pets_donation.Models.AdotanteDAO;

import java.util.ArrayList;
import java.util.List;

public class Listar_Adotantes extends AppCompatActivity {

    //declarando as variaveis
    private TextView textView;
    private ListView listView;
    private AdotanteDAO adotanteDAO;
    private List<Adotante> adotanteList;
    private List<Adotante> adotanteListFiltrados = new ArrayList<>();
    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_adotantes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Adotantes");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        textView = findViewById(R.id.msgAdotantes);
        listView = findViewById(R.id.lista_adotantes);
        adotanteDAO = new AdotanteDAO(this);
        adotanteList = adotanteDAO.obterTodosAdotantes();
        adotanteListFiltrados.addAll(adotanteList);

        if (adotanteList != null && adotanteList.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
        } else {
            //exibir lista
            ArrayAdapter<Adotante> adotanteArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adotanteList);
            listView.setAdapter(adotanteArrayAdapter);
            getListViewSize(listView);

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


    /**
     * Funcao para adaptar lista
     * @param myListView Lista de adotantes
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