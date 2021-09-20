package br.com.petshop.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

import br.com.petshop.entidade.Dono;


public class DonoDaoImpl extends BaseDaoImpl<Dono, Long> implements DonoDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
    public Dono pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Dono) sessao.get(Dono.class, id);
    }

    @Override
    public Dono pesquisarPorCpfDono(String cpf, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Dono where cpf = :cpf");
        consulta.setParameter("cpf", cpf);
        return (Dono) consulta.uniqueResult();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Dono> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Dono where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

}
