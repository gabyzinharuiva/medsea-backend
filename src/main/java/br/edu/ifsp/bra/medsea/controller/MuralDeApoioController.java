package br.edu.ifsp.bra.medsea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ifsp.bra.medsea.model.Comentario;
import br.edu.ifsp.bra.medsea.model.MuralDeApoio;
import br.edu.ifsp.bra.medsea.repository.MuralDeApoioRepository;
import br.edu.ifsp.bra.medsea.repository.ComentarioRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mural")
public class MuralDeApoioController {

    @Autowired
    private MuralDeApoioRepository muralDeApoioRepo;

    @Autowired
    private ComentarioRepository comentarioRepo; // Adicionado repositório de comentários

    // Método GET para obter todos os murais de apoio
    @GetMapping
    public List<MuralDeApoio> getAllMural() {
        return (List<MuralDeApoio>) muralDeApoioRepo.findAll();
    }

    // Método GET para obter um mural de apoio específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<MuralDeApoio> getMuralById(@PathVariable int id) {
        Optional<MuralDeApoio> mural = muralDeApoioRepo.findById(id);
        return mural.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método POST para criar um novo mural de apoio
    @PostMapping
    public ResponseEntity<MuralDeApoio> createNewMural(@RequestBody MuralDeApoio newMural) {
        for (Comentario comentario : newMural.getComentarios()) {
            comentario.setMuralDeApoio(newMural);
        }
        MuralDeApoio savedMural = muralDeApoioRepo.save(newMural);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMural);
    }

    // Método PUT para atualizar um mural de apoio existente
    @PutMapping("/{id}")
    public ResponseEntity<MuralDeApoio> updateMural(@PathVariable int id, @RequestBody MuralDeApoio muralAtualizado) {
        Optional<MuralDeApoio> muralExistente = muralDeApoioRepo.findById(id);

        if (muralExistente.isPresent()) {
            MuralDeApoio mural = muralExistente.get();
            mural.setConteudoComentario(muralAtualizado.getConteudoComentario());
            mural.setComentarios(muralAtualizado.getComentarios());
            return ResponseEntity.ok(muralDeApoioRepo.save(mural));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método DELETE para remover um mural de apoio
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMural(@PathVariable int id) {
        if (muralDeApoioRepo.existsById(id)) {
            muralDeApoioRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Mural deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mural não encontrado.");
        }
    }

    // Método POST para criar um novo comentário em um mural
    @PostMapping("/{muralId}/comentario")
    public ResponseEntity<Comentario> createComentario(@PathVariable int muralId, @RequestBody Comentario newComentario) {
        Optional<MuralDeApoio> muralOpt = muralDeApoioRepo.findById(muralId);
    
        if (muralOpt.isPresent()) {
            MuralDeApoio mural = muralOpt.get();
            newComentario.setMuralDeApoio(mural);
            mural.getComentarios().add(newComentario);
            comentarioRepo.save(newComentario); // Salva o novo comentário
            return ResponseEntity.status(HttpStatus.CREATED).body(newComentario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Método DELETE para remover um comentário
    @DeleteMapping("/comentario/{id}")
    public ResponseEntity<String> deleteComentario(@PathVariable int id) {
        if (comentarioRepo.existsById(id)) { // Alterado para usar comentarioRepo
            comentarioRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Comentário deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comentário não encontrado.");
        }
    }
}
