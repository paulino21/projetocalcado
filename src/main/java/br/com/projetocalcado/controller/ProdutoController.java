package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.produto.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private ProdutoService produtoService;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroProduto dadosProduto, UriComponentsBuilder uriBuilder){

        var produto = produtoService.cadastraProduto(dadosProduto);
        var uri = uriBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalheDoproduto(produto));
     }
    @GetMapping
    public ResponseEntity<Page<DadosListaProduto>> listaProdutos(@PageableDefault(size = 10 , sort = {"nomeProd"}) Pageable pageable){
         var page = repository.findAll(pageable).map(DadosListaProduto::new);
         return ResponseEntity.ok(page);
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
    public ResponseEntity atualizaProduto(@RequestBody @Valid DadosProdutoAlterado dadosProdutoAlterado){
        produtoService.atualizaProduto(dadosProdutoAlterado);
        return ResponseEntity.ok(produtoService.atualizaProduto(dadosProdutoAlterado));
    }
    @GetMapping("/busca/{termoBusca}")
    public ResponseEntity buscaPorNomeProduto(@PathVariable String termoBusca){
        var produtos = repository.findByNomeProdStartingWithIgnoreCase(termoBusca).stream().map(DadosDetalheDoproduto::new);
        return ResponseEntity.ok(produtos);
    }
    @GetMapping("/busca/porEan/{termoBusca}")
    public ResponseEntity buscaPorEanProduto(@PathVariable String termoBusca){
       var produtos = repository.findByCodEanStartingWithIgnoreCase(termoBusca).stream().map(DadosDetalheDoproduto::new);;
        return ResponseEntity.ok(produtos);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }
}
