package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.estoque.DadosEstoque;
import br.com.projetocalcado.domain.estoque.EstoqueRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearer-key")
@RequestMapping("/estoques")
@RestController
public class EstoqueController {
    @Autowired
    EstoqueRepository estoqueRepository;

    @GetMapping
    public ResponseEntity listaEstoque(){
        var estoque = estoqueRepository.findAll().stream().map(DadosEstoque::new).toList();
        return ResponseEntity.ok(estoque);
    }
    @GetMapping("/busca/{id}")
    public ResponseEntity buscaEstoque(@PathVariable Long id){
        var estoque = estoqueRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosEstoque(estoque));
    }


}
