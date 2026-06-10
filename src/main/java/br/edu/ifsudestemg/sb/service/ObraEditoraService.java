package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.ObraEditora;
import br.edu.ifsudestemg.sb.model.repository.ObraEditoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ObraEditoraService
{
    private ObraEditoraRepository repository;

    public ObraEditoraService(ObraEditoraRepository repository) {
        this.repository = repository;
    }

    public List<ObraEditora> getObraEditoras() {
        return repository.findAll();
    }

    public Optional<ObraEditora> getObraEditoraById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ObraEditora salvar(ObraEditora obraEditora) {
        validar(obraEditora);
        return repository.save(obraEditora);
    }

    @Transactional
    public void excluir(ObraEditora obraEditora) {
        Objects.requireNonNull(obraEditora.getId());
        repository.delete(obraEditora);
    }

    public void validar(ObraEditora obraEditora) {
//        if (ObraEditora.getNome() == null || ObraEditora.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (ObraEditora.getCurso() == null || ObraEditora.getCurso().getId() == null || ObraEditora.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }

}
