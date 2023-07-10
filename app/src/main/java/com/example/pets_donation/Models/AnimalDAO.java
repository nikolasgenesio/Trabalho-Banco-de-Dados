package com.example.pets_donation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pets_donation.Adocoes;
import com.example.pets_donation.Animal;
import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.ProcessoAdocao;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnimalDAO {

    //declarando as variaveis
    private Conexao_Banco conexaoBanco;
    private SQLiteDatabase banco;

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;

    public AnimalDAO(Context context) {
        conexaoBanco = new Conexao_Banco(context);
        banco = conexaoBanco.getWritableDatabase();
    }

    /**
     * Funcao para inserir animal no banco
     * @param animal animal a ser inserido
     * @return verdadeiro se inseriu e falso, caso contrario
     */
    public Boolean inserir(Animal animal) {
        ContentValues values = new ContentValues();
        values.put("nome", animal.getNome());
        values.put("tipo", animal.getTipo());
        values.put("idade", animal.getIdade());
        values.put("cor", animal.getCor());
        values.put("raca", animal.getRaca());
        values.put("genero", animal.getGenero());
        values.put("porte_fisico", animal.getPorteFisico());


        byteArrayOutputStream = new ByteArrayOutputStream();
        animal.getFoto().compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInBytes = byteArrayOutputStream.toByteArray();

        values.put("foto", imageInBytes);
        values.put("id_abrigo", animal.getId_abrigo());
        values.put("CPF_Secretario", animal.getCPF_Secretario());
        values.put("CPF_Fiscal", animal.getCPF_Fiscal());
        long inserir = banco.insert("animal", null, values);
        if (inserir == -1)
            return false;
        else
            return true;
    }

    /**
     * Funcao para obter todos os animais do banco
     * @return lista de animais
     */
    public List<Animal> obterTodosAnimais() {
        return conexaoBanco.animalList();
    }

}
