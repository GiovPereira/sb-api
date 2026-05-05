package br.edu.ifsudestemg.sb.api.controller;


import br.edu.ifsudestemg.sb.api.dto.ExemplarDTO;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import br.edu.ifsudestemg.sb.service.ExemplarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/exemplares")
@RequiredArgsConstructor
@CrossOrigin

public class ExemplarController {

    private final ExemplarService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Exemplar> exemplares = service.getExemplares();
        return ResponseEntity.ok(exemplares.stream().map(ExemplarDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Exemplar> exemplar = service.getExemplarById(id);
        if (!exemplar.isPresent()) {
            return new ResponseEntity("Exemplar não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(exemplar.map(ExemplarDTO::create));
    }

}
