package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class Adotante_Duvidas extends AppCompatActivity {

    //declaracao da variavel
    private Adotante adotante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("FAQ");
        this.adotante = (Adotante) getIntent().getSerializableExtra("adotante");
        setContentView(R.layout.activity_adotante_duvidas);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(this, Tela_Adotante.class);
                intent.putExtra("adotante", adotante);
                startActivity(intent);
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:
                break;
        }
        return true;
    }

}