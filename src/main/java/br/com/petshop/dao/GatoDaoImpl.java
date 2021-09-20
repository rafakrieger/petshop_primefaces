package br.com.petshop.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

import br.com.petshop.entidade.Gato;

public class GatoDaoImpl extends BaseDaoImpl<Gato, Long> implements GatoDao, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
    public Gato pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Gato) sessao.get(Gato.class, id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Gato> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Gato where nome = :nome");
        consulta.setParameter("nome", nome);
        return consulta.list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Gato> pesquisarTodos(Session sessao) throws HibernateException {
		return sessao.createQuery("from Gato").list();
	}
    
}
