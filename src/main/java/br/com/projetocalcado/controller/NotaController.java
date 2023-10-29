package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.nota.DadosNotaFiscal;
import br.com.projetocalcado.domain.nota.NotaFiscalRepository;
import br.com.projetocalcado.domain.nota.NotaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/nota")
public class NotaController {
    @Autowired
    NotaService notaService;
    @Autowired
    NotaFiscalRepository notaFiscalRepository;

    @GetMapping("/{caminho}")
    public ResponseEntity pegaXmlNota(@PathVariable String caminho , UriComponentsBuilder uriBuilder){
            var infXml = notaService.devolveDadosXml(caminho);
            var nota = notaService.salvaNotaXml(infXml);
            var uri = uriBuilder.path("/nota/{id}").buildAndExpand(nota.id()).toUri();
        return ResponseEntity.created(uri).body(nota);
    }
    @PostMapping
    @Transactional
    public ResponseEntity cadastraNota(@RequestBody @Valid DadosNotaFiscal dadosNotaFiscal , UriComponentsBuilder uriBuilder){
            var nota = notaService.cadastraNota(dadosNotaFiscal);
            var uri = uriBuilder.path("/nota/{id}").buildAndExpand(nota.id()).toUri();
        return ResponseEntity.created(uri).body(nota);
    }

}
