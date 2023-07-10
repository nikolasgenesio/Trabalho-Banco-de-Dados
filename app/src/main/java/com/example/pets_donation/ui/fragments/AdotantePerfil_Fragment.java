package com.example.pets_donation.ui.fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pets_donation.Adotante;
import com.example.pets_donation.GerenciaAdotantes_Dados;
import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.R;

import java.util.ArrayList;
import java.util.List;

public class AdotantePerfil_Fragment extends Fragment {

    //declaracao das variaveis
    private Adotante adotante;
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

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.adotante = (Adotante) getActivity().getIntent().getSerializableExtra("adotante");
        banco = new Conexao_Banco(getActivity());
        return inflater.inflate(R.layout.fragment_adotante_perfil_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.listViewPerfil);
        circleImageView = view.findViewById(R.id.circleImageView);
        textView = view.findViewById(R.id.textNomePerfil);
        textView.setText(adotante.getNome());

        //selecao
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Nova Imagem");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sua camera");
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


        //se ja existe imagem
        Bitmap imagem = banco.retornaFotoAdotante(adotante.getCpf(), adotante.getSenha());
        if (imagem != null) {
            circleImageView.setImageBitmap(imagem);
        }
        Log.i("Adotante FOTO", "NOME1: " + imagem);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });
        List<String> list = new ArrayList<>();
        list.add("Editar Dados Pessoais");

        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        //clique para gerenciar dados
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GerenciaAdotantes_Dados.class);
                intent.putExtra("adotante", adotante);
                startActivity(intent);
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
                //banco.alterarFotoAdotante(adotante.getCpf(), adotante.getSenha(), imageToStore);
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