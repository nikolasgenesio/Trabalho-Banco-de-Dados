package com.example.pets_donation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pets_donation.Lib.Conexao_Banco;

public class UsuarioDAO {

    //declarando as variaveis
    private Conexao_Banco conexaoBanco;
    private SQLiteDatabase banco;

    public UsuarioDAO(Context context) {
        conexaoBanco = new Conexao_Banco(context);
        banco = conexaoBanco.getWritableDatabase();
    }




}
