package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.XmlNota.LerXmlNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/nota")
public class NotaController {
    @Autowired
    private LerXmlNota lerXmlNota;
    @GetMapping("/{caminho}")
    public ResponseEntity pegaXmlNota(@PathVariable String caminho){
        lerXmlNota.setCaminho(caminho);
        var itensDaNota = lerXmlNota.devolveDadosXml();
        return ResponseEntity.ok().body(itensDaNota);
    }
}
