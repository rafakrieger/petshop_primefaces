package br.com.petshop.dao;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import br.com.petshop.entidade.*;


public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Configuration cfg = new Configuration();

            cfg.addAnnotatedClass(Endereco.class);    
            cfg.addAnnotatedClass(Comportamento.class);
            cfg.addAnnotatedClass(Dono.class);
            cfg.addAnnotatedClass(Animal.class);
            cfg.addAnnotatedClass(Gato.class);
            cfg.addAnnotatedClass(Cachorro.class);
            cfg.addAnnotatedClass(Consulta.class);

            cfg.configure("/META-INF/hibernate.cfg.xml");
            StandardServiceRegistryBuilder build = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
            sessionFactory = cfg.buildSessionFactory(build.build());
        } catch (HibernateException ex) {
            System.err.println("Erro ao criar Hibernate util." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session abrirSessao() {
        return sessionFactory.openSession();
    }
}
