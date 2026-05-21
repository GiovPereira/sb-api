package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.ReservaDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Reserva;
import br.edu.ifsudestemg.sb.service.ReservaService;
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
@RequestMapping("/api/v1/reservas")
@CrossOrigin

public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Reserva> reservas = service.getReservas();
        return ResponseEntity.ok(
                reservas.stream()
                        .map(ReservaDTO::create)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma reserva")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reserva encontrada"),
            @ApiResponse(code = 404, message = "Reserva não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity(
                    "Reserva não encontrada",
                    HttpStatus.NOT_FOUND
            );
        }
        return ResponseEntity.ok(
                reserva.map(ReservaDTO::create)
        );
    }

    @PostMapping()
    @ApiOperation("Salva uma nova reserva")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Reserva salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o reserva")
    })
    public ResponseEntity post(@RequestBody ReservaDTO dto) {

        try {
            Reserva reserva = converter(dto);
            reserva = service.salvar(reserva);
            return new ResponseEntity(
                    ReservaDTO.create(reserva),
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
            @RequestBody ReservaDTO dto) {
        if (!service.getReservaById(id).isPresent()) {
            return new ResponseEntity(
                    "Reserva não encontrada",
                    HttpStatus.NOT_FOUND
            );
        }
        try {

            Reserva reserva = converter(dto);
            reserva.setId(id);
            reserva = service.salvar(reserva);
            return ResponseEntity.ok(
                    ReservaDTO.create(reserva)
            );
        } catch (RegraNegocioException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity(
                    "Reserva não encontrada",
                    HttpStatus.NOT_FOUND
            );
        }
        try {
            service.excluir(reserva.get());
            return new ResponseEntity(
                    HttpStatus.NO_CONTENT
            );
        } catch (RegraNegocioException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    public Reserva converter(ReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Reserva.class);
    }
}