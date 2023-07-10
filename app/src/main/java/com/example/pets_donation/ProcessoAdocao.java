package com.example.pets_donation;

import android.widget.EditText;

import java.io.Serializable;

public class ProcessoAdocao implements Serializable {

    //dados do processo de adocao
    private Integer ID, IDAnimal;
    private String CPFAdotante, CPFSecretario;
    private String qtdePessoas, qtdeAnimais, localAnimais, permanecerAnimais, animaisAtual, animalFalecido, sustentarAnimal, vizinhosAnimal;
    private String passeioAnimal, custosAnimal, alergiaAnimal, respeitoAnimal, criancaAnimal, horasAnimal, viajarAnimal, fugirAnimal, criarAnimal;
    private String morada, imovel, veterinario, status, data, questionario;

    public String getQuestionario() {
        return questionario;
    }

    public void setQuestionario(String questionario) {
        this.questionario = questionario;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getIDAnimal() {
        return IDAnimal;
    }

    public void setIDAnimal(Integer IDAnimal) {
        this.IDAnimal = IDAnimal;
    }

    public String getCPFAdotante() {
        return CPFAdotante;
    }

    public void setCPFAdotante(String CPFAdotante) {
        this.CPFAdotante = CPFAdotante;
    }

    public String getQtdePessoas() {
        return qtdePessoas;
    }

    public void setQtdePessoas(String qtdePessoas) {
        this.qtdePessoas = qtdePessoas;
    }

    public String getQtdeAnimais() {
        return qtdeAnimais;
    }

    public void setQtdeAnimais(String qtdeAnimais) {
        this.qtdeAnimais = qtdeAnimais;
    }

    public String getLocalAnimais() {
        return localAnimais;
    }

    public void setLocalAnimais(String localAnimais) {
        this.localAnimais = localAnimais;
    }

    public String getPermanecerAnimais() {
        return permanecerAnimais;
    }

    public void setPermanecerAnimais(String permanecerAnimais) {
        this.permanecerAnimais = permanecerAnimais;
    }

    public String getAnimaisAtual() {
        return animaisAtual;
    }

    public void setAnimaisAtual(String animaisAtual) {
        this.animaisAtual = animaisAtual;
    }

    public String getAnimalFalecido() {
        return animalFalecido;
    }

    public void setAnimalFalecido(String animalFalecido) {
        this.animalFalecido = animalFalecido;
    }

    public String getSustentarAnimal() {
        return sustentarAnimal;
    }

    public void setSustentarAnimal(String sustentarAnimal) {
        this.sustentarAnimal = sustentarAnimal;
    }

    public String getVizinhosAnimal() {
        return vizinhosAnimal;
    }

    public void setVizinhosAnimal(String vizinhosAnimal) {
        this.vizinhosAnimal = vizinhosAnimal;
    }

    public String getPasseioAnimal() {
        return passeioAnimal;
    }

    public void setPasseioAnimal(String passeioAnimal) {
        this.passeioAnimal = passeioAnimal;
    }

    public String getCustosAnimal() {
        return custosAnimal;
    }

    public void setCustosAnimal(String custosAnimal) {
        this.custosAnimal = custosAnimal;
    }

    public String getAlergiaAnimal() {
        return alergiaAnimal;
    }

    public void setAlergiaAnimal(String alergiaAnimal) {
        this.alergiaAnimal = alergiaAnimal;
    }

    public String getRespeitoAnimal() {
        return respeitoAnimal;
    }

    public void setRespeitoAnimal(String respeitoAnimal) {
        this.respeitoAnimal = respeitoAnimal;
    }

    public String getCriancaAnimal() {
        return criancaAnimal;
    }

    public void setCriancaAnimal(String criancaAnimal) {
        this.criancaAnimal = criancaAnimal;
    }

    public String getHorasAnimal() {
        return horasAnimal;
    }

    public void setHorasAnimal(String horasAnimal) {
        this.horasAnimal = horasAnimal;
    }

    public String getViajarAnimal() {
        return viajarAnimal;
    }

    public void setViajarAnimal(String viajarAnimal) {
        this.viajarAnimal = viajarAnimal;
    }

    public String getFugirAnimal() {
        return fugirAnimal;
    }

    public void setFugirAnimal(String fugirAnimal) {
        this.fugirAnimal = fugirAnimal;
    }

    public String getCriarAnimal() {
        return criarAnimal;
    }

    public void setCriarAnimal(String criarAnimal) {
        this.criarAnimal = criarAnimal;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getImovel() {
        return imovel;
    }

    public void setImovel(String imovel) {
        this.imovel = imovel;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCPFSecretario() {
        return CPFSecretario;
    }

    public void setCPFSecretario(String CPFSecretario) {
        this.CPFSecretario = CPFSecretario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Status: " + status;
    }
}
