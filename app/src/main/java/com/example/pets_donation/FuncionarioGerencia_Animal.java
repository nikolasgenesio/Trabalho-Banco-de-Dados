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

public class FuncionarioGerencia_Animal extends AppCompatActivity {

    private RadioButton cachorro, gato;
    private RadioButton macho, femea;
    private EditText nome, idade, cor, raca, porteFisico, vacinacao;
    private String tipo, genero;
    private Spinner spinner;
    private Button btnAlterar, btnCancelar, btnDeletar;


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
    private Funcionario funcionario;
    private Animal animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_gerencia_animal);

        getSupportActionBar().setTitle("CADASTRO - ANIMAL");

        animalDAO = new AnimalDAO(this);
        banco = new Conexao_Banco(this);
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        this.animal = (Animal) getIntent().getSerializableExtra("animal");
        abrigoDAO = new AbrigoDAO(this);
        abrigoList = abrigoDAO.obterTodosAbrigos();


        nome = findViewById(R.id.nomeAnimal);
        idade = findViewById(R.id.idade);
        cor = findViewById(R.id.cor);
        raca = findViewById(R.id.raca);
        porteFisico = findViewById(R.id.porte);
        vacinacao = findViewById(R.id.vacinacao);

        cachorro = (RadioButton) findViewById(R.id.radioButtonCachorro);
        gato = (RadioButton) findViewById(R.id.radioButtonGato);
        macho = (RadioButton) findViewById(R.id.radioButtonMacho);
        femea = (RadioButton) findViewById(R.id.radioButtonFemea);
        spinner = (Spinner) findViewById(R.id.spinnerAbrigo);

        imageViewAnimal = findViewById(R.id.imageViewAnimal);

        btnAlterar = findViewById(R.id.btnAltera);
        btnDeletar = findViewById(R.id.btnDeleta);
        btnCancelar = findViewById(R.id.btnCancela);

        preencheInformacoes();


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
                finish();
            }
        });

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterar_Animal();
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar_Animal();
            }
        });

    }

    public void preencheInformacoes() {
        nome.setText(animal.getNome());
        idade.setText(animal.getIdade());
        cor.setText(animal.getCor());
        raca.setText(animal.getRaca());
        porteFisico.setText(animal.getPortFisico());

        String str = String.join(", ", animal.getVacinacao());
        vacinacao.setText(str);

        if (animal.getTipo().equals("Cachorro")) {
            cachorro.setChecked(true);
        } else if (animal.getTipo().equals("Gato")) {
            gato.setChecked(true);
        }

        if (animal.getGenero().equals("Macho")) {
            macho.setChecked(true);
        } else if (animal.getTipo().equals("Fêmea")) {
            femea.setChecked(true);
        }

        ArrayAdapter<Abrigo> adp1 = new ArrayAdapter<Abrigo>(this, android.R.layout.simple_list_item_1, abrigoList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);

        String selecionado = banco.retornaNomeAbrigo(animal.getIDAbrigo());
        spinner.setSelection(getIndex(spinner, selecionado));

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


    public void alterar_Animal() {
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
        String cor1 = idade.getText().toString();
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

        String vacinacao1 = vacinacao.getText().toString();
        if (vacinacao1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a vacinação", Toast.LENGTH_SHORT).show();
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

        animal.setNome(nome1);
        animal.setTipo(tipo);
        animal.setIdade(idade1);
        animal.setCor(cor1);
        animal.setRaca(raca1);
        animal.setGenero(genero);
        animal.setPortFisico(porteFisico1);

        List<String> vacinacoes = Arrays.asList(vacinacao1.split("\\s*,\\s*"));

        animal.setVacinacao(vacinacoes);
        animal.setFoto(imageToStore);

        String Nomeabrigo = spinner.getSelectedItem().toString();
        animal.setIDAbrigo(banco.retornaIDAbrigo(Nomeabrigo));

        boolean alterar = animalDAO.alterar(animal);
        if (alterar) {
            Toast.makeText(getApplicationContext(), "Animal alterado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Listar_Animais.class);
            intent.putExtra("funcionario", funcionario);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Animal não alterado", Toast.LENGTH_SHORT).show();
        }
    }


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

    public void deletar_Animal() {
        boolean deletar = animalDAO.excluir(animal);
        if (deletar) {
            Toast.makeText(getApplicationContext(), "Animal excluído", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Listar_Animais.class);
            intent.putExtra("funcionario", funcionario);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Animal não excluído", Toast.LENGTH_SHORT).show();
        }
    }

}