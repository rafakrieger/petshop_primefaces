package br.com.petshop.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.petshop.entidade.Consulta;

public class ConsultaDaoImpl extends BaseDaoImpl<Consulta, Long> implements ConsultaDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
    public Consulta pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Consulta) sessao.get(Consulta.class, id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Consulta> pesquisarPorData(Date dia, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Consulta where Date(dia) = :dia");
        consulta.setParameter("dia", dia);
        return consulta.list();
    }

}
