package com.example.pets_donation;

import java.io.Serializable;

public class Linhas_Telefonicas implements Serializable {

    private int ID, ID_Abrigo;
    private String tipo_Telefone, numero;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Abrigo() {
        return ID_Abrigo;
    }

    public void setID_Abrigo(int ID_Abrigo) {
        this.ID_Abrigo = ID_Abrigo;
    }

    public String getTipo_Telefone() {
        return tipo_Telefone;
    }

    public void setTipo_Telefone(String tipo_Telefone) {
        this.tipo_Telefone = tipo_Telefone;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
