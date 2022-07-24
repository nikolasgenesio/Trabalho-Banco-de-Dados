package com.example.pets_donation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.ProcessoAdocao;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProcessoAdocaoDAO {

    //declarando as variaveis
    private Conexao_Banco conexaoBanco;
    private SQLiteDatabase banco;

    public ProcessoAdocaoDAO(Context context) {
        conexaoBanco = new Conexao_Banco(context);
        banco = conexaoBanco.getWritableDatabase();
    }

    public Boolean inserir(ProcessoAdocao adocao) {
        ContentValues values = new ContentValues();
        values.put("MORADA", adocao.getMorada());
        values.put("IMOVEL", adocao.getImovel());
        values.put("QTDE_PESSOAS", adocao.getQtdePessoas());
        values.put("QTDE_ANIMAIS", adocao.getQtdeAnimais());
        values.put("LOCAL", adocao.getLocalAnimais());
        values.put("PERMANENCIA", adocao.getPermanecerAnimais());
        values.put("ATUAL", adocao.getAnimaisAtual());
        values.put("FALECIDO", adocao.getAnimalFalecido());
        values.put("SUSTENTO", adocao.getSustentarAnimal());
        values.put("VIZINHOS", adocao.getVizinhosAnimal());
        values.put("PASSEIO", adocao.getPasseioAnimal());
        values.put("CUSTOS", adocao.getCustosAnimal());
        values.put("ALERGIA", adocao.getAlergiaAnimal());
        values.put("RESPEITO", adocao.getRespeitoAnimal());
        values.put("CRIANCA", adocao.getCriancaAnimal());
        values.put("HORAS", adocao.getHorasAnimal());
        values.put("VIAGEM", adocao.getViajarAnimal());
        values.put("FUGIR", adocao.getFugirAnimal());
        values.put("CRIAR", adocao.getCriarAnimal());
        values.put("VETERINARIO", adocao.getVeterinario());
        values.put("CPF_ADOTANTE", adocao.getCPFAdotante());
        values.put("ID_ANIMAL", adocao.getIDAnimal());
        values.put("STATUS", adocao.getStatus());
        long inserir = banco.insert("processoAdocao", null, values);
        if (inserir == -1)
            return false;
        else
            return true;
    }

    public List<ProcessoAdocao> retornaStatusAdocaoAdotante(String CPF) {
        List<ProcessoAdocao> adocaoList = new ArrayList<>();
        Cursor cursor = banco.query("processoAdocao", new String[]{"ID, MORADA, IMOVEL, QTDE_PESSOAS," +
                        "QTDE_ANIMAIS, LOCAL, PERMANENCIA, ATUAL, FALECIDO, SUSTENTO, VIZINHOS," +
                        "PASSEIO, CUSTOS, ALERGIA, RESPEITO, CRIANCA, HORAS, VIAGEM, FUGIR, CRIAR," +
                        "VETERINARIO, STATUS, CPF_ADOTANTE, ID_ANIMAL"}, "CPF_ADOTANTE like " + "'" + CPF + "'", null,
                null, null, null);
        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {
            ProcessoAdocao adocao = new ProcessoAdocao();
            adocao.setID(cursor.getInt(0));
            adocao.setMorada(cursor.getString(1));
            adocao.setImovel(cursor.getString(2));
            adocao.setQtdePessoas(cursor.getString(3));
            adocao.setQtdeAnimais(cursor.getString(4));
            adocao.setLocalAnimais(cursor.getString(5));
            adocao.setPermanecerAnimais(cursor.getString(6));
            adocao.setAnimaisAtual(cursor.getString(7));
            adocao.setAnimalFalecido(cursor.getString(8));
            adocao.setSustentarAnimal(cursor.getString(9));
            adocao.setVizinhosAnimal(cursor.getString(10));
            adocao.setPasseioAnimal(cursor.getString(11));
            adocao.setCustosAnimal(cursor.getString(12));
            adocao.setAlergiaAnimal(cursor.getString(13));
            adocao.setRespeitoAnimal(cursor.getString(14));
            adocao.setCriancaAnimal(cursor.getString(15));
            adocao.setHorasAnimal(cursor.getString(16));
            adocao.setViajarAnimal(cursor.getString(17));
            adocao.setFugirAnimal(cursor.getString(18));
            adocao.setCriarAnimal(cursor.getString(19));
            adocao.setVeterinario(cursor.getString(20));
            adocao.setStatus(cursor.getString(21));
            adocao.setCPFAdotante(cursor.getString(22));
            adocao.setIDAnimal(cursor.getInt(23));
            adocaoList.add(adocao);
        }
        return adocaoList;
    }

    public List<ProcessoAdocao> retornaStatusAdocaoFuncionario() {
        List<ProcessoAdocao> adocaoList = new ArrayList<>();
        Cursor cursor = banco.query("processoAdocao", new String[]{"ID, MORADA, IMOVEL, QTDE_PESSOAS," +
                        "QTDE_ANIMAIS, LOCAL, PERMANENCIA, ATUAL, FALECIDO, SUSTENTO, VIZINHOS," +
                        "PASSEIO, CUSTOS, ALERGIA, RESPEITO, CRIANCA, HORAS, VIAGEM, FUGIR, CRIAR," +
                        "VETERINARIO, STATUS, CPF_ADOTANTE, ID_ANIMAL"}, null, null,
                null, null, null);
        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {
            ProcessoAdocao adocao = new ProcessoAdocao();
            adocao.setID(cursor.getInt(0));
            adocao.setMorada(cursor.getString(1));
            adocao.setImovel(cursor.getString(2));
            adocao.setQtdePessoas(cursor.getString(3));
            adocao.setQtdeAnimais(cursor.getString(4));
            adocao.setLocalAnimais(cursor.getString(5));
            adocao.setPermanecerAnimais(cursor.getString(6));
            adocao.setAnimaisAtual(cursor.getString(7));
            adocao.setAnimalFalecido(cursor.getString(8));
            adocao.setSustentarAnimal(cursor.getString(9));
            adocao.setVizinhosAnimal(cursor.getString(10));
            adocao.setPasseioAnimal(cursor.getString(11));
            adocao.setCustosAnimal(cursor.getString(12));
            adocao.setAlergiaAnimal(cursor.getString(13));
            adocao.setRespeitoAnimal(cursor.getString(14));
            adocao.setCriancaAnimal(cursor.getString(15));
            adocao.setHorasAnimal(cursor.getString(16));
            adocao.setViajarAnimal(cursor.getString(17));
            adocao.setFugirAnimal(cursor.getString(18));
            adocao.setCriarAnimal(cursor.getString(19));
            adocao.setVeterinario(cursor.getString(20));
            adocao.setStatus(cursor.getString(21));
            adocao.setCPFAdotante(cursor.getString(22));
            adocao.setIDAnimal(cursor.getInt(23));
            adocaoList.add(adocao);
        }
        return adocaoList;
    }

    public boolean alterar(ProcessoAdocao adocao) {
        ContentValues values = new ContentValues();
        values.put("MORADA", adocao.getMorada());
        values.put("IMOVEL", adocao.getImovel());
        values.put("QTDE_PESSOAS", adocao.getQtdePessoas());
        values.put("QTDE_ANIMAIS", adocao.getQtdeAnimais());
        values.put("LOCAL", adocao.getLocalAnimais());
        values.put("PERMANENCIA", adocao.getPermanecerAnimais());
        values.put("ATUAL", adocao.getAnimaisAtual());
        values.put("FALECIDO", adocao.getAnimalFalecido());
        values.put("SUSTENTO", adocao.getSustentarAnimal());
        values.put("VIZINHOS", adocao.getVizinhosAnimal());
        values.put("PASSEIO", adocao.getPasseioAnimal());
        values.put("CUSTOS", adocao.getCustosAnimal());
        values.put("ALERGIA", adocao.getAlergiaAnimal());
        values.put("RESPEITO", adocao.getRespeitoAnimal());
        values.put("CRIANCA", adocao.getCriancaAnimal());
        values.put("HORAS", adocao.getHorasAnimal());
        values.put("VIAGEM", adocao.getViajarAnimal());
        values.put("FUGIR", adocao.getFugirAnimal());
        values.put("CRIAR", adocao.getCriarAnimal());
        values.put("VETERINARIO", adocao.getVeterinario());
        long inserir = banco.update("processoAdocao", values, "ID = ?", new String[]{adocao.getID().toString()});
        if (inserir == -1)
            return false;
        else
            return true;
    }

    public boolean excluir(ProcessoAdocao adocao) {
        long deletar = banco.delete("processoAdocao", "ID = ?", new String[]{adocao.getID().toString()});
        if (deletar == -1)
            return false;
        else
            return true;
    }
}
