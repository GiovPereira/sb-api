package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;

import br.edu.ifsudestemg.sb.api.dto.GeneroDTO;
import br.edu.ifsudestemg.sb.model.entity.Genero;
import br.edu.ifsudestemg.sb.service.GeneroService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
@CrossOrigin
public class GeneroController {

    private final GeneroService service;

    @GetMapping()
    @ApiOperation("Obter gêneros")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gêneros listados"),
            @ApiResponse(code = 404, message = "Gêneros não listados")
    })
    public ResponseEntity get() {
        List<Genero> generos = service.getGeneros();
        return ResponseEntity.ok(generos.stream().map(GeneroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um genero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Genero encontrado"),
            @ApiResponse(code = 404, message = "Genero não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Genero> genero = service.getGeneroById(id);
        if (!genero.isPresent()) {
            return new ResponseEntity("Genero não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(genero.map(GeneroDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo genero")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Genero salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o genero")
    })
    public ResponseEntity post(@RequestBody GeneroDTO dto) {
        try {
            Genero genero = converter(dto);
            genero = service.salvar(genero);
            return new ResponseEntity(genero, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar o gênero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gênero atualizado"),
            @ApiResponse(code = 404, message = "Gênero não atualizado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody GeneroDTO dto) {
        if (!service.getGeneroById(id).isPresent()) {
            return new ResponseEntity("Genero não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Genero genero = converter(dto);
            genero.setId(id);
            service.salvar(genero);
            return ResponseEntity.ok(genero);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um gênero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gênero deletado"),
            @ApiResponse(code = 404, message = "Gênero não deletado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Genero> genero = service.getGeneroById(id);
        if (!genero.isPresent()) {
            return new ResponseEntity("Genero não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(genero.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Genero converter(GeneroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Genero genero = modelMapper.map(dto, Genero.class);
        return genero;
    }
}
