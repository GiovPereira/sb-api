package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;

import br.edu.ifsudestemg.sb.api.dto.ObraAutorDTO;
import br.edu.ifsudestemg.sb.model.entity.ObraAutor;
import br.edu.ifsudestemg.sb.service.ObraAutorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
public class ObraAutorController {

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

    @PostMapping()
    public ResponseEntity post(@RequestBody ObraAutorDTO dto) {
        try {
            ObraAutor obraAutor = converter(dto);
            obraAutor = service.salvar(obraAutor);
            return new ResponseEntity(obraAutor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ObraAutorDTO dto) {
        if (!service.getObraAutorById(id).isPresent()) {
            return new ResponseEntity("ObraAutor não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ObraAutor obraAutor = converter(dto);
            obraAutor.setId(id);
            service.salvar(obraAutor);
            return ResponseEntity.ok(obraAutor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ObraAutor> obraAutor = service.getObraAutorById(id);
        if (!obraAutor.isPresent()) {
            return new ResponseEntity("ObraAutor não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(obraAutor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ObraAutor converter(ObraAutorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ObraAutor obraAutor = modelMapper.map(dto, ObraAutor.class);
        return obraAutor;
    }
}
