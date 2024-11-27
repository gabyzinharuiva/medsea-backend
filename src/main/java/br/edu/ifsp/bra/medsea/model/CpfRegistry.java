package br.edu.ifsp.bra.medsea.model;

import jakarta.persistence.*;

@Entity
public class CpfRegistry {
    @Id
    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    // Construtor
    public CpfRegistry(String cpf) {
        this.cpf = cpf;
    }

    public CpfRegistry() {} // Construtor padrão necessário para JPA

    // Getter e Setter
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
