package br.edu.ifsp.bra.medsea.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String conteudo;
    private String autor;
    
    private String fotoAutor;

// Getter e Setter

    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "mural_de_apoio_id")
    private MuralDeApoio muralDeApoio;;

    public String getFotoAutor() {
        return fotoAutor;
    }
    
    public void setFotoAutor(String fotoAutor) {
        this.fotoAutor = fotoAutor;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public MuralDeApoio getMuralDeApoio() {
        return muralDeApoio;
    }

    public void setMuralDeApoio(MuralDeApoio muralDeApoio) {
        this.muralDeApoio = muralDeApoio;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
 
}
