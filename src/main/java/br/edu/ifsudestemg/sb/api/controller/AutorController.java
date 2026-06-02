package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;

import br.edu.ifsudestemg.sb.api.dto.AutorDTO;
import br.edu.ifsudestemg.sb.model.entity.Autor;
import br.edu.ifsudestemg.sb.service.AutorService;
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
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
@CrossOrigin
public class AutorController {

    private final AutorService service;

    @GetMapping()
    @ApiOperation("Obter autores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Autores listados"),
            @ApiResponse(code = 404, message = "Autores não listados")
    })
    public ResponseEntity get() {
        List<Autor> autores = service.getAutores();
        return ResponseEntity.ok(autores.stream().map(AutorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um autor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Autor encontrado"),
            @ApiResponse(code = 404, message = "Autor não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Autor> autor = service.getAutorById(id);
        if (!autor.isPresent()) {
            return new ResponseEntity("Autor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(autor.map(AutorDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo autor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Autor salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o autor")
    })
    public ResponseEntity post(@RequestBody AutorDTO dto) {
        try {
            Autor autor = converter(dto);
            autor = service.salvar(autor);
            return new ResponseEntity(autor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um autor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Autor atualizado"),
            @ApiResponse(code = 404, message = "Autor não atualizado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AutorDTO dto) {
        if (!service.getAutorById(id).isPresent()) {
            return new ResponseEntity("Autor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Autor autor = converter(dto);
            autor.setId(id);
            service.salvar(autor);
            return ResponseEntity.ok(autor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um autor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Autor deletado"),
            @ApiResponse(code = 404, message = "Autor não deletado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Autor> autor = service.getAutorById(id);
        if (!autor.isPresent()) {
            return new ResponseEntity("Autor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(autor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Autor converter(AutorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Autor autor = modelMapper.map(dto, Autor.class);
        return autor;
    }
}
