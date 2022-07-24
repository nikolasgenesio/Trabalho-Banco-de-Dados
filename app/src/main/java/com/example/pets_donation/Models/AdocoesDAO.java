package com.example.pets_donation.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pets_donation.Adocoes;
import com.example.pets_donation.Animal;
import com.example.pets_donation.Lib.Conexao_Banco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdocoesDAO {

    private Conexao_Banco conexaoBanco;
    private SQLiteDatabase banco;

    public AdocoesDAO(Context context) {
        conexaoBanco = new Conexao_Banco(context);
        banco = conexaoBanco.getWritableDatabase();
    }

    public List<Adocoes> obterTodosAnimaisAdotados() {
        List<Adocoes> adocoesList = new ArrayList<>();
        Cursor cursor = banco.query("adocaoDeferida", new String[]{"ID, DATA, CPF_ADOTANTE, ID_ANIMAL," +
                        "CPF_FUNCIONARIO"}, null, null,
                null, null, null);

        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {
            Adocoes adocoes = new Adocoes();
            adocoes.setID(cursor.getInt(0));
            adocoes.setData(cursor.getString(1));
            adocoes.setCPF_Adotante(cursor.getString(2));
            adocoes.setID_Animal(cursor.getInt(3));
            adocoes.setCPF_Funcionario(cursor.getString(4));
            adocoesList.add(adocoes);
        }
        return adocoesList;
    }

}
