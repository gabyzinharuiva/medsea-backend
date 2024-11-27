package br.edu.ifsp.bra.medsea.repository;

import br.edu.ifsp.bra.medsea.model.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamiliarRepository extends JpaRepository<Familiar, String> {
    Optional<Familiar> findByCpf(String cpf);
}
