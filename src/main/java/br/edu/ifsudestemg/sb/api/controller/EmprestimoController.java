package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.EmprestimoDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Emprestimo;
import br.edu.ifsudestemg.sb.service.EmprestimoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emprestimos")
@RequiredArgsConstructor
@CrossOrigin
public class EmprestimoController {

    private final EmprestimoService service;

    @GetMapping
    @ApiOperation("Obter empréstimos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Empréstimos encontrados")
    })
    public ResponseEntity get() {

        List<EmprestimoDTO> emprestimos =
                service.getEmprestimosDTO();

        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um empréstimo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Empréstimo encontrado"),
            @ApiResponse(code = 404, message = "Empréstimo não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {

        Optional<Emprestimo> emprestimo =
                service.getEmprestimoById(id);

        if (!emprestimo.isPresent()) {

            return new ResponseEntity(
                    "Empréstimo não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        EmprestimoDTO dto =
                service.createDTO(
                        emprestimo.get()
                );

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @ApiOperation("Salvar empréstimo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Empréstimo salvo"),
            @ApiResponse(code = 400, message = "Erro ao salvar")
    })
    public ResponseEntity post(
            @RequestBody EmprestimoDTO dto) {

        try {

            Emprestimo emprestimo =
                    converter(dto);

            emprestimo =
                    service.salvar(
                            emprestimo,
                            dto.getIdCliente(),
                            dto.getIdExemplar()
                    );

            return new ResponseEntity(
                    service.createDTO(
                            emprestimo
                    ),
                    HttpStatus.CREATED
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar empréstimo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Empréstimo atualizado"),
            @ApiResponse(code = 404, message = "Empréstimo não encontrado")
    })
    public ResponseEntity atualizar(
            @PathVariable("id") Long id,
            @RequestBody EmprestimoDTO dto) {

        if (!service.getEmprestimoById(id).isPresent()) {

            return new ResponseEntity(
                    "Empréstimo não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            Emprestimo emprestimo =
                    converter(dto);

            emprestimo.setId(id);

            emprestimo =
                    service.atualizar(
                            emprestimo,
                            dto.getIdCliente(),
                            dto.getIdExemplar()
                    );

            return ResponseEntity.ok(
                    service.createDTO(
                            emprestimo
                    )
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Excluir empréstimo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Empréstimo excluído"),
            @ApiResponse(code = 404, message = "Empréstimo não encontrado")
    })
    public ResponseEntity excluir(
            @PathVariable("id") Long id) {

        Optional<Emprestimo> emprestimo =
                service.getEmprestimoById(id);

        if (!emprestimo.isPresent()) {

            return new ResponseEntity(
                    "Empréstimo não encontrado",
                    HttpStatus.NOT_FOUND
            );
        }

        try {

            service.excluir(
                    emprestimo.get()
            );

            return new ResponseEntity(
                    HttpStatus.NO_CONTENT
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/entrega")
    @ApiOperation("Registrar entrega do empréstimo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Entrega registrada"),
            @ApiResponse(code = 404, message = "Empréstimo não encontrado")
    })
    public ResponseEntity registrarEntrega(
            @PathVariable("id") Long id) {

        try {

            Emprestimo emprestimo =
                    service.registrarEntrega(id);

            return ResponseEntity.ok(
                    service.createDTO(
                            emprestimo
                    )
            );

        } catch (RegraNegocioException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    private Emprestimo converter(
            EmprestimoDTO dto) {

        ModelMapper modelMapper =
                new ModelMapper();

        return modelMapper.map(
                dto,
                Emprestimo.class
        );
    }
}