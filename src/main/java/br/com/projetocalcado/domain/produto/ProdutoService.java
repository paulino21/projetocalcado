package br.com.projetocalcado.domain.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository repository;
    public void deletaProduto(Long id){

            var produto = repository.getReferenceById(id);
            repository.delete(produto);
    }
}
