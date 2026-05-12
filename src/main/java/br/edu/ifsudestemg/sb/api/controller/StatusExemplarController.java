package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.StatusExemplarDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.StatusExemplar;
import br.edu.ifsudestemg.sb.service.StatusExemplarService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/statusExemplares")
@CrossOrigin
public class StatusExemplarController {

    private StatusExemplarService service;

    public StatusExemplarController(
            StatusExemplarService service) {

        this.service = service;
    }

    @GetMapping()
    public ResponseEntity get() {

        List<StatusExemplar> statusExemplares =
                service.getStatusExemplares();

        return ResponseEntity.ok(
                statusExemplares.stream()
                        .map(StatusExemplarDTO::create)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {

        Optional<StatusExemplar> statusExemplar =
                service.getStatusExemplarById(id);

        if (!statusExemplar.isPresent()) {

            return new ResponseEntity(
                    "Status não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        return ResponseEntity.ok(
                statusExemplar.map(StatusExemplarDTO::create)
        );
    }

    @PostMapping()
    public ResponseEntity post(
            @RequestBody StatusExemplarDTO dto) {

        try {

            StatusExemplar statusExemplar =
                    converter(dto);

            statusExemplar =
                    service.salvar(statusExemplar);

            return new ResponseEntity(
                    StatusExemplarDTO.create(statusExemplar),
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
            @RequestBody StatusExemplarDTO dto) {

        if (!service.getStatusExemplarById(id).isPresent()) {

            return new ResponseEntity(
                    "Status não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            StatusExemplar statusExemplar =
                    converter(dto);

            statusExemplar.setId(id);

            statusExemplar =
                    service.salvar(statusExemplar);

            return ResponseEntity.ok(
                    StatusExemplarDTO.create(statusExemplar)
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(
            @PathVariable("id") Long id) {

        Optional<StatusExemplar> statusExemplar =
                service.getStatusExemplarById(id);

        if (!statusExemplar.isPresent()) {

            return new ResponseEntity(
                    "Status não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            service.excluir(statusExemplar.get());

            return new ResponseEntity(
                    HttpStatus.NO_CONTENT
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    public StatusExemplar converter(
            StatusExemplarDTO dto) {

        ModelMapper modelMapper =
                new ModelMapper();

        return modelMapper.map(
                dto,
                StatusExemplar.class
        );
    }
}