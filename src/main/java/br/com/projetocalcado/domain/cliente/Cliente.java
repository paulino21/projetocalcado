package br.com.projetocalcado.domain.cliente;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    @Embedded
    private Endereco endereco;

    public Cliente(DadosCliente dadosCliente) {
        this.nome = dadosCliente.nome();
        this.cpf = dadosCliente.cpf();
        this.telefone = dadosCliente.telefone();
        this.endereco = dadosCliente.endereco();
    }

    public void atualuzaCliente(DadosDetalheCliente dadosDetalheCliente) {
        this.nome = dadosDetalheCliente.nome();
        this.cpf = dadosDetalheCliente.cpf();
        this.telefone = dadosDetalheCliente.telefone();
        this.endereco = dadosDetalheCliente.endereco();
    }
}
