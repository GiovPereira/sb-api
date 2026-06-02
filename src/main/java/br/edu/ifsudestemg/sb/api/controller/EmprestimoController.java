package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;

import br.edu.ifsudestemg.sb.api.dto.EmprestimoDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/emprestimos")
@RequiredArgsConstructor
@CrossOrigin
public class EmprestimoController {

    private final EmprestimoService service;

    @GetMapping()
    @ApiOperation("Obter empréstimos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Empréstimos listados"),
            @ApiResponse(code = 404, message = "Empréstimos não listados")
    })
    public ResponseEntity get() {
        List<Emprestimo> emprestimos = service.getEmprestimos();
        return ResponseEntity.ok(emprestimos.stream().map(EmprestimoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um emprestimo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Emprestimo encontrado"),
            @ApiResponse(code = 404, message = "Emprestimo não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Emprestimo> emprestimo = service.getEmprestimoById(id);
        if (!emprestimo.isPresent()) {
            return new ResponseEntity("Emprestimo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(emprestimo.map(EmprestimoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo emprestimo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Emprestimo salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o emprestimo")
    })
    public ResponseEntity post(@RequestBody EmprestimoDTO dto) {
        try {
            Emprestimo emprestimo = converter(dto);
            emprestimo = service.salvar(emprestimo);
            return new ResponseEntity(emprestimo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar empréstimo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Empréstimo atualizado"),
            @ApiResponse(code = 404, message = "Empréstimo não atualizado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EmprestimoDTO dto) {
        if (!service.getEmprestimoById(id).isPresent()) {
            return new ResponseEntity("Emprestimo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Emprestimo emprestimo = converter(dto);
            emprestimo.setId(id);
            service.salvar(emprestimo);
            return ResponseEntity.ok(emprestimo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um emprestimo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Emprestimo deletado"),
            @ApiResponse(code = 404, message = "Emprestimo não deletado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Emprestimo> emprestimo = service.getEmprestimoById(id);
        if (!emprestimo.isPresent()) {
            return new ResponseEntity("Emprestimo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(emprestimo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Emprestimo converter(EmprestimoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Emprestimo emprestimo = modelMapper.map(dto, Emprestimo.class);
        return emprestimo;
    }
}
