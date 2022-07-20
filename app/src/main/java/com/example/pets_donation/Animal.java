package com.example.pets_donation;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class Animal implements Serializable {

    private Integer ID, IDAbrigo;
    private String nome, tipo, idade, cor, raca, genero, portFisico;
    private transient Bitmap foto;
    private List<String> vacinacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getIDAbrigo() {
        return IDAbrigo;
    }

    public void setIDAbrigo(Integer IDAbrigo) {
        this.IDAbrigo = IDAbrigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getPortFisico() {
        return portFisico;
    }

    public void setPortFisico(String portFisico) {
        this.portFisico = portFisico;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public List<String> getVacinacao() {
        return vacinacao;
    }

    public void setVacinacao(List<String> vacinacao) {
        this.vacinacao = vacinacao;
    }

    @Override
    public String toString() {
        return nome;
    }
}
