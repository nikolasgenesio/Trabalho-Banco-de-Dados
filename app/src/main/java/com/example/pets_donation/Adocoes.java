package com.example.pets_donation;

public class Adocoes {

    //dados da adocao
    private int ID, ID_Animal;
    private String data, CPF_Adotante, CPF_Funcionario;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Animal() {
        return ID_Animal;
    }

    public void setID_Animal(int ID_Animal) {
        this.ID_Animal = ID_Animal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCPF_Adotante() {
        return CPF_Adotante;
    }

    public void setCPF_Adotante(String CPF_Adotante) {
        this.CPF_Adotante = CPF_Adotante;
    }

    public String getCPF_Funcionario() {
        return CPF_Funcionario;
    }

    public void setCPF_Funcionario(String CPF_Funcionario) {
        this.CPF_Funcionario = CPF_Funcionario;
    }

    @Override
    public String toString() {
        return CPF_Adotante;
    }
}
