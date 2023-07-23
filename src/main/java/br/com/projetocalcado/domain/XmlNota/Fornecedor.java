package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@XStreamAlias("emit")
@Entity
public class Fornecedor {
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Id
     Long id;
     @XStreamAlias("CNPJ")
     private Long cnpj;
     @XStreamAlias("xNome")
     private String razaoSocial;
     @XStreamAlias("enderEmit")
     @Embedded
     private EnderFornecedor enderEmit;
     @XStreamAlias("IE")
     private Long inscricaoEstadual;


}
