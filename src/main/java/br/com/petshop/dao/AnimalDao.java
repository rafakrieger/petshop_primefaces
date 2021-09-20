package br.com.petshop.dao;

import java.util.List;
import org.hibernate.*;

import br.com.petshop.entidade.Animal;

public interface AnimalDao {

    List<Animal> pesquisarPorIdDono(Long id, Session sessao) throws HibernateException;
}
