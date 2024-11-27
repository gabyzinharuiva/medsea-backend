package br.edu.ifsp.bra.medsea.repository;

import br.edu.ifsp.bra.medsea.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, String> {
}
