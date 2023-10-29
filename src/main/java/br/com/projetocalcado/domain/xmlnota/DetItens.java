package br.com.projetocalcado.domain.xmlnota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Getter
@Setter
@XStreamAlias("det")
public class DetItens {

   @XStreamImplicit
   private List<ProdDetalheNota> produtos;

}
