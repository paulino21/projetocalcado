package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.converter.ConverterData;
import br.com.projetocalcado.converter.ConverterInt;
import br.com.projetocalcado.domain.xmlnota.*;
import br.com.projetocalcado.domain.fornecedor.Fornecedor;
import br.com.projetocalcado.domain.fornecedor.FornecedorRepository;
import br.com.projetocalcado.domain.produto.Produto;
import br.com.projetocalcado.domain.produto.ProdutoRepository;
import br.com.projetocalcado.utilConfig.XstreamConfig;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
@Service
public class NotaService {

    private Fornecedor fornecedor;
    private Duplicata duplicata;
    private Produto produto;
    private ItensNota itensNota;
    @Autowired
    NotaFiscalRepository notaFiscalRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    FornecedorRepository fornecedorRepository;
    @Autowired
    private XstreamConfig xStream;

    public DadosDetalheNotaFiscal cadastraNota(DadosNotaFiscal dadosNota) {

        var fornecedor = fornecedorRepository.getReferenceById(dadosNota.idFornecedor());
        var produto = produtoRepository.getReferenceById(dadosNota.idProduto());
        var nota = new NotaFiscal(dadosNota.numeroNF(), dadosNota.dataEmissao(), fornecedor);
        var itemPedido = new ItensNota(dadosNota.quantidade(), nota, produto);

        for (Duplicata dup : dadosNota.duplicatas()) {
            duplicata = new Duplicata(dup.getNumParcelaDup(), dup.getDataVenc(), dup.getValorDup(), nota);
            nota.adicionaPagamento(duplicata);
        }
        nota.adiconarItem(itemPedido);
        notaFiscalRepository.save(nota);
        return new DadosDetalheNotaFiscal(nota);
    }

    public InfNFe devolveDadosXml(String caminho) {

        File file = new File("c:\\arq\\" + caminho);
        xStream.xStream().registerConverter(new ConverterData());
        xStream.xStream().registerConverter(new ConverterInt());
        xStream.xStream().ignoreUnknownElements();
        xStream.xStream().autodetectAnnotations(true);
        xStream.xStream().addPermission(AnyTypePermission.ANY);
        xStream.xStream().processAnnotations(NfeProc.class);
        NfeProc nfeProc = (NfeProc) xStream.xStream().fromXML(file);
        var infXml = nfeProc.getNfe().getInfNFe();

        return infXml;
    }

    public DadosDetalheNotaFiscal salvaNotaXml(InfNFe infXml) {



        if (!fornecedorRepository.existsByCnpj(infXml.getFornecedor().getCnpj())) {
            fornecedor = fornecedorRepository.save(infXml.getFornecedor());
        }
        else{
            fornecedor = fornecedorRepository.findByCnpj(infXml.getFornecedor().getCnpj());
        }
        var nota = new NotaFiscal(infXml.getIdentifNota().getNumeroNF(), infXml.getIdentifNota().getDataEmiss(), fornecedor);

        for(Duplicata dup: infXml.getCobranca().getDuplicatas() ){
              duplicata = new Duplicata(dup.getNumParcelaDup(), dup.getDataVenc(), dup.getValorDup(), nota);
              nota.adicionaPagamento(duplicata);
        }
           for(DetItens detItens : infXml.getDetList()){

               for (ProdDetalheNota prodD : detItens.getProdutos()){

                   produto = new Produto( prodD.getCodProd(), prodD.getCodEan(), prodD.getNomeProd(), prodD.getValorUnit());

                   if(!produtoRepository.existsByCodEan(produto.getCodEan())){
                       produtoRepository.save(produto);
                       itensNota = new ItensNota(prodD.getQuantidade(),nota, produto);
                       nota.adiconarItem(itensNota);
                   }
                   else {
                       produto = produtoRepository.findByCodEan(produto.getCodEan());
                       itensNota = new ItensNota(prodD.getQuantidade(),nota, produto);
                       nota.adiconarItem(itensNota);
                   }

               }

           }
            notaFiscalRepository.save(nota);
            return new DadosDetalheNotaFiscal(nota);
    }
}