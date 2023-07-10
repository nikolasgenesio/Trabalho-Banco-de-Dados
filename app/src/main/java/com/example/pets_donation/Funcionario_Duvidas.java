package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pets_donation.ui.fragments.FuncionarioPerfil_Fragment;

public class Funcionario_Duvidas extends AppCompatActivity {

    //declaracao da variavel

    private Funcionario funcionario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_duvidas2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("FAQ");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(this, Tela_Funcionario.class);
                intent.putExtra("funcionario", funcionario);
                startActivity(intent);
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:
                break;
        }
        return true;
    }
}