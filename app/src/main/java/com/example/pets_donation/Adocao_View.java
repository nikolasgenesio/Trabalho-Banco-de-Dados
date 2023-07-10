package com.example.pets_donation;

import java.io.Serializable;

public class Adocao_View implements Serializable {

    //dados do processo de adocao
    private Integer id_adocao, id_animal;
    private String CPF_Secretario, CPFAdotante;
    private String data, resposta, questionario;

    public Integer getId_adocao() {
        return id_adocao;
    }

    public void setId_adocao(Integer id_adocao) {
        this.id_adocao = id_adocao;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public String getCPF_Secretario() {
        return CPF_Secretario;
    }

    public void setCPF_Secretario(String CPF_Secretario) {
        this.CPF_Secretario = CPF_Secretario;
    }

    public String getCPFAdotante() {
        return CPFAdotante;
    }

    public void setCPFAdotante(String CPFAdotante) {
        this.CPFAdotante = CPFAdotante;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getQuestionario() {
        return questionario;
    }

    public void setQuestionario(String questionario) {
        this.questionario = questionario;
    }
}
