package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;

import java.util.List;
@Getter
@XStreamAlias("InfNFe")
public class InfNFe {

   @XStreamAlias("ide")
   private IdentifNota identifNota;
   @XStreamAlias("emit")
   private Fornecedor fornecedor;
   @XStreamImplicit
   private List<DetItens> detList;
   @XStreamAlias("cobr")
   private Cobranca cobranca;
}
