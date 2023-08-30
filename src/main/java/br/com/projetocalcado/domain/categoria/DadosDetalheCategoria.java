package br.com.projetocalcado.domain.categoria;

public record DadosDetalheCategoria(Long id, String nome) {


    public DadosDetalheCategoria(Categoria categoria){

        this(categoria.getId(), categoria.getNome());
    }

}
