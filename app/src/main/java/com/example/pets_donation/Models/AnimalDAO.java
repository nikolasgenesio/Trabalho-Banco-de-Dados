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

    public Boolean inserir(Animal animal) {
        ContentValues values = new ContentValues();
        values.put("NOME", animal.getNome());
        values.put("TIPO", animal.getTipo());
        values.put("IDADE", animal.getIdade());
        values.put("COR", animal.getCor());
        values.put("RACA", animal.getRaca());
        values.put("GENERO", animal.getGenero());
        values.put("PORTE_FISICO", animal.getPortFisico());

        String listString = String.join(", ", animal.getVacinacao());
        values.put("VACINACAO", listString);

        byteArrayOutputStream = new ByteArrayOutputStream();
        animal.getFoto().compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInBytes = byteArrayOutputStream.toByteArray();

        values.put("FOTO", imageInBytes);
        values.put("ID_ABRIGO", animal.getIDAbrigo());
        values.put("CPF_FUNCIONARIO", animal.getCPF_Funcionario());
        long inserir = banco.insert("animal", null, values);
        if (inserir == -1)
            return false;
        else
            return true;
    }

    public List<Animal> obterTodosAnimais() {
        List<Animal> animalList = new ArrayList<>();
        Cursor cursor = banco.query("animal", new String[]{"ID, NOME, TIPO, IDADE," +
                        "COR, RACA, GENERO, PORTE_FISICO, VACINACAO, FOTO, ID_ABRIGO, CPF_FUNCIONARIO"}, null, null,
                null, null, null);

        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {
            Animal animal = new Animal();
            animal.setID(cursor.getInt(0));
            animal.setNome(cursor.getString(1));
            animal.setTipo(cursor.getString(2));
            animal.setIdade(cursor.getString(3));
            animal.setCor(cursor.getString(4));
            animal.setRaca(cursor.getString(5));
            animal.setGenero(cursor.getString(6));
            animal.setPortFisico(cursor.getString(7));
            List<String> vacinacoes = Arrays.asList(cursor.getString(8).split("\\s*,\\s*"));
            animal.setVacinacao(vacinacoes);
            byte[] imageByte = cursor.getBlob(9);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            animal.setFoto(bitmap);
            animal.setIDAbrigo(cursor.getInt(10));
            animal.setCPF_Funcionario(cursor.getString(11));
            animalList.add(animal);
        }
        return animalList;
    }


    public boolean alterar(Animal animal) {
        ContentValues values = new ContentValues();
        values.put("NOME", animal.getNome());
        values.put("TIPO", animal.getTipo());
        values.put("IDADE", animal.getIdade());
        values.put("COR", animal.getCor());
        values.put("RACA", animal.getRaca());
        values.put("GENERO", animal.getGenero());
        values.put("PORTE_FISICO", animal.getPortFisico());
        String listString = String.join(", ", animal.getVacinacao());
        values.put("VACINACAO", listString);
        values.put("ID_ABRIGO", animal.getIDAbrigo());

        Bitmap imagem = animal.getFoto();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInBytes = byteArrayOutputStream.toByteArray();
        values.put("FOTO", imageInBytes);

        long inserir = banco.update("animal", values, "ID = ?", new String[]{animal.getID().toString()});
        if (inserir == -1)
            return false;
        else
            return true;
    }


    public boolean excluir(Animal animal) {
        long deletar = banco.delete("animal", "ID = ?", new String[]{animal.getID().toString()});
        if (deletar == -1)
            return false;
        else
            return true;
    }

}
