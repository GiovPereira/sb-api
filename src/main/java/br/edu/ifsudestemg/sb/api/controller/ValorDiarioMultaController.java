package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.ValorDiarioMultaDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.ValorDiarioMulta;
import br.edu.ifsudestemg.sb.service.ValorDiarioMultaService;
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
@RequestMapping("/api/v1/valorDiarioMultas")
@CrossOrigin
public class ValorDiarioMultaController {

    private ValorDiarioMultaService service;

    public ValorDiarioMultaController(ValorDiarioMultaService service) {
        this.service = service;
    }

    @GetMapping()
    @ApiOperation("Obter valores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valores encontrados"),
            @ApiResponse(code = 404, message = "Valores não encontrados")
    })
    public ResponseEntity get() {
        List<ValorDiarioMulta> valorDiarioMultas = service.getValorDiarioMultas();
        return ResponseEntity.ok(valorDiarioMultas.stream().map(ValorDiarioMultaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um valor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valor encontrado"),
            @ApiResponse(code = 404, message = "Valor não encontrado")
    })
    public ResponseEntity get(
            @PathVariable("id") Long id) {
        Optional<ValorDiarioMulta> valorDiarioMulta = service.getValorDiarioMultaById(id);
        if (!valorDiarioMulta.isPresent()) {
            return new ResponseEntity("Valor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(valorDiarioMulta.map(ValorDiarioMultaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo valor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Valor salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o valor")
    })
    public ResponseEntity post(
            @RequestBody ValorDiarioMultaDTO dto) {
        try {
            ValorDiarioMulta valorDiarioMulta = converter(dto);
            valorDiarioMulta = service.salvar(valorDiarioMulta);
            return new ResponseEntity(ValorDiarioMultaDTO.create(valorDiarioMulta), HttpStatus.CREATED);

        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar o valor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Valor atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar o valor")
    })
    public ResponseEntity atualizar(
            @PathVariable("id") Long id,
            @RequestBody ValorDiarioMultaDTO dto) {
        if (!service.getValorDiarioMultaById(id).isPresent()) {
            return new ResponseEntity("Valor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ValorDiarioMulta valorDiarioMulta = converter(dto);
            valorDiarioMulta.setId(id);
            valorDiarioMulta = service.salvar(valorDiarioMulta);
            return ResponseEntity.ok(ValorDiarioMultaDTO.create(valorDiarioMulta));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deleta um valor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valor deletado"),
            @ApiResponse(code = 404, message = "Valor não deletado")
    })
    public ResponseEntity excluir(
            @PathVariable("id") Long id) {
        Optional<ValorDiarioMulta> valorDiarioMulta = service.getValorDiarioMultaById(id);

        if (!valorDiarioMulta.isPresent()) {
            return new ResponseEntity("Valor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(valorDiarioMulta.get());

            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public ValorDiarioMulta converter(
            ValorDiarioMultaDTO dto) {ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, ValorDiarioMulta.class);
    }
}