package com.example.pets_donation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
        ContentValues values = new ContentValues();
        values.put("NOME", adotante.getNome());
        values.put("DATA_DE_NASCIMENTO", adotante.getDataNascimento());
        values.put("SEXO", adotante.getSexo());
        values.put("TIPO_TELEFONE", adotante.getTipoTelefone());
        values.put("TELEFONE", adotante.getTelefone());
        values.put("EMAIL", adotante.getEmail());
        values.put("RENDA_MENSAL", adotante.getRendaMensal());
        values.put("CEP", adotante.getCep());
        values.put("ESTADO", adotante.getEstado());
        values.put("CIDADE", adotante.getCidade());
        values.put("BAIRRO", adotante.getBairro());
        values.put("RUA", adotante.getRua());
        values.put("NUMERO", adotante.getNumero());
        values.put("CPF", adotante.getCpf());
        values.put("SENHA", adotante.getSenha());
        values.put("FOTO", (byte[]) null);
        long inserir = banco.insert("adotante", null, values);
        if(inserir == -1)
            return false;
        else
            return true;
    }

    /**
     * Funcao para obter todos os adotantes cadastrados no banco
     * @return
     */
    public List<Adotante> obterTodosAdotantes()
    {
        List<Adotante> adotanteList = new ArrayList<>();
        Cursor cursor = banco.query("adotante", new String[]{"NOME, DATA_DE_NASCIMENTO, SEXO," +
                "TIPO_TELEFONE, TELEFONE, EMAIL, RENDA_MENSAL, CEP, ESTADO, CIDADE, " +
                        "BAIRRO, RUA, NUMERO, CPF, SENHA, FOTO"}, null, null,
                null, null, null);

        //deslocar nas linhas da tabela
        while(cursor.moveToNext())
        {
            Adotante adotante = new Adotante();
            adotante.setNome(cursor.getString(0));
            adotante.setDataNascimento(cursor.getString(1));
            adotante.setSexo(cursor.getString(2));
            adotante.setTipoTelefone(cursor.getString(3));
            adotante.setTelefone(cursor.getString(4));
            adotante.setEmail(cursor.getString(5));
            String rendaMensal1 = cursor.getString(6);
            adotante.setRendaMensal(Double.parseDouble(rendaMensal1));
            adotante.setCep(cursor.getString(7));
            adotante.setEstado(cursor.getString(8));
            adotante.setCidade(cursor.getString(9));
            adotante.setBairro(cursor.getString(10));
            adotante.setRua(cursor.getString(11));
            adotante.setNumero(cursor.getString(12));
            adotante.setCpf(cursor.getString(13));
            adotante.setSenha(cursor.getString(14));
            byte[] imageByte = cursor.getBlob(15);
            if (imageByte != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                adotante.setFoto(bitmap);
            }
            adotanteList.add(adotante);
        }
        return adotanteList;
    }

    public boolean excluir(Adotante adotante) {
        long deletar = banco.delete("adotante", "CPF = ?", new String[]{adotante.getCpf()});
        if (deletar == -1)
            return false;
        else
            return true;
    }

    public boolean alterar(Adotante adotante) {
        ContentValues values = new ContentValues();
        values.put("NOME", adotante.getNome());
        values.put("DATA_DE_NASCIMENTO", adotante.getDataNascimento());
        values.put("SEXO", adotante.getSexo());
        values.put("TIPO_TELEFONE", adotante.getTipoTelefone());
        values.put("TELEFONE", adotante.getTelefone());
        values.put("EMAIL", adotante.getEmail());
        values.put("RENDA_MENSAL", adotante.getRendaMensal());
        values.put("CEP", adotante.getCep());
        values.put("ESTADO", adotante.getEstado());
        values.put("CIDADE", adotante.getCidade());
        values.put("BAIRRO", adotante.getBairro());
        values.put("RUA", adotante.getRua());
        values.put("NUMERO", adotante.getNumero());
        values.put("CPF", adotante.getCpf());
        values.put("SENHA", adotante.getSenha());
        long inserir = banco.update("adotante", values, "CPF = ?", new String[]{adotante.getCpf()});
        if (inserir == -1)
            return false;
        else
            return true;
    }




}
