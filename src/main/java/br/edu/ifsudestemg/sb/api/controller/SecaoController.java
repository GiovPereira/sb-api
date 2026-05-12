package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.SecaoDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Secao;
import br.edu.ifsudestemg.sb.service.SecaoService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.dynamic.DynamicType;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/secoes")
@RequiredArgsConstructor
@CrossOrigin

public class SecaoController {

    private final SecaoService service;

    public SecaoController(SecaoService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Secao> secao = service.getSecoes();
        return ResponseEntity.ok(secao.stream().map(SecaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        DynamicType.Builder.FieldDefinition.Optional<Secao> secao = service.getSecaoById(id);
        if (!secao.isPresent()) {
            return new ResponseEntity("Seção não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(secao.map(SecaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SecaoDTO dto) {
        try {
            Secao secao = converter(dto);
            secao = service.salvar(secao);
            return new ResponseEntity(secao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Secao converter(SecaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Secao secao = modelMapper.map(dto, Secao.class);
        return secao;
    }

}
