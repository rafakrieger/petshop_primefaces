package br.com.petshop.entidade;

import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "cachorro")
@PrimaryKeyJoinColumn(name = "id_animal")
public class Cachorro extends Animal {


	private static final long serialVersionUID = 1L;
	private boolean treinado;

    public Cachorro() {
    }

    public Cachorro(String nome, String sexo, String observacao, Date nascimento, double peso,
            boolean treinado) {
        super(nome, sexo, observacao, nascimento, peso);
        this.treinado = treinado;
    }

    public boolean isTreinado() {
        return treinado;
    }

    public void setTreinado(boolean treinado) {
        this.treinado = treinado;
    }

}
