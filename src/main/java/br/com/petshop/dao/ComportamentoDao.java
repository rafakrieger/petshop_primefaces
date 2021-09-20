package br.com.petshop.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.petshop.entidade.Comportamento;

public interface ComportamentoDao {

    List<Comportamento> pesquisarTodos(Session sessao)throws  HibernateException;
}
