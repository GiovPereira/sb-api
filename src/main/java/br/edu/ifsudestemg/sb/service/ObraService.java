package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.api.dto.ObraDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ObraService {

    private final ObraRepository obraRepository;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;
    private final GeneroRepository generoRepository;
    private final IdiomaRepository idiomaRepository;
    private final ObraAutorRepository obraAutorRepository;
    private final ObraEditoraRepository obraEditoraRepository;
    private final ObraGeneroRepository obraGeneroRepository;
    private final ObraIdiomaRepository obraIdiomaRepository;

    public ObraService(ObraRepository obraRepository, AutorRepository autorRepository, EditoraRepository editoraRepository,
                       GeneroRepository generoRepository, IdiomaRepository idiomaRepository, ObraAutorRepository obraAutorRepository,
                       ObraEditoraRepository obraEditoraRepository, ObraGeneroRepository obraGeneroRepository, ObraIdiomaRepository obraIdiomaRepository) {
        this.obraRepository = obraRepository;
        this.autorRepository = autorRepository;
        this.editoraRepository = editoraRepository;
        this.generoRepository = generoRepository;
        this.idiomaRepository = idiomaRepository;
        this.obraAutorRepository = obraAutorRepository;
        this.obraEditoraRepository = obraEditoraRepository;
        this.obraGeneroRepository = obraGeneroRepository;
        this.obraIdiomaRepository = obraIdiomaRepository;
    }

    public List<Obra> getObras() {
        return obraRepository.findAll();
    }

    public List<ObraDTO> getObrasDTO() {
        return obraRepository.findAll().stream().map(this::createDTO).collect(Collectors.toList());
    }

    public Optional<Obra> getObraById(Long id) {
        return obraRepository.findById(id);
    }

    @Transactional
    public Obra salvar(Obra obra, List<Long> autoresIds, List<Long> editorasIds, List<Long> generosIds, List<Long> idiomasIds) {

        validar(obra);

        if (obraRepository.existsByIsbn(obra.getIsbn())) {
            throw new RegraNegocioException("Já existe uma obra cadastrada com este ISBN");
        }

        String nomeNormalizado = obra.getTitulo().trim().toLowerCase();

        obra.setTitulo(nomeNormalizado);

        obra = obraRepository.save(obra);
        salvarAutores(obra, autoresIds);
        salvarEditoras(obra, editorasIds);
        salvarGeneros(obra, generosIds);
        salvarIdiomas(obra, idiomasIds);

        return obra;
    }

    @Transactional
    public Obra atualizar(Obra obra, List<Long> autoresIds, List<Long> editorasIds, List<Long> generosIds, List<Long> idiomasIds) {

        validar(obra);

        if (obra.getId() == null) {
            throw new RegraNegocioException("Obra inválida");
        }

        Obra obraBanco = obraRepository.findById(obra.getId())
                .orElseThrow(() -> new RegraNegocioException("Obra não encontrada"));

        if (obraRepository.existsByIsbnAndIdNot(obra.getIsbn(), obra.getId())) {
            throw new RegraNegocioException("Já existe outra obra cadastrada com este ISBN");
        }

        obraBanco.setTitulo(obra.getTitulo());
        obraBanco.setIsbn(obra.getIsbn());
        obraBanco.setEdicao(obra.getEdicao());
        obraBanco = obraRepository.save(obraBanco);

        removerRelacionamentos(obraBanco.getId());
        salvarAutores(obraBanco, autoresIds);
        salvarEditoras(obraBanco, editorasIds);
        salvarGeneros(obraBanco, generosIds);
        salvarIdiomas(obraBanco, idiomasIds);

        return obraBanco;
    }

    @Transactional
    public void excluir(Obra obra) {
        if (obra.getId() == null) {
            throw new RegraNegocioException("Obra sem id");
        }
        removerRelacionamentos(obra.getId());
        obraRepository.delete(obra);
    }

    public void validar(Obra obra) {
        if (obra == null) {
            throw new RegraNegocioException("Obra inválida");
        }
        if (obra.getTitulo() == null || obra.getTitulo().trim().isEmpty()) {
            throw new RegraNegocioException("Informe o título");
        }
        if (obra.getIsbn() == null || obra.getIsbn().trim().isEmpty()) {
            throw new RegraNegocioException("Informe o ISBN");
        }
        if (obra.getEdicao() == null || obra.getEdicao().trim().isEmpty()) {
            throw new RegraNegocioException("Informe a edição");
        }
    }

    private void removerRelacionamentos(Long obraId) {
        obraAutorRepository.deleteByObra_Id(obraId);
        obraEditoraRepository.deleteByObra_Id(obraId);
        obraGeneroRepository.deleteByObra_Id(obraId);
        obraIdiomaRepository.deleteByObra_Id(obraId);
    }

    private void salvarAutores(Obra obra, List<Long> autoresIds) {
        if (autoresIds == null) return;

        for (Long autorId : autoresIds) {
            if (obraAutorRepository.existsByObra_IdAndAutor_Id(obra.getId(), autorId)) continue;

            Autor autor = autorRepository.findById(autorId)
                    .orElseThrow(() -> new RegraNegocioException("Autor não encontrado"));

            ObraAutor obraAutor = new ObraAutor();
            obraAutor.setObra(obra);
            obraAutor.setAutor(autor);
            obraAutorRepository.save(obraAutor);
        }
    }

    private void salvarEditoras(Obra obra, List<Long> editorasIds) {
        if (editorasIds == null) return;

        for (Long editoraId : editorasIds) {
            if (obraEditoraRepository.existsByObra_IdAndEditora_Id(obra.getId(), editoraId)) continue;

            Editora editora = editoraRepository.findById(editoraId)
                    .orElseThrow(() -> new RegraNegocioException("Editora não encontrada"));

            ObraEditora obraEditora = new ObraEditora();
            obraEditora.setObra(obra);
            obraEditora.setEditora(editora);
            obraEditoraRepository.save(obraEditora);
        }
    }

    private void salvarGeneros(Obra obra, List<Long> generosIds) {
        if (generosIds == null) return;

        for (Long generoId : generosIds) {
            if (obraGeneroRepository.existsByObra_IdAndGenero_Id(obra.getId(), generoId)) continue;

            Genero genero = generoRepository.findById(generoId)
                    .orElseThrow(() -> new RegraNegocioException("Gênero não encontrado"));

            ObraGenero obraGenero = new ObraGenero();
            obraGenero.setObra(obra);
            obraGenero.setGenero(genero);
            obraGeneroRepository.save(obraGenero);
        }
    }

    private void salvarIdiomas(Obra obra, List<Long> idiomasIds) {
        if (idiomasIds == null) return;

        for (Long idiomaId : idiomasIds) {
            if (obraIdiomaRepository.existsByObra_IdAndIdioma_Id(obra.getId(), idiomaId)) continue;

            Idioma idioma = idiomaRepository.findById(idiomaId)
                    .orElseThrow(() -> new RegraNegocioException("Idioma não encontrado"));

            ObraIdioma obraIdioma = new ObraIdioma();
            obraIdioma.setObra(obra);
            obraIdioma.setIdioma(idioma);
            obraIdiomaRepository.save(obraIdioma);
        }
    }

    public ObraDTO createDTO(Obra obra) {
        ObraDTO dto = ObraDTO.create(obra);

        dto.setAutores(obraAutorRepository.buscarAutoresDaObra(obra.getId()).stream().map(Autor::getNome).collect(Collectors.toList()));
        dto.setAutoresIds(obraAutorRepository.buscarIdsAutoresDaObra(obra.getId()));

        dto.setEditoras(obraEditoraRepository.buscarEditorasDaObra(obra.getId()).stream().map(Editora::getNome).collect(Collectors.toList()));
        dto.setEditorasIds(obraEditoraRepository.buscarIdsEditorasDaObra(obra.getId()));

        dto.setGeneros(obraGeneroRepository.buscarGenerosDaObra(obra.getId()).stream().map(Genero::getNome).collect(Collectors.toList()));
        dto.setGenerosIds(obraGeneroRepository.buscarIdsGenerosDaObra(obra.getId()));

        dto.setIdiomas(obraIdiomaRepository.buscarIdiomasDaObra(obra.getId()).stream().map(Idioma::getNome).collect(Collectors.toList()));
        dto.setIdiomasIds(obraIdiomaRepository.buscarIdsIdiomasDaObra(obra.getId()));

        return dto;
    }
}