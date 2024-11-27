package br.edu.ifsp.bra.medsea.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Arrays;

@Entity
public class Perfil {
    @Id
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "nome_usuario", nullable = false)
    private String nomeUsuario;

    @Column(name = "bio_usuario", nullable = false)
    private String bioUsuario;

    @Column(name = "foto_usuario", nullable = true)
    private byte[] fotoUsuario;  

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getBioUsuario() {
        return bioUsuario;
    }

    public void setBioUsuario(String bioUsuario) {
        this.bioUsuario = bioUsuario;
    }

    public byte[] getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(byte[] fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "cpf=" + cpf +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", bioUsuario='" + bioUsuario + '\'' +
                ", fotoUsuario=" + Arrays.toString(fotoUsuario) +
                '}';
    }
}
