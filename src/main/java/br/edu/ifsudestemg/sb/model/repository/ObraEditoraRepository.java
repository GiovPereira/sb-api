package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.ObraEditora;
import br.edu.ifsudestemg.sb.model.entity.ObraGenero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObraEditoraRepository extends JpaRepository<ObraEditora, Long> {

}