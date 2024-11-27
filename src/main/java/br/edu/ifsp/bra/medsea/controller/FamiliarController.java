package br.edu.ifsp.bra.medsea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ifsp.bra.medsea.model.CpfRegistry;
import br.edu.ifsp.bra.medsea.model.Familiar;
import br.edu.ifsp.bra.medsea.repository.CpfRegistryRepository;
import br.edu.ifsp.bra.medsea.repository.FamiliarRepository;

@RestController
@RequestMapping("/api/familiar")
public class FamiliarController {

    @Autowired
    private FamiliarRepository familiarRepo;

    @Autowired
    private CpfRegistryRepository cpfRegistryRepo;

    @PostMapping
    public ResponseEntity<?> createFamiliar(@RequestBody Familiar newFamiliar) {
        try {
            // Registrar CPF na tabela auxiliar
            cpfRegistryRepo.save(new CpfRegistry(newFamiliar.getCpf()));

            // Salvar o Familiar
            Familiar createdFamiliar = familiarRepo.save(newFamiliar);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFamiliar);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: CPF já registrado!");
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Familiar> getFamiliarByCpf(@PathVariable String cpf) {
        return familiarRepo.findById(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<Familiar> getAllFamiliares() {
        return familiarRepo.findAll();
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Familiar> updateFamiliar(@PathVariable String cpf, @RequestBody Familiar updatedFamiliar) {
        return familiarRepo.findById(cpf)
                .map(familiar -> {
                    familiar.setCartaoSUSFamiliar(updatedFamiliar.getCartaoSUSFamiliar());
                    familiar.setConvenioFamiliar(updatedFamiliar.getConvenioFamiliar());
                    familiar.setPaciente(updatedFamiliar.getPaciente());
                    familiarRepo.save(familiar);
                    return ResponseEntity.ok(familiar);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteFamiliar(@PathVariable String cpf) {
        return familiarRepo.findById(cpf)
                .map(familiar -> {
                    familiarRepo.delete(familiar);
                    cpfRegistryRepo.deleteById(cpf); // Remover o CPF da tabela auxiliar
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
