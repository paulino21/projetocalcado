package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.cliente.Cliente;
import br.com.projetocalcado.domain.cliente.ClienteRepository;
import br.com.projetocalcado.domain.cliente.DadosCliente;
import br.com.projetocalcado.domain.cliente.DadosDetalheCliente;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("cliente")
public class ClienteController {
    @Autowired
    ClienteRepository clienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity casdastraCliente(@RequestBody @Valid DadosCliente dadosCliente, UriComponentsBuilder uriComponentsBuilder) {
        var cliente = new Cliente(dadosCliente);
        clienteRepository.save(cliente);
        var uri = uriComponentsBuilder.path("cliente/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalheCliente(cliente));
    }

    @GetMapping
    public ResponseEntity listaCliente() {

        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletaCliente(@PathVariable Long id) {

        var cliente = clienteRepository.getReferenceById(id);
        clienteRepository.delete(cliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalheCliente(@PathVariable Long id) {
        var cliente = clienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheCliente(cliente));
    }
    @PutMapping
    @Transactional
    public ResponseEntity alteraCliente(@RequestBody @Valid DadosDetalheCliente dadosDetalheCliente){

        var cliente = clienteRepository.getReferenceById(dadosDetalheCliente.id());
        cliente.atualuzaCliente(dadosDetalheCliente);
        return ResponseEntity.ok(new DadosDetalheCliente(cliente)) ;
    }
}