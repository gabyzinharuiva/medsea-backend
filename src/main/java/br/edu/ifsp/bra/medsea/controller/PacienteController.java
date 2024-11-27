package br.edu.ifsp.bra.medsea.controller;

import br.edu.ifsp.bra.medsea.model.CpfRegistry;
import br.edu.ifsp.bra.medsea.model.Paciente;
import br.edu.ifsp.bra.medsea.repository.CpfRegistryRepository;
import br.edu.ifsp.bra.medsea.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepo;

    @Autowired
    private CpfRegistryRepository cpfRegistryRepo;

    @PostMapping
    public ResponseEntity<?> createPaciente(@RequestBody Paciente newPaciente) {
        try {
            // Registrar CPF na tabela auxiliar
            cpfRegistryRepo.save(new CpfRegistry(newPaciente.getCpf()));

            // Salvar o Paciente
            Paciente createdPaciente = pacienteRepo.save(newPaciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPaciente);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: CPF já registrado!");
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Paciente> getPacienteByCpf(@PathVariable String cpf) {
        return pacienteRepo.findById(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteRepo.findAll();
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable String cpf, @RequestBody Paciente updatedPaciente) {
        return pacienteRepo.findById(cpf)
                .map(paciente -> {
                    paciente.setNome(updatedPaciente.getNome());
                    pacienteRepo.save(paciente);
                    return ResponseEntity.ok(paciente);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletePaciente(@PathVariable String cpf) {
        return pacienteRepo.findById(cpf)
                .map(paciente -> {
                    pacienteRepo.delete(paciente);
                    cpfRegistryRepo.deleteById(cpf); // Remover o CPF da tabela auxiliar
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
