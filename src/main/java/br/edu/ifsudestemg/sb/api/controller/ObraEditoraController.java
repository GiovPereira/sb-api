package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.ObraEditoraDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.ObraEditora;
import br.edu.ifsudestemg.sb.service.ObraEditoraService;
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
@RequestMapping("/api/v1/obraEditoras")
@RequiredArgsConstructor
@CrossOrigin
public class ObraEditoraController
{

    private final ObraEditoraService service;

    @GetMapping()
    @ApiOperation("Obter editoras da obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Editoras da obra encontrados"),
            @ApiResponse(code = 404, message = "Editoras da obra não encontrados")
    })
    public ResponseEntity get() {
        List<ObraEditora> obraEditoras = service.getObraEditoras();
        return ResponseEntity.ok(obraEditoras.stream().map(ObraEditoraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um editora da obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Editora da obra encontrado"),
            @ApiResponse(code = 404, message = "Editora da obra não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ObraEditora> obraEditora = service.getObraEditoraById(id);
        if (!obraEditora.isPresent()) {
            return new ResponseEntity("Editora da obra não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(obraEditora.map(ObraEditoraDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo editora da obra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Editora da obra salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o editora da obra")
    })
    public ResponseEntity post(@RequestBody ObraEditoraDTO dto) {
        try {
            ObraEditora obraEditora = converter(dto);
            obraEditora = service.salvar(obraEditora);
            return new ResponseEntity(obraEditora, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar o editora da obra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Editora da obra atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar o editora da obra")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ObraEditoraDTO dto) {
        if (!service.getObraEditoraById(id).isPresent()) {
            return new ResponseEntity("Editora da obra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ObraEditora obraEditora = converter(dto);
            obraEditora.setId(id);
            service.salvar(obraEditora);
            return ResponseEntity.ok(obraEditora);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Editora da obra uma duração")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Editora da obra deletado"),
            @ApiResponse(code = 404, message = "Editora da obra não deletado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ObraEditora> obraEditora = service.getObraEditoraById(id);
        if (!obraEditora.isPresent()) {
            return new ResponseEntity("Editora da obra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(obraEditora.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ObraEditora converter(ObraEditoraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ObraEditora obraEditora = modelMapper.map(dto, ObraEditora.class);
        return obraEditora;
    }
}
