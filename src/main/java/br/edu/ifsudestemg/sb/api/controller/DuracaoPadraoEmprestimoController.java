package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.DuracaoPadraoEmprestimoDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import br.edu.ifsudestemg.sb.service.DuracaoPadraoEmprestimoService;
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
@RequestMapping("/api/v1/duracaoPadraoEmprestimos")
@CrossOrigin
public class DuracaoPadraoEmprestimoController {

    private DuracaoPadraoEmprestimoService service;

    public DuracaoPadraoEmprestimoController(DuracaoPadraoEmprestimoService service) {
        this.service = service;
    }

    @GetMapping()
    @ApiOperation("Obter durações")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Durações listados"),
            @ApiResponse(code = 404, message = "Durações não listados")
    })
    public ResponseEntity get() {
        List<DuracaoPadraoEmprestimo> duracaoPadraoEmprestimos = service.getDuracaoPadraoEmprestimos();
        return ResponseEntity.ok( duracaoPadraoEmprestimos.stream().map(DuracaoPadraoEmprestimoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de duração")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Duração encontrada"),
            @ApiResponse(code = 404, message = "Duração não encontrada")
    })
    public ResponseEntity get(
            @PathVariable("id") Long id) {
        Optional<DuracaoPadraoEmprestimo> duracao = service.getDuracaoPadraoEmprestimoById(id);
        if (!duracao.isPresent()) {
            return new ResponseEntity("Duração não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(duracao.map(DuracaoPadraoEmprestimoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova duração")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Duração salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a duração")
    })
    public ResponseEntity post(
            @RequestBody DuracaoPadraoEmprestimoDTO dto) {

        try {
            System.out.println("ENTROU NO POST");
            DuracaoPadraoEmprestimo duracao = converter(dto);
            duracao = service.salvar(duracao);
            return new ResponseEntity(DuracaoPadraoEmprestimoDTO.create(duracao), HttpStatus.CREATED);

        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar a duração")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Duração atualizada"),
            @ApiResponse(code = 404, message = "Duração não atualizada")
    })
    public ResponseEntity atualizar(
            @PathVariable("id") Long id,
            @RequestBody DuracaoPadraoEmprestimoDTO dto) {

        if (!service.getDuracaoPadraoEmprestimoById(id).isPresent()) {
            return new ResponseEntity("Duração não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            DuracaoPadraoEmprestimo duracao = converter(dto);
            duracao.setId(id);
            duracao = service.salvar(duracao);
            return ResponseEntity.ok(DuracaoPadraoEmprestimoDTO.create(duracao));

        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deleta uma duração")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Duração deletada"),
            @ApiResponse(code = 404, message = "Duração não deletada")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {

        Optional<DuracaoPadraoEmprestimo> duracao = service.getDuracaoPadraoEmprestimoById(id);

        if (!duracao.isPresent()) {
            return new ResponseEntity("Duração não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            service.excluir(duracao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public DuracaoPadraoEmprestimo converter(
            DuracaoPadraoEmprestimoDTO dto) {ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, DuracaoPadraoEmprestimo.class);
    }
}