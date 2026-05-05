package br.edu.ifsudestemg.sb.api.controller;


import br.edu.ifsudestemg.sb.api.dto.AutorDTO;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Autor;
import br.edu.ifsudestemg.sb.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
@CrossOrigin

public class AutorController {

    private final AutorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Autor> autores = service.getAutores();
        return ResponseEntity.ok(autores.stream().map(AutorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Autor> autor = service.getAutorById(id);
        if (!autor.isPresent()) {
            return new ResponseEntity("Autor não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(autor.map(AutorDTO::create));
    }

}
