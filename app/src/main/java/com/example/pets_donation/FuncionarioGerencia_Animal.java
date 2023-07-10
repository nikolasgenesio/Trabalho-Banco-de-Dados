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
import android.view.MenuItem;
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

    //declaracao das variaveis
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Editar Animal");

        animalDAO = new AnimalDAO(this);
        banco = new Conexao_Banco(this);
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        this.animal = (Animal) getIntent().getSerializableExtra("animal");
        abrigoDAO = new AbrigoDAO(this);
        abrigoList = abrigoDAO.obterTodosAbrigos();

        //Inicializando as variaveis
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

        //botao de alterar
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alterar_Animal();
            }
        });

        //botao para deletar
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deletar_Animal();
            }
        });

    }

    /**
     * Funcao para preencher as informacoes
     */
    public void preencheInformacoes() {
        nome.setText(animal.getNome());
        idade.setText(animal.getIdade());
        cor.setText(animal.getCor());
        raca.setText(animal.getRaca());
        porteFisico.setText(animal.getPorteFisico());

//        String str = String.join(", ", animal.getVacinacao());
//        vacinacao.setText(str);

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

        String selecionado = banco.retornaNomeAbrigo(animal.getId_abrigo());
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



    /**
     * Funcao para abrir a galeria e selecionar imagem
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}