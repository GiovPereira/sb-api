package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Obra;
import br.edu.ifsudestemg.sb.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByObraOrderByPosicaoFilaAsc(
            Obra obra
    );

    Optional<Reserva> findTopByObraOrderByPosicaoFilaDesc(
            Obra obra
    );

    boolean existsByClienteIdAndObraId(
            Long clienteId,
            Long obraId
    );

    Integer countByObra(
            Obra obra
    );

    List<Reserva> findByObraIdAndStatusReservaIdOrderByPosicaoFilaAsc(
            Long obraId,
            Long statusReservaId
    );

    List<Reserva> findByStatusReservaId(
            Long statusReservaId
    );

    boolean existsByObraIdAndStatusReservaId(
            Long obraId,
            Long statusReservaId
    );
}