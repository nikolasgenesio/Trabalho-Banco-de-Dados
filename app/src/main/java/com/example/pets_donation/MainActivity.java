package com.example.pets_donation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.FuncionarioDAO;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.io.Serializable;
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
    private Conexao_Banco banco;
    private FuncionarioDAO funcionarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        banco = new Conexao_Banco(this);
        //inserindo funcionario

        if (!(banco.checkSenhaAdministrador("126.745.986-22", "barrafunda"))) {
            funcionarioDAO = new FuncionarioDAO(this);
            Boolean inserirFuncionario = funcionarioDAO.inserirFuncionarioADM();
            if (inserirFuncionario)
                Log.i("TESTE FUNCIONARIO", "FUNCIONARIO INSERIDO");
            else
                Log.i("TESTE FUNCIONARIO", "FUNCIONARIO NAO INSERIDO");
        }
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

        //cpf.setText("884.825.720-83");
        //cpf.setText("200.117.980-41");
        //senha.setText("nikolas1");

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
                Intent intent = new Intent(MainActivity.this, Termos.class);
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

        content1 = new SpannableString("Esqueci minha senha");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        esqueciSenha.setText(content1);

        //NAO IMPLEMENTADO
        esqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, Termos.class);
                //startActivity(intent);
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

        String tipo = banco.checkCPFForAll(username);
        Log.i("Tarefa 1 - status Main", "OIi: " + tipo);

        switch (tipo) {
            case "administrador":
                if (banco.checkSenhaAdministrador(username, password)) {
                    Toast.makeText(MainActivity.this, "Login com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(MainActivity.this, Tela_Administrador.class);
                    Funcionario funcionario = banco.obterAdministrador(username, password);
                    Log.i("Tarefa 1 - status Main", "ADM: ");
                    intent1.putExtra("funcionario", funcionario);
                    startActivity(intent1);
                }
                break;
            case "secretario":
                if (banco.checkSenhaSecretario(username, password)) {
                    Toast.makeText(MainActivity.this, "Login com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(MainActivity.this, Tela_Funcionario.class);
                    Funcionario funcionario = banco.obterSecretario(username, password);
                    intent1.putExtra("funcionario", funcionario);
                    startActivity(intent1);
                }
                break;
            case "adotante":
                if (banco.checkSenhaAdotante(username, password)) {
                    Toast.makeText(MainActivity.this, "Login com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(MainActivity.this, Tela_Adotante.class);
                    Adotante adotante = banco.obterAdotante(username, password);
                    intent1.putExtra("adotante", adotante);
                    startActivity(intent1);
                }
                break;
            default:
                Toast.makeText(MainActivity.this, "CPF e/ou Senha incorreto(s)!", Toast.LENGTH_SHORT).show();
                break;
        }


//        else if (banco.checkSenhaSecretario(username, password)) {
//            Toast.makeText(MainActivity.this, "Login com sucesso!", Toast.LENGTH_SHORT).show();
//            Intent intent1 = new Intent(MainActivity.this, Tela_Secretario.class);
//            Funcionario funcionario = banco.obterFuncionario(username, password);
//            Log.i("Tarefa 1 - status Main", "NOME: " + funcionario.getNome());
//            intent1.putExtra("funcionario", funcionario);
//            startActivity(intent1);
//        }
//        else if (banco.checkSenhaAdministrador(username, password)) {
//            Toast.makeText(MainActivity.this, "Login com sucesso!", Toast.LENGTH_SHORT).show();
//            Intent intent1 = new Intent(MainActivity.this, Tela_Administrador.class);
//            Funcionario funcionario = banco.obterFuncionario(username, password);
//            Log.i("Tarefa 1 - status Main", "NOME: " + funcionario.getNome());
//            intent1.putExtra("funcionario", funcionario);
//            startActivity(intent1);
//        }

//        else {
//            Toast.makeText(MainActivity.this, "CPF e/ou Senha incorreto(s)!", Toast.LENGTH_SHORT).show();
//        }
    }
}

