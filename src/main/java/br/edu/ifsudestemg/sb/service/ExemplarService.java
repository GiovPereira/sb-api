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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExemplarService {

    private final ExemplarRepository repository;
    private final ObraRepository obraRepository;
    private final StatusExemplarRepository statusExemplarRepository;
    private final SecaoRepository secaoRepository;

    public ExemplarService(
            ExemplarRepository repository,
            ObraRepository obraRepository,
            StatusExemplarRepository statusExemplarRepository,
            SecaoRepository secaoRepository) {

        this.repository = repository;
        this.obraRepository = obraRepository;
        this.statusExemplarRepository = statusExemplarRepository;
        this.secaoRepository = secaoRepository;
    }

    public List<Exemplar> getExemplares() {
        return repository.findAll();
    }

    public List<ExemplarDTO> getExemplaresDTO() {
        return repository.findAll().stream().map(this::createDTO).collect(Collectors.toList());
    }

    public Optional<Exemplar> getExemplarById(Long id) {
        return repository.findById(id);
    }

    public ExemplarDTO createDTO(Exemplar exemplar) {

        ModelMapper modelMapper = new ModelMapper();
        ExemplarDTO dto = modelMapper.map(exemplar, ExemplarDTO.class);

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
    public Exemplar salvar(
            Exemplar exemplar,
            Long idObra,
            Long idSecao) {

        Obra obra = obraRepository.findById(idObra)
                .orElseThrow(() -> new RegraNegocioException("Obra não encontrada."));

        StatusExemplar statusExemplar = statusExemplarRepository.findById(1L)
                .orElseThrow(() -> new RegraNegocioException("Status padrão não encontrado."));

        exemplar.setObra(obra);
        exemplar.setStatusExemplar(statusExemplar);

        if (idSecao != null) {

            Secao secao = secaoRepository.findById(idSecao)
                    .orElseThrow(() -> new RegraNegocioException("Seção não encontrada."));

            exemplar.setSecao(secao);

        } else {

            exemplar.setSecao(null);

        }

        validar(exemplar);

        return repository.save(exemplar);
    }

    @Transactional
    public Exemplar atualizar(
            Exemplar exemplar,
            Long idObra,
            Long idStatusExemplar,
            Long idSecao) {

        Obra obra = obraRepository.findById(idObra)
                .orElseThrow(() -> new RegraNegocioException("Obra não encontrada."));

        StatusExemplar statusExemplar = statusExemplarRepository.findById(idStatusExemplar)
                .orElseThrow(() -> new RegraNegocioException("Status do exemplar não encontrado."));

        exemplar.setObra(obra);
        exemplar.setStatusExemplar(statusExemplar);

        if (idSecao != null) {

            Secao secao = secaoRepository.findById(idSecao)
                    .orElseThrow(() -> new RegraNegocioException("Seção não encontrada."));

            exemplar.setSecao(secao);

        } else {

            exemplar.setSecao(null);

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

            throw new RegraNegocioException(
                    "Não é possível excluir um exemplar que possui empréstimos cadastrados.");

        }

    }

    public void validar(Exemplar exemplar) {

        if (exemplar.getObra() == null) {
            throw new RegraNegocioException("Informe a obra.");
        }

        if (exemplar.getStatusExemplar() == null) {
            throw new RegraNegocioException("Informe o status do exemplar.");
        }

    }

}