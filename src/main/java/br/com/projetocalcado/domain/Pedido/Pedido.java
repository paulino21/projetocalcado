package br.com.projetocalcado.domain.Pedido;

import br.com.projetocalcado.domain.cliente.Cliente;
import br.com.projetocalcado.domain.pagamentoPedido.PagamentoPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valorTotal = BigDecimal.ZERO;
    @JsonFormat(pattern =" dd/MM/yyyy")
    private LocalDate dataPedido = LocalDate.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToMany(mappedBy = "pedido" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<ItensPedido> itens = new ArrayList<>();
    @OneToMany(mappedBy = "pedido" ,  cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<PagamentoPedido> pagamentoPedido = new ArrayList<>();

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }
     public void adiconarItem(ItensPedido item){
        item.setPedido(this);
        this.getItens().add(item);
        this.valorTotal = this.valorTotal.add(item.getValor());
    }
    public void adicionaPagamentoPedido(PagamentoPedido pagamentoPedido) {
        pagamentoPedido.setPedido(this);
        this.getPagamentoPedido().add(pagamentoPedido);
    }
}
