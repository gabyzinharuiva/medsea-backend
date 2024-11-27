package br.edu.ifsp.bra.medsea.repository;

import br.edu.ifsp.bra.medsea.model.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitanteRepository extends JpaRepository<Visitante, String> {
}
