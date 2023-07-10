package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pets_donation.Adotante;
import com.example.pets_donation.Animal;
import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.ProcessoAdocaoDAO;
import com.example.pets_donation.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private String[] texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario_adotante);
        getSupportActionBar().setTitle("Questionário");
        this.adotante = (Adotante) getIntent().getSerializableExtra("adotante");
        this.animal = (Animal) getIntent().getSerializableExtra("animal");
        banco = new Conexao_Banco(this);
        processoAdocaoDAO = new ProcessoAdocaoDAO(this);

        //inicializando variaveis
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

//        int[] selecao = {R.id.pergunta1, R.id.pergunta2, R.id.pergunta3, R.id.pergunta4, R.id.pergunta5,
//                R.id.pergunta6, R.id.pergunta7, R.id.pergunta8, R.id.pergunta9, R.id.pergunta10,
//                R.id.pergunta11, R.id.pergunta12, R.id.pergunta13, R.id.pergunta14, R.id.pergunta15,
//                R.id.pergunta16, R.id.pergunta17, R.id.pergunta18, R.id.pergunta19, R.id.pergunta20};

        texto = new String[] { "1. Mora em casa ou apartamento?", "2. Você tem certeza que é permitido animais no imóvel?", "3. Quantas pessoas moram no imóvel?",
                "4. Existem outros animais em casa? Quais?", "5. Onde ficará o animal?", "6. Como o animal vai permanecer (preso parte do dia ou o dia todo, solto...):",
                "7. Se você tem outros animais atualmente, haverá espaço para prevenir uma briga territorial? Terá paciência para fazer a adaptação deles? Como será a adaptação?",
                "8. Já teve algum animal que faleceu? Por que motivo?",
                "9. Quem vai sustentar o animal adotado?", "10. Qual será sua atitude se o animal fazer barulho à noite ou quando não há ninguém em casa (tendo reclamações dos vizinhos)?",
                "11. Está disposto a limpar xixi e cocô, levá-lo para passear, dar comida nos horários certos, tratá-lo quando doente e dar atenção e carinho?",
                "12. Poderá arcar com os custos de ração de qualidade, vermifugação, vacinação, castração e assistência veterinária? Qual seria sua atitude se ele adoecesse ou sofresse algum acidente?",
                "13. Alguém da sua família apresenta sinais de alergia a pêlos de animais? Se, após a adoção, você descobrisse que alguém na sua casa tem alergia, o que faria?",
                "14. Todos os moradores da casa respeitam animais e aceitaram a adoção sem problemas e restrições? Qual seria sua atitude se alguém da residência não se adaptasse ao bichinho?",
                "15. Se tiver criança em casa, se responsabilizará a ensiná-la que o animal não é um brinquedo, que sente dor e deve ser respeitado, evitando que ambos se machuquem?",
                "16. Em média, quantas horas seu animal ficará sozinho em casa? Caso seja necessário ficar mais de 6h sozinho, está disposto a enriquecer o ambiente com brinquedos, passear antes de sair e ao chegar e/ou contratar uma petsitter?",
                "17. Em caso de viagens longas com quem ficará o animal?", "18. O que fará se o animal fugir ou se perder?",
                "19. O que fará se não puder mais criar o animal?", "20. Em caso de emergência, tem como levar o animal imediatamento a um veterinário?"};

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


    /**
     * Funcao para adicionar processo de adocao
     */
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

        //questionario
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

        adocao.setQuestionario(
                texto[0] + adocao.getMorada() + "\n" +
                        texto[1] + " " + adocao.getImovel() + "\n" +
                        texto[2] + " " + adocao.getQtdePessoas() + "\n" +
                        texto[3] + " " + adocao.getQtdeAnimais() + "\n" +
                        texto[4] + " " + adocao.getLocalAnimais() + "\n" +
                        texto[5] + " " + adocao.getPermanecerAnimais() + "\n" +
                        texto[6] + " " + adocao.getAnimaisAtual() + "\n" +
                        texto[7] + " " + adocao.getAnimalFalecido() + "\n" +
                        texto[8] + " " + adocao.getSustentarAnimal() + "\n" +
                        texto[9] + " " + adocao.getVizinhosAnimal() + "\n" +
                        texto[10] + " " + adocao.getPasseioAnimal() + "\n" +
                        texto[11] + " " + adocao.getCustosAnimal() + "\n" +
                        texto[12] + " " + adocao.getAlergiaAnimal() + "\n" +
                        texto[13] + " " + adocao.getRespeitoAnimal() + "\n" +
                        texto[14] + " " + adocao.getCriancaAnimal() + "\n" +
                        texto[15] + " " + adocao.getHorasAnimal() + "\n" +
                        texto[16] + " " + adocao.getViajarAnimal() + "\n" +
                        texto[17] + " " + adocao.getFugirAnimal() + "\n" +
                        texto[18] + " " + adocao.getCriarAnimal() + "\n" +
                        texto[19] + " " + adocao.getVeterinario());
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        adocao.setData(df.format(c));

        //chaves estrangerias
        adocao.setCPFAdotante(adotante.getCpf());
        adocao.setIDAnimal(animal.getId_animal());

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