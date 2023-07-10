package com.example.pets_donation.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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

    /**
     * Funcao para inserir o 1° funcionario no banco
     * @return verdadeiro se inseriu e falso, caso contrario
     */
    public Boolean inserirFuncionarioADM() {
        ContentValues values, funcionario, adm;

        values = new ContentValues();
        values.put("nome", "João");
        values.put("dataNascimento", "13/05/2000");
        values.put("sexo", "Masculino");
        values.put("tipoTel", "Fixo");
        values.put("telefone", "(35)8536-7412");

        values.put("CEP", "01153-000");
        values.put("UF", "SP");
        values.put("cidade", "São Paulo");
        values.put("bairro", "Barra Funda");
        values.put("rua", "Rua Vitorino Carmilo");
        values.put("numero", "2041");

        values.put("CPF", "126.745.986-22");
        values.put("senha", "barrafunda");

        values.put("foto", (byte[]) null);

        long inserir = banco.insert("usuario", null, values);
        if (inserir == -1) {
            Log.i("TESTE FUNCIONARIO", "AQUI");
            return false;
        }
        else
        {
            funcionario = new ContentValues();
            funcionario.put("salario", 1250);
            funcionario.put("CPF", "126.745.986-22");
            inserir = banco.insert("funcionario", null, funcionario);

            if(inserir != -1)
            {
                adm = new ContentValues();
                adm.put("CPF", "126.745.986-22");
                inserir = banco.insert("administrador", null, adm);
                if(inserir != -1)
                    return true;
            }
            return false;
        }
    }

    /**
     * Funcao para inserir funcionarios no banco
     * @param funcionario funcionario a ser inserido
     * @return verdadeiro se inseriu e falso, caso contrario
     */
    public Boolean inserir(Funcionario funcionario, Funcionario funcionarioADM) {
        ContentValues values, funcionario1, secretario;
        values = new ContentValues();
        values.put("nome", funcionario.getNome());
        values.put("dataNascimento", funcionario.getDataNascimento());
        values.put("sexo", funcionario.getSexo());
        values.put("tipoTel", funcionario.getTipoTelefone());
        values.put("telefone", funcionario.getTelefone());

        values.put("CEP", funcionario.getCep());
        values.put("UF", funcionario.getEstado());
        values.put("cidade", funcionario.getCidade());
        values.put("bairro", funcionario.getBairro());
        values.put("rua", funcionario.getRua());
        values.put("numero", funcionario.getNumero());

        values.put("CPF", funcionario.getCpf());
        values.put("senha", funcionario.getSenha());
        values.put("foto", (byte[]) null);

        long inserir = banco.insert("usuario", null, values);
        if (inserir == -1)
            return false;
        else
        {
            funcionario1 = new ContentValues();
            funcionario1.put("salario", funcionario.getSalario());
            funcionario1.put("CPF", funcionario.getCpf());
            inserir = banco.insert("funcionario", null, funcionario1);

            if(inserir != -1)
            {
                secretario = new ContentValues();
                secretario.put("CPF", funcionario.getCpf());
                secretario.put("CPF_Administrador", funcionarioADM.getCpf());
                inserir = banco.insert("secretario", null, secretario);
                return inserir != -1;
            }
            return false;
        }
    }

    /**
     * Funcao para obter todos os funcionários do banco
     * @return lista de funcionários
     */
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


    /**
     * Funcao para alterar funcionário no banco
     * @param funcionario funcionário a ser alterado
     * @return verdadeiro se alterou e falso, caso contrario
     */
    public boolean alterar(Funcionario funcionario) {
        ContentValues values, funcionario1, secretario;
        values = new ContentValues();
        values.put("nome", funcionario.getNome());
        values.put("dataNascimento", funcionario.getDataNascimento());
        values.put("sexo", funcionario.getSexo());
        values.put("tipoTel", funcionario.getTipoTelefone());
        values.put("telefone", funcionario.getTelefone());

        values.put("CEP", funcionario.getCep());
        values.put("UF", funcionario.getEstado());
        values.put("cidade", funcionario.getCidade());
        values.put("bairro", funcionario.getBairro());
        values.put("rua", funcionario.getRua());
        values.put("numero", funcionario.getNumero());

        values.put("CPF", funcionario.getCpf());
        values.put("senha", funcionario.getSenha());
        long inserir = banco.update("usuario", values, "CPF = ?", new String[]{funcionario.getCpf()});
        if (inserir == -1)
            return false;
        else
        {
            funcionario1 = new ContentValues();
            funcionario1.put("salario", funcionario.getSalario());
            funcionario1.put("CPF", funcionario.getCpf());
            inserir = banco.update("funcionario", values, "CPF = ?", new String[]{funcionario.getCpf()});

            if(inserir != -1)
            {
                secretario = new ContentValues();
                secretario.put("CPF", funcionario.getCpf());
                inserir = banco.update("secretario", values, "CPF = ?", new String[]{funcionario.getCpf()});
                return inserir != -1;
            }
            return false;
        }
    }
}
