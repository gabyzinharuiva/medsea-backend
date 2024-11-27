package br.edu.ifsp.bra.medsea.repository;

import br.edu.ifsp.bra.medsea.model.HistoricoMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoricoMedicoRepository extends JpaRepository<HistoricoMedico, Long> {
    List<HistoricoMedico> findByPacienteCpf(String cpf);
}

