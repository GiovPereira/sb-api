package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.api.dto.EmprestimoDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    private final EmprestimoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ExemplarRepository exemplarRepository;
    private final DuracaoPadraoEmprestimoRepository duracaoRepository;
    private final ValorDiarioMultaRepository valorMultaRepository;
    private final StatusExemplarRepository statusExemplarRepository;
    private final ReservaRepository reservaRepository;
    private final StatusReservaRepository statusReservaRepository;

    public EmprestimoService(
            EmprestimoRepository repository,
            ClienteRepository clienteRepository,
            ExemplarRepository exemplarRepository,
            DuracaoPadraoEmprestimoRepository duracaoRepository,
            ValorDiarioMultaRepository valorMultaRepository,
            StatusExemplarRepository statusExemplarRepository,
            ReservaRepository reservaRepository,
            StatusReservaRepository statusReservaRepository
    ) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.exemplarRepository = exemplarRepository;
        this.duracaoRepository = duracaoRepository;
        this.valorMultaRepository = valorMultaRepository;
        this.statusExemplarRepository = statusExemplarRepository;
        this.reservaRepository = reservaRepository;
        this.statusReservaRepository = statusReservaRepository;
    }

    // =========================
    // CONSULTAS
    // =========================

    public List<Emprestimo> getEmprestimos() {
        atualizarEmprestimosAtrasados();
        return repository.findAll();
    }

    public List<EmprestimoDTO> getEmprestimosDTO() {
        atualizarEmprestimosAtrasados();
        return repository.findAll()
                .stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
    }

    public Optional<Emprestimo> getEmprestimoById(Long id) {
        atualizarEmprestimosAtrasados();
        return repository.findById(id);
    }

    // =========================
    // DTO
    // =========================

    public EmprestimoDTO createDTO(Emprestimo emprestimo) {

        atualizarMulta(emprestimo);

        ModelMapper mapper = new ModelMapper();
        EmprestimoDTO dto = mapper.map(emprestimo, EmprestimoDTO.class);

        if (emprestimo.getCliente() != null) {
            dto.setIdCliente(emprestimo.getCliente().getId());
            dto.setNomeCliente(emprestimo.getCliente().getNome());
        }

        if (emprestimo.getExemplar() != null) {
            dto.setIdExemplar(emprestimo.getExemplar().getId());
            dto.setTomboExemplar(emprestimo.getExemplar().getTombo());
            dto.setCodigoBarrasExemplar(emprestimo.getExemplar().getCodigoBarras());

            if (emprestimo.getExemplar().getObra() != null) {
                dto.setTituloObra(emprestimo.getExemplar().getObra().getTitulo());
            }
        }

        if (emprestimo.getDuracaoPadraoEmprestimo() != null) {
            dto.setIdDuracaoPadraoEmprestimo(
                    emprestimo.getDuracaoPadraoEmprestimo().getId()
            );
            dto.setDiasUteis(
                    emprestimo.getDuracaoPadraoEmprestimo().getDiasUteis()
            );
        }

        if (emprestimo.getValorDiarioMulta() != null) {
            dto.setIdValorDiarioMulta(
                    emprestimo.getValorDiarioMulta().getId()
            );
            dto.setValorDia(
                    emprestimo.getValorDiarioMulta().getValorDia()
            );
        }

        return dto;
    }

    // =========================
    // CRIAR
    // =========================

    @Transactional
    public Emprestimo salvar(Emprestimo emprestimo, Long idCliente, Long idExemplar) {

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado."));

        Exemplar exemplar = exemplarRepository.findById(idExemplar)
                .orElseThrow(() -> new RegraNegocioException("Exemplar não encontrado."));

        if (!exemplar.getStatusExemplar().getId().equals(1L)) {
            throw new RegraNegocioException("Somente exemplares disponíveis podem ser emprestados.");
        }

        if (repository.existsByClienteIdAndExemplarObraIdAndDataHoraEntregaIsNull(
                cliente.getId(),
                exemplar.getObra().getId()
        )) {
            throw new RegraNegocioException("O cliente já possui um empréstimo ativo desta obra.");
        }

        DuracaoPadraoEmprestimo duracao = duracaoRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new RegraNegocioException("Nenhuma duração padrão cadastrada."));

        ValorDiarioMulta multa = valorMultaRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new RegraNegocioException("Nenhum valor de multa cadastrado."));

        StatusExemplar emPosse = statusExemplarRepository.findById(3L)
                .orElseThrow(() -> new RegraNegocioException("Status Em Posse não encontrado."));

        exemplar.setStatusExemplar(emPosse);
        exemplarRepository.save(exemplar);

        emprestimo.setCliente(cliente);
        emprestimo.setExemplar(exemplar);
        emprestimo.setDuracaoPadraoEmprestimo(duracao);
        emprestimo.setValorDiarioMulta(multa);

        emprestimo.setDataHoraEmprestimo(LocalDateTime.now());
        emprestimo.setDataPrevistaDevolucao(
                LocalDate.now().plusDays(duracao.getDiasUteis())
        );

        emprestimo.setDataHoraEntrega(null);
        emprestimo.setMulta(BigDecimal.ZERO);

        validar(emprestimo);

        return repository.save(emprestimo);
    }

    // =========================
    // ATUALIZAR (RESTORED)
    // =========================

    @Transactional
    public Emprestimo atualizar(Emprestimo emprestimo, Long idCliente, Long idExemplar) {

        Emprestimo original = repository.findById(emprestimo.getId())
                .orElseThrow(() -> new RegraNegocioException("Empréstimo não encontrado."));

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado."));

        Exemplar exemplar = exemplarRepository.findById(idExemplar)
                .orElseThrow(() -> new RegraNegocioException("Exemplar não encontrado."));

        // RN: não permite mudar regras críticas do empréstimo
        emprestimo.setCliente(cliente);
        emprestimo.setExemplar(exemplar);

        emprestimo.setDuracaoPadraoEmprestimo(original.getDuracaoPadraoEmprestimo());
        emprestimo.setValorDiarioMulta(original.getValorDiarioMulta());
        emprestimo.setDataHoraEmprestimo(original.getDataHoraEmprestimo());
        emprestimo.setDataPrevistaDevolucao(original.getDataPrevistaDevolucao());
        emprestimo.setDataHoraEntrega(original.getDataHoraEntrega());

        atualizarMulta(emprestimo);
        validar(emprestimo);

        return repository.save(emprestimo);
    }

    // =========================
    // EXCLUIR (RESTORED)
    // =========================

    @Transactional
    public void excluir(Emprestimo emprestimo) {

        Objects.requireNonNull(emprestimo.getId());

        try {
            repository.delete(emprestimo);
            repository.flush();

        } catch (DataIntegrityViolationException e) {
            throw new RegraNegocioException("Não foi possível excluir o empréstimo.");
        }
    }

    // =========================
    // ENTREGA
    // =========================

    @Transactional
    public Emprestimo registrarEntrega(Long idEmprestimo) {

        Emprestimo emprestimo = repository.findById(idEmprestimo)
                .orElseThrow(() -> new RegraNegocioException("Empréstimo não encontrado."));

        if (emprestimo.getDataHoraEntrega() != null) {
            throw new RegraNegocioException("Empréstimo já finalizado.");
        }

        atualizarMulta(emprestimo);
        emprestimo.setDataHoraEntrega(LocalDateTime.now());

        StatusExemplar disponivel = statusExemplarRepository.findById(1L)
                .orElseThrow(() -> new RegraNegocioException("Status Disponível não encontrado."));

        Exemplar exemplar = emprestimo.getExemplar();
        exemplar.setStatusExemplar(disponivel);

        exemplarRepository.save(exemplar);
        repository.save(emprestimo);

        promoverReserva(exemplar);

        return emprestimo;
    }

    // =========================
    // RESERVA
    // =========================

    private void promoverReserva(Exemplar exemplar) {

        List<Reserva> fila =
                reservaRepository.findByObraIdAndStatusReservaIdOrderByPosicaoFilaAsc(
                        exemplar.getObra().getId(), 1L
                );

        if (fila.isEmpty()) return;

        Reserva reserva = fila.get(0);

        StatusReserva disponivelRetirada = statusReservaRepository.findById(2L)
                .orElseThrow(() -> new RegraNegocioException("Status Reserva não encontrado."));

        StatusExemplar reservado = statusExemplarRepository.findById(2L)
                .orElseThrow(() -> new RegraNegocioException("Status Reservado não encontrado."));

        exemplar.setStatusExemplar(reservado);
        exemplarRepository.save(exemplar);

        reserva.setExemplar(exemplar);
        reserva.setStatusReserva(disponivelRetirada);
        reserva.setDataMaximaPrevistaColeta(
                LocalDate.now().plusDays(
                        reserva.getDuracaoPadraoReserva().getDiasUteis()
                )
        );

        reservaRepository.save(reserva);
    }

    // =========================
    // ATRASOS
    // =========================

    @Transactional
    public void atualizarEmprestimosAtrasados() {

        List<Emprestimo> emprestimos = repository.findAll();
        StatusExemplar emAtraso = statusExemplarRepository.findById(4L).orElse(null);

        if (emAtraso == null) return;

        LocalDate hoje = LocalDate.now();

        for (Emprestimo e : emprestimos) {

            if (e.getDataHoraEntrega() == null &&
                    hoje.isAfter(e.getDataPrevistaDevolucao())) {

                Exemplar ex = e.getExemplar();

                if (ex != null && ex.getStatusExemplar() != null &&
                        ex.getStatusExemplar().getId().equals(3L)) {

                    ex.setStatusExemplar(emAtraso);
                    exemplarRepository.save(ex);
                }

                atualizarMulta(e);
                repository.save(e);
            }
        }
    }

    private void atualizarMulta(Emprestimo emprestimo) {

        if (emprestimo.getDataHoraEntrega() != null) return;

        LocalDate hoje = LocalDate.now();

        if (!hoje.isAfter(emprestimo.getDataPrevistaDevolucao())) {
            emprestimo.setMulta(BigDecimal.ZERO);
            return;
        }

        long dias = ChronoUnit.DAYS.between(
                emprestimo.getDataPrevistaDevolucao(),
                hoje
        );

        BigDecimal valor = emprestimo.getValorDiarioMulta().getValorDia()
                .multiply(BigDecimal.valueOf(dias));

        emprestimo.setMulta(valor);
    }

    // =========================
    // VALIDAÇÃO
    // =========================

    public void validar(Emprestimo emprestimo) {

        if (emprestimo.getCliente() == null)
            throw new RegraNegocioException("Informe o cliente.");

        if (emprestimo.getExemplar() == null)
            throw new RegraNegocioException("Informe o exemplar.");

        if (emprestimo.getDuracaoPadraoEmprestimo() == null)
            throw new RegraNegocioException("Duração não encontrada.");

        if (emprestimo.getValorDiarioMulta() == null)
            throw new RegraNegocioException("Valor da multa não encontrado.");
    }
}