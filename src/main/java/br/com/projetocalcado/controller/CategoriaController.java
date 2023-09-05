package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.categoria.Categoria;
import br.com.projetocalcado.domain.categoria.CategoriaRepository;
import br.com.projetocalcado.domain.categoria.DadosCategoria;
import br.com.projetocalcado.domain.categoria.DadosDetalheCategoria;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/categoria")
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
        List<Categoria> categorias = repository.findAll();
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
    public ResponseEntity DetalheCategoria(@PathVariable Long id){
        var categoria = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheCategoria(categoria));
    }
    @PutMapping
    @Transactional
    public ResponseEntity AtualizaCategoria(@RequestBody DadosDetalheCategoria dadosCategoria){
        var categoria = repository.getReferenceById(dadosCategoria.id());
        categoria.atualizaCategoria(dadosCategoria);
        return ResponseEntity.ok(new DadosDetalheCategoria(categoria));
    }
}
