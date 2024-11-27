package br.com.projetocalcado.domain.fornecedor;

import br.com.projetocalcado.domain.xmlnota.EnderFornecedor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("emit")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Fornecedor {

     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Id
     Long id;
     @XStreamAlias("CNPJ")
     private String cnpj;
     @XStreamAlias("xNome")
     private String razaoSocial;
     @XStreamAlias("enderEmit")
     @Embedded
     private EnderFornecedor enderFornecedor;
     @XStreamAlias("IE")
     private String inscricaoEstadual;

     public Fornecedor (DadosFornecedor dadosFornecedor){
          this.cnpj = dadosFornecedor.cnpj();
          this.razaoSocial = dadosFornecedor.razaoSocial();
          this.enderFornecedor = dadosFornecedor.enderFornecedor();
          this.inscricaoEstadual = dadosFornecedor.inscricaoEstadual();
     }
    public void atualizaFornecedor (DadosDetalheFornecedor dadosFornecedor){
          this.id = dadosFornecedor.id();
          this.cnpj = dadosFornecedor.cnpj();
          this.razaoSocial = dadosFornecedor.razaoSocial();
          this.inscricaoEstadual = dadosFornecedor.inscricaoEstadual();
          this.enderFornecedor = dadosFornecedor.enderFornecedor();
      }


}
