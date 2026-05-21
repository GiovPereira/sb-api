package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.StatusReservaDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.StatusReserva;
import br.edu.ifsudestemg.sb.service.StatusReservaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
            StatusReservaService service) {this.service = service;
    }

    @GetMapping()
    public ResponseEntity get() {
        List<StatusReserva> statusReservas = service.getStatusReservas();
        return ResponseEntity.ok(
                statusReservas.stream().map(StatusReservaDTO::create).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um status reserva")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Status reserva encontrado"),
            @ApiResponse(code = 404, message = "Status reserva não encontrado")
    })
    public ResponseEntity get(
            @PathVariable("id") Long id) {

        Optional<StatusReserva> statusReserva =
                service.getStatusReservaById(id);
        if (!statusReserva.isPresent()) {
            return new ResponseEntity("Status reserva não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(statusReserva.map(StatusReservaDTO::create)
        );
    }

    @PostMapping()
    @ApiOperation("Salva um novo status reserva")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Status reserva salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o status reserva")
    })
    public ResponseEntity post(
            @RequestBody StatusReservaDTO dto) {
        try {
            StatusReserva statusReserva = converter(dto);
            statusReserva = service.salvar(statusReserva);
            return new ResponseEntity(StatusReservaDTO.create(statusReserva), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(
            @PathVariable("id") Long id,
            @RequestBody StatusReservaDTO dto) {
        if (!service.getStatusReservaById(id).isPresent()) {
            return new ResponseEntity("Status reserva não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            StatusReserva statusReserva = converter(dto);
            statusReserva.setId(id);
            statusReserva = service.salvar(statusReserva);
            return ResponseEntity.ok(StatusReservaDTO.create(statusReserva));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {

        Optional<StatusReserva> statusReserva = service.getStatusReservaById(id);
        if (!statusReserva.isPresent()) {
            return new ResponseEntity("Status reserva não encontrado", HttpStatus.NOT_FOUND
            );
        }
        try {
            service.excluir(statusReserva.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public StatusReserva converter(
            StatusReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, StatusReserva.class
        );
    }
}