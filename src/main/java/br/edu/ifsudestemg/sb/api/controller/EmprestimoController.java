package br.edu.ifsudestemg.sb.api.controller;


import br.edu.ifsudestemg.sb.api.dto.EmprestimoDTO;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Emprestimo;
import br.edu.ifsudestemg.sb.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/emprestimos")
@RequiredArgsConstructor
@CrossOrigin

public class EmprestimoController {

    private final EmprestimoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Emprestimo> emprestimos = service.getEmprestimos();
        return ResponseEntity.ok(emprestimos.stream().map(EmprestimoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Emprestimo> emprestimo = service.getEmprestimoById(id);
        if (!emprestimo.isPresent()) {
            return new ResponseEntity("Empréstimo não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(emprestimo.map(EmprestimoDTO::create));
    }

}
