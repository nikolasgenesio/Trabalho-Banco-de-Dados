package com.example.pets_donation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.pets_donation.Adotante;
import com.example.pets_donation.Lib.Conexao_Banco;

import java.util.ArrayList;
import java.util.List;

public class AdotanteDAO {

    //declarando as variaveis
    private Conexao_Banco conexaoBanco;
    private SQLiteDatabase banco;

    /**
     * Construtor
     * @param context
     */
    public AdotanteDAO(Context context)
    {
        conexaoBanco = new Conexao_Banco(context);
        banco = conexaoBanco.getWritableDatabase();
    }

    /**
     * Funcao para inserir no banco de dados
     * @param adotante Adotante a ser inserido
     * @return verdadeiro se inseriu e falso, caso contrario
     */
    public Boolean inserir(Adotante adotante)
    {
        ContentValues values, adotante1;
        values = new ContentValues();
        values.put("nome", adotante.getNome());
        values.put("dataNascimento", adotante.getDataNascimento());
        values.put("sexo", adotante.getSexo());
        values.put("tipoTel", adotante.getTipoTelefone());
        values.put("telefone", adotante.getTelefone());

        values.put("CEP", adotante.getCep());
        values.put("UF", adotante.getEstado());
        values.put("cidade", adotante.getCidade());
        values.put("bairro", adotante.getBairro());
        values.put("rua", adotante.getRua());
        values.put("numero", adotante.getNumero());

        values.put("CPF", adotante.getCpf());
        values.put("senha", adotante.getSenha());

        values.put("foto", (byte[]) null);
        long inserir = banco.insert("usuario", null, values);
        if(inserir == -1) {
            Log.i("Tarefa 1 - status Tela", "ENTROUU: " + adotante.getNome());

            return false;
        }
        else
        {
            Log.i("Tarefa 1 - status Tela", "ENTROU: " + adotante.getNome());

            adotante1 = new ContentValues();
            adotante1.put("rendaMensal", adotante.getRendaMensal());
            adotante1.put("CPF", adotante.getCpf());
            inserir = banco.insert("adotante", null, adotante1);
            return inserir != -1;
        }
    }

    /**
     * Funcao para obter todos os adotantes cadastrados no banco
     * @return lista de adotantes
     */
    public List<Adotante> obterTodosAdotantes()
    {
        return conexaoBanco.adotanteList();
    }

}
