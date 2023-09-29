package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.domain.xmlnota.Duplicata;
import br.com.projetocalcado.domain.fornecedor.Fornecedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NotaFiscal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numeroNF;
    private LocalDateTime dataEmissao;
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private LocalDate dataLacamento = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;
    @OneToMany(mappedBy = "notaFiscal" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<ItensNota> itens = new ArrayList<>();

    @OneToMany(mappedBy = "notaFiscal" ,  cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Duplicata> duplicatas = new ArrayList<>();
    public NotaFiscal(Long numeroNF, LocalDateTime dataEmissao, Fornecedor fornecedor) {
        this.numeroNF = numeroNF;
        this.dataEmissao = dataEmissao;
        this.fornecedor = fornecedor;
    }
     public void adiconarItem(ItensNota item){
        item.setNotaFiscal(this);
        this.getItens().add(item);
        this.valorTotal = this.valorTotal.add(item.getValor());
    }
    public void adicionaPagamento(Duplicata duplicata) {
        duplicata.setNotaFiscal(this);
        this.getDuplicatas().add(duplicata);
    }
}
