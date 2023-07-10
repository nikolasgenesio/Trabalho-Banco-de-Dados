package com.example.pets_donation.Lib;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pets_donation.Abrigo;
import com.example.pets_donation.Adotante;
import com.example.pets_donation.Animal;
import com.example.pets_donation.Funcionario;
import com.example.pets_donation.Linhas_Telefonicas;
import com.example.pets_donation.ProcessoAdocao;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;

public class Conexao_Banco extends SQLiteOpenHelper {

    //declarando as variaveis
    private static final String name = "petsdonation.db";
    private static final int version = 6;

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;

    public Conexao_Banco(@Nullable Context context) {
        super(context, name, null, version);
    }

    /**
     * Criar banco
     *
     * @param db banco
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table usuario (CPF varchar(14) NOT NULL, nome varchar(100) NOT NULL, foto BLOB," +
                "dataNascimento varchar(10) NOT NULL, tipoTel varchar(7), telefone varchar(14)," +
                "sexo varchar(9) CHECK (sexo IN ('Masculino', 'Feminino')), senha varchar(100) NOT NULL, CEP varchar(9) NOT NULL, " +
                "UF varchar(2) CHECK (UF IN ('AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO')), " +
                "cidade varchar(100) NOT NULL, bairro varchar(100) NOT NULL, rua varchar(100) NOT NULL, numero varchar(100) NOT NULL, " +
                "PRIMARY KEY(CPF))");

        db.execSQL("create table funcionario (salario  numeric(8,2) NOT NULL, CPF varchar(14) NOT NULL, " +
                "PRIMARY KEY(CPF)," +
                "FOREIGN KEY (CPF) REFERENCES usuario (CPF))");

        db.execSQL("create table adotante (rendaMensal  numeric(8,2)  CHECK (rendaMensal >= 1320), CPF varchar(14) NOT NULL, " +
                "PRIMARY KEY(CPF)," +
                "FOREIGN KEY (CPF) REFERENCES usuario (CPF))");

        db.execSQL("create table administrador (CPF varchar(14) NOT NULL, " +
                "PRIMARY KEY(CPF)," +
                "FOREIGN KEY (CPF) REFERENCES  funcionario (CPF))");

        db.execSQL("create table secretario (CPF varchar(14) NOT NULL, CPF_Administrador varchar(14) NOT NULL, " +
                "PRIMARY KEY(CPF)," +
                "FOREIGN KEY (CPF) REFERENCES  funcionario(CPF)," +
                "FOREIGN KEY (CPF_Administrador) REFERENCES  administrador(CPF))");

        db.execSQL("create table abrigo (id_abrigo integer primary key autoincrement, " +
                "nome varchar(100) NOT NULL, CEP varchar(9) NOT NULL, rua varchar(100) NOT NULL, numero varchar(100) NOT NULL,  " +
                "bairro varchar(100) NOT NULL, cidade varchar(100) NOT NULL, UF varchar(2) CHECK (UF IN ('AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO')), " +
                "CPF_Secretario varchar(14) NOT NULL," +
                "FOREIGN KEY (CPF_Secretario) REFERENCES  secretario(CPF))");

        db.execSQL("create table linhas_telefonicas (id_linhas_telefonicas integer primary key autoincrement, " +
                "id_abrigo integer NOT NULL, tipoTel varchar(7) NOT NULL, numero varchar(14) NOT NULL," +
                "unique(id_linhas_telefonicas , id_abrigo)," +
                "FOREIGN KEY (id_abrigo) REFERENCES  abrigo(id_abrigo))");

        db.execSQL("create table fiscal (CPF varchar(14) NOT NULL, nome varchar(100) NOT NULL," +
                "PRIMARY KEY(CPF))");

        db.execSQL("create table animal (id_animal integer primary key autoincrement, " +
                "nome varchar(100) NOT NULL, idade varchar(3), foto BLOB, genero varchar(100) CHECK (genero IN ('Macho', 'Fêmea'))," +
                "porte_fisico varchar(100) NOT NULL, tipo varchar(100) NOT NULL, cor varchar(100) NOT NULL, raca varchar(100) NOT NULL, " +
                "CPF_Secretario varchar(14) NOT NULL, CPF_Fiscal varchar(14), id_abrigo integer NOT NULL," +
                "FOREIGN KEY (CPF_Secretario) REFERENCES Secretario(CPF)," +
                "FOREIGN KEY (CPF_Fiscal) REFERENCES fiscal (CPF)," +
                "FOREIGN KEY (id_abrigo) REFERENCES abrigo (id_abrigo))");

        db.execSQL("create table visualiza (CPF_Usuario varchar(14) NOT NULL, id_animal integer NOT NULL," +
                "PRIMARY KEY(CPF_Usuario, id_animal)," +
                "FOREIGN KEY (CPF_Usuario) REFERENCES Usuario(CPF)," +
                "FOREIGN KEY (id_animal) REFERENCES animal (id_animal))");

        db.execSQL("create table fiscaliza (CPF_Fiscal varchar(14) NOT NULL, id_animal integer NOT NULL," +
                "PRIMARY KEY(CPF_Fiscal, id_animal)," +
                "FOREIGN KEY (CPF_Fiscal) REFERENCES Fical(CPF)," +
                "FOREIGN KEY (id_animal) REFERENCES animal (id_animal))");

        db.execSQL("create table vacina (id_vacina integer primary key autoincrement," +
                "nome varchar(100) NOT NULL, descricao varchar(100), dose integer NOT NULL, validade varchar(10) NOT NULL," +
                "id_animal integer NOT NULL," +
                "FOREIGN KEY (id_animal) REFERENCES animal (id_animal))");

        db.execSQL("create table servico (id_servico integer primary key autoincrement," +
                "descricao varchar(100) NOT NULL, data varchar(10) NOT NULL, id_animal integer NOT NULL," +
                "FOREIGN KEY (id_animal) REFERENCES animal (id_animal))");

        db.execSQL("create table tosa (id_tosa integer NOT NULL, tipo varchar(100) NOT NULL," +
                "PRIMARY KEY (id_tosa)," +
                "FOREIGN KEY (id_tosa) REFERENCES servico (id_servico))");

        db.execSQL("create table banho (id_banho integer NOT NULL," +
                "PRIMARY KEY (id_banho)," +
                "FOREIGN KEY (id_banho) REFERENCES servico (id_servico))");

        db.execSQL("create table veterinario (CRMV integer NOT NULL, nome varchar(100) NOT NULL," +
                "telefone varchar(14), CPF_Secretario varchar(14) NOT NULL," +
                "PRIMARY KEY (CRMV)," +
                "FOREIGN KEY (CPF_Secretario) REFERENCES secretario (CPF))");

        db.execSQL("create table consulta (id_consulta integer primary key autoincrement," +
                "data varchar(10) NOT NULL, id_animal integer NOT NULL, CRMV_veterinario integer NOT NULL," +
                "FOREIGN KEY (id_animal) REFERENCES animal (id_animal)," +
                "FOREIGN KEY (CRMV_veterinario) REFERENCES veterinario (CRMV))");

        db.execSQL("create table prontuario (id_prontuario integer primary key autoincrement," +
                "prescricao varchar(100), diagnostico varchar(100), observacoes varchar(100), id_consulta integer NOT NULL," +
                "FOREIGN KEY (id_consulta) REFERENCES consulta (id_consulta))");

        db.execSQL("create table adocao (id_adocao integer primary key autoincrement," +
                "data varchar(10) NOT NULL, resposta varchar(100) NOT NULL, questionario text NOT NULL," +
                "CPF_Secretario varchar(14) NOT NULL, CPF_Adotante varchar(14) NOT NULL, id_animal integer NOT NULL, " +
                "FOREIGN KEY (CPF_Secretario) REFERENCES secretario (CPF)," +
                "FOREIGN KEY (CPF_Adotante) REFERENCES adotante (CPF)," +
                "FOREIGN KEY (id_animal) REFERENCES animal (id_animal))");


    }

    /**
     * Funcao quando o banco é aberto
     * @param db banco
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * Funcao para quando a versão do seu banco de dados é alterada
     * @param db banco
     * @param oldVersion versao antiga
     * @param newVersion versao nova
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists usuario");
        db.execSQL("drop Table if exists funcionario");
        db.execSQL("drop Table if exists adotante");
        db.execSQL("drop Table if exists administrador");
        db.execSQL("drop Table if exists secretario");
        db.execSQL("drop Table if exists abrigo");
        db.execSQL("drop Table if exists linhas_telefonicas");
        db.execSQL("drop Table if exists abrigo");
        db.execSQL("drop Table if exists animal");
        db.execSQL("drop Table if exists visualiza");
        db.execSQL("drop Table if exists fiscal");
        db.execSQL("drop Table if exists fiscaliza");
        db.execSQL("drop Table if exists vacina");
        db.execSQL("drop Table if exists servico");
        db.execSQL("drop Table if exists tosa");
        db.execSQL("drop Table if exists banho");
        db.execSQL("drop Table if exists veterinario");
        db.execSQL("drop Table if exists consulta");
        db.execSQL("drop Table if exists prontuario");
        db.execSQL("drop Table if exists adocao");
        onCreate(db);
    }

    /**
     * Funcao para verificar o CPF do adotante
     * @param CPF CPF do adotante
     * @return true se existe e false, caso contrário
     */
    public Boolean checkCPF(String CPF) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from adotante where CPF = ?", new String[]{CPF});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    /**
     * Funcao para verificar se o adotante está cadastrado
     * @param CPF CPF do adotante
     * @param SENHA senha do adotante
     * @return true se está cadastrado e false, caso contrário
     */
    public Boolean checkSenhaAdotante(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM usuario u " +
                "JOIN adotante a ON u.CPF = a.CPF " +
                "WHERE u.CPF = ? AND u.senha = ?", new String[]{CPF, SENHA});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    /**
     * Funcao para verificar se o funcionario está cadastrado
     * @param CPF CPF do funcionario
     * @param SENHA senha do funcionario
     * @return true se está cadastrado e false, caso contrário
     */
    public Boolean checkSenhaUsuario(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from usuario where CPF = ? and senha = ?", new String[]{CPF, SENHA});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkSenhaSecretario(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM usuario u " +
                "JOIN funcionario f ON u.CPF = f.CPF " +
                "JOIN secretario sec ON f.CPF = sec.CPF " +
                "WHERE u.CPF = ? AND u.senha = ?", new String[]{CPF, SENHA});

        if (cursor.getCount() > 0)
        {
            return true;
        }
        else
            return false;
    }

    public Boolean checkSenhaAdministrador(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM usuario u " +
                "JOIN funcionario f ON u.CPF = f.CPF " +
                "JOIN administrador adm ON f.CPF = adm.CPF " +
                "WHERE u.CPF = ? AND u.senha = ?", new String[]{CPF, SENHA});

        if (cursor.getCount() > 0)
        {
            return true;
        }
        else
            return false;
    }

    public String checkCPFForAll(String CPF)
    {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT 'administrador' AS tipo FROM administrador a WHERE a.CPF = ? " +
                "UNION ALL " +
                "SELECT 'secretario' AS tipo FROM secretario s WHERE s.CPF = ?" +
                "UNION ALL " +
                "SELECT 'adotante' AS tipo FROM adotante ad WHERE ad.CPF = ?", new String[]{CPF, CPF, CPF});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return "";

    }

    /**
     * Funcao para obter informações do adotante
     * @param CPF CPF do adotante
     * @param SENHA senha do adotante
     * @return adotante
     */
    public Adotante obterAdotante(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM usuario u " +
                "JOIN adotante a ON u.CPF = a.CPF " +
                "WHERE u.CPF = ? AND u.senha = ?", new String[]{CPF, SENHA});
        Adotante adotante = new Adotante();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            adotante.setCpf(cursor.getString(0));
            adotante.setNome(cursor.getString(1));

            byte[] imageByte = cursor.getBlob(2);
            if (imageByte != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                adotante.setFoto(bitmap);
            }

            adotante.setDataNascimento(cursor.getString(3));
            adotante.setTipoTelefone(cursor.getString(4));
            adotante.setTelefone(cursor.getString(5));
            adotante.setSexo(cursor.getString(6));
            adotante.setSenha(cursor.getString(7));
            adotante.setCep(cursor.getString(8));
            adotante.setEstado(cursor.getString(9));
            adotante.setCidade(cursor.getString(10));
            adotante.setBairro(cursor.getString(11));
            adotante.setRua(cursor.getString(12));
            adotante.setNumero(cursor.getString(13));
            String salario1 = cursor.getString(14);
            adotante.setRendaMensal(Double.parseDouble(salario1));
        }
        return adotante;
    }

    public Funcionario obterAdministrador(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM usuario u " +
                "JOIN funcionario f ON u.CPF = f.CPF " +
                "JOIN administrador adm ON f.CPF = adm.CPF " +
                "WHERE u.CPF = ? AND u.senha = ?", new String[]{CPF, SENHA});
        Funcionario funcionario = new Funcionario();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            funcionario.setCpf(cursor.getString(0));
            funcionario.setNome(cursor.getString(1));

            byte[] imageByte = cursor.getBlob(2);
            if (imageByte != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                funcionario.setFoto(bitmap);
            }

            funcionario.setDataNascimento(cursor.getString(3));
            funcionario.setTipoTelefone(cursor.getString(4));
            funcionario.setTelefone(cursor.getString(5));
            funcionario.setSexo(cursor.getString(6));
            funcionario.setSenha(cursor.getString(7));
            funcionario.setCep(cursor.getString(8));
            funcionario.setEstado(cursor.getString(9));
            funcionario.setCidade(cursor.getString(10));
            funcionario.setBairro(cursor.getString(11));
            funcionario.setRua(cursor.getString(12));
            funcionario.setNumero(cursor.getString(13));
            String salario1 = cursor.getString(14);
            funcionario.setSalario(Double.parseDouble(salario1));
        }
        return funcionario;
    }

    public Funcionario obterSecretario(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM usuario u " +
                "JOIN funcionario f ON u.CPF = f.CPF " +
                "JOIN secretario sec ON f.CPF = sec.CPF " +
                "WHERE u.CPF = ? AND u.senha = ?", new String[]{CPF, SENHA});
        Funcionario funcionario = new Funcionario();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            funcionario.setCpf(cursor.getString(0));
            funcionario.setNome(cursor.getString(1));

            byte[] imageByte = cursor.getBlob(2);
            if (imageByte != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                funcionario.setFoto(bitmap);
            }

            funcionario.setDataNascimento(cursor.getString(3));
            funcionario.setTipoTelefone(cursor.getString(4));
            funcionario.setTelefone(cursor.getString(5));
            funcionario.setSexo(cursor.getString(6));
            funcionario.setSenha(cursor.getString(7));
            funcionario.setCep(cursor.getString(8));
            funcionario.setEstado(cursor.getString(9));
            funcionario.setCidade(cursor.getString(10));
            funcionario.setBairro(cursor.getString(11));
            funcionario.setRua(cursor.getString(12));
            funcionario.setNumero(cursor.getString(13));
            String salario1 = cursor.getString(14);
            funcionario.setSalario(Double.parseDouble(salario1));
        }
        return funcionario;
    }
    /**
     * Funcao para obter animal
     * @param ID ID do animal no banco
     * @return animal
     */
    public Animal obterAnimal(int ID) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from animal where id_animal = ?", new String[]{String.valueOf(ID)});
        Animal animal = new Animal();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            animal.setId_animal(cursor.getInt(0));
            animal.setNome(cursor.getString(1));
            animal.setIdade(cursor.getString(2));
            byte[] imageByte = cursor.getBlob(3);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            animal.setFoto(bitmap);
            animal.setGenero(cursor.getString(4));
            animal.setPorteFisico(cursor.getString(5));
            animal.setTipo(cursor.getString(6));
            animal.setCor(cursor.getString(7));
            animal.setRaca(cursor.getString(8));
            animal.setCPF_Secretario(cursor.getString(9));
            animal.setCPF_Fiscal(cursor.getString(10));
            animal.setId_animal(cursor.getInt(11));
        }
        return animal;
    }

    /**
     * Funcao para obter abrigo
     * @param NOME nome do abrigo
     * @return abrigo
     */
    public Abrigo obterNomeAbrigo(String NOME) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from abrigo where nome = ?", new String[]{NOME});
        Abrigo abrigo = new Abrigo();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            abrigo.setID(cursor.getInt(0));
            abrigo.setNome(cursor.getString(1));
            abrigo.setCep(cursor.getString(2));
            abrigo.setRua(cursor.getString(3));
            abrigo.setNumero(cursor.getString(4));
            abrigo.setBairro(cursor.getString(5));
            abrigo.setCidade(cursor.getString(6));
            abrigo.setEstado(cursor.getString(7));
            abrigo.setCPF_Secretario(cursor.getString(8));
        }
        return abrigo;
    }

    /**
     * Funcao para obter abrigo
     * @param ID id do abrigo
     * @return abrigo
     */
    public Abrigo obterAbrigo(int ID) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from abrigo where id_abrigo = ?", new String[]{String.valueOf(ID)});
        Abrigo abrigo = new Abrigo();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            abrigo.setID(cursor.getInt(0));
            abrigo.setNome(cursor.getString(1));
            abrigo.setTipoTelefone(cursor.getString(2));
            abrigo.setTelefone(cursor.getString(3));
            abrigo.setCep(cursor.getString(4));
            abrigo.setEstado(cursor.getString(5));
            abrigo.setCidade(cursor.getString(6));
            abrigo.setBairro(cursor.getString(7));
            abrigo.setRua(cursor.getString(8));
            abrigo.setNumero(cursor.getString(9));
        }
        return abrigo;
    }

    /**
     * Funcao para alterar foto do funcionário
     * @param CPF CPF do funcionário
     * @param FOTO foto do funcionário
     */
    public void alterarFotoFuncionario(String CPF, Bitmap FOTO) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        String sql = "UPDATE usuario set FOTO = ? where CPF = ?";
        SQLiteStatement statement = MYDB.compileStatement(sql);

        byteArrayOutputStream = new ByteArrayOutputStream();
        FOTO.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInBytes = byteArrayOutputStream.toByteArray();

        statement.bindBlob(1, imageInBytes);
        statement.bindString(2, CPF);
        statement.executeUpdateDelete();
    }


    /**
     * Funcao para retornar foto do adotante
     * @param CPF CPF do adotante
     * @param SENHA senha do adotante
     * @return foto do adotante
     */
    public Bitmap retornaFotoAdotante(String CPF, String SENHA) {
        Bitmap bitmap = null;
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT u.foto FROM usuario u " +
                "JOIN adotante a ON u.CPF = a.CPF " +
                "WHERE u.CPF = ? AND u.senha = ?", new String[]{CPF, SENHA});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            byte[] imageByte = cursor.getBlob(0);
            if (imageByte != null)
                bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        }
        return bitmap;
    }

    /**
     * Funcao para retornar foto do funcionário
     * @param CPF CPF do funcionário
     * @param SENHA senha do funcionário
     * @return foto do funcionário
     */
    public Bitmap retornaFotoFuncionario(String CPF, String SENHA) {

        Bitmap bitmap = null;
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT u.foto FROM usuario u " +
                "JOIN funcionario o ON u.CPF = o.CPF " +
                "WHERE u.CPF = ? AND u.senha = ?", new String[]{CPF, SENHA});        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            byte[] imageByte = cursor.getBlob(0);
            if (imageByte != null)
                bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        }
        return bitmap;
    }



    /**
     * Funcao para retornar foto do animal
     * @param animal animal solicitado
     * @return foto do animal
     */
    public Bitmap retornaFotoAnimal(Animal animal) {
        Bitmap bitmap = null;
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select foto from animal where id_animal = ?", new String[]{animal.getId_animal().toString()});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            byte[] imageByte = cursor.getBlob(0);
            if (imageByte != null)
                bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        }
        return bitmap;
    }

    /**
     * Funcao para retornar ID do abrigo
     * @param NOME nome do abrigo
     * @return ID do abrigo
     */
    public int retornaIDAbrigo(String NOME) {
        int id = -1;
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select id_abrigo from abrigo where NOME = ?", new String[]{NOME});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        return id;
    }

    public Linhas_Telefonicas retornaLinha(int ID)
    {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT lt.* FROM linhas_telefonicas lt " +
                "JOIN abrigo a ON lt.id_abrigo = a.id_abrigo " +
                "WHERE a.id_abrigo = ?", new String[]{String.valueOf(ID)});
        Linhas_Telefonicas linhas_telefonicas = new Linhas_Telefonicas();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            linhas_telefonicas.setID(cursor.getInt(0));
            linhas_telefonicas.setID_Abrigo(cursor.getInt(1));
            linhas_telefonicas.setTipo_Telefone(cursor.getString(2));
            linhas_telefonicas.setNumero(cursor.getString(3));
        }
        return linhas_telefonicas;
    }

    /**
     * Funcao para retornar nome do abrigo
     * @param ID ID do abrigo
     * @return nome do abrigo
     */
    public String retornaNomeAbrigo(int ID) {
        String nome = "";
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select nome from abrigo where id_abrigo = ?", new String[]{String.valueOf(ID)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            nome = cursor.getString(0);
        }
        return nome;
    }

    /**
     * Funcao para retornar endereco do abrigo
     * @param ID ID do abrigo
     * @return cidade do abrigo
     */
    public String retornaEnderecoAbrigo(int ID) {
        String nome = "";
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select cidade from abrigo where id_abrigo = ?", new String[]{String.valueOf(ID)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            nome = cursor.getString(0);
        }
        return nome;
    }

    /**
     * Funcao para obter o adotante da adocao
     * @param CPF CPF do adotante
     * @return adotante
     */
    public Adotante obterAdotanteAdocao(String CPF) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM usuario u " +
                "JOIN adotante a ON u.CPF = a.CPF " +
                "WHERE u.CPF = ?", new String[]{CPF});

        Adotante adotante = new Adotante();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            adotante.setCpf(cursor.getString(0));
            adotante.setNome(cursor.getString(1));
            byte[] imageByte = cursor.getBlob(2);
            if (imageByte != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                adotante.setFoto(bitmap);
            }

            adotante.setDataNascimento(cursor.getString(3));
            adotante.setTipoTelefone(cursor.getString(4));
            adotante.setTelefone(cursor.getString(5));

            adotante.setSexo(cursor.getString(6));
            adotante.setSenha(cursor.getString(7));
            adotante.setCep(cursor.getString(8));
            adotante.setEstado(cursor.getString(9));
            adotante.setCidade(cursor.getString(10));
            adotante.setBairro(cursor.getString(11));
            adotante.setRua(cursor.getString(12));
            adotante.setNumero(cursor.getString(13));

            String rendaMensal1 = cursor.getString(14);
            adotante.setRendaMensal(Double.parseDouble(rendaMensal1));
        }
        return adotante;
    }

    /**
     * Funcao para obter o funcionário que autorizou a adocao
     * @param CPF CPF do funcionário
     * @return funcionário
     */
    public Funcionario obterFuncionarioAdocao(String CPF) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from funcionario where CPF = ?", new String[]{CPF});
        Funcionario funcionario = new Funcionario();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
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
        }
        return funcionario;
    }


    /**
     * Funcao para retornar a lista de animais
     * @return lista de animais
     */
    public List<Animal> animaisAdocao() {
        List<Animal> animalList = new ArrayList<>();
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM animal a " +
                        "LEFT JOIN adocao ad ON a.id_animal = ad.id_animal " +
                        "WHERE ad.resposta <> 'Finalizado! Buscar animal!' OR ad.resposta IS NULL",
                new String[]{});

        while (cursor.moveToNext()) {
        //if (cursor.getCount() > 0) {
            //cursor.moveToFirst();
            Animal animal = new Animal();
            animal.setId_animal(cursor.getInt(0));
            animal.setNome(cursor.getString(1));
            animal.setIdade(cursor.getString(2));
            byte[] imageByte = cursor.getBlob(3);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            animal.setFoto(bitmap);
            animal.setGenero(cursor.getString(4));
            animal.setPorteFisico(cursor.getString(5));
            animal.setTipo(cursor.getString(6));
            animal.setCor(cursor.getString(7));
            animal.setRaca(cursor.getString(8));
            animal.setCPF_Secretario(cursor.getString(9));
            animal.setCPF_Fiscal(cursor.getString(10));
            animal.setId_abrigo(cursor.getInt(11));
            animalList.add(animal);
        }
        return animalList;
    }

    public List<Adotante> adotanteList() {
        List<Adotante> adotanteList = new ArrayList<>();
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM usuario u " +
                "JOIN adotante a ON u.CPF = a.CPF", new String[]{});

        while (cursor.moveToNext()) {
            Adotante adotante = new Adotante();

            adotante.setCpf(cursor.getString(0));
            adotante.setNome(cursor.getString(1));
            byte[] imageByte = cursor.getBlob(2);
            if (imageByte != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                adotante.setFoto(bitmap);
            }

            adotante.setDataNascimento(cursor.getString(3));
            adotante.setTipoTelefone(cursor.getString(4));
            adotante.setTelefone(cursor.getString(5));

            adotante.setSexo(cursor.getString(6));
            adotante.setSenha(cursor.getString(7));
            adotante.setCep(cursor.getString(8));
            adotante.setEstado(cursor.getString(9));
            adotante.setCidade(cursor.getString(10));
            adotante.setBairro(cursor.getString(11));
            adotante.setRua(cursor.getString(12));
            adotante.setNumero(cursor.getString(13));

            String rendaMensal1 = cursor.getString(14);
            adotante.setRendaMensal(Double.parseDouble(rendaMensal1));
            adotanteList.add(adotante);
        }
        return adotanteList;
    }

    public List<Animal> animalList() {
        List<Animal> animalList = new ArrayList<>();
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM animal",
                new String[]{});

        while (cursor.moveToNext()) {
            Animal animal = new Animal();
            animal.setId_animal(cursor.getInt(0));
            animal.setNome(cursor.getString(1));
            animal.setIdade(cursor.getString(2));
            byte[] imageByte = cursor.getBlob(3);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            animal.setFoto(bitmap);
            animal.setGenero(cursor.getString(4));
            animal.setPorteFisico(cursor.getString(5));
            animal.setTipo(cursor.getString(6));
            animal.setCor(cursor.getString(7));
            animal.setRaca(cursor.getString(8));
            animal.setCPF_Secretario(cursor.getString(9));
            animal.setCPF_Fiscal(cursor.getString(10));
            animal.setId_abrigo(cursor.getInt(11));
            animalList.add(animal);
        }
        return animalList;
    }
}
