package br.com.petshop.dao;

import java.util.List;
import org.hibernate.*;

import br.com.petshop.entidade.Gato;

public interface GatoDao extends BaseDao<Gato, Long>{
    
    List<Gato> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
    List<Gato> pesquisarTodos(Session sessao) throws HibernateException;

}
