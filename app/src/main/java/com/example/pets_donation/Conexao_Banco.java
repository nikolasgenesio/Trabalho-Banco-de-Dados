package com.example.pets_donation;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Conexao_Banco extends SQLiteOpenHelper {

    //declarando as variaveis
    private static final String name = "pets_donation.db";
    private static final int version = 1;

    public Conexao_Banco(@Nullable Context context) {
        super(context, name, null, version);
    }

    /**
     * Criar banco
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table adotante(NOME varchar(100), " +
                "DATA_DE_NASCIMENTO varchar(10), SEXO varchar(9), TIPO_TELEFONE varchar(7)," +
                "TELEFONE varchar(14), EMAIL varchar(100), RENDA_MENSAL varchar(50)," +
                "CEP varchar(9), ESTADO varchar(2), CIDADE varchar(100), BAIRRO varchar(100)," +
                "RUA varchar(100), NUMERO varchar(100), CPF varchar(14), SENHA varchar(100)," +
                "PRIMARY KEY(CPF))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
