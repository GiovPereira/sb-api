package br.edu.ifsudestemg.sb.api.controller;

import br.edu.ifsudestemg.sb.api.dto.MultaDTO;
//import br.edu.ifsudestemg.sb.service.MultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multas")
public class MultaController {

    @Autowired
    //private MultaService service;

    @GetMapping("/{id}")
    public ResponseEntity<MultaDTO> obterMulta(@PathVariable Long id) {
       // return ResponseEntity.ok(service.buscarPorId(id));
    }
}
