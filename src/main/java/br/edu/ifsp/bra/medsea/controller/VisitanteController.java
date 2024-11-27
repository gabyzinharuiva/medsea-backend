package br.edu.ifsp.bra.medsea.controller;

import br.edu.ifsp.bra.medsea.model.CpfRegistry;
import br.edu.ifsp.bra.medsea.model.Visitante;
import br.edu.ifsp.bra.medsea.repository.CpfRegistryRepository;
import br.edu.ifsp.bra.medsea.repository.VisitanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitante")
public class VisitanteController {

    @Autowired
    private VisitanteRepository visitanteRepo;

    @Autowired
    private CpfRegistryRepository cpfRegistryRepo;

    @PostMapping
    public ResponseEntity<?> createVisitante(@RequestBody Visitante newVisitante) {
        try {
            // Registrar CPF na tabela auxiliar
            cpfRegistryRepo.save(new CpfRegistry(newVisitante.getCpf()));

            // Salvar o Visitante
            Visitante createdVisitante = visitanteRepo.save(newVisitante);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVisitante);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: CPF já registrado!");
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Visitante> getVisitanteByCpf(@PathVariable String cpf) {
        return visitanteRepo.findById(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<Visitante> getAllVisitantes() {
        return visitanteRepo.findAll();
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Visitante> updateVisitante(@PathVariable String cpf, @RequestBody Visitante updatedVisitante) {
        return visitanteRepo.findById(cpf)
                .map(visitante -> {
                    visitante.setNome(updatedVisitante.getNome());
                    visitanteRepo.save(visitante);
                    return ResponseEntity.ok(visitante);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteVisitante(@PathVariable String cpf) {
        return visitanteRepo.findById(cpf)
                .map(visitante -> {
                    visitanteRepo.delete(visitante);
                    cpfRegistryRepo.deleteById(cpf); // Remover o CPF da tabela auxiliar
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
