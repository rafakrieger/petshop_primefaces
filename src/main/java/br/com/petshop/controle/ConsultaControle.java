package br.com.petshop.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import br.com.petshop.dao.AnimalDao;
import br.com.petshop.dao.AnimalDaoImpl;
import br.com.petshop.dao.ConsultaDao;
import br.com.petshop.dao.ConsultaDaoImpl;
import br.com.petshop.dao.DonoDao;
import br.com.petshop.dao.DonoDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.entidade.Animal;
import br.com.petshop.entidade.Consulta;
import br.com.petshop.entidade.Dono;


@ManagedBean(name = "consultaC")
@ViewScoped
public class ConsultaControle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Consulta consulta;
	private ConsultaDao consultaDao;
	private Dono dono;
	private DonoDao donoDao;
	private Session sessao;
	private List<Consulta> consultas;
	private DataModel<Consulta> modelConsultas;
	private int aba;
	private Animal animal; 
	private List<SelectItem> comboAnimais;
	private boolean dataFutura;

	public ConsultaControle() {
		consultaDao = new ConsultaDaoImpl();
	}

	public void pesquisarPorData() {
		sessao = HibernateUtil.abrirSessao();
		try {
			consultas = consultaDao.pesquisarPorData(consulta.getDia(), sessao);
			modelConsultas = new ListDataModel<>(consultas);
			
			Calendar calendar = Calendar.getInstance();
			Date data = calendar.getTime();
			if (consulta.getDia().after(data)) {
				dataFutura = true;
			} else {
				dataFutura = false;
			}
			consulta.setDia(null);
		} catch (HibernateException e) {
			System.out.println("Erro ao pesquisar: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}

	public void salvar() {
		sessao = HibernateUtil.abrirSessao();
		try {
			consulta.setAnimal(animal);			
			consultaDao.salvarOuAlterar(consulta, sessao);
			consulta = null;
			animal = null;
			dono = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", null));
			modelConsultas = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", null));
		} finally {
			sessao.close();
		}
	}

	private void buscarDono() {
		donoDao = new DonoDaoImpl();
		sessao = HibernateUtil.abrirSessao();
		try {			
			dono = donoDao.pesquisarPorCpfDono(dono.getCpf(), sessao);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dono não encontrado", null));
		} finally {
			sessao.close();
		}	 	
	}

	public void loadAnimais() {
		this.buscarDono();		
		AnimalDao animalDao = new AnimalDaoImpl();
		sessao = HibernateUtil.abrirSessao();
		try {
			List<Animal> animais = animalDao.pesquisarPorIdDono(dono.getId(), sessao);
			comboAnimais = new ArrayList<>();
			for (Animal animal : animais) {
				comboAnimais.add(new SelectItem(animal.getId(), animal.getNome()));
			}
		} catch (HibernateException e) {
			System.out.println("Erro ao carregar combo box: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}
	
	public void excluir() {
		consulta = modelConsultas.getRowData();
		sessao = HibernateUtil.abrirSessao();
		try {
			consultaDao.excluir(consulta, sessao);			
			consulta = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", null));
			modelConsultas = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", null));
		} finally {
			sessao.close();
		}
	}
	
	public void prepAlterar() {
		consulta = modelConsultas.getRowData();
		dono = consulta.getAnimal().getDono();
		animal = consulta.getAnimal();
		aba = 1;
	}

	@SuppressWarnings("rawtypes")
	public void onTabChange(TabChangeEvent event) {
		if (event.getTab().getTitle().equals("Novo")) {
			loadAnimais();
		}
	}

	@SuppressWarnings("rawtypes")
	public void onTabClose(TabCloseEvent event) {

	}


	// GET SET
	

	public Consulta getConsulta() {
		if (consulta == null) {
			consulta = new Consulta();
		}
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Dono getDono() {
		if (dono == null) {
			dono = new Dono();
		}
		return dono;
	}
	
	public void setDono(Dono dono) {
		this.dono = dono;
	}

	public int getAba() {
		return aba;
	}

	public DataModel<Consulta> getModelConsultas() {
		return modelConsultas;
	}

	public Animal getAnimal() {
		if (animal == null) {
			animal = new Animal();
		}
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public List<SelectItem> getComboAnimais() {
		return comboAnimais;
	}

	public boolean isDataFutura() {
		return dataFutura;
	}

	public void setDataFutura(boolean dataFutura) {
		this.dataFutura = dataFutura;
	}
	
	
}
