package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.produto.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private ProdutoService produtoService;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroProduto dadosProduto, UriComponentsBuilder uriBuilder){

        var produto = new Produto(dadosProduto);
        repository.save(produto);
        var uri = uriBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalheDoproduto(produto));
     }
    @GetMapping
    public ResponseEntity listaProdutos(){
        List<Produto> produtos =  repository.findAll();
        return ResponseEntity.ok(produtos);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletaProduto(@PathVariable Long id){
        produtoService.deletaProduto(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detalheProduto(@PathVariable Long id){

        var produto = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheDoproduto(produto));
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizaProduto(@RequestBody @Valid DadosDetalheDoproduto dadosProduto){
        var produto = repository.getReferenceById(dadosProduto.id());
        produto.atualizaProduto(dadosProduto);
        return ResponseEntity.ok(new DadosDetalheDoproduto(produto));
    }
    @GetMapping("/busca/{nome}")
    public ResponseEntity buscaPorNomeProduto(@PathVariable String nome){
        List<Produto> produtos = repository.findByNomeProdStartingWithIgnoreCase(nome);
        return ResponseEntity.ok(produtos);
    }
}
