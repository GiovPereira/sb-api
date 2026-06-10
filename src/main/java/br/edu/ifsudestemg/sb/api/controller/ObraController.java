package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.ObraDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Obra;
import br.edu.ifsudestemg.sb.service.ObraService;
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
@RequestMapping("/api/v1/obras")
@RequiredArgsConstructor
@CrossOrigin
public class ObraController {

    private final ObraService service;

    @GetMapping
    @ApiOperation("Obter obras")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Obras encontradas")
    })
    public ResponseEntity get() {

        List<ObraDTO> obras =
                service.getObrasDTO();

        return ResponseEntity.ok(obras);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Obra encontrada"),
            @ApiResponse(code = 404, message = "Obra não encontrada")
    })
    public ResponseEntity get(
            @PathVariable("id") Long id) {

        Optional<Obra> obra =
                service.getObraById(id);

        if (!obra.isPresent()) {
            return new ResponseEntity(
                    "Obra não encontrada",
                    HttpStatus.NOT_FOUND);
        }

        ObraDTO dto =
                service.createDTO(obra.get());

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @ApiOperation("Salvar obra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Obra salva"),
            @ApiResponse(code = 400, message = "Erro ao salvar")
    })
    public ResponseEntity post(
            @RequestBody ObraDTO dto) {

        try {

            Obra obra =
                    converter(dto);

            obra =
                    service.salvar(
                            obra,
                            dto.getAutoresIds(),
                            dto.getEditorasIds(),
                            dto.getGenerosIds(),
                            dto.getIdiomasIds()
                    );

            return new ResponseEntity(
                    service.createDTO(obra),
                    HttpStatus.CREATED);

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Obra atualizada"),
            @ApiResponse(code = 404, message = "Obra não encontrada")
    })
    public ResponseEntity atualizar(
            @PathVariable("id") Long id,
            @RequestBody ObraDTO dto) {

        if (!service.getObraById(id).isPresent()) {

            return new ResponseEntity(
                    "Obra não encontrada",
                    HttpStatus.NOT_FOUND);
        }

        try {

            Obra obra =
                    converter(dto);

            obra.setId(id);

            obra =
                    service.atualizar(
                            obra,
                            dto.getAutoresIds(),
                            dto.getEditorasIds(),
                            dto.getGenerosIds(),
                            dto.getIdiomasIds()
                    );

            return ResponseEntity.ok(
                    service.createDTO(obra));

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Excluir obra")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Obra excluída"),
            @ApiResponse(code = 404, message = "Obra não encontrada")
    })
    public ResponseEntity excluir(
            @PathVariable("id") Long id) {

        Optional<Obra> obra =
                service.getObraById(id);

        if (!obra.isPresent()) {

            return new ResponseEntity(
                    "Obra não encontrada",
                    HttpStatus.NOT_FOUND);
        }

        try {

            service.excluir(obra.get());

            return new ResponseEntity(
                    HttpStatus.NO_CONTENT);

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    private Obra converter(
            ObraDTO dto) {

        ModelMapper modelMapper =
                new ModelMapper();

        return modelMapper.map(
                dto,
                Obra.class);
    }
}