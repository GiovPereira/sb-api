package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.StatusReservaDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.StatusReserva;
import br.edu.ifsudestemg.sb.service.StatusReservaService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/statusReservas")
@CrossOrigin
public class StatusReservaController {

    private StatusReservaService service;

    public StatusReservaController(
            StatusReservaService service) {

        this.service = service;
    }

    @GetMapping()
    public ResponseEntity get() {

        List<StatusReserva> statusReservas =
                service.getStatusReservas();

        return ResponseEntity.ok(
                statusReservas.stream()
                        .map(StatusReservaDTO::create)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity get(
            @PathVariable("id") Long id) {

        Optional<StatusReserva> statusReserva =
                service.getStatusReservaById(id);

        if (!statusReserva.isPresent()) {

            return new ResponseEntity(
                    "Status não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        return ResponseEntity.ok(
                statusReserva.map(StatusReservaDTO::create)
        );
    }

    @PostMapping()
    public ResponseEntity post(
            @RequestBody StatusReservaDTO dto) {

        try {

            StatusReserva statusReserva =
                    converter(dto);

            statusReserva =
                    service.salvar(statusReserva);

            return new ResponseEntity(
                    StatusReservaDTO.create(statusReserva),
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
            @RequestBody StatusReservaDTO dto) {

        if (!service.getStatusReservaById(id).isPresent()) {

            return new ResponseEntity(
                    "Status não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            StatusReserva statusReserva =
                    converter(dto);

            statusReserva.setId(id);

            statusReserva =
                    service.salvar(statusReserva);

            return ResponseEntity.ok(
                    StatusReservaDTO.create(statusReserva)
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

        Optional<StatusReserva> statusReserva =
                service.getStatusReservaById(id);

        if (!statusReserva.isPresent()) {

            return new ResponseEntity(
                    "Status não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            service.excluir(statusReserva.get());

            return new ResponseEntity(
                    HttpStatus.NO_CONTENT
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    public StatusReserva converter(
            StatusReservaDTO dto) {

        ModelMapper modelMapper =
                new ModelMapper();

        return modelMapper.map(
                dto,
                StatusReserva.class
        );
    }
}