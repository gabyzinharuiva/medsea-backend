package br.edu.ifsp.bra.medsea.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Paciente extends Cadastro {
   
    private String nome;
    private String endereco;
    private String cartaoSUSPaciente;
    private String convenioPaciente;

    // Construtor padrão (sem parâmetros)
    public Paciente() {
    }

    // Construtor com parâmetros, se necessário
    public Paciente(String nome, String endereco, String cartaoSUSPaciente, String convenioPaciente) {
        this.nome = nome;
        this.endereco = endereco;
        this.cartaoSUSPaciente = cartaoSUSPaciente;
        this.convenioPaciente = convenioPaciente;
    }

    // Getters e setters
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCartaoSUSPaciente() {
        return cartaoSUSPaciente;
    }

    public void setCartaoSUSPaciente(String cartaoSUSPaciente) {
        this.cartaoSUSPaciente = cartaoSUSPaciente;
    }

    public String getConvenioPaciente() {
        return convenioPaciente;
    }

    public void setConvenioPaciente(String convenioPaciente) {
        this.convenioPaciente = convenioPaciente;
    }
}
