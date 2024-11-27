// PerfilRepository.java
package br.edu.ifsp.bra.medsea.repository;

import br.edu.ifsp.bra.medsea.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, String> {
    Perfil findByNomeUsuario(String nomeUsuario); 
}
