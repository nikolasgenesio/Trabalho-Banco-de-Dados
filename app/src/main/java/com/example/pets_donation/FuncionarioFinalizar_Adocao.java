package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class FuncionarioFinalizar_Adocao extends AppCompatActivity {

    //atores
    private Adotante adotante;
    private Animal animal;
    private ProcessoAdocao processoAdocao;

    //variaveis adotante

    private EditText nome, nascimento, telefone, email, rendaMensal;
    private EditText cep, estado, cidade, rua, numero, bairro;
    private RadioButton masculino, feminino;
    private RadioButton celular, fixo;
    private String sexo, tipoTelefone;

    //variaveis questionario

    //botões
    private RadioButton apartamento, casa;
    private RadioButton simImovel, naoImovel;
    private RadioButton simVet, naoVet;

    //respostas
    private EditText qtdePessoas, qtdeAnimais, localAnimais, permanecerAnimais, animaisAtual, animalFalecido, sustentarAnimal, vizinhosAnimal;
    private EditText passeioAnimal, custosAnimal, alergiaAnimal, respeitoAnimal, criancaAnimal, horasAnimal, viajarAnimal, fugirAnimal, criarAnimal;

    //auxiliares
    private String morada, imovel, veterinario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_finalizar_adocao);

        getSupportActionBar().setTitle("INFORMAÇÕES");

        this.adotante = (Adotante) getIntent().getSerializableExtra("adotante");
        this.animal = (Animal) getIntent().getSerializableExtra("animal");
        this.processoAdocao = (ProcessoAdocao) getIntent().getSerializableExtra("adocao");

        //adotante
        nome = findViewById(R.id.nome);
        nascimento = findViewById(R.id.nascimento);
        fixo = (RadioButton) findViewById(R.id.radioButtonTelefoneFixo);
        celular = (RadioButton) findViewById(R.id.radioButtonTelefoneCelular);
        telefone = findViewById(R.id.telefone);
        telefone.setFocusable(false);
        email = findViewById(R.id.email);
        masculino = (RadioButton) findViewById(R.id.radioButtonMasculino);
        feminino = (RadioButton) findViewById(R.id.radioButtonFeminino);
        rendaMensal = findViewById(R.id.rendaMensal);

        nome.setFocusable(false);
        nascimento.setFocusable(false);
        fixo.setEnabled(false);
        celular.setEnabled(false);
        telefone.setFocusable(false);
        email.setFocusable(false);
        masculino.setEnabled(false);
        feminino.setEnabled(false);
        rendaMensal.setFocusable(false);

        cep = findViewById(R.id.cep);
        estado = findViewById(R.id.estado);
        cidade = findViewById(R.id.cidade);
        rua = findViewById(R.id.rua);
        numero = findViewById(R.id.numero);
        bairro = findViewById(R.id.bairro);

        cep.setFocusable(false);
        estado.setFocusable(false);
        cidade.setFocusable(false);
        rua.setFocusable(false);
        numero.setFocusable(false);
        bairro.setFocusable(false);

        //questionario
        apartamento = (RadioButton) findViewById(R.id.radioButtonApartamento);
        casa = (RadioButton) findViewById(R.id.radioButtonCasa);

        apartamento.setEnabled(false);
        casa.setEnabled(false);

        simImovel = (RadioButton) findViewById(R.id.radioButtonSimImovel);
        naoImovel = (RadioButton) findViewById(R.id.radioButtonNaoImovel);

        simImovel.setEnabled(false);
        naoImovel.setEnabled(false);

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

        qtdePessoas.setFocusable(false);
        qtdeAnimais.setFocusable(false);
        localAnimais.setFocusable(false);
        permanecerAnimais.setFocusable(false);
        animaisAtual.setFocusable(false);
        animalFalecido.setFocusable(false);
        sustentarAnimal.setFocusable(false);
        vizinhosAnimal.setFocusable(false);
        passeioAnimal.setFocusable(false);
        custosAnimal.setFocusable(false);
        alergiaAnimal.setFocusable(false);
        respeitoAnimal.setFocusable(false);
        criancaAnimal.setFocusable(false);
        horasAnimal.setFocusable(false);
        viajarAnimal.setFocusable(false);
        fugirAnimal.setFocusable(false);
        criarAnimal.setFocusable(false);

        simVet = (RadioButton) findViewById(R.id.radioButtonSimVet);
        naoVet = (RadioButton) findViewById(R.id.radioButtonNaoVet);

        simVet.setEnabled(false);
        naoVet.setEnabled(false);

        preencheInformacoes();
    }

    public void preencheInformacoes() {
        //adotante
        nome.setText(adotante.getNome());
        nascimento.setText(adotante.getDataNascimento());

        sexo = adotante.getSexo();
        tipoTelefone = adotante.getTipoTelefone();

        if (sexo.equals("Masculino"))
            masculino.setChecked(true);
        else if (sexo.equals("Feminino"))
            feminino.setChecked(true);


        if (tipoTelefone.equals("Fixo"))
            fixo.setChecked(true);
        else if (tipoTelefone.equals("Celular"))
            celular.setChecked(true);

        telefone.setText(adotante.getTelefone());
        email.setText(adotante.getEmail());

        String rendamensal1 = String.valueOf(adotante.getRendaMensal());
        rendaMensal.setText(rendamensal1);

        cep.setText(adotante.getCep());
        estado.setText(adotante.getEstado());
        cidade.setText(adotante.getCidade());
        rua.setText(adotante.getRua());
        numero.setText(adotante.getNumero());
        bairro.setText(adotante.getBairro());

        //questionario
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

}