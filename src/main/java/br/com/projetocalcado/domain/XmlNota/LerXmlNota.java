package br.com.projetocalcado.domain.XmlNota;
import br.com.projetocalcado.utilConfig.XstreamConfig;
import com.thoughtworks.xstream.security.AnyTypePermission;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
@Setter
@Service
public class LerXmlNota {

    @Autowired
    private NfeProc nfeProc;
    @Autowired
    private XstreamConfig xStream;

    private String caminho;
    public NfeProc devolveDadosXml() {

        File file = new File("c:\\arq\\"+caminho);
        xStream.xStream().ignoreUnknownElements();
        xStream.xStream().autodetectAnnotations(true);
        xStream.xStream().addPermission(AnyTypePermission.ANY);
        xStream.xStream().processAnnotations(NfeProc.class);
        nfeProc = (NfeProc) xStream.xStream().fromXML(file);

        return nfeProc;

    }


}
