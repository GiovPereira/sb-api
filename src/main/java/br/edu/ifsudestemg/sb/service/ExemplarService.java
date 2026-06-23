package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.api.dto.ExemplarDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import br.edu.ifsudestemg.sb.model.entity.Obra;
import br.edu.ifsudestemg.sb.model.entity.Secao;
import br.edu.ifsudestemg.sb.model.entity.StatusExemplar;
import br.edu.ifsudestemg.sb.model.repository.ExemplarRepository;
import br.edu.ifsudestemg.sb.model.repository.ObraRepository;
import br.edu.ifsudestemg.sb.model.repository.SecaoRepository;
import br.edu.ifsudestemg.sb.model.repository.StatusExemplarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExemplarService {

    private final ExemplarRepository repository;
    private final ObraRepository obraRepository;
    private final StatusExemplarRepository statusRepository;
    private final SecaoRepository secaoRepository;

    public ExemplarService(ExemplarRepository repository, ObraRepository obraRepository, StatusExemplarRepository statusRepository, SecaoRepository secaoRepository) {
        this.repository = repository;
        this.obraRepository = obraRepository;
        this.statusRepository = statusRepository;
        this.secaoRepository = secaoRepository;
    }

    public List<Exemplar> getExemplares() {
        return repository.findAll();
    }

    public List<ExemplarDTO> getExemplaresDTO() {
        return repository.findAll().stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
    }

    public Optional<Exemplar> getExemplarById(Long id) {
        return repository.findById(id);
    }

    public ExemplarDTO createDTO(Exemplar exemplar) {
        ModelMapper mapper = new ModelMapper();
        ExemplarDTO dto = mapper.map(exemplar, ExemplarDTO.class);

        if (exemplar.getObra() != null) {
            dto.setIdObra(exemplar.getObra().getId());
            dto.setTituloObra(exemplar.getObra().getTitulo());
        }

        if (exemplar.getStatusExemplar() != null) {
            dto.setIdStatusExemplar(exemplar.getStatusExemplar().getId());
            dto.setNomeStatusExemplar(exemplar.getStatusExemplar().getNome());
        }

        if (exemplar.getSecao() != null) {
            dto.setIdSecao(exemplar.getSecao().getId());
            dto.setNomeSecao(exemplar.getSecao().getNome());
        }

        return dto;
    }

    @Transactional
    public Exemplar salvar(Exemplar exemplar, Long idObra, Long idSecao) {
        Obra obra = obraRepository.findById(idObra)
                .orElseThrow(() -> new RegraNegocioException("Obra não encontrada."));

        Secao secao = secaoRepository.findById(idSecao)
                .orElseThrow(() -> new RegraNegocioException("Seção não encontrada."));

        StatusExemplar disponivel = statusRepository.findById(1L)
                .orElseThrow(() -> new RegraNegocioException("Status Disponível não encontrado."));

        exemplar.setObra(obra);
        exemplar.setSecao(secao);
        exemplar.setStatusExemplar(disponivel);

        if (exemplar.getDataAquisicao() == null) {
            exemplar.setDataAquisicao(LocalDate.now());
        }

        validar(exemplar);
        return repository.save(exemplar);
    }

    @Transactional
    public Exemplar atualizar(Exemplar exemplar, Long idObra, Long idStatusExemplar, Long idSecao) {
        Exemplar original = repository.findById(exemplar.getId())
                .orElseThrow(() -> new RegraNegocioException("Exemplar não encontrado."));

        Obra obra = obraRepository.findById(idObra)
                .orElseThrow(() -> new RegraNegocioException("Obra não encontrada."));

        Secao secao = secaoRepository.findById(idSecao)
                .orElseThrow(() -> new RegraNegocioException("Seção não encontrada."));

        StatusExemplar status = statusRepository.findById(idStatusExemplar)
                .orElseThrow(() -> new RegraNegocioException("Status não encontrado."));

        exemplar.setObra(obra);
        exemplar.setSecao(secao);
        exemplar.setStatusExemplar(status);

        if (exemplar.getDataAquisicao() == null) {
            exemplar.setDataAquisicao(original.getDataAquisicao());
        }

        validar(exemplar);
        return repository.save(exemplar);
    }

    @Transactional
    public void excluir(Exemplar exemplar) {
        Objects.requireNonNull(exemplar.getId());

        try {
            repository.delete(exemplar);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new RegraNegocioException("Não foi possível excluir o exemplar.");
        }
    }

    @Transactional
    public Exemplar alterarStatus(Long idExemplar, Long idNovoStatus) {
        Exemplar exemplar = repository.findById(idExemplar)
                .orElseThrow(() -> new RegraNegocioException("Exemplar não encontrado."));

        StatusExemplar novoStatus = statusRepository.findById(idNovoStatus)
                .orElseThrow(() -> new RegraNegocioException("Status não encontrado."));

        StatusExemplar atual = exemplar.getStatusExemplar();

        validarTransicao(atual.getId(), novoStatus.getId());
        exemplar.setStatusExemplar(novoStatus);

        return repository.save(exemplar);
    }

    private void validarTransicao(Long atual, Long novo) {
        // Em Posse
        if (atual.equals(3L)) {
            throw new RegraNegocioException("Exemplar em posse não pode ter status alterado manualmente.");
        }

        // Em Atraso
        if (atual.equals(4L)) {
            throw new RegraNegocioException("Exemplar em atraso não pode ter status alterado manualmente.");
        }

        // Disponível
        if (novo.equals(1L)) {
            return;
        }

        // Reservado
        if (novo.equals(2L)) {
            if (atual.equals(1L)) {
                return;
            }
            throw new RegraNegocioException("Transição de status inválida.");
        }

        // Extraviado
        if (novo.equals(5L)) {
            if (atual.equals(1L) || atual.equals(2L)) {
                return;
            }
            throw new RegraNegocioException("Transição de status inválida.");
        }

        // Danificado
        if (novo.equals(6L)) {
            if (atual.equals(1L) || atual.equals(2L)) {
                return;
            }
            throw new RegraNegocioException("Transição de status inválida.");
        }

        throw new RegraNegocioException("Transição de status inválida.");
    }

    public void validar(Exemplar exemplar) {
        if (exemplar.getTombo() == null || exemplar.getTombo().trim().isEmpty()) {
            throw new RegraNegocioException("Informe o tombo.");
        }

        if (exemplar.getCodigoBarras() == null || exemplar.getCodigoBarras().trim().isEmpty()) {
            throw new RegraNegocioException("Informe o código de barras.");
        }

        if (exemplar.getObra() == null) {
            throw new RegraNegocioException("Informe a obra.");
        }

        if (exemplar.getStatusExemplar() == null) {
            throw new RegraNegocioException("Informe o status.");
        }
    }
}