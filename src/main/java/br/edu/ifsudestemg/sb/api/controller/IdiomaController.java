package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.IdiomaDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Idioma;
import br.edu.ifsudestemg.sb.service.IdiomaService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/idiomas")
@CrossOrigin
public class IdiomaController {

    private IdiomaService service;

    public IdiomaController(IdiomaService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity get() {

        List<Idioma> idiomas = service.getIdiomas();

        return ResponseEntity.ok(
                idiomas.stream()
                        .map(IdiomaDTO::create)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {

        Optional<Idioma> idioma = service.getIdiomaById(id);

        if (!idioma.isPresent()) {

            return new ResponseEntity(
                    "Idioma não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        return ResponseEntity.ok(
                idioma.map(IdiomaDTO::create)
        );
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody IdiomaDTO dto) {

        try {

            Idioma idioma = converter(dto);

            idioma = service.salvar(idioma);

            return new ResponseEntity(
                    IdiomaDTO.create(idioma),
                    HttpStatus.CREATED
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(
            @PathVariable("id") Long id,
            @RequestBody IdiomaDTO dto) {

        if (!service.getIdiomaById(id).isPresent()) {

            return new ResponseEntity(
                    "Idioma não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            Idioma idioma = converter(dto);

            idioma.setId(id);

            idioma = service.salvar(idioma);

            return ResponseEntity.ok(
                    IdiomaDTO.create(idioma)
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {

        Optional<Idioma> idioma = service.getIdiomaById(id);

        if (!idioma.isPresent()) {

            return new ResponseEntity(
                    "Idioma não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            service.excluir(idioma.get());

            return new ResponseEntity(
                    HttpStatus.NO_CONTENT
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    public Idioma converter(IdiomaDTO dto) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(dto, Idioma.class);
    }
}