package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    Optional<Emprestimo> findByExemplarIdAndDataHoraEntregaIsNull(
            Long exemplarId
    );

    List<Emprestimo> findByClienteIdAndDataHoraEntregaIsNull(
            Long clienteId
    );

    boolean existsByClienteIdAndExemplarObraIdAndDataHoraEntregaIsNull(
            Long clienteId,
            Long obraId
    );

    boolean existsByExemplarIdAndDataHoraEntregaIsNull(
            Long exemplarId
    );
}