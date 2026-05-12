package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.EditoraDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Editora;
import br.edu.ifsudestemg.sb.service.EditoraService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/editoras")
@CrossOrigin
public class EditoraController {

    private EditoraService service;

    public EditoraController(EditoraService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity get() {

        List<Editora> editoras = service.getEditoras();

        return ResponseEntity.ok(
                editoras.stream()
                        .map(EditoraDTO::create)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {

        Optional<Editora> editora = service.getEditoraById(id);

        if (!editora.isPresent()) {

            return new ResponseEntity(
                    "Editora não encontrada",
                    HttpStatus.NOT_FOUND
            );
        }

        return ResponseEntity.ok(
                editora.map(EditoraDTO::create)
        );
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody EditoraDTO dto) {

        try {

            Editora editora = converter(dto);

            editora = service.salvar(editora);

            return new ResponseEntity(
                    EditoraDTO.create(editora),
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
            @RequestBody EditoraDTO dto) {

        if (!service.getEditoraById(id).isPresent()) {

            return new ResponseEntity(
                    "Editora não encontrada",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            Editora editora = converter(dto);

            editora.setId(id);

            editora = service.salvar(editora);

            return ResponseEntity.ok(
                    EditoraDTO.create(editora)
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {

        Optional<Editora> editora = service.getEditoraById(id);

        if (!editora.isPresent()) {

            return new ResponseEntity(
                    "Editora não encontrada",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            service.excluir(editora.get());

            return new ResponseEntity(
                    HttpStatus.NO_CONTENT
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    public Editora converter(EditoraDTO dto) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(dto, Editora.class);
    }
}
