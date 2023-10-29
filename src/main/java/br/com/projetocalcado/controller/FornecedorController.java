package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.fornecedor.DadosDetalheFornecedor;
import br.com.projetocalcado.domain.fornecedor.DadosFornecedor;
import br.com.projetocalcado.domain.fornecedor.Fornecedor;
import br.com.projetocalcado.domain.fornecedor.FornecedorRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/fornecedor")
@RestController
public class FornecedorController {
    @Autowired
    FornecedorRepository fornecedorRepository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastraFornecedor(@RequestBody @Valid DadosFornecedor dadosFornecedor , UriComponentsBuilder uriBuilder){
        var fornecedor = new Fornecedor(dadosFornecedor);
        fornecedorRepository.save(fornecedor);
        var uri = uriBuilder.path("/fornecedor/{id}").buildAndExpand(fornecedor.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalheFornecedor(fornecedor));
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizaFornecedor(@RequestBody @Valid DadosDetalheFornecedor dadosFornecedor){
        var fornecedor = fornecedorRepository.getReferenceById(dadosFornecedor.id());
        System.out.println(dadosFornecedor.enderFornecedor().getMunicipio());
        fornecedor.atualizaFornecedor(dadosFornecedor);
        return ResponseEntity.ok(new DadosDetalheFornecedor(fornecedor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var fornecedor = fornecedorRepository.getReferenceById(id);
        fornecedorRepository.delete(fornecedor);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detalheFornecedor(@PathVariable Long id){
        var fornecedor = fornecedorRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheFornecedor(fornecedor));
    }

}
