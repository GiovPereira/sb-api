package br.edu.ifsudestemg.sb.api.controller;


import br.edu.ifsudestemg.sb.api.dto.ObraGeneroDTO;

import br.edu.ifsudestemg.sb.model.entity.ObraGenero;
import br.edu.ifsudestemg.sb.service.ObraGeneroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/obraGeneros")
@RequiredArgsConstructor
@CrossOrigin

public class ObraGeneroController
{

    private final ObraGeneroService service;

    @GetMapping()
    public ResponseEntity get() {
        List<ObraGenero> obraGeneros = service.getObraGeneros();
        return ResponseEntity.ok(obraGeneros.stream().map(ObraGeneroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ObraGenero> obraGenero = service.getObraGeneroById(id);
        if (!obraGenero.isPresent()) {
            return new ResponseEntity("ObraGenero não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(obraGenero.map(ObraGeneroDTO::create));
    }

}
