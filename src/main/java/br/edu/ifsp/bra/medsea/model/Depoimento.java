package br.edu.ifsp.bra.medsea.model;

import jakarta.persistence.Id;

public class Depoimento {
    @Id
    private String usuarioCpf; // CPF como identificador principal
    private String tituloDepoimento;
    private String conteudoDepoimento;

    public Depoimento(String usuarioCpf, String tituloDepoimento, String conteudoDepoimento) {
        this.usuarioCpf = usuarioCpf;
        this.tituloDepoimento = tituloDepoimento;
        this.conteudoDepoimento = conteudoDepoimento;
    }

    public String getUsuarioCpf() {
        return usuarioCpf;
    }

    public void setUsuarioCpf(String usuarioCpf) {
        this.usuarioCpf = usuarioCpf;
    }

    public String getTituloDepoimento() {
        return tituloDepoimento;
    }

    public void setTituloDepoimento(String tituloDepoimento) {
        this.tituloDepoimento = tituloDepoimento;
    }

    public String getConteudoDepoimento() {
        return conteudoDepoimento;
    }

    public void setConteudoDepoimento(String conteudoDepoimento) {
        this.conteudoDepoimento = conteudoDepoimento;
    }
}
