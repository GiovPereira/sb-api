package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.AutorDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Autor;
import br.edu.ifsudestemg.sb.model.entity.Secao;
import br.edu.ifsudestemg.sb.api.dto.SecaoDTO;
import br.edu.ifsudestemg.sb.service.SecaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.dynamic.DynamicType;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/secoes")
@RequiredArgsConstructor
@CrossOrigin

public class SecaoController {

    private final SecaoService service;

    @GetMapping("/{id}")
    @ApiOperation("Obter seções")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Seções encontradas"),
            @ApiResponse(code = 404, message = "Seções não encontradas")
    })
    public ResponseEntity get() {
        List<Secao> secao = service.getSecoes();
        return ResponseEntity.ok(secao.stream().map(SecaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma seção")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Seção encontrada"),
            @ApiResponse(code = 404, message = "Seção não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Secao> secao = service.getSecaoById(id);
        if (!secao.isPresent()) {
            return new ResponseEntity("Seção não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(secao.map(SecaoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova seção")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Seção salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a seção")
    })
    public ResponseEntity post(@RequestBody SecaoDTO dto) {
        try {
            Secao secao = converter(dto);
            secao = service.salvar(secao);
            return new ResponseEntity(secao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    @ApiOperation("Atualizar a seção")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Seção atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar a seção")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SecaoDTO dto) {
        if (!service.getSecaoById(id).isPresent()) {
            return new ResponseEntity("Secao não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Secao secao = converter(dto);
            secao.setId(id);
            service.salvar(secao);
            return ResponseEntity.ok(secao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta uma seção")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Seção deletada"),
            @ApiResponse(code = 404, message = "Seção não deletada")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Secao> secao = service.getSecaoById(id);
        if (!secao.isPresent()) {
            return new ResponseEntity("Secao não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(secao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Secao converter(SecaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Secao secao = modelMapper.map(dto, Secao.class);
        return secao;
    }
}
