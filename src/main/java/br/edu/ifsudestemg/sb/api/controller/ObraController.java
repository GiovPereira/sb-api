package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;

import br.edu.ifsudestemg.sb.api.dto.ObraDTO;
import br.edu.ifsudestemg.sb.model.entity.Obra;
import br.edu.ifsudestemg.sb.service.ObraService;
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
@RequestMapping("/api/v1/obras")
@RequiredArgsConstructor
@CrossOrigin
public class ObraController {

    private final ObraService service;

    @GetMapping("/{id}")
    @ApiOperation("Obter obras")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Obras encontradas"),
            @ApiResponse(code = 404, message = "Obras não encontradas")
    })
    public ResponseEntity get() {
        List<Obra> obras = service.getObras();
        return ResponseEntity.ok(obras.stream().map(ObraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Obra encontrada"),
            @ApiResponse(code = 404, message = "Obra não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Obra> obra = service.getObraById(id);
        if (!obra.isPresent()) {
            return new ResponseEntity("Obra não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(obra.map(ObraDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova obra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Obra salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a obra")
    })
    public ResponseEntity post(@RequestBody ObraDTO dto) {
        try {
            Obra obra = converter(dto);
            obra = service.salvar(obra);
            return new ResponseEntity(obra, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar a obra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Obra atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar a obra")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ObraDTO dto) {
        if (!service.getObraById(id).isPresent()) {
            return new ResponseEntity("Obra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Obra obra = converter(dto);
            obra.setId(id);
            service.salvar(obra);
            return ResponseEntity.ok(obra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta uma obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Obra deletada"),
            @ApiResponse(code = 404, message = "Obra não deletada")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Obra> obra = service.getObraById(id);
        if (!obra.isPresent()) {
            return new ResponseEntity("Obra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(obra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Obra converter(ObraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Obra obra = modelMapper.map(dto, Obra.class);
        return obra;
    }
}