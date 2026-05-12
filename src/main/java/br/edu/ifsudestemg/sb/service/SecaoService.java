package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Secao;
import br.edu.ifsudestemg.sb.model.repository.SecaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SecaoService {

    private SecaoRepository repository;

    public SecaoService(SecaoRepository repository) {
        this.repository = repository;
    }

    public List<Secao> getSecoes() {
        return repository.findAll();
    }

    public Optional<Secao> getSecaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Secao salvar(Secao secao) {

        validar(secao);

        String nomeNormalizado = secao.getNome().trim().toUpperCase();

        secao.setNome(nomeNormalizado);

        if (repository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new RegraNegocioException(
                    "Já existe uma seção com esse nome");
        }

        return repository.save(secao);
    }

    @Transactional
    public void excluir(Secao secao) {
        Objects.requireNonNull(secao.getId());
        repository.delete(secao);
    }

    public void validar(Secao secao) {

        if (secao == null) {
            throw new RegraNegocioException("Seção inválida");
        }

        if (secao.getNome() == null ||
                secao.getNome().trim().equals("")) {

            throw new RegraNegocioException("Nome inválido");
        }

        if (secao.getNome().length() > 100) {
            throw new RegraNegocioException("Nome inválido");
        }
    }
}