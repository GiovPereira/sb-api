package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifsudestemg.sb.api.dto.ReservaDTO;
import org.modelmapper.ModelMapper;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository repository;
    private final ClienteRepository clienteRepository;
    private final ObraRepository obraRepository;
    private final ExemplarRepository exemplarRepository;
    private final StatusReservaRepository statusReservaRepository;
    private final StatusExemplarRepository statusExemplarRepository;
    private final DuracaoPadraoReservaRepository duracaoPadraoReservaRepository;

    public ReservaService(
            ReservaRepository repository,
            ClienteRepository clienteRepository,
            ObraRepository obraRepository,
            ExemplarRepository exemplarRepository,
            StatusReservaRepository statusReservaRepository,
            StatusExemplarRepository statusExemplarRepository,
            DuracaoPadraoReservaRepository duracaoPadraoReservaRepository) {

        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.obraRepository = obraRepository;
        this.exemplarRepository = exemplarRepository;
        this.statusReservaRepository = statusReservaRepository;
        this.statusExemplarRepository = statusExemplarRepository;
        this.duracaoPadraoReservaRepository = duracaoPadraoReservaRepository;
    }

    public List<ReservaDTO> getReservasDTO() {

        atualizarReservasExpiradas();

        return repository.findAll()
                .stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
    }

    public ReservaDTO createDTO(
            Reserva reserva) {

        ModelMapper modelMapper =
                new ModelMapper();

        ReservaDTO dto =
                modelMapper.map(
                        reserva,
                        ReservaDTO.class
                );

        if (reserva.getCliente() != null) {

            dto.setIdCliente(
                    reserva.getCliente().getId()
            );

            dto.setNomeCliente(
                    reserva.getCliente().getNome()
            );
        }

        if (reserva.getObra() != null) {

            dto.setIdObra(
                    reserva.getObra().getId()
            );

            dto.setTituloObra(
                    reserva.getObra().getTitulo()
            );
        }

        if (reserva.getExemplar() != null) {

            dto.setIdExemplar(
                    reserva.getExemplar().getId()
            );
        }

        if (reserva.getStatusReserva() != null) {

            dto.setIdStatusReserva(
                    reserva.getStatusReserva().getId()
            );

            dto.setNomeStatusReserva(
                    reserva.getStatusReserva().getNome()
            );
        }

        if (reserva.getDuracaoPadraoReserva() != null) {

            dto.setIdDuracaoPadraoReserva(
                    reserva.getDuracaoPadraoReserva().getId()
            );

            dto.setDiasUteisDuracao(
                    reserva.getDuracaoPadraoReserva().getDiasUteis()
            );
        }

        return dto;
    }

    public List<Reserva> getReservas() {

        atualizarReservasExpiradas();

        return repository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id) {

        atualizarReservasExpiradas();

        return repository.findById(id);
    }

    @Transactional
    public Reserva salvar(
            Reserva reserva,
            Long idCliente,
            Long idObra) {

        Cliente cliente =
                clienteRepository.findById(idCliente)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Cliente não encontrado."
                                ));

        Obra obra =
                obraRepository.findById(idObra)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Obra não encontrada."
                                ));

        if (repository.existsByClienteIdAndObraId(
                cliente.getId(),
                obra.getId())) {

            throw new RegraNegocioException(
                    "Cliente já possui reserva para esta obra."
            );
        }

        reserva.setCliente(cliente);
        reserva.setObra(obra);

        validar(reserva);

        reserva.setDataHoraReserva(
                LocalDateTime.now()
        );

        DuracaoPadraoReserva duracao =
                duracaoPadraoReservaRepository
                        .findTopByOrderByIdDesc()
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Nenhuma duração padrão cadastrada."
                                ));

        reserva.setDuracaoPadraoReserva(
                duracao
        );

        StatusReserva statusSolicitada =
                statusReservaRepository
                        .findById(1L)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Status Solicitada não encontrado."
                                ));

        StatusReserva statusDisponivel =
                statusReservaRepository
                        .findById(3L)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Status Disponível para Retirada não encontrado."
                                ));

        Optional<Exemplar> exemplarDisponivel =
                exemplarRepository
                        .findFirstByObraIdAndStatusExemplarId(
                                obra.getId(),
                                1L
                        );

        if (exemplarDisponivel.isPresent()) {

            Exemplar exemplar =
                    exemplarDisponivel.get();

            StatusExemplar reservado =
                    statusExemplarRepository
                            .findById(3L)
                            .orElseThrow(() ->
                                    new RegraNegocioException(
                                            "Status RESERVADO não encontrado."
                                    ));

            exemplar.setStatusExemplar(
                    reservado
            );

            exemplarRepository.save(
                    exemplar
            );

            reserva.setExemplar(
                    exemplar
            );

            reserva.setStatusReserva(
                    statusDisponivel
            );

            reserva.setDataMaximaPrevistaColeta(
                    LocalDate.now()
                            .plusDays(
                                    duracao.getDiasUteis()
                            )
            );

        } else {

            reserva.setExemplar(null);

            reserva.setStatusReserva(
                    statusSolicitada
            );

            reserva.setDataMaximaPrevistaColeta(
                    LocalDate.now()
            );
        }

        Optional<Reserva> ultimaReserva =
                repository.findTopByObraOrderByPosicaoFilaDesc(
                        obra
                );

        Integer proximaPosicao =
                ultimaReserva
                        .map(r -> r.getPosicaoFila() + 1)
                        .orElse(1);

        reserva.setPosicaoFila(
                proximaPosicao
        );

        return repository.save(
                reserva
        );
    }

    @Transactional
    public Reserva atualizar(
            Reserva reserva,
            Long idCliente,
            Long idObra) {

        Objects.requireNonNull(
                reserva.getId()
        );

        Cliente cliente =
                clienteRepository.findById(idCliente)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Cliente não encontrado."
                                ));

        Obra obra =
                obraRepository.findById(idObra)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Obra não encontrada."
                                ));

        reserva.setCliente(
                cliente
        );

        reserva.setObra(
                obra
        );

        validar(
                reserva
        );

        return repository.save(
                reserva
        );
    }

    @Transactional
    public void excluir(
            Reserva reserva) {

        Objects.requireNonNull(
                reserva.getId()
        );

        try {

            Obra obra =
                    reserva.getObra();

            Exemplar exemplar =
                    reserva.getExemplar();

            repository.delete(
                    reserva
            );

            repository.flush();

            if (exemplar != null) {

                StatusExemplar disponivel =
                        statusExemplarRepository
                                .findById(1L)
                                .orElseThrow(() ->
                                        new RegraNegocioException(
                                                "Status DISPONÍVEL não encontrado."
                                        ));

                exemplar.setStatusExemplar(
                        disponivel
                );

                exemplarRepository.save(
                        exemplar
                );

                promoverProximaReserva(
                        obra,
                        exemplar
                );
            }

            reorganizarFila(
                    obra
            );

        } catch (
                DataIntegrityViolationException e) {

            throw new RegraNegocioException(
                    "Não foi possível excluir a reserva."
            );
        }
    }

    @Transactional
    public void atualizarReservasExpiradas() {

        StatusReserva statusExpirada =
                statusReservaRepository
                        .findById(4L)
                        .orElse(null);

        if (statusExpirada == null) {
            return;
        }

        LocalDate hoje =
                LocalDate.now();

        List<Reserva> reservas =
                repository.findAll();

        for (Reserva reserva : reservas) {

            if (
                    reserva.getDataHoraColetaEfetiva() == null
                            &&
                            reserva.getDataMaximaPrevistaColeta() != null
                            &&
                            hoje.isAfter(
                                    reserva.getDataMaximaPrevistaColeta()
                            )
            ) {

                if (
                        reserva.getStatusReserva() != null
                                &&
                                !reserva.getStatusReserva()
                                        .getId()
                                        .equals(4L)
                ) {

                    reserva.setStatusReserva(
                            statusExpirada
                    );

                    reserva.setPosicaoFila(
                            0
                    );

                    Exemplar exemplarLiberado =
                            reserva.getExemplar();

                    if (exemplarLiberado != null) {

                        StatusExemplar disponivel =
                                statusExemplarRepository
                                        .findById(1L)
                                        .orElseThrow(() ->
                                                new RegraNegocioException(
                                                        "Status DISPONÍVEL não encontrado."
                                                ));

                        exemplarLiberado.setStatusExemplar(
                                disponivel
                        );

                        exemplarRepository.save(
                                exemplarLiberado
                        );
                    }

                    repository.save(
                            reserva
                    );

                    reorganizarFila(
                            reserva.getObra()
                    );

                    if (exemplarLiberado != null) {

                        promoverProximaReserva(
                                reserva.getObra(),
                                exemplarLiberado
                        );
                    }
                }
            }
        }
    }

    private void reorganizarFila(
            Obra obra) {

        List<Reserva> reservas =
                repository.findByObraOrderByPosicaoFilaAsc(
                        obra
                );

        int posicao = 1;

        for (Reserva reserva : reservas) {

            if (
                    reserva.getStatusReserva() != null
                            &&
                            !reserva.getStatusReserva()
                                    .getId()
                                    .equals(4L)
            ) {

                reserva.setPosicaoFila(
                        posicao++
                );

            } else {

                reserva.setPosicaoFila(
                        0
                );
            }

            repository.save(
                    reserva
            );
        }
    }

    private void promoverProximaReserva(
            Obra obra,
            Exemplar exemplar) {

        List<Reserva> fila =
                repository.findByObraOrderByPosicaoFilaAsc(
                        obra
                );

        Reserva proxima = null;

        for (Reserva reserva : fila) {

            if (
                    reserva.getPosicaoFila() != null
                            &&
                            reserva.getPosicaoFila() == 1
                            &&
                            reserva.getStatusReserva() != null
                            &&
                            reserva.getStatusReserva()
                                    .getId()
                                    .equals(1L)
            ) {

                proxima = reserva;
                break;
            }
        }

        if (proxima == null) {
            return;
        }

        StatusReserva disponivelRetirada =
                statusReservaRepository
                        .findById(3L)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Status Disponível para Retirada não encontrado."
                                ));

        StatusExemplar reservado =
                statusExemplarRepository
                        .findById(3L)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Status RESERVADO não encontrado."
                                ));

        exemplar.setStatusExemplar(
                reservado
        );

        exemplarRepository.save(
                exemplar
        );

        proxima.setExemplar(
                exemplar
        );

        proxima.setStatusReserva(
                disponivelRetirada
        );

        proxima.setDataMaximaPrevistaColeta(
                LocalDate.now()
                        .plusDays(
                                proxima
                                        .getDuracaoPadraoReserva()
                                        .getDiasUteis()
                        )
        );

        repository.save(
                proxima
        );
    }

    public void validar(
            Reserva reserva) {

        if (reserva == null) {

            throw new RegraNegocioException(
                    "Reserva inválida."
            );
        }

        if (reserva.getCliente() == null) {

            throw new RegraNegocioException(
                    "Cliente obrigatório."
            );
        }

        if (reserva.getObra() == null) {

            throw new RegraNegocioException(
                    "Obra obrigatória."
            );
        }
    }
}