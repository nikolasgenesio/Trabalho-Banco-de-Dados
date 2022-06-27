package com.example.pets_donation;

public class BuscaCepException extends Exception {
    private String CEP;

    /**
     * Gera uma nova exceção
     *
     * @param message descrição do erro
     */
    public BuscaCepException(String message) {
        super(message);

        this.CEP = "";
    }

    /**
     * Gera uma nova exceção e define o CEP que foi solicitado
     *
     * @param message descrição do erro
     * @param cep     CEP que foi usado durante o processo
     */
    public BuscaCepException(String message, String cep) {
        super(message);

        this.CEP = cep;
    }

    /**
     * Define o CEP da exceção
     *
     * @param cep
     */
    public void setCEP(String cep) {
        this.CEP = cep;
    }

    /**
     * Retorna o CEP da exceção
     *
     * @return String CEP
     */
    public String getCEP() {
        return this.CEP;
    }

    /**
     * Retorna se tem algum CEP
     *
     * @return boolean
     */
    public boolean hasCEP() {
        return !this.CEP.isEmpty();
    }
}
