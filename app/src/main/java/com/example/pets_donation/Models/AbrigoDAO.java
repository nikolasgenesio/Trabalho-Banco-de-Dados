package com.example.pets_donation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pets_donation.Abrigo;
import com.example.pets_donation.Lib.Conexao_Banco;

import java.util.ArrayList;
import java.util.List;

public class AbrigoDAO {
    //declarando as variaveis
    private Conexao_Banco conexaoBanco;
    private SQLiteDatabase banco;

    public AbrigoDAO(Context context) {
        conexaoBanco = new Conexao_Banco(context);
        banco = conexaoBanco.getWritableDatabase();
    }

    public long inserir(Abrigo abrigo) {
        ContentValues values = new ContentValues();
        values.put("NOME", abrigo.getNome());
        values.put("TIPO_TELEFONE", abrigo.getTipoTelefone());
        values.put("TELEFONE", abrigo.getTelefone());
        values.put("CEP", abrigo.getCep());
        values.put("ESTADO", abrigo.getEstado());
        values.put("CIDADE", abrigo.getCidade());
        values.put("BAIRRO", abrigo.getBairro());
        values.put("RUA", abrigo.getRua());
        values.put("NUMERO", abrigo.getNumero());
        return banco.insert("abrigo", null, values);
    }

    public List<Abrigo> obterTodosAbrigos() {
        List<Abrigo> abrigoList = new ArrayList<>();
        Cursor cursor = banco.query("abrigo", new String[]{"ID, NOME, TIPO_TELEFONE, TELEFONE," +
                        "CEP, ESTADO, CIDADE, BAIRRO, RUA, NUMERO"}, null, null,
                null, null, null);

        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {
            Abrigo abrigo = new Abrigo();
            abrigo.setID(cursor.getInt(0));
            abrigo.setNome(cursor.getString(1));
            abrigo.setTipoTelefone(cursor.getString(2));
            abrigo.setTelefone(cursor.getString(3));
            abrigo.setCep(cursor.getString(4));
            abrigo.setEstado(cursor.getString(5));
            abrigo.setCidade(cursor.getString(6));
            abrigo.setBairro(cursor.getString(7));
            abrigo.setRua(cursor.getString(8));
            abrigo.setNumero(cursor.getString(9));
            abrigoList.add(abrigo);
        }
        return abrigoList;
    }


    public boolean excluir(Abrigo abrigo) {

        long deletar = banco.delete("abrigo", "ID = ?", new String[]{abrigo.getID().toString()});
        if (deletar == -1)
            return false;
        else
            return true;
    }

    public boolean alterar(Abrigo abrigo) {
        ContentValues values = new ContentValues();

        Log.i("Abrigoo", "NOME2: " + abrigo.getNome());

        values.put("NOME", abrigo.getNome());
        values.put("TIPO_TELEFONE", abrigo.getTipoTelefone());
        values.put("TELEFONE", abrigo.getTelefone());
        values.put("CEP", abrigo.getCep());
        values.put("ESTADO", abrigo.getEstado());
        values.put("CIDADE", abrigo.getCidade());
        values.put("BAIRRO", abrigo.getBairro());
        values.put("RUA", abrigo.getRua());
        values.put("NUMERO", abrigo.getNumero());
        long inserir = banco.update("abrigo", values, "ID = ?", new String[]{abrigo.getID().toString()});
        if (inserir == -1)
            return false;
        else
            return true;
    }
}
