package com.example.pets_donation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pets_donation.Funcionario;
import com.example.pets_donation.Lib.Conexao_Banco;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    //declarando as variaveis
    private Conexao_Banco conexaoBanco;
    private SQLiteDatabase banco;

    public FuncionarioDAO(Context context) {
        conexaoBanco = new Conexao_Banco(context);
        banco = conexaoBanco.getWritableDatabase();
    }

    public Boolean inserirFuncionarioADM() {
        ContentValues values = new ContentValues();
        values.put("NOME", "João");
        values.put("DATA_DE_NASCIMENTO", "13/05/2000");
        values.put("SEXO", "Masculino");
        values.put("TIPO_TELEFONE", "Fixo");
        values.put("TELEFONE", "(35)8536-7412");
        values.put("EMAIL", "joaopets@gmail.com");
        values.put("SALARIO", "3450");
        values.put("CEP", "01153-000");
        values.put("ESTADO", "SP");
        values.put("CIDADE", "São Paulo");
        values.put("BAIRRO", "Barra Funda");
        values.put("RUA", "Rua Vitorino Carmilo");
        values.put("NUMERO", "2041");
        values.put("CPF", "126.745.986-20");
        values.put("SENHA", "barrafunda");
        values.put("FOTO", (byte[]) null);
        long inserir = banco.insert("funcionario", null, values);
        if (inserir == -1)
            return false;
        else
            return true;
    }

    public Boolean inserir(Funcionario funcionario) {
        ContentValues values = new ContentValues();
        values.put("NOME", funcionario.getNome());
        values.put("DATA_DE_NASCIMENTO", funcionario.getDataNascimento());
        values.put("SEXO", funcionario.getSexo());
        values.put("TIPO_TELEFONE", funcionario.getTipoTelefone());
        values.put("TELEFONE", funcionario.getTelefone());
        values.put("EMAIL", funcionario.getEmail());
        values.put("SALARIO", funcionario.getSalario());
        values.put("CEP", funcionario.getCep());
        values.put("ESTADO", funcionario.getEstado());
        values.put("CIDADE", funcionario.getCidade());
        values.put("BAIRRO", funcionario.getBairro());
        values.put("RUA", funcionario.getRua());
        values.put("NUMERO", funcionario.getNumero());
        values.put("CPF", funcionario.getCpf());
        values.put("SENHA", funcionario.getSenha());
        values.put("FOTO", (byte[]) null);
        long inserir = banco.insert("funcionario", null, values);
        if (inserir == -1)
            return false;
        else
            return true;
    }

    public List<Funcionario> obterTodosFuncionarios() {
        List<Funcionario> funcionarioList = new ArrayList<>();
        Cursor cursor = banco.query("funcionario", new String[]{"NOME, DATA_DE_NASCIMENTO, SEXO," +
                        "TIPO_TELEFONE, TELEFONE, EMAIL, SALARIO, CEP, ESTADO, CIDADE, " +
                        "BAIRRO, RUA, NUMERO, CPF, SENHA, FOTO"}, null, null,
                null, null, null);

        //deslocar nas linhas da tabela
        while (cursor.moveToNext()) {
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(cursor.getString(0));
            funcionario.setDataNascimento(cursor.getString(1));
            funcionario.setSexo(cursor.getString(2));
            funcionario.setTipoTelefone(cursor.getString(3));
            funcionario.setTelefone(cursor.getString(4));
            funcionario.setEmail(cursor.getString(5));
            String salario1 = cursor.getString(6);
            funcionario.setSalario(Double.parseDouble(salario1));
            funcionario.setCep(cursor.getString(7));
            funcionario.setEstado(cursor.getString(8));
            funcionario.setCidade(cursor.getString(9));
            funcionario.setBairro(cursor.getString(10));
            funcionario.setRua(cursor.getString(11));
            funcionario.setNumero(cursor.getString(12));
            funcionario.setCpf(cursor.getString(13));
            funcionario.setSenha(cursor.getString(14));
            byte[] imageByte = cursor.getBlob(15);
            if (imageByte != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                funcionario.setFoto(bitmap);
            }
            funcionarioList.add(funcionario);
        }
        return funcionarioList;
    }


    public boolean excluir(Funcionario funcionario) {
        long deletar = banco.delete("funcionario", "CPF = ?", new String[]{funcionario.getCpf()});
        if (deletar == -1)
            return false;
        else
            return true;
    }

    public boolean alterar(Funcionario funcionario) {
        ContentValues values = new ContentValues();
        values.put("NOME", funcionario.getNome());
        values.put("DATA_DE_NASCIMENTO", funcionario.getDataNascimento());
        values.put("SEXO", funcionario.getSexo());
        values.put("TIPO_TELEFONE", funcionario.getTipoTelefone());
        values.put("TELEFONE", funcionario.getTelefone());
        values.put("EMAIL", funcionario.getEmail());
        values.put("SALARIO", funcionario.getSalario());
        values.put("CEP", funcionario.getCep());
        values.put("ESTADO", funcionario.getEstado());
        values.put("CIDADE", funcionario.getCidade());
        values.put("BAIRRO", funcionario.getBairro());
        values.put("RUA", funcionario.getRua());
        values.put("NUMERO", funcionario.getNumero());
        values.put("CPF", funcionario.getCpf());
        values.put("SENHA", funcionario.getSenha());
        long inserir = banco.update("funcionario", values, "CPF = ?", new String[]{funcionario.getCpf()});
        if (inserir == -1)
            return false;
        else
            return true;
    }
}
