package br.edu.ifsp.bra.medsea.controller;

import br.edu.ifsp.bra.medsea.model.Comentario;
import br.edu.ifsp.bra.medsea.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepo;

    // Obter todos os comentários
    @GetMapping
    public ResponseEntity<List<Comentario>> getAllComentarios() {
        List<Comentario> comentarios = comentarioRepo.findAll();
        return ResponseEntity.ok(comentarios);
    }

    // Obter um comentário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Comentario> getComentarioById(@PathVariable int id) {
        Optional<Comentario> comentario = comentarioRepo.findById(id);
        return comentario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Criar um novo comentário
    @PostMapping
    public ResponseEntity<Comentario> createComentario(@RequestBody Comentario comentario) {
        if (comentario.getFotoAutor() != null && 
        !(comentario.getFotoAutor().startsWith("data:image/") || comentario.getFotoAutor().startsWith("http"))) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

        Comentario savedComentario = comentarioRepo.save(comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComentario);
    }

    // Atualizar um comentário existente
    @PutMapping("/{id}")
    public ResponseEntity<Comentario> updateComentario(@PathVariable int id, @RequestBody Comentario comentarioAtualizado) {
        Optional<Comentario> comentarioOpt = comentarioRepo.findById(id);

        if (comentarioOpt.isPresent()) {
            Comentario comentario = comentarioOpt.get();
            comentario.setConteudo(comentarioAtualizado.getConteudo());
            comentario.setAutor(comentarioAtualizado.getAutor());
            comentario.setFotoAutor(comentarioAtualizado.getFotoAutor());
            return ResponseEntity.ok(comentarioRepo.save(comentario));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Deletar um comentário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable int id) {
        if (comentarioRepo.existsById(id)) {
            comentarioRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
