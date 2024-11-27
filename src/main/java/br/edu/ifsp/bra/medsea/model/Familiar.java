package br.edu.ifsp.bra.medsea.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Familiar extends Cadastro {

    private int cartaoSUSFamiliar;
    private int convenioFamiliar;

    @ManyToOne
    @JoinColumn(name = "paciente_cpf")
    private Paciente paciente;

    // Getters e Setters
    
    public int getCartaoSUSFamiliar() {
        return cartaoSUSFamiliar;
    }

    public void setCartaoSUSFamiliar(int cartaoSUSFamiliar) {
        this.cartaoSUSFamiliar = cartaoSUSFamiliar;
    }

    public int getConvenioFamiliar() {
        return convenioFamiliar;
    }

    public void setConvenioFamiliar(int convenioFamiliar) {
        this.convenioFamiliar = convenioFamiliar;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
