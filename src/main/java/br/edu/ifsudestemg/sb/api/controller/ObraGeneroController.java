package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;

import br.edu.ifsudestemg.sb.api.dto.ObraGeneroDTO;
import br.edu.ifsudestemg.sb.model.entity.ObraGenero;
import br.edu.ifsudestemg.sb.service.ObraGeneroService;
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
@RequestMapping("/api/v1/obraGeneros")
@RequiredArgsConstructor
@CrossOrigin
public class ObraGeneroController {

    private final ObraGeneroService service;

    @GetMapping("/{id}")
    @ApiOperation("Obter generos da obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Generos da obra encontrados"),
            @ApiResponse(code = 404, message = "Generos da obra não encontrados")
    })
    public ResponseEntity get() {
        List<ObraGenero> obraGeneros = service.getObraGeneros();
        return ResponseEntity.ok(obraGeneros.stream().map(ObraGeneroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um genero da obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Genero da obra encontrado"),
            @ApiResponse(code = 404, message = "Genero da obra não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ObraGenero> obraGenero = service.getObraGeneroById(id);
        if (!obraGenero.isPresent()) {
            return new ResponseEntity("Genero da obra não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(obraGenero.map(ObraGeneroDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo genero da obra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Genero da obra salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o genero da obra")
    })
    public ResponseEntity post(@RequestBody ObraGeneroDTO dto) {
        try {
            ObraGenero obraGenero = converter(dto);
            obraGenero = service.salvar(obraGenero);
            return new ResponseEntity(obraGenero, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar o genero da obra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Genero da obra atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar o genero da obra")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ObraGeneroDTO dto) {
        if (!service.getObraGeneroById(id).isPresent()) {
            return new ResponseEntity("Genero da obra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ObraGenero obraGenero = converter(dto);
            obraGenero.setId(id);
            service.salvar(obraGenero);
            return ResponseEntity.ok(obraGenero);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Gênero da obra uma duração")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Genero da obra deletado"),
            @ApiResponse(code = 404, message = "Genero da obra não deletado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ObraGenero> obraGenero = service.getObraGeneroById(id);
        if (!obraGenero.isPresent()) {
            return new ResponseEntity("Genero da obra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(obraGenero.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ObraGenero converter(ObraGeneroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ObraGenero obraGenero = modelMapper.map(dto, ObraGenero.class);
        return obraGenero;
    }
}
