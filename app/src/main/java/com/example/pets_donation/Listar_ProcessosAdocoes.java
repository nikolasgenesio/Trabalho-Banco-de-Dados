package com.example.pets_donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.pets_donation.Models.ProcessoAdocaoDAO;

import java.util.List;

import android.widget.TextView;
import android.widget.Toast;

public class Listar_ProcessosAdocoes extends AppCompatActivity {

    //declaracao das variaveis
    private TextView textView;
    private Funcionario funcionario;
    private ListView listView;
    private ProcessoAdocaoDAO processoAdocaoDAO;
    private List<Adocao_View> processoAdocaoList;
    private AdocaoFuncionario adocaoFuncionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_processos_adocoes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Relatório - Processos");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        //inicializando as variaveis
        textView = findViewById(R.id.msgProcessos);
        listView = findViewById(R.id.lista_adocoes);
        processoAdocaoDAO = new ProcessoAdocaoDAO(this);
        processoAdocaoList = processoAdocaoDAO.retornaRelatorioAdocao();

        if (processoAdocaoList != null && processoAdocaoList.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
        } else {
            adocaoFuncionario = new AdocaoFuncionario(this, R.layout.list_view_adocoes, processoAdocaoList, funcionario);

            listView.setAdapter(adocaoFuncionario);
            getListViewSize(listView);
        }
    }

    /**
     * Funcao para adaptar lista
     * @param myListView Lista de processos de adocao
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
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.menu_pdf, menu);

        MenuItem busca = menu.findItem(R.id.pdf);

        busca.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), "PDF", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
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