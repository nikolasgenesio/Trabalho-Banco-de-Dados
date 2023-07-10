package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.AbrigoDAO;
import com.example.pets_donation.Models.AnimalDAO;

import java.util.List;

public class AdotanteVisualiza_Animal extends AppCompatActivity {

    //declaracao das variaveis
    private RadioButton cachorro, gato;
    private RadioButton macho, femea;
    private TextView nome, idade, cor, raca, porteFisico, vacinacao;
    private String tipo, genero;
    private Spinner spinner;
    private Button btnCancelar, btnConfirmar;


    private ImageView imageViewAnimal;
    private static final int PICK_STORAGE_REQUEST = 99;
    private Uri imagePath;
    private Bitmap imageToStore = null;
    private Uri imageUri;
    private ContentValues values;

    private AbrigoDAO abrigoDAO;
    private List<Abrigo> abrigoList;

    private AnimalDAO animalDAO;
    private Conexao_Banco banco;
    private Adotante adotante;
    private Animal animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adotante_visualiza_animal);

        getSupportActionBar().setTitle("Animal Selecionado");

        //atribuicoes
        animalDAO = new AnimalDAO(this);
        banco = new Conexao_Banco(this);
        this.adotante = (Adotante) getIntent().getSerializableExtra("adotante");
        this.animal = (Animal) getIntent().getSerializableExtra("animal");
        abrigoDAO = new AbrigoDAO(this);
        abrigoList = abrigoDAO.obterTodosAbrigos();


        nome = findViewById(R.id.nomeAnimal);
        idade = findViewById(R.id.idade);
        cor = findViewById(R.id.cor);
        raca = findViewById(R.id.raca);
        porteFisico = findViewById(R.id.porte);
        vacinacao = findViewById(R.id.vacinacao);


        nome.setFocusable(false);
        idade.setFocusable(false);
        cor.setFocusable(false);
        raca.setFocusable(false);
        porteFisico.setFocusable(false);
        vacinacao.setFocusable(false);


        cachorro = (RadioButton) findViewById(R.id.radioButtonCachorro);
        gato = (RadioButton) findViewById(R.id.radioButtonGato);
        macho = (RadioButton) findViewById(R.id.radioButtonMacho);
        femea = (RadioButton) findViewById(R.id.radioButtonFemea);
        spinner = (Spinner) findViewById(R.id.spinnerAbrigo);

        imageViewAnimal = findViewById(R.id.imageViewAnimal);

        btnConfirmar = findViewById(R.id.btnConfirma);
        btnCancelar = findViewById(R.id.btnCancela);

        preencheInformacoes();

        //clique no botao de cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //clique no botao de confirmar
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdotanteVisualiza_Animal.this, QuestionarioAdotante.class);
                intent.putExtra("adotante", adotante);
                intent.putExtra("animal", animal);
                startActivity(intent);
            }
        });
    }

    /**
     * Preencher informacoes do animal
     */
    public void preencheInformacoes() {
        nome.setText("Nome: " + animal.getNome());
        idade.setText("Idade: " + animal.getIdade());
        cor.setText("Cor: " + animal.getCor());
        raca.setText("Raça: " + animal.getRaca());
        porteFisico.setText("Porte Físico: " + animal.getPorteFisico());


        if (animal.getTipo().equals("Cachorro")) {
            cachorro.setChecked(true);
        } else if (animal.getTipo().equals("Gato")) {
            gato.setChecked(true);
        }

        if (animal.getGenero().equals("Macho")) {
            macho.setChecked(true);
        } else if (animal.getGenero().equals("Fêmea")) {
            femea.setChecked(true);
        }

        cachorro.setEnabled(false);
        gato.setEnabled(false);
        macho.setEnabled(false);
        femea.setEnabled(false);

        ArrayAdapter<Abrigo> adp1 = new ArrayAdapter<Abrigo>(this, android.R.layout.simple_list_item_1, abrigoList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);

        String selecionado = banco.retornaNomeAbrigo(animal.getId_abrigo());
        spinner.setSelection(getIndex(spinner, selecionado));
        spinner.setEnabled(false);

        Bitmap imagem = banco.retornaFotoAnimal(animal);
        if (imagem != null) {
            imageViewAnimal.setImageBitmap(imagem);
            imageToStore = imagem;
        }
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }
}