package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.ObraIdiomaDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.ObraIdioma;
import br.edu.ifsudestemg.sb.service.ObraIdiomaService;
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
@RequestMapping("/api/v1/obraIdiomas")
@RequiredArgsConstructor
@CrossOrigin
public class ObraIdiomaController
{

    private final ObraIdiomaService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de idiomas da obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Idioma da obra encontrado"),
            @ApiResponse(code = 404, message = "Idioma da obra não encontrado")
    })
    public ResponseEntity get() {
        List<ObraIdioma> obraIdiomas = service.getObraIdiomas();
        return ResponseEntity.ok(obraIdiomas.stream().map(ObraIdiomaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um idioma da obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Idioma da obra encontrado"),
            @ApiResponse(code = 404, message = "Idioma da obra não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ObraIdioma> obraIdioma = service.getObraIdiomaById(id);
        if (!obraIdioma.isPresent()) {
            return new ResponseEntity("Idioma da obra não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(obraIdioma.map(ObraIdiomaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo idioma de obra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Idioma da obra salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o idioma da obra")
    })
    public ResponseEntity post(@RequestBody ObraIdiomaDTO dto) {
        try {
            ObraIdioma obraIdioma = converter(dto);
            obraIdioma = service.salvar(obraIdioma);
            return new ResponseEntity(obraIdioma, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar a obra do idioma")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Obra do idioma atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar a obra do idioma")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ObraIdiomaDTO dto) {
        if (!service.getObraIdiomaById(id).isPresent()) {
            return new ResponseEntity("Idioma da obra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ObraIdioma obraIdioma = converter(dto);
            obraIdioma.setId(id);
            service.salvar(obraIdioma);
            return ResponseEntity.ok(obraIdioma);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deleta um idioma da obra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Idioma da obra deletado"),
            @ApiResponse(code = 404, message = "Idioma da obra não deletado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ObraIdioma> obraIdioma = service.getObraIdiomaById(id);
        if (!obraIdioma.isPresent()) {
            return new ResponseEntity("Idioma da obra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(obraIdioma.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ObraIdioma converter(ObraIdiomaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ObraIdioma obraIdioma = modelMapper.map(dto, ObraIdioma.class);
        return obraIdioma;
    }
}
