package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.ReservaDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Reserva;
import br.edu.ifsudestemg.sb.service.ReservaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity get() {

        List<ReservaDTO> reservas =
                service.getReservasDTO();

        return ResponseEntity.ok(
                reservas
        );
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma reserva")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reserva encontrada"),
            @ApiResponse(code = 404, message = "Reserva não encontrada")
    })
    public ResponseEntity get(
            @PathVariable("id") Long id) {

        return service
                .getReservaById(id)
                .map(reserva ->
                        ResponseEntity.ok(
                                service.createDTO(reserva)
                        ))
                .orElseGet(() ->
                        new ResponseEntity(
                                "Reserva não encontrada",
                                HttpStatus.NOT_FOUND
                        ));
    }

    @PostMapping
    @ApiOperation("Salvar reserva")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Reserva salva"),
            @ApiResponse(code = 400, message = "Erro ao salvar")
    })
    public ResponseEntity post(
            @RequestBody ReservaDTO dto) {

        try {

            Reserva reserva =
                    converter(dto);

            reserva =
                    service.salvar(
                            reserva,
                            dto.getIdCliente(),
                            dto.getIdObra()
                    );

            return new ResponseEntity(
                    service.createDTO(
                            reserva
                    ),
                    HttpStatus.CREATED
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            e.getMessage()
                    );
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar reserva")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reserva atualizada"),
            @ApiResponse(code = 404, message = "Reserva não encontrada"),
            @ApiResponse(code = 400, message = "Erro ao atualizar")
    })
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

            Reserva reserva =
                    converter(dto);

            reserva.setId(id);

            reserva =
                    service.atualizar(
                            reserva,
                            dto.getIdCliente(),
                            dto.getIdObra()
                    );

            return ResponseEntity.ok(
                    service.createDTO(
                            reserva
                    )
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            e.getMessage()
                    );
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Excluir reserva")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Reserva excluída"),
            @ApiResponse(code = 404, message = "Reserva não encontrada"),
            @ApiResponse(code = 400, message = "Erro ao excluir")
    })
    public ResponseEntity excluir(
            @PathVariable("id") Long id) {

        return service
                .getReservaById(id)
                .map(reserva -> {

                    try {

                        service.excluir(
                                reserva
                        );

                        return new ResponseEntity(
                                HttpStatus.NO_CONTENT
                        );

                    } catch (RegraNegocioException e) {

                        return ResponseEntity
                                .badRequest()
                                .body(
                                        e.getMessage()
                                );
                    }

                })
                .orElseGet(() ->
                        new ResponseEntity(
                                "Reserva não encontrada",
                                HttpStatus.NOT_FOUND
                        ));
    }

    private Reserva converter(
            ReservaDTO dto) {

        ModelMapper modelMapper =
                new ModelMapper();

        return modelMapper.map(
                dto,
                Reserva.class
        );
    }

}