package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import br.com.caelum.livraria.modelo.Livro;

public class LivroDao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager manager;
	
	private DAO<Livro> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Livro>(this.manager, Livro.class);
	}

	public void adiciona(Livro livro) {
		this.dao.adiciona(livro);
	}

	public void remove(Livro livro) {
		this.dao.remove(livro);
	}

	public void atualiza(Livro livro) {
		this.dao.atualiza(livro);
	}

	public List<Livro> listaTodos() {
		return this.dao.listaTodos();
	}

	public Livro buscaPorId(Integer id) {
		return this.dao.buscaPorId(id);
	}

	public int contaTodos() {
		return this.dao.contaTodos();
	}

	public int quantidadeDeElementos() {
		return this.dao.quantidadeDeElementos();
	}
	
}
