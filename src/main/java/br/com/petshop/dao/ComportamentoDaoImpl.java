package br.com.petshop.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.petshop.entidade.Comportamento;


public class ComportamentoDaoImpl implements ComportamentoDao, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
    public List<Comportamento> pesquisarTodos(Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Comportamento");
        return consulta.list();
    }

}
