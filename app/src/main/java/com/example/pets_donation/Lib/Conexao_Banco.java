package com.example.pets_donation.Lib;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.pets_donation.Abrigo;
import com.example.pets_donation.Adotante;
import com.example.pets_donation.Animal;
import com.example.pets_donation.Funcionario;
import com.example.pets_donation.ProcessoAdocao;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;

public class Conexao_Banco extends SQLiteOpenHelper {

    //declarando as variaveis
    private static final String name = "pets_donation.db";
    private static final int version = 9;

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;

    public Conexao_Banco(@Nullable Context context) {
        super(context, name, null, version);
    }

    /**
     * Criar banco
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table funcionario(NOME varchar(100), " +
                "DATA_DE_NASCIMENTO varchar(10), SEXO varchar(9), TIPO_TELEFONE varchar(7)," +
                "TELEFONE varchar(14), EMAIL varchar(100), SALARIO varchar(50)," +
                "CEP varchar(9), ESTADO varchar(2), CIDADE varchar(100), BAIRRO varchar(100)," +
                "RUA varchar(100), NUMERO varchar(100), CPF varchar(14), SENHA varchar(100)," +
                "FOTO BLOB, PRIMARY KEY(CPF))");

        db.execSQL("create table adotante(NOME varchar(100), " +
                "DATA_DE_NASCIMENTO varchar(10), SEXO varchar(9), TIPO_TELEFONE varchar(7)," +
                "TELEFONE varchar(14), EMAIL varchar(100), RENDA_MENSAL varchar(50)," +
                "CEP varchar(9), ESTADO varchar(2), CIDADE varchar(100), BAIRRO varchar(100)," +
                "RUA varchar(100), NUMERO varchar(100), CPF varchar(14), SENHA varchar(100)," +
                "FOTO BLOB, PRIMARY KEY(CPF))");

        db.execSQL("create table abrigo(ID integer primary key autoincrement, " +
                "NOME varchar(100), TIPO_TELEFONE varchar(7), TELEFONE varchar(14)," +
                "CEP varchar(9), ESTADO varchar(2), CIDADE varchar(100), BAIRRO varchar(100)," +
                "RUA varchar(100), NUMERO varchar(100))");

        db.execSQL("create table animal(ID integer primary key autoincrement, " +
                "NOME varchar(100), TIPO varchar(100), IDADE varchar(3), COR varchar(100)," +
                "RACA varchar(100), GENERO varchar(100), PORTE_FISICO varchar(100), VACINACAO varchar(100)," +
                "FOTO BLOB, ID_ABRIGO integer, CPF_FUNCIONARIO varchar(14)," +
                "FOREIGN KEY (ID_ABRIGO) REFERENCES abrigo (ID)," +
                "FOREIGN KEY (CPF_FUNCIONARIO) REFERENCES funcionario(CPF))");

        db.execSQL("create table processoAdocao(ID integer primary key autoincrement, " +
                "MORADA varchar(11), IMOVEL varchar(100), QTDE_PESSOAS varchar(3), QTDE_ANIMAIS varchar(100)," +
                "LOCAL varchar(100), PERMANENCIA varchar(100), ATUAL varchar(100), FALECIDO varchar(100)," +
                "SUSTENTO varchar(100), VIZINHOS varchar(100), PASSEIO varchar(100)," +
                "CUSTOS varchar(100), ALERGIA varchar(100), RESPEITO varchar(100)," +
                "CRIANCA varchar(100), HORAS varchar(100), VIAGEM varchar(100)," +
                "FUGIR varchar(100), CRIAR varchar(100), VETERINARIO varchar(100), STATUS varchar(100)," +
                "CPF_ADOTANTE varchar(14), ID_ANIMAL integer, " +
                "FOREIGN KEY (CPF_ADOTANTE) REFERENCES adotante (CPF)," +
                "FOREIGN KEY (ID_ANIMAL) REFERENCES animal (ID))");

        db.execSQL("create table adocaoDeferida(ID integer primary key autoincrement, " +
                "DATA varchar(10), CPF_ADOTANTE varchar(14), ID_ANIMAL integer, CPF_FUNCIONARIO varchar(14)," +
                "FOREIGN KEY (CPF_ADOTANTE) REFERENCES adotante (CPF)," +
                "FOREIGN KEY (CPF_FUNCIONARIO) REFERENCES funcionario (CPF)," +
                "FOREIGN KEY (ID_ANIMAL) REFERENCES animal (ID))");

        db.execSQL("create table adocaoIndeferida(ID integer primary key autoincrement, " +
                "DATA varchar(10), CPF_ADOTANTE varchar(14), ID_ANIMAL integer, CPF_FUNCIONARIO varchar(14)," +
                "FOREIGN KEY (CPF_ADOTANTE) REFERENCES adotante (CPF)," +
                "FOREIGN KEY (CPF_FUNCIONARIO) REFERENCES funcionario (CPF)," +
                "FOREIGN KEY (ID_ANIMAL) REFERENCES animal (ID))");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists adotante");
        db.execSQL("drop Table if exists funcionario");
        db.execSQL("drop Table if exists abrigo");
        db.execSQL("drop Table if exists animal");
        db.execSQL("drop Table if exists processoAdocao");
        db.execSQL("drop Table if exists adocaoIndeferida");
        db.execSQL("drop Table if exists adocaoDeferida");
        onCreate(db);
    }


    public Boolean checkCPF(String CPF) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from adotante where CPF = ?", new String[]{CPF});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkSenhaAdotante(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from adotante where CPF = ? and SENHA = ?", new String[]{CPF, SENHA});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkSenhaFuncionario(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from funcionario where CPF = ? and SENHA = ?", new String[]{CPF, SENHA});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Adotante obterAdotante(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from adotante where CPF = ? and SENHA = ?", new String[]{CPF, SENHA});
        Adotante adotante = new Adotante();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
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
            Log.i("Tarefa 1 - status", "NOME: " + adotante.getNome());
        }
        return adotante;
    }

    public Funcionario obterFuncionario(String CPF, String SENHA) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from funcionario where CPF = ? and SENHA = ?", new String[]{CPF, SENHA});
        Funcionario funcionario = new Funcionario();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
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

            Log.i("Tarefa 1 - status", "NOME: " + funcionario.getNome());
        }
        return funcionario;
    }

    public Animal obterAnimal(int ID) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from animal where ID = ?", new String[]{String.valueOf(ID)});
        Animal animal = new Animal();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            animal.setID(cursor.getInt(0));
            animal.setNome(cursor.getString(1));
            animal.setTipo(cursor.getString(2));
            animal.setIdade(cursor.getString(3));
            animal.setCor(cursor.getString(4));
            animal.setRaca(cursor.getString(5));
            animal.setGenero(cursor.getString(6));
            animal.setPortFisico(cursor.getString(7));
            List<String> vacinacoes = Arrays.asList(cursor.getString(8).split("\\s*,\\s*"));
            animal.setVacinacao(vacinacoes);
            byte[] imageByte = cursor.getBlob(9);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            animal.setFoto(bitmap);
            animal.setIDAbrigo(cursor.getInt(10));
            animal.setCPF_Funcionario(cursor.getString(11));
        }
        return animal;
    }

    public Abrigo obterNomeAbrigo(String NOME) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from abrigo where NOME = ?", new String[]{NOME});
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

    public Abrigo obterAbrigo(int ID) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from abrigo where ID = ?", new String[]{String.valueOf(ID)});
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

    public void alterarFotoFuncionario(String CPF, String SENHA, Bitmap FOTO) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        String sql = "UPDATE funcionario set FOTO = ? where CPF = ? and SENHA = ?";
        SQLiteStatement statement = MYDB.compileStatement(sql);

        byteArrayOutputStream = new ByteArrayOutputStream();
        FOTO.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInBytes = byteArrayOutputStream.toByteArray();

        statement.bindBlob(1, imageInBytes);
        statement.bindString(2, CPF);
        statement.bindString(3, SENHA);
        statement.executeUpdateDelete();

        Log.i("Funcionario FOTO", "NOME: " + imageInBytes);
    }

    public void alterarFotoAdotante(String CPF, String SENHA, Bitmap FOTO) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        String sql = "UPDATE adotante set FOTO = ? where CPF = ? and SENHA = ?";
        SQLiteStatement statement = MYDB.compileStatement(sql);

        byteArrayOutputStream = new ByteArrayOutputStream();
        FOTO.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInBytes = byteArrayOutputStream.toByteArray();

        statement.bindBlob(1, imageInBytes);
        statement.bindString(2, CPF);
        statement.bindString(3, SENHA);
        statement.executeUpdateDelete();

    }

    public Bitmap retornaFotoAdotante(String CPF, String SENHA) {

        Bitmap bitmap = null;
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select FOTO from adotante where CPF = ? and SENHA = ?", new String[]{CPF, SENHA});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            byte[] imageByte = cursor.getBlob(0);
            if (imageByte != null)
                bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        }
        return bitmap;
    }

    public Bitmap retornaFotoFuncionario(String CPF, String SENHA) {

        Bitmap bitmap = null;
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select FOTO from funcionario where CPF = ? and SENHA = ?", new String[]{CPF, SENHA});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            byte[] imageByte = cursor.getBlob(0);
            if (imageByte != null)
                bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        }
        return bitmap;
    }

    public Bitmap retornaFotoAnimal(Animal animal) {

        Bitmap bitmap = null;
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select FOTO from animal where ID = ?", new String[]{animal.getID().toString()});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            byte[] imageByte = cursor.getBlob(0);
            if (imageByte != null)
                bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        }
        return bitmap;
    }

    public int retornaIDAbrigo(String NOME) {
        int id = -1;
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select ID from abrigo where NOME = ?", new String[]{NOME});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        return id;
    }

    public String retornaNomeAbrigo(int ID) {
        String nome = "";
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select NOME from abrigo where ID = ?", new String[]{String.valueOf(ID)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            nome = cursor.getString(0);
        }
        return nome;
    }

    public String retornaEnderecoAbrigo(int ID) {
        String nome = "";
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select CIDADE from abrigo where ID = ?", new String[]{String.valueOf(ID)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            nome = cursor.getString(0);
        }
        return nome;
    }


    public Adotante obterAdotanteAdocao(String CPF) {
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("Select * from adotante where CPF = ?", new String[]{CPF});
        Adotante adotante = new Adotante();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
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
            Log.i("Tarefa 1 - status", "NOME: " + adotante.getNome());
        }
        return adotante;
    }

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

            Log.i("Tarefa 1 - status", "NOME: " + funcionario.getNome());
        }
        return funcionario;
    }

    public List<Animal> animaisAdocao() {
        List<Animal> animalList = new ArrayList<>();
        SQLiteDatabase MYDB = this.getWritableDatabase();
        Cursor cursor = MYDB.rawQuery("SELECT * FROM animal LEFT JOIN adocaoDeferida " +
                        "ON adocaoDeferida.ID_ANIMAL = animal.ID  WHERE adocaoDeferida.ID_ANIMAL IS NULL",
                new String[]{});

        while (cursor.moveToNext()) {
        //if (cursor.getCount() > 0) {
            //cursor.moveToFirst();
            Animal animal = new Animal();
            animal.setID(cursor.getInt(0));
            animal.setNome(cursor.getString(1));
            Log.i("Tarefa 1 - status", "ENTROU " + animal.getNome());
            animal.setTipo(cursor.getString(2));
            animal.setIdade(cursor.getString(3));
            animal.setCor(cursor.getString(4));
            animal.setRaca(cursor.getString(5));
            animal.setGenero(cursor.getString(6));
            animal.setPortFisico(cursor.getString(7));
            List<String> vacinacoes = Arrays.asList(cursor.getString(8).split("\\s*,\\s*"));
            animal.setVacinacao(vacinacoes);
            byte[] imageByte = cursor.getBlob(9);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            animal.setFoto(bitmap);
            animal.setIDAbrigo(cursor.getInt(10));
            animal.setCPF_Funcionario(cursor.getString(11));
            animalList.add(animal);
        }
        return animalList;
    }
}
