package com.example.pets_donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.pets_donation.ui.fragments.FuncionarioAdocao_Fragment;
import com.example.pets_donation.ui.fragments.FuncionarioCadastro_Fragment;
import com.example.pets_donation.ui.fragments.FuncionarioGerenciar_Fragment;
import com.example.pets_donation.ui.fragments.FuncionarioInicio_Fragment;
import com.example.pets_donation.ui.fragments.FuncionarioPerfil_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Tela_Funcionario extends AppCompatActivity {

    //declaracao das variaveis
    private BottomNavigationView bottomNavigationView;
    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_funcionario);

        bottomNavigationView = findViewById(R.id.navegacao);
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,
                new FuncionarioInicio_Fragment()).commit();

        //opcoes
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_Inicio:
                        selectFragment = new FuncionarioInicio_Fragment();
                        break;
                    case R.id.nav_Adocoes:
                        selectFragment = new FuncionarioAdocao_Fragment();
                        break;
                    case R.id.nav_Cadastro:
                        selectFragment = new FuncionarioCadastro_Fragment();
                        break;
                    case R.id.nav_Gerenciar:
                        selectFragment = new FuncionarioGerenciar_Fragment();
                        break;
                    case R.id.nav_Perfil:
                        selectFragment = new FuncionarioPerfil_Fragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,
                        selectFragment).commit();
                return true;
            }
        });

    }

}