package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.ReservaDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Reserva;
import br.edu.ifsudestemg.sb.service.ReservaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
@CrossOrigin
public class ReservaController {

    private final ReservaService service;

    @GetMapping
    @ApiOperation("Obter reservas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reservas encontradas")
    })
    public ResponseEntity<List<ReservaDTO>> get() {

        return ResponseEntity.ok(service.getReservasDTO());
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma reserva")
    public ResponseEntity<?> get(@PathVariable Long id) {

        Optional<Reserva> reserva = service.getReservaById(id);

        if (!reserva.isPresent()) {
            return new ResponseEntity<>(
                    "Reserva não encontrada",
                    HttpStatus.NOT_FOUND
            );
        }

        return ResponseEntity.ok(service.createDTO(reserva.get()));
    }

    @PostMapping
    @ApiOperation("Salvar reserva")
    public ResponseEntity<?> post(@RequestBody ReservaDTO dto) {

        try {

            Reserva reserva = new Reserva();

            reserva = service.salvar(
                    reserva,
                    dto.getIdCliente(),
                    dto.getIdObra()
            );

            return new ResponseEntity<>(
                    service.createDTO(reserva),
                    HttpStatus.CREATED
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar reserva")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody ReservaDTO dto) {

        Optional<Reserva> existente = service.getReservaById(id);

        if (!existente.isPresent()) {
            return new ResponseEntity<>(
                    "Reserva não encontrada",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            Reserva reserva = new Reserva();
            reserva.setId(id);

            reserva = service.atualizar(
                    reserva,
                    dto.getIdCliente(),
                    dto.getIdObra()
            );

            return ResponseEntity.ok(service.createDTO(reserva));

        } catch (RegraNegocioException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Excluir reserva")
    public ResponseEntity<?> excluir(@PathVariable Long id) {

        Optional<Reserva> reserva = service.getReservaById(id);

        if (!reserva.isPresent()) {
            return new ResponseEntity<>(
                    "Reserva não encontrada",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            service.excluir(reserva.get());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (RegraNegocioException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}