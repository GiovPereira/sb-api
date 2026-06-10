package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Obra;
import br.edu.ifsudestemg.sb.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByObraOrderByPosicaoFilaAsc(Obra obra);

    boolean existsByClienteIdAndObraId(Long clienteId, Long obraId);

    Integer countByObra(Obra obra);
}