package br.com.caelum.livraria.modelo;

public class Venda {
	
	private Livro livros;	
	private Integer quantidade;
	
	public Venda(Livro livro, Integer quantidade) {
		this.livros = livro;
		this.quantidade = quantidade;
	}
	public Livro getLivros() {
		return livros;
	}
	public void setLivros(Livro livros) {
		this.livros = livros;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
