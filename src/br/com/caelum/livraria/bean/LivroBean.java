package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Transacional;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LivroDao livroDao;
	
	@Inject 
	private AutorDao autorDao;
	
	private Livro livro = new Livro();

	private Integer autorId;

	private Integer livroId;

	private List<Livro> listaTodos;

	private List<Livro> livros;	
	
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação", "Ficção");
	
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}
	
	//Método de busca por números
	public boolean ordenarPrecos(Object valorColuna, Object filtroDigitado, Locale locale){
		
		//Tirando Espaços dos Filtros
		 String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();
		
		System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);
		
		//O Filtro é nulo ou vazio
		if (textoDigitado == null || textoDigitado.equals("")){
			return true;
		}
		
		//Elemento da coluna é nulo
		if (valorColuna == null) {
			return true;
		}
		
		 try {
	            // fazendo o parsing do filtro para converter para Double
	            Double precoDigitado = Double.valueOf(textoDigitado);
	            Double precoColuna = (Double) valorColuna;

	            // comparando os valores, compareTo devolve um valor negativo se o value é menor do que o filtro
	            return precoColuna.compareTo(precoDigitado) < 0;

	        } catch (NumberFormatException e) {

	            // usuario nao digitou um numero
	            return false;
	        }
		
	}

	public List<Livro> getLivros() {
		if(this.livros == null){
		this.livros = this.livroDao.listaTodos();
		}
		return livros;
	}

	public List<Autor> getAutores() {
		return this.autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
	
	public void gravarAutor() {
		Autor autor = this.autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}
	
	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if (this.livro.getId() == null) {
			this.livroDao.adiciona(this.livro);
			this.livros = this.livroDao.listaTodos();
		} else {
			this.livroDao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	public void carregar(Livro livro) {
		System.out.println("Carregando livro " + livro.getTitulo());
		this.livro = livro;
	}
	
	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo livro " + livro.getTitulo());
		this.livroDao.remove(livro);
		this.livros = this.livroDao.listaTodos();
	}

	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	public String formAutor() {
		System.out.println("Chamanda do formulário do Autor.");
		return "autor?faces-redirect=true";
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component,
			Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage(
					"ISBN deveria começar com 1"));
		}

	}

	public void carregarLivroPelaId() {
		this.livro = this.livroDao.buscaPorId(livroId);
	}

	public List<String> getGeneros() {
		return generos;
	}
	
}
