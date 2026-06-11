package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.api.dto.EmprestimoDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Cliente;
import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import br.edu.ifsudestemg.sb.model.entity.Emprestimo;
import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import br.edu.ifsudestemg.sb.model.entity.ValorDiarioMulta;
import br.edu.ifsudestemg.sb.model.repository.ClienteRepository;
import br.edu.ifsudestemg.sb.model.repository.DuracaoPadraoEmprestimoRepository;
import br.edu.ifsudestemg.sb.model.repository.EmprestimoRepository;
import br.edu.ifsudestemg.sb.model.repository.ExemplarRepository;
import br.edu.ifsudestemg.sb.model.repository.ValorDiarioMultaRepository;
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

    public EmprestimoService(
            EmprestimoRepository repository,
            ClienteRepository clienteRepository,
            ExemplarRepository exemplarRepository,
            DuracaoPadraoEmprestimoRepository duracaoRepository,
            ValorDiarioMultaRepository valorMultaRepository) {

        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.exemplarRepository = exemplarRepository;
        this.duracaoRepository = duracaoRepository;
        this.valorMultaRepository = valorMultaRepository;
    }

    public List<Emprestimo> getEmprestimos() {
        return repository.findAll();
    }

    public List<EmprestimoDTO> getEmprestimosDTO() {

        return repository
                .findAll()
                .stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
    }

    public Optional<Emprestimo> getEmprestimoById(Long id) {
        return repository.findById(id);
    }

    public EmprestimoDTO createDTO(Emprestimo emprestimo) {

        atualizarMulta(emprestimo);

        ModelMapper modelMapper = new ModelMapper();

        EmprestimoDTO dto =
                modelMapper.map(
                        emprestimo,
                        EmprestimoDTO.class
                );

        if (emprestimo.getCliente() != null) {

            dto.setIdCliente(
                    emprestimo.getCliente().getId()
            );

            dto.setNomeCliente(
                    emprestimo.getCliente().getNome()
            );
        }

        if (emprestimo.getExemplar() != null) {

            dto.setIdExemplar(
                    emprestimo.getExemplar().getId()
            );

            if (emprestimo.getExemplar().getObra() != null) {

                dto.setTituloObra(
                        emprestimo.getExemplar()
                                .getObra()
                                .getTitulo()
                );
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

    @Transactional
    public Emprestimo salvar(
            Emprestimo emprestimo,
            Long idCliente,
            Long idExemplar) {

        Cliente cliente =
                clienteRepository.findById(idCliente)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Cliente não encontrado."
                                ));

        Exemplar exemplar =
                exemplarRepository.findById(idExemplar)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Exemplar não encontrado."
                                ));

        DuracaoPadraoEmprestimo duracao =
                duracaoRepository
                        .findTopByOrderByIdDesc()
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Nenhuma duração padrão cadastrada."
                                ));

        ValorDiarioMulta valorMulta =
                valorMultaRepository
                        .findTopByOrderByIdDesc()
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Nenhum valor de multa cadastrado."
                                ));

        emprestimo.setCliente(cliente);

        emprestimo.setExemplar(exemplar);

        emprestimo.setDuracaoPadraoEmprestimo(duracao);

        emprestimo.setValorDiarioMulta(valorMulta);

        emprestimo.setDataHoraEmprestimo(
                LocalDateTime.now()
        );

        emprestimo.setDataPrevistaDevolucao(
                emprestimo.getDataHoraEmprestimo()
                        .toLocalDate()
                        .plusDays(
                                duracao.getDiasUteis()
                        )
        );

        emprestimo.setDataHoraEntrega(null);

        emprestimo.setMulta(BigDecimal.ZERO);

        validar(emprestimo);

        return repository.save(emprestimo);
    }

    @Transactional
    public Emprestimo atualizar(
            Emprestimo emprestimo,
            Long idCliente,
            Long idExemplar) {

        Cliente cliente =
                clienteRepository.findById(idCliente)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Cliente não encontrado."
                                ));

        Exemplar exemplar =
                exemplarRepository.findById(idExemplar)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Exemplar não encontrado."
                                ));

        Emprestimo original =
                repository.findById(emprestimo.getId())
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Empréstimo não encontrado."
                                ));

        emprestimo.setCliente(cliente);

        emprestimo.setExemplar(exemplar);

        emprestimo.setDuracaoPadraoEmprestimo(
                original.getDuracaoPadraoEmprestimo()
        );

        emprestimo.setValorDiarioMulta(
                original.getValorDiarioMulta()
        );

        emprestimo.setDataHoraEmprestimo(
                original.getDataHoraEmprestimo()
        );

        emprestimo.setDataPrevistaDevolucao(
                original.getDataPrevistaDevolucao()
        );

        atualizarMulta(emprestimo);

        validar(emprestimo);

        return repository.save(emprestimo);
    }

    @Transactional
    public Emprestimo registrarEntrega(Long idEmprestimo) {

        Emprestimo emprestimo =
                repository.findById(idEmprestimo)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Empréstimo não encontrado."
                                ));

        atualizarMulta(emprestimo);

        emprestimo.setDataHoraEntrega(
                LocalDateTime.now()
        );

        return repository.save(emprestimo);
    }

    @Transactional
    public void excluir(Emprestimo emprestimo) {

        Objects.requireNonNull(
                emprestimo.getId()
        );

        try {

            repository.delete(emprestimo);

            repository.flush();

        } catch (DataIntegrityViolationException e) {

            throw new RegraNegocioException(
                    "Não foi possível excluir o empréstimo."
            );
        }
    }

    private void atualizarMulta(Emprestimo emprestimo) {

        if (emprestimo.getDataHoraEntrega() != null) {
            return;
        }

        LocalDate hoje = LocalDate.now();

        if (!hoje.isAfter(
                emprestimo.getDataPrevistaDevolucao())) {

            emprestimo.setMulta(BigDecimal.ZERO);

            return;
        }

        long diasAtraso =
                ChronoUnit.DAYS.between(
                        emprestimo.getDataPrevistaDevolucao(),
                        hoje
                );

        BigDecimal multa =
                emprestimo.getValorDiarioMulta()
                        .getValorDia()
                        .multiply(
                                BigDecimal.valueOf(
                                        diasAtraso
                                )
                        );

        emprestimo.setMulta(multa);
    }

    public void validar(Emprestimo emprestimo) {

        if (emprestimo.getCliente() == null) {
            throw new RegraNegocioException(
                    "Informe o cliente."
            );
        }

        if (emprestimo.getExemplar() == null) {
            throw new RegraNegocioException(
                    "Informe o exemplar."
            );
        }

        if (emprestimo.getDuracaoPadraoEmprestimo() == null) {
            throw new RegraNegocioException(
                    "Duração padrão não encontrada."
            );
        }

        if (emprestimo.getValorDiarioMulta() == null) {
            throw new RegraNegocioException(
                    "Valor da multa não encontrado."
            );
        }
    }
}