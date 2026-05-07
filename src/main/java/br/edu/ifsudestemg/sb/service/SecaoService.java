package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Secao;
import br.edu.ifsudestemg.sb.model.repository.SecaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SecaoService {

    private final SecaoRepository repository;

    public SecaoService(SecaoRepository repository) {
        this.repository = repository;
    }

    public List<Secao> listarTodos() {
        return repository.findAll();
    }

    public Optional<Secao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Secao salvar(Secao secao) {
        validar(secao);

        String nomeNormalizado = secao.getNome().trim().toUpperCase();
        secao.setNome(nomeNormalizado);

        if (repository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new RuntimeException("Já existe uma seção com esse nome");
        }

        return repository.save(secao);
    }

    @Transactional
    public void excluir(Secao secao) {

        Objects.requireNonNull(secao.getId(), "ID não pode ser nulo");
        repository.delete(secao);
    }

    private void validar(Secao secao) {

        if (secao == null) {
            throw new RuntimeException("Seção não pode ser nula");
        }

        if (secao.getNome() == null || secao.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (secao.getNome().length() > 100) {
            throw new RuntimeException("Nome muito longo");
        }
    }
}