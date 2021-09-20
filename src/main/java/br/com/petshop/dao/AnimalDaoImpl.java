package br.com.petshop.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

import br.com.petshop.entidade.Animal;

public class AnimalDaoImpl implements AnimalDao, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Animal> pesquisarPorIdDono(Long id, Session sessao) throws HibernateException {
		Query consulta = sessao.createQuery("from Animal where id_dono = :id");
		consulta.setParameter("id", id);
		return consulta.list();
	}

}
