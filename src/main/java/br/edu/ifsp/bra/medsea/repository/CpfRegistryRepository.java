package br.edu.ifsp.bra.medsea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifsp.bra.medsea.model.CpfRegistry;

public interface CpfRegistryRepository extends JpaRepository<CpfRegistry, String> {
}

