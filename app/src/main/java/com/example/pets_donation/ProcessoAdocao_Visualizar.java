package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.ProcessoAdocaoDAO;

public class ProcessoAdocao_Visualizar extends AppCompatActivity {

    //botões
    private RadioButton apartamento, casa;
    private RadioButton simImovel, naoImovel;
    private RadioButton simVet, naoVet;
    private Button btnAlterar, btnCancelar, btnDeletar;

    //respostas
    private EditText qtdePessoas, qtdeAnimais, localAnimais, permanecerAnimais, animaisAtual, animalFalecido, sustentarAnimal, vizinhosAnimal;
    private EditText passeioAnimal, custosAnimal, alergiaAnimal, respeitoAnimal, criancaAnimal, horasAnimal, viajarAnimal, fugirAnimal, criarAnimal;

    //auxiliares
    private String morada, imovel, veterinario;

    private ProcessoAdocao processoAdocao;
    private Conexao_Banco banco;
    private ProcessoAdocaoDAO processoAdocaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processo_adocao_visualizar);
        getSupportActionBar().setTitle("Questionário");

        //inicializando variaveis
        this.processoAdocao = (ProcessoAdocao) getIntent().getSerializableExtra("adocao");
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

        btnAlterar = findViewById(R.id.btnAltera);
        btnCancelar = findViewById(R.id.btnCancela);
        btnDeletar = findViewById(R.id.btnDeleta);

        preencheInformacoes();

        //botao de cancelamento
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //botao de alterar
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alterar_Adocao();
            }
        });

        //botao de deletar
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deletar_Adocao();
            }
        });
    }

    /**
     * Funcao para preencher informacoes
     */
    public void preencheInformacoes() {

        if (processoAdocao.getMorada().equals("Apartamento")) {
            apartamento.setChecked(true);
        } else if (processoAdocao.getMorada().equals("Caso")) {
            casa.setChecked(true);
        }

        if (processoAdocao.getImovel().equals("Sim, já verifiquei e tenho certeza!")) {
            simImovel.setChecked(true);
        } else if (processoAdocao.getImovel().equals("Não")) {
            naoImovel.setChecked(true);
        }

        qtdePessoas.setText(processoAdocao.getQtdePessoas());
        qtdeAnimais.setText(processoAdocao.getQtdeAnimais());
        localAnimais.setText(processoAdocao.getLocalAnimais());
        permanecerAnimais.setText(processoAdocao.getPermanecerAnimais());
        animaisAtual.setText(processoAdocao.getAnimaisAtual());
        animalFalecido.setText(processoAdocao.getAnimalFalecido());
        sustentarAnimal.setText(processoAdocao.getSustentarAnimal());
        vizinhosAnimal.setText(processoAdocao.getVizinhosAnimal());
        passeioAnimal.setText(processoAdocao.getPasseioAnimal());
        custosAnimal.setText(processoAdocao.getCustosAnimal());
        alergiaAnimal.setText(processoAdocao.getAlergiaAnimal());
        respeitoAnimal.setText(processoAdocao.getRespeitoAnimal());
        criancaAnimal.setText(processoAdocao.getCriancaAnimal());
        horasAnimal.setText(processoAdocao.getHorasAnimal());
        viajarAnimal.setText(processoAdocao.getViajarAnimal());
        fugirAnimal.setText(processoAdocao.getFugirAnimal());
        criarAnimal.setText(processoAdocao.getCriarAnimal());

        if (processoAdocao.getVeterinario().equals("Sim")) {
            simVet.setChecked(true);
        } else if (processoAdocao.getVeterinario().equals("Não")) {
            naoVet.setChecked(true);
        }
    }

    /**
     * Funcao para alterar adocao
     */
//    public void alterar_Adocao() {
//
//        if (apartamento.isChecked()) {
//            morada = "Apartamento";
//        }
//        if (casa.isChecked()) {
//            morada = "Casa";
//        }
//
//        if (!(apartamento.isChecked() || casa.isChecked())) {
//            Toast.makeText(getApplicationContext(), "Você não respondeu a pergunta 1!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (simImovel.isChecked()) {
//            imovel = "Sim, já verifiquei e tenho certeza!";
//        }
//        if (naoImovel.isChecked()) {
//            imovel = "Não";
//        }
//
//        if (!(simImovel.isChecked() || naoImovel.isChecked())) {
//            Toast.makeText(getApplicationContext(), "Você não respondeu a pergunta 2!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String qtdePessoas1 = qtdePessoas.getText().toString();
//        if (qtdePessoas1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou a quantidade de pessoas! (pergunta 3)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String qtdeAnimais1 = qtdeAnimais.getText().toString();
//        if (qtdeAnimais1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou a quantidade de animais! (pergunta 4)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String localAnimais1 = localAnimais.getText().toString();
//        if (localAnimais1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou o local do animal! (pergunta 5)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String permanecerAnimais1 = permanecerAnimais.getText().toString();
//        if (permanecerAnimais1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou onde o animal vai permanecer! (pergunta 6)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String animaisAtual1 = animaisAtual.getText().toString();
//        if (animaisAtual1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou se tem outras animais atualmente! (pergunta 7)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String animalFalecido1 = animalFalecido.getText().toString();
//        if (animalFalecido1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou se teve algum animal falecido! (pergunta 8)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String sustentarAnimal1 = sustentarAnimal.getText().toString();
//        if (sustentarAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou quem vai sustentar o animal! (pergunta 9)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String vizinhosAnimal1 = vizinhosAnimal.getText().toString();
//        if (vizinhosAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou sua atitude caso o animal fazer barulho à noite! (pergunta 10)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String passeioAnimal1 = passeioAnimal.getText().toString();
//        if (passeioAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou se está disposto a passear com o animal! (pergunta 11)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String custosAnimal1 = custosAnimal.getText().toString();
//        if (custosAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou se poderá arcar com os custos do animal! (pergunta 12)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String alergiaAnimal1 = alergiaAnimal.getText().toString();
//        if (alergiaAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou se alguém da família tem alergia! (pergunta 13)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String respeitoAnimal1 = respeitoAnimal.getText().toString();
//        if (respeitoAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou se todos da casa respeitam o animal! (pergunta 14)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String criancaAnimal1 = criancaAnimal.getText().toString();
//        if (criancaAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou se tem criança em casa e como ensiná-la! (pergunta 15)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String horasAnimal1 = horasAnimal.getText().toString();
//        if (horasAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou quantas horas passará com o animal! (pergunta 16)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String viajarAnimal1 = viajarAnimal.getText().toString();
//        if (viajarAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou quem ficará com o animal, caso faça uma viagem! (pergunta 17)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String fugirAnimal1 = fugirAnimal.getText().toString();
//        if (fugirAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou o que fará se o animal fugir! (pergunta 18)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String criarAnimal1 = criarAnimal.getText().toString();
//        if (criarAnimal1.matches("")) {
//            Toast.makeText(getApplicationContext(), "Você não digitou o que fará se não puder cuidar do animal! (pergunta 19)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (simVet.isChecked()) {
//            veterinario = "Sim";
//        }
//        if (naoVet.isChecked()) {
//            veterinario = "Não";
//        }
//
//        if (!(simVet.isChecked() || naoVet.isChecked())) {
//            Toast.makeText(getApplicationContext(), "Você não respondeu a pergunta 20!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        processoAdocao.setMorada(morada);
//        processoAdocao.setImovel(imovel);
//        processoAdocao.setQtdePessoas(qtdePessoas1);
//        processoAdocao.setQtdeAnimais(qtdeAnimais1);
//        processoAdocao.setLocalAnimais(localAnimais1);
//        processoAdocao.setPermanecerAnimais(permanecerAnimais1);
//        processoAdocao.setAnimaisAtual(animaisAtual1);
//        processoAdocao.setAnimalFalecido(animalFalecido1);
//        processoAdocao.setSustentarAnimal(sustentarAnimal1);
//        processoAdocao.setVizinhosAnimal(vizinhosAnimal1);
//        processoAdocao.setPasseioAnimal(passeioAnimal1);
//        processoAdocao.setCustosAnimal(custosAnimal1);
//        processoAdocao.setAlergiaAnimal(alergiaAnimal1);
//        processoAdocao.setRespeitoAnimal(respeitoAnimal1);
//        processoAdocao.setCriancaAnimal(criancaAnimal1);
//        processoAdocao.setHorasAnimal(horasAnimal1);
//        processoAdocao.setViajarAnimal(viajarAnimal1);
//        processoAdocao.setFugirAnimal(fugirAnimal1);
//        processoAdocao.setCriarAnimal(criarAnimal1);
//        processoAdocao.setVeterinario(veterinario);
//
//        boolean alterar = processoAdocaoDAO.alterar(processoAdocao);
//        if (alterar) {
//            Toast.makeText(getApplicationContext(), "Pedido alterado", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), Tela_Adotante.class);
//            Adotante adotante = banco.obterAdotanteAdocao(processoAdocao.getCPFAdotante());
//            intent.putExtra("adotante", adotante);
//            startActivity(intent);
//            finish();
//        } else {
//            Toast.makeText(getApplicationContext(), "Pedido não alterado", Toast.LENGTH_SHORT).show();
//        }
//
//    }

}