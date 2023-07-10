package com.example.pets_donation.ui.fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pets_donation.Funcionario;
import com.example.pets_donation.Funcionario_Duvidas;
import com.example.pets_donation.Funcionario_ListCadastros;
import com.example.pets_donation.GerenciaFuncionarios_Dados;
import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.MainActivity;
import com.example.pets_donation.R;

import java.util.ArrayList;
import java.util.List;

public class AministradorPerfil_Fragment extends Fragment {

    //declaracao das variaveis
    private Funcionario funcionario;
    private ListView listView;
    private CircleImageView circleImageView;
    String fotoEmString = "";
    private TextView textView;
    private Conexao_Banco banco;


    private static final int PICK_IMAGE_REQUEST = 99;
    private static final int PICK_STORAGE_REQUEST = 99;
    private Uri imagePath;
    private Bitmap imageToStore;
    private Uri imageUri;
    private ContentValues values;

    String[] maintitle = {
            "Dados Pessoais",
            "Dúvidas Frequentes",
            "Sair",
    };

    String[] subtitle = {
            "Clique aqui para editar seus dados pessoais", "Verifique as dúvidas frequentes dos funcionários"
            , "Sair do aplicativo",
    };

    Integer[] imgid = {
            R.drawable.perfil_vector, R.drawable.duvida_vector,
            R.drawable.sair_vector,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.funcionario = (Funcionario) getActivity().getIntent().getSerializableExtra("funcionario");
        banco = new Conexao_Banco(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Perfil");
        return inflater.inflate(R.layout.fragment_aministrador_perfil_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.listViewPerfilAdm);
        circleImageView = view.findViewById(R.id.circleImageViewAdm);
        textView = view.findViewById(R.id.textNomePerfilAdm);
        textView.setText(funcionario.getNome());

        //selecao
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Nova Imagem");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sua camera");
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //verifica se ja existe imagem
        Bitmap imagem = banco.retornaFotoFuncionario(funcionario.getCpf(), funcionario.getSenha());
        if (imagem != null) {
            Log.i("Funcionario FOTO", "NOME2: " + funcionario.getNome());
            circleImageView.setImageBitmap(imagem);
        }
        Log.i("Funcionario FOTO", "NOME1: " + imagem);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });
        List<String> list = new ArrayList<>();
        list.add("Dados Pessoais");
        list.add("Dúvidas frequentes");
        list.add("Sair");

        Funcionario_ListCadastros adapter = new Funcionario_ListCadastros(getActivity(), maintitle, subtitle, imgid);

        //exibir lista adaptada
        //ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        //abrir novas tela de acordo com a selecao
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(getActivity(), GerenciaFuncionarios_Dados.class);
                        intent.putExtra("funcionario", funcionario);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(getActivity(), Funcionario_Duvidas.class);
                        intent.putExtra("funcionario", funcionario);
                        startActivity(intent);
                        break;
                    }
                    default:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Realmente deseja sair?");

                        // set dialog message
                        builder
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //Menu_Alergia.this.finish();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = builder.create();

                        // show it
                        alertDialog.show();
                        //openDialog();
                        break;
                }
            }
        });
    }

    /**
     * Funcao para abrir a galeria e selecionar imagem
     */
    private void imagePickDialog() {
        String[] options = {"Abrir a galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
                imageToStore = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagePath);
                circleImageView.setImageBitmap(imageToStore);
                banco.alterarFotoFuncionario(funcionario.getCpf(), imageToStore);
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Funcao para escolher imagem
     */
    private void chooseImage() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_STORAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}