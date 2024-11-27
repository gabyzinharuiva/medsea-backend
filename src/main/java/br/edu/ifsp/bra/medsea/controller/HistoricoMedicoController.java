package br.edu.ifsp.bra.medsea.controller;

import br.edu.ifsp.bra.medsea.model.HistoricoMedico;
import br.edu.ifsp.bra.medsea.model.Paciente;
import br.edu.ifsp.bra.medsea.repository.HistoricoMedicoRepository;
import br.edu.ifsp.bra.medsea.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico-medico")
public class HistoricoMedicoController {

    @Autowired
    private HistoricoMedicoRepository historicoRepo;

    @Autowired
    private PacienteRepository pacienteRepo;

    @PostMapping("/{cpf}")
    public ResponseEntity<?> criarHistoricoMedico(@PathVariable String cpf, @RequestBody HistoricoMedico historico) {
        Paciente paciente = pacienteRepo.findById(cpf).orElse(null);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado.");
        }

        historico.setPaciente(paciente);
        HistoricoMedico salvo = historicoRepo.save(historico);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<List<HistoricoMedico>> obterHistoricoPorPaciente(@PathVariable String cpf) {
        List<HistoricoMedico> historicos = historicoRepo.findByPacienteCpf(cpf);
        return ResponseEntity.ok(historicos);
    }
}
