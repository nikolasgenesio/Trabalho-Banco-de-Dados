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

    /**
     * Funcao para inserir abrigo no banco
     * @param abrigo abrigo
     * @return insercao do abrigo
     */
    public Boolean inserir(Abrigo abrigo) {
        ContentValues values, telefone;
        values = new ContentValues();
        values.put("nome", abrigo.getNome());
        values.put("CEP", abrigo.getCep());
        values.put("rua", abrigo.getRua());
        values.put("numero", abrigo.getNumero());
        values.put("bairro", abrigo.getBairro());
        values.put("cidade", abrigo.getCidade());
        values.put("UF", abrigo.getEstado());
        values.put("CPF_Secretario", abrigo.getCPF_Secretario());
        long insert = banco.insert("abrigo", null, values);
        if(insert == -1)
        {
            return false;
        }
        else
        {
            telefone = new ContentValues();
            telefone.put("id_abrigo", conexaoBanco.retornaIDAbrigo(abrigo.getNome()));
            telefone.put("tipoTel", abrigo.getTipoTelefone());
            telefone.put("numero", abrigo.getTelefone());
            insert = banco.insert("linhas_telefonicas", null, telefone);
            return insert != -1;
        }
    }

    /**
     * Funcao para obter todos os abrigos
     * @return lista de abrigos
     */
    public List<Abrigo> obterTodosAbrigos() {
        List<Abrigo> abrigoList = new ArrayList<>();
        Cursor cursor = banco.query("abrigo", new String[]{"id_abrigo, nome, CEP, rua," +
                        "numero, bairro, cidade, UF, CPF_Secretario"}, null, null,
                null, null, null);

        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {
            Abrigo abrigo = new Abrigo();
            abrigo.setID(cursor.getInt(0));
            abrigo.setNome(cursor.getString(1));
            abrigo.setCep(cursor.getString(2));
            abrigo.setRua(cursor.getString(3));
            abrigo.setNumero(cursor.getString(4));
            abrigo.setBairro(cursor.getString(5));
            abrigo.setCidade(cursor.getString(6));
            abrigo.setEstado(cursor.getString(7));
            abrigo.setCPF_Secretario(cursor.getString(8));
            abrigoList.add(abrigo);
        }
        return abrigoList;
    }

}
