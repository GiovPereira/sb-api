package br.edu.ifsudestemg.sb.api.controller;


import br.edu.ifsudestemg.sb.api.dto.ObraAutorDTO;

import br.edu.ifsudestemg.sb.model.entity.ObraAutor;
import br.edu.ifsudestemg.sb.service.ObraAutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/obraAutores")
@RequiredArgsConstructor
@CrossOrigin

public class ObraAutorController
{

    private final ObraAutorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<ObraAutor> obraAutores = service.getObraAutores();
        return ResponseEntity.ok(obraAutores.stream().map(ObraAutorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ObraAutor> obraAutor = service.getObraAutorById(id);
        if (!obraAutor.isPresent()) {
            return new ResponseEntity("ObraAutor não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(obraAutor.map(ObraAutorDTO::create));
    }

}
