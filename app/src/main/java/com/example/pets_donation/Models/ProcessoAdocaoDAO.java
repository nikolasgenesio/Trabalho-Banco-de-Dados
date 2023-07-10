package com.example.pets_donation.Models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.pets_donation.Adocao_View;
import com.example.pets_donation.Animal;
import com.example.pets_donation.Funcionario;
import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.ProcessoAdocao;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProcessoAdocaoDAO {

    //declarando as variaveis
    private Conexao_Banco conexaoBanco;
    private SQLiteDatabase banco;

    public ProcessoAdocaoDAO(Context context) {
        conexaoBanco = new Conexao_Banco(context);
        banco = conexaoBanco.getWritableDatabase();
    }

    /**
     * Funcao para inserir adotante no banco
     * @param adocao adocao a ser inserida
     * @return verdadeiro se inseriu e falso, caso contrario
     */
    public Boolean inserir(ProcessoAdocao adocao) {
        ContentValues values = new ContentValues();

        values.put("data", adocao.getData());
        values.put("resposta", adocao.getStatus());

        values.put("questionario", adocao.getQuestionario());


        values.put("CPF_Secretario", adocao.getCPFSecretario());
        values.put("CPF_Adotante", adocao.getCPFAdotante());
        values.put("id_animal", adocao.getIDAnimal());

        long inserir = banco.insert("adocao", null, values);
        if (inserir == -1)
            return false;
        else
            return true;
    }

    /**
     * Funcao para retornar o status de uma adocao
     * @param CPF CPF do adotante
     * @return lista de processos
     */
    public List<Adocao_View> retornaStatusAdocaoAdotante(String CPF) {
        List<Adocao_View> adocaoList = new ArrayList<>();
        Cursor cursor = banco.query("adocao", new String[]{"id_adocao, data, resposta, questionario," +
                        "CPF_Secretario, CPF_Adotante, id_animal"}, "CPF_Adotante like " + "'" + CPF + "'", null,
                null, null, null);
        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {
            Adocao_View adocao = new Adocao_View();
            adocao.setId_adocao(cursor.getInt(0));
            adocao.setData(cursor.getString(1));
            adocao.setResposta(cursor.getString(2));
            adocao.setQuestionario(cursor.getString(3));
            adocao.setCPF_Secretario(cursor.getString(4));
            adocao.setCPFAdotante(cursor.getString(5));
            adocao.setId_animal(cursor.getInt(6));
            adocaoList.add(adocao);
        }
        return adocaoList;
    }


    /**
     * Funcao para retornar o relatorio de adocoes
     * @return lista de processos de adocao
     */
    public List<Adocao_View> retornaRelatorioAdocao() {
        List<Adocao_View> adocaoList = new ArrayList<>();
        Cursor cursor = banco.query("adocao", new String[]{"id_adocao, data, resposta, questionario," +
                        "CPF_Secretario, CPF_Adotante, id_animal"}, null, null,
                null, null, null);
        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {
            Adocao_View adocao = new Adocao_View();
            adocao.setId_adocao(cursor.getInt(0));
            adocao.setData(cursor.getString(1));
            adocao.setResposta(cursor.getString(2));
            adocao.setQuestionario(cursor.getString(3));
            adocao.setCPF_Secretario(cursor.getString(4));
            adocao.setCPFAdotante(cursor.getString(5));
            adocao.setId_animal(cursor.getInt(6));
            adocaoList.add(adocao);
        }
        return adocaoList;
    }

    /**
     * Funcao para retornar o relatorio de adocoes
     * @return lista de processos de adocao
     */
    public List<Adocao_View> retornaAnimaisAdocao() {
        List<Adocao_View> adocaoList = new ArrayList<>();
        Cursor cursor = banco.query("adocao", new String[]{"id_adocao, data, resposta, questionario," +
                        "CPF_Secretario, CPF_Adotante, id_animal"}, null, null,
                null, null, null);
        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {

            if((cursor.getString(2)).equals("Em Andamento")) {
                Adocao_View adocao = new Adocao_View();
                adocao.setId_adocao(cursor.getInt(0));
                adocao.setData(cursor.getString(1));
                adocao.setResposta(cursor.getString(2));
                adocao.setQuestionario(cursor.getString(3));
                adocao.setCPF_Secretario(cursor.getString(4));
                adocao.setCPFAdotante(cursor.getString(5));
                adocao.setId_animal(cursor.getInt(6));
                adocaoList.add(adocao);
            }
        }
        return adocaoList;
    }


    /**
     * Funcao para alterar processo status do processo de adocao
     * @param adocao processo de adocao
     * @return verdadeiro se alterou e falso, caso contrario
     */
    public boolean alterarStatus(Adocao_View adocao) {
        ContentValues values = new ContentValues();
        values.put("resposta", adocao.getResposta());
        long inserir = banco.update("adocao", values, "id_adocao = ?", new String[]{adocao.getId_adocao().toString()});
        if (inserir == -1)
            return false;
        else
            return true;
    }

    /**
     * Funcao para retornar a data atual
     * @return data atual
     */
    public String retornaDataAtual() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }



    /**
     * Funcao para listar processos de adocao de um certo animal
     * @param ID ID do animal
     * @return processos de adocao daquele animal
     */
    public List<Adocao_View> automatizaSistemaAdocoes(int ID) {
        List<Adocao_View> adocaoList = new ArrayList<>();
        Cursor cursor = banco.query("adocao", new String[]{"id_adocao, data, resposta, questionario," +
                        "CPF_Secretario, CPF_Adotante, id_animal"}, "id_animal like " + "'" + ID + "'", null,
                null, null, null);
        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {
            if (cursor.getString(2).equals("Em Andamento")) {
                Adocao_View adocao = new Adocao_View();
                adocao.setId_adocao(cursor.getInt(0));
                adocao.setData(cursor.getString(1));
                adocao.setResposta(cursor.getString(2));
                adocao.setQuestionario(cursor.getString(3));
                adocao.setCPF_Secretario(cursor.getString(4));
                adocao.setCPFAdotante(cursor.getString(5));
                adocao.setId_animal(cursor.getInt(6));
                adocaoList.add(adocao);
            }
        }
        return adocaoList;
    }

}
