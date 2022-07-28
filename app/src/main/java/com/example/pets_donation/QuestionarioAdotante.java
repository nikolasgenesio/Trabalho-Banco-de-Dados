package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.pets_donation.Adotante;
import com.example.pets_donation.Animal;
import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.ProcessoAdocaoDAO;
import com.example.pets_donation.R;

public class QuestionarioAdotante extends AppCompatActivity {


    //botões
    private RadioButton apartamento, casa;
    private RadioButton simImovel, naoImovel;
    private RadioButton simVet, naoVet;
    private Button btnLimpar, btnCancelar, btnAvancar;

    //respostas
    private EditText qtdePessoas, qtdeAnimais, localAnimais, permanecerAnimais, animaisAtual, animalFalecido, sustentarAnimal, vizinhosAnimal;
    private EditText passeioAnimal, custosAnimal, alergiaAnimal, respeitoAnimal, criancaAnimal, horasAnimal, viajarAnimal, fugirAnimal, criarAnimal;

    //auxiliares
    private String morada, imovel, veterinario;

    //atores
    private Adotante adotante;
    private Animal animal;

    private Conexao_Banco banco;
    private ProcessoAdocaoDAO processoAdocaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario_adotante);
        getSupportActionBar().setTitle("Questionário");
        this.adotante = (Adotante) getIntent().getSerializableExtra("adotante");
        this.animal = (Animal) getIntent().getSerializableExtra("animal");
        banco = new Conexao_Banco(this);
        processoAdocaoDAO = new ProcessoAdocaoDAO(this);

        apartamento = (RadioButton) findViewById(R.id.radioButtonApartamento);
        casa = (RadioButton) findViewById(R.id.radioButtonCasa);

        simImovel = (RadioButton) findViewById(R.id.radioButtonSimImovel);
        naoImovel = (RadioButton) findViewById(R.id.radioButtonNaoImovel);

        qtdePessoas = findViewById(R.id.qtdePessoas);
        qtdeAnimais = findViewById(R.id.qtdeAnimais);
        localAnimais = findViewById(R.id.localAnimais);
        permanecerAnimais = findViewById(R.id.permanecerAnimais);
        animaisAtual = findViewById(R.id.animaisAtual);
        animalFalecido = findViewById(R.id.animalFalecido);
        sustentarAnimal = findViewById(R.id.sustentarAnimal);
        vizinhosAnimal = findViewById(R.id.vizinhosAnimal);
        passeioAnimal = findViewById(R.id.passeioAnimal);
        custosAnimal = findViewById(R.id.custosAnimal);
        alergiaAnimal = findViewById(R.id.alergiaAnimal);
        respeitoAnimal = findViewById(R.id.respeitoAnimal);
        criancaAnimal = findViewById(R.id.criancaAnimal);
        horasAnimal = findViewById(R.id.horasAnimal);
        viajarAnimal = findViewById(R.id.viajarAnimal);
        fugirAnimal = findViewById(R.id.fugirAnimal);
        criarAnimal = findViewById(R.id.criarAnimal);

        simVet = (RadioButton) findViewById(R.id.radioButtonSimVet);
        naoVet = (RadioButton) findViewById(R.id.radioButtonNaoVet);

        btnAvancar = findViewById(R.id.btnConfirma);
        btnCancelar = findViewById(R.id.btnCancela);
        btnLimpar = findViewById(R.id.btnLimpa);

        //botao de cancelamento
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //botao de limpar
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtdePessoas.setText("");
                qtdeAnimais.setText("");
                localAnimais.setText("");
                permanecerAnimais.setText("");
                animaisAtual.setText("");
                animalFalecido.setText("");
                sustentarAnimal.setText("");
                vizinhosAnimal.setText("");
                passeioAnimal.setText("");
                custosAnimal.setText("");
                alergiaAnimal.setText("");
                respeitoAnimal.setText("");
                criancaAnimal.setText("");
                horasAnimal.setText("");
                viajarAnimal.setText("");
                fugirAnimal.setText("");
                criarAnimal.setText("");
            }
        });

        //botao de adicionar
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processo_Adocao();
            }
        });
    }


    public void processo_Adocao() {

        if (apartamento.isChecked()) {
            morada = "Apartamento";
        }
        if (casa.isChecked()) {
            morada = "Casa";
        }

        if (!(apartamento.isChecked() || casa.isChecked())) {
            Toast.makeText(getApplicationContext(), "Você não respondeu a pergunta 1!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (simImovel.isChecked()) {
            imovel = "Sim, já verifiquei e tenho certeza!";
        }
        if (naoImovel.isChecked()) {
            imovel = "Não";
        }

        if (!(simImovel.isChecked() || naoImovel.isChecked())) {
            Toast.makeText(getApplicationContext(), "Você não respondeu a pergunta 2!", Toast.LENGTH_SHORT).show();
            return;
        }

        String qtdePessoas1 = qtdePessoas.getText().toString();
        if (qtdePessoas1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a quantidade de pessoas! (pergunta 3)", Toast.LENGTH_SHORT).show();
            return;
        }

        String qtdeAnimais1 = qtdeAnimais.getText().toString();
        if (qtdeAnimais1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a quantidade de animais! (pergunta 4)", Toast.LENGTH_SHORT).show();
            return;
        }

        String localAnimais1 = localAnimais.getText().toString();
        if (localAnimais1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o local do animal! (pergunta 5)", Toast.LENGTH_SHORT).show();
            return;
        }

        String permanecerAnimais1 = permanecerAnimais.getText().toString();
        if (permanecerAnimais1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou onde o animal vai permanecer! (pergunta 6)", Toast.LENGTH_SHORT).show();
            return;
        }

        String animaisAtual1 = animaisAtual.getText().toString();
        if (animaisAtual1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou se tem outras animais atualmente! (pergunta 7)", Toast.LENGTH_SHORT).show();
            return;
        }

        String animalFalecido1 = animalFalecido.getText().toString();
        if (animalFalecido1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou se teve algum animal falecido! (pergunta 8)", Toast.LENGTH_SHORT).show();
            return;
        }

        String sustentarAnimal1 = sustentarAnimal.getText().toString();
        if (sustentarAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou quem vai sustentar o animal! (pergunta 9)", Toast.LENGTH_SHORT).show();
            return;
        }

        String vizinhosAnimal1 = vizinhosAnimal.getText().toString();
        if (vizinhosAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou sua atitude caso o animal fazer barulho à noite! (pergunta 10)", Toast.LENGTH_SHORT).show();
            return;
        }

        String passeioAnimal1 = passeioAnimal.getText().toString();
        if (passeioAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou se está disposto a passear com o animal! (pergunta 11)", Toast.LENGTH_SHORT).show();
            return;
        }

        String custosAnimal1 = custosAnimal.getText().toString();
        if (custosAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou se poderá arcar com os custos do animal! (pergunta 12)", Toast.LENGTH_SHORT).show();
            return;
        }

        String alergiaAnimal1 = alergiaAnimal.getText().toString();
        if (alergiaAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou se alguém da família tem alergia! (pergunta 13)", Toast.LENGTH_SHORT).show();
            return;
        }

        String respeitoAnimal1 = respeitoAnimal.getText().toString();
        if (respeitoAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou se todos da casa respeitam o animal! (pergunta 14)", Toast.LENGTH_SHORT).show();
            return;
        }

        String criancaAnimal1 = criancaAnimal.getText().toString();
        if (criancaAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou se tem criança em casa e como ensiná-la! (pergunta 15)", Toast.LENGTH_SHORT).show();
            return;
        }

        String horasAnimal1 = horasAnimal.getText().toString();
        if (horasAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou quantas horas passará com o animal! (pergunta 16)", Toast.LENGTH_SHORT).show();
            return;
        }

        String viajarAnimal1 = viajarAnimal.getText().toString();
        if (viajarAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou quem ficará com o animal, caso faça uma viagem! (pergunta 17)", Toast.LENGTH_SHORT).show();
            return;
        }

        String fugirAnimal1 = fugirAnimal.getText().toString();
        if (fugirAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o que fará se o animal fugir! (pergunta 18)", Toast.LENGTH_SHORT).show();
            return;
        }

        String criarAnimal1 = criarAnimal.getText().toString();
        if (criarAnimal1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o que fará se não puder cuidar do animal! (pergunta 19)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (simVet.isChecked()) {
            veterinario = "Sim";
        }
        if (naoVet.isChecked()) {
            veterinario = "Não";
        }

        if (!(simVet.isChecked() || naoVet.isChecked())) {
            Toast.makeText(getApplicationContext(), "Você não respondeu a pergunta 20!", Toast.LENGTH_SHORT).show();
            return;
        }

        //tudo certo
        ProcessoAdocao adocao = new ProcessoAdocao();

        adocao.setMorada(morada);
        adocao.setImovel(imovel);
        adocao.setQtdePessoas(qtdePessoas1);
        adocao.setQtdeAnimais(qtdeAnimais1);
        adocao.setLocalAnimais(localAnimais1);
        adocao.setPermanecerAnimais(permanecerAnimais1);
        adocao.setAnimaisAtual(animaisAtual1);
        adocao.setAnimalFalecido(animalFalecido1);
        adocao.setSustentarAnimal(sustentarAnimal1);
        adocao.setVizinhosAnimal(vizinhosAnimal1);
        adocao.setPasseioAnimal(passeioAnimal1);
        adocao.setCustosAnimal(custosAnimal1);
        adocao.setAlergiaAnimal(alergiaAnimal1);
        adocao.setRespeitoAnimal(respeitoAnimal1);
        adocao.setCriancaAnimal(criancaAnimal1);
        adocao.setHorasAnimal(horasAnimal1);
        adocao.setViajarAnimal(viajarAnimal1);
        adocao.setFugirAnimal(fugirAnimal1);
        adocao.setCriarAnimal(criarAnimal1);
        adocao.setVeterinario(veterinario);

        adocao.setCPFAdotante(adotante.getCpf());
        adocao.setIDAnimal(animal.getID());
        adocao.setStatus("Em Andamento");

        Boolean inserir = processoAdocaoDAO.inserir(adocao);
        if (inserir) {
            Toast.makeText(getApplicationContext(), "PEDIDO FEITO COM SUCESSO!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QuestionarioAdotante.this, Tela_Adotante.class);
            intent.putExtra("adotante", adotante);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(), "ERRO NO PEDIDO!", Toast.LENGTH_SHORT).show();
    }
}