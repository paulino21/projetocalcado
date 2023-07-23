package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;

import java.util.List;

@Getter
@XStreamAlias("det")
public class DetItens {

   @XStreamImplicit
   private List <ProdDetalheNota> produtos;

}
