package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class GerenciadorDeTransacao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager manager;
	
	
	@AroundInvoke
	public Object executaTX(InvocationContext contexto) throws Exception{
		
		System.out.println("Incianto tx...");
		// abre transacao
		manager.getTransaction().begin();
		
		Object resultado = contexto.proceed();
		
		System.out.println("Salvando tx...");
		// commita a transacao
		manager.getTransaction().commit();
		
		return resultado;
	}

}
