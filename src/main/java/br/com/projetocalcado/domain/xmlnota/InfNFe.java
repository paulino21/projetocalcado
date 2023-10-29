package br.com.projetocalcado.domain.xmlnota;

import br.com.projetocalcado.domain.fornecedor.Fornecedor;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Getter
@XStreamAlias("InfNFe")
public class InfNFe {

   @XStreamAlias("ide")
   private IdentifNota identifNota;
   @XStreamAlias("emit")
   private Fornecedor fornecedor;
   @XStreamImplicit
   private List <DetItens> detList;
   @XStreamAlias("cobr")
   private Cobranca cobranca;


}
