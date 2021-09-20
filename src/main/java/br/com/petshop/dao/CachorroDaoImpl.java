package br.com.petshop.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

import br.com.petshop.entidade.Cachorro;

public class CachorroDaoImpl extends BaseDaoImpl<Cachorro, Long> implements CachorroDao, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
    public Cachorro pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Cachorro) sessao.get(Cachorro.class, id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Cachorro> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Cachorro where nome = :nome");
        consulta.setParameter("nome", nome);
        return consulta.list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Cachorro> pesquisarTodos(Session sessao) throws HibernateException {
		return sessao.createQuery("from Cachorro").list();
	}
    
}
