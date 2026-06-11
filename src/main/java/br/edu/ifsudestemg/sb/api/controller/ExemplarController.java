package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.ExemplarDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import br.edu.ifsudestemg.sb.service.ExemplarService;
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

@RestController
@RequestMapping("/api/v1/exemplares")
@RequiredArgsConstructor
@CrossOrigin
public class ExemplarController {

    private final ExemplarService service;

    @GetMapping
    @ApiOperation("Obter exemplares")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exemplares encontrados")
    })
    public ResponseEntity get() {

        List<ExemplarDTO> exemplares = service.getExemplaresDTO();

        return ResponseEntity.ok(exemplares);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um exemplar")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exemplar encontrado"),
            @ApiResponse(code = 404, message = "Exemplar não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {

        Optional<Exemplar> exemplar = service.getExemplarById(id);

        if (!exemplar.isPresent()) {
            return new ResponseEntity("Exemplar não encontrado", HttpStatus.NOT_FOUND);
        }

        ExemplarDTO dto = service.createDTO(exemplar.get());

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @ApiOperation("Salvar exemplar")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Exemplar salvo"),
            @ApiResponse(code = 400, message = "Erro ao salvar")
    })
    public ResponseEntity post(@RequestBody ExemplarDTO dto) {

        try {

            Exemplar exemplar = converter(dto);

            exemplar = service.salvar(
                    exemplar,
                    dto.getIdObra(),
                    dto.getIdSecao()
            );

            return new ResponseEntity(
                    service.createDTO(exemplar),
                    HttpStatus.CREATED
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar exemplar")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exemplar atualizado"),
            @ApiResponse(code = 404, message = "Exemplar não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ExemplarDTO dto) {

        if (!service.getExemplarById(id).isPresent()) {
            return new ResponseEntity("Exemplar não encontrado", HttpStatus.NOT_FOUND);
        }

        try {

            Exemplar exemplar = converter(dto);

            exemplar.setId(id);

            exemplar = service.atualizar(
                    exemplar,
                    dto.getIdObra(),
                    dto.getIdStatusExemplar(),
                    dto.getIdSecao()
            );

            return ResponseEntity.ok(service.createDTO(exemplar));

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Excluir exemplar")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Exemplar excluído"),
            @ApiResponse(code = 404, message = "Exemplar não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {

        Optional<Exemplar> exemplar = service.getExemplarById(id);

        if (!exemplar.isPresent()) {
            return new ResponseEntity("Exemplar não encontrado", HttpStatus.NOT_FOUND);
        }

        try {

            service.excluir(exemplar.get());

            return new ResponseEntity(HttpStatus.NO_CONTENT);

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        }
    }

    private Exemplar converter(ExemplarDTO dto) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(dto, Exemplar.class);
    }

}