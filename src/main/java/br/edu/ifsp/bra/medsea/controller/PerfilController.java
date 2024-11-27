package br.edu.ifsp.bra.medsea.controller;

import br.edu.ifsp.bra.medsea.model.Perfil;
import br.edu.ifsp.bra.medsea.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    private final PerfilRepository perfilRepository;

    @Autowired
    public PerfilController(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    // Método GET para todos os perfis
    @GetMapping
    public List<Perfil> getAllPerfis() {
        return perfilRepository.findAll();
    }

    // Método GET para um perfil específico pelo CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<Perfil> getPerfilByCpf(@PathVariable String cpf) {
        Optional<Perfil> perfil = perfilRepository.findById(cpf);
        return perfil.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método POST para adicionar um novo perfil
    @PostMapping
    public ResponseEntity<Perfil> addPerfil(@RequestBody Perfil perfil) {
        if (perfilRepository.existsById(perfil.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Perfil createdPerfil = perfilRepository.save(perfil);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerfil);
    }

    // Método PUT para atualizar um perfil existente
    @PutMapping("/{cpf}")
    public ResponseEntity<Perfil> updatePerfil(@PathVariable String cpf, @RequestBody Perfil perfilAtualizado) {
        return perfilRepository.findById(cpf)
                .map(perfil -> {
                    perfil.setNomeUsuario(perfilAtualizado.getNomeUsuario());
                    perfil.setBioUsuario(perfilAtualizado.getBioUsuario());
                    perfil.setFotoUsuario(perfilAtualizado.getFotoUsuario());
                    Perfil perfilSalvo = perfilRepository.save(perfil);
                    return ResponseEntity.ok(perfilSalvo);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método DELETE para excluir um perfil pelo CPF
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletePerfil(@PathVariable String cpf) {
        return perfilRepository.findById(cpf)
                .map(perfil -> {
                    perfilRepository.delete(perfil);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
