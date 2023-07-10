package com.example.pets_donation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
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

import com.example.pets_donation.Models.AbrigoDAO;
import com.example.pets_donation.ui.fragments.FuncionarioGerenciar_Fragment;

import java.util.ArrayList;
import java.util.List;

public class Listar_Abrigos extends AppCompatActivity {

    //declarando as variaveis
    private TextView textView;
    private ListView listView;
    private AbrigoDAO abrigoDAO;
    private List<Abrigo> abrigoList;
    private List<Abrigo> abrigoListFiltrados = new ArrayList<>();

    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_abrigos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Abrigos");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        textView = findViewById(R.id.msgAbrigos);
        listView = findViewById(R.id.lista_abrigos);
        abrigoDAO = new AbrigoDAO(this);
        abrigoList = abrigoDAO.obterTodosAbrigos();
        abrigoListFiltrados.addAll(abrigoList);

        if (abrigoList != null && abrigoList.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
        } else {
            //exibir lista
            ArrayAdapter<Abrigo> abrigoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, abrigoList);
            listView.setAdapter(abrigoArrayAdapter);
            getListViewSize(listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Abrigo abrigo = (Abrigo) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), FuncionarioGerencia_Abrigos.class);
                    intent.putExtra("funcionario", funcionario);
                    intent.putExtra("abrigo", abrigo);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }


    /**
     * Funcao para funcionario excluir abrigo
     * @param item abrigo a ser excluido
     */
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
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }


    /**
     * Funcao para adaptar lista
     * @param myListView Lista de abrigos
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