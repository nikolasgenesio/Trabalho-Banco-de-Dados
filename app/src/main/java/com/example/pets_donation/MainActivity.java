package com.example.pets_donation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.EventListener;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements EventListener {

    //Declaracao das variaveis
    private EditText cpf;
    private EditText senha;
    private Button botaoSegundaTela, btnLogar;
    private TextView esqueciSenha, termo;
    private ProgressBar progressBar;
    private CheckBox checkBox;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //Inicializando as variaveis
        cpf = findViewById(R.id.login);
        senha = findViewById(R.id.senha);
        esqueciSenha = findViewById(R.id.textoSenha);
        termo = findViewById(R.id.termo);
        btnLogar = findViewById(R.id.btnLogar);
        botaoSegundaTela = findViewById(R.id.btnSegundaTela);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        checkBox = (CheckBox) findViewById(R.id.salvar);

        //Criando a máscara para o campo de CPF
        SimpleMaskFormatter cpf1 = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher cpf2 = new MaskTextWatcher(cpf, cpf1);
        cpf.addTextChangedListener(cpf2);
        //Fim da máscara

        //clique no botao de login
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLogin();
            }
        });

        SpannableString content1 = new SpannableString("Termos de Uso");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        termo.setText(content1);

        //passando para a tela de termo de uso
        termo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Listar_Adotantes.class);
                startActivity(intent);
            }
        });

        //passando para a tela de cadastro do adotante
        botaoSegundaTela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cadastro_Adotante.class);
                startActivity(intent);
            }
        });

        //teste
        esqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cadastro_Abrigo.class);
                startActivity(intent);
            }
        });



    }


    /**
     * Funcao para verificar se as informacoes de login estao corretas
     */
    public void OnLogin() {
        String username = cpf.getText().toString();
        String password = senha.getText().toString();

        if (username.matches("")) {
            Toast.makeText(MainActivity.this, "Você não digitou seu cpf", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.matches("")) {
            Toast.makeText(MainActivity.this, "Você não digitou sua senha", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(MainActivity.this, "Senha: mínimo 8 digitos", Toast.LENGTH_SHORT).show();
            return;
        }
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (username.equals("000.000.000-00") ||
                username.equals("111.111.111-11") ||
                username.equals("222.222.222-22") || username.equals("333.333.333-33") ||
                username.equals("444.444.444-44") || username.equals("555.555.555-55") ||
                username.equals("666.666.666-66") || username.equals("777.777.777-77") ||
                username.equals("888.888.888-88") || username.equals("999.999.999-99") ||
                (username.length() != 14)) {
            Toast.makeText(MainActivity.this, "CPF incorreto", Toast.LENGTH_SHORT).show();
            return;
        }

        if(checkBox.isChecked()){
            editor.putBoolean("savelogin", true);
            editor.putString("username", username);
            editor.putString("password", password);
            editor.commit();
        }else{
            editor.putBoolean("savelogin", false);
            editor.clear();
            editor.commit();
        }
    }

}

