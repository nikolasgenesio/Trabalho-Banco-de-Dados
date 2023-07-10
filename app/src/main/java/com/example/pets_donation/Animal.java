package com.example.pets_donation;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class Animal implements Serializable {

    //dados do animal
    private Integer id_animal, id_abrigo;
    private String nome, idade, cor, raca, genero, porteFisico, tipo;
    private transient Bitmap foto;
    private String CPF_Secretario, CPF_Fiscal;

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public Integer getId_abrigo() {
        return id_abrigo;
    }

    public void setId_abrigo(Integer id_abrigo) {
        this.id_abrigo = id_abrigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPorteFisico() {
        return porteFisico;
    }

    public void setPorteFisico(String porteFisico) {
        this.porteFisico = porteFisico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getCPF_Secretario() {
        return CPF_Secretario;
    }

    public void setCPF_Secretario(String CPF_Secretario) {
        this.CPF_Secretario = CPF_Secretario;
    }

    public String getCPF_Fiscal() {
        return CPF_Fiscal;
    }

    public void setCPF_Fiscal(String CPF_Fiscal) {
        this.CPF_Fiscal = CPF_Fiscal;
    }

    @Override
    public String toString() {
        return nome;
    }
}
