package com.example.pets_donation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.AbrigoDAO;
import com.example.pets_donation.Models.AnimalDAO;

import java.util.Arrays;
import java.util.List;

public class Cadastro_Animal extends AppCompatActivity {

    //declaracao das variaveis
    private RadioButton cachorro, gato;
    private RadioButton macho, femea;
    private EditText nome, idade, cor, raca, porteFisico;
    private String tipo, genero;
    private Spinner spinner;
    private Button btnLimpar, btnCancelar, btnAvancar;


    private ImageView imageViewAnimal;
    private static final int PICK_STORAGE_REQUEST = 99;
    private Uri imagePath;
    private Bitmap imageToStore = null;
    private Uri imageUri;
    private ContentValues values;

    private AnimalDAO animalDAO;
    private Conexao_Banco banco;
    private Funcionario funcionario;

    private AbrigoDAO abrigoDAO;
    private List<Abrigo> abrigoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_animal);
        getSupportActionBar().setTitle("Cadastro - Animal");

        //iniciando variaveis
        animalDAO = new AnimalDAO(this);
        banco = new Conexao_Banco(this);
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        abrigoDAO = new AbrigoDAO(this);
        abrigoList = abrigoDAO.obterTodosAbrigos();

        nome = findViewById(R.id.nomeAnimal);
        idade = findViewById(R.id.idade);
        cor = findViewById(R.id.cor);
        raca = findViewById(R.id.raca);
        porteFisico = findViewById(R.id.porte);

        cachorro = (RadioButton) findViewById(R.id.radioButtonCachorro);
        gato = (RadioButton) findViewById(R.id.radioButtonGato);
        macho = (RadioButton) findViewById(R.id.radioButtonMacho);
        femea = (RadioButton) findViewById(R.id.radioButtonFemea);
        spinner = (Spinner) findViewById(R.id.spinnerAbrigo);

        imageViewAnimal = findViewById(R.id.imageViewAnimal);


        ArrayAdapter<Abrigo> adp1 = new ArrayAdapter<Abrigo>(this, android.R.layout.simple_list_item_1, abrigoList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);

        btnAvancar = findViewById(R.id.btnConfirma);
        btnCancelar = findViewById(R.id.btnCancela);
        btnLimpar = findViewById(R.id.btnLimpa);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Nomeabrigo = spinner.getSelectedItem().toString();
                Abrigo abrigo = banco.obterNomeAbrigo(Nomeabrigo);
                Toast.makeText(getApplicationContext(), "Nome: " + abrigo.getNome() +
                        "\nEstado: " + abrigo.getEstado() +
                        "\nCidade: " + abrigo.getCidade() +
                        "\nBairro: " + abrigo.getBairro() +
                        "\nRua: " + abrigo.getRua() +
                        "\nNúmero: " + abrigo.getNumero(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imageViewAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        //botao de cancelamento
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (funcionario == null) {
                    finish();
                }
            }
        });

        //botao de limpar
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome.setText("");
                idade.setText("");
                cor.setText("");
                raca.setText("");
                porteFisico.setText("");
            }
        });

        //botao de adicionar
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar_Animal();
            }
        });
    }

    /**
     * Funcao para limpar dados
     */
    public void limparDados() {
        nome.setText("");
        idade.setText("");
        cor.setText("");
        raca.setText("");
        porteFisico.setText("");
    }

    /**
     * Funcao para adicionar animal
     */
    public void adicionar_Animal() {

        String nome1 = nome.getText().toString();
        if (nome1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o nome", Toast.LENGTH_SHORT).show();
            return;
        }

        String idade1 = idade.getText().toString();
        if (idade1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a idade", Toast.LENGTH_SHORT).show();
            return;
        }
        String cor1 = cor.getText().toString();
        if (idade1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a idade", Toast.LENGTH_SHORT).show();
            return;
        }

        String raca1 = raca.getText().toString();
        if (raca1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a raça", Toast.LENGTH_SHORT).show();
            return;
        }

        String porteFisico1 = porteFisico.getText().toString();
        if (porteFisico1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o porte físico", Toast.LENGTH_SHORT).show();
            return;
        }

        if (macho.isChecked()) {
            genero = "Macho";
        }
        if (femea.isChecked()) {
            genero = "Fêmea";
        }

        if (cachorro.isChecked()) {
            tipo = "Cachorro";
        }
        if (gato.isChecked()) {
            tipo = "Gato";
        }

        if (imageToStore == null) {
            Toast.makeText(getApplicationContext(), "Você não arquivou a foto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (abrigoList == null || abrigoList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Cadastre o abrigo", Toast.LENGTH_SHORT).show();
            return;
        }

        Animal animal = new Animal();
        animal.setNome(nome1);
        animal.setTipo(tipo);
        animal.setIdade(idade1);
        animal.setCor(cor1);
        animal.setRaca(raca1);
        animal.setGenero(genero);
        animal.setPorteFisico(porteFisico1);

        animal.setFoto(imageToStore);

        String Nomeabrigo = spinner.getSelectedItem().toString();
        animal.setId_abrigo(banco.retornaIDAbrigo(Nomeabrigo));
        animal.setCPF_Secretario(funcionario.getCpf());

        Boolean inserir = animalDAO.inserir(animal);

        if (inserir) {
            Toast.makeText(getApplicationContext(), "CADASTRO COM SUCESSO!", Toast.LENGTH_SHORT).show();
            limparDados();
            Intent intent = new Intent(Cadastro_Animal.this, Tela_Funcionario.class);
            intent.putExtra("funcionario", funcionario);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(), "ERRO NO CADASTRO!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Funcao para abrir galeria e selecionar imagem
     */
    private void imagePickDialog() {
        String[] options = {"Abrir a galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Selecione a imagem:");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooseImage();
            }
        });
        builder.create().show();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_STORAGE_REQUEST && resultCode == -1 && data != null && data.getData() != null) {
                imagePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                imageViewAnimal.setImageBitmap(imageToStore);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Funcao para selecionar imagem
     */
    private void chooseImage() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_STORAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}