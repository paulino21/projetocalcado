package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.categoria.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/categorias")
@RestController
public class CategoriaController {
    @Autowired
    CategoriaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastraCategotia(@RequestBody @Valid DadosCategoria dadosCategoria, UriComponentsBuilder uriBuilder){
        var categoria = new Categoria(dadosCategoria);
        repository.save(categoria);
        var uri = uriBuilder.path("categoria/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalheCategoria(categoria));
    }
    @GetMapping
    public ResponseEntity listaCategoria(){
        var categorias = repository.findAll().stream().map(DadosDetalheCategoria::new).toList();
        return ResponseEntity.ok(categorias);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletaCategoria(@PathVariable Long id){
        var categoria = repository.getReferenceById(id);
        repository.delete(categoria);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detalheCategoria(@PathVariable Long id){
        var categoria = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheCategoria(categoria));
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizaCategoria(@RequestBody @Valid DadosDetalheCategoria dadosCategoria){
        var categoria = repository.getReferenceById(dadosCategoria.id());
        categoria.atualizaCategoria(dadosCategoria);
        return ResponseEntity.ok(new DadosDetalheCategoria(categoria));
    }
}
