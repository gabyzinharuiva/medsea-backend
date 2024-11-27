package br.edu.ifsp.bra.medsea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifsp.bra.medsea.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
}
