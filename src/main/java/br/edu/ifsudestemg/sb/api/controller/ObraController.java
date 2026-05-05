package br.edu.ifsudestemg.sb.api.controller;


import br.edu.ifsudestemg.sb.api.dto.ObraDTO;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Obra;
import br.edu.ifsudestemg.sb.service.ObraService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/obras")
@RequiredArgsConstructor
@CrossOrigin

public class ObraController {

    private final ObraService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Obra> obras = service.getObras();
        return ResponseEntity.ok(obras.stream().map(ObraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Obra> obra = service.getObraById(id);
        if (!obra.isPresent()) {
            return new ResponseEntity("Obra não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(obra.map(ObraDTO::create));
    }

}
