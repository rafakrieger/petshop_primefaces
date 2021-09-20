package br.com.petshop.controle;

import java.io.Serializable;
import java.util.ArrayList;
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

import br.com.petshop.dao.CachorroDao;
import br.com.petshop.dao.CachorroDaoImpl;
import br.com.petshop.dao.ComportamentoDao;
import br.com.petshop.dao.ComportamentoDaoImpl;
import br.com.petshop.dao.DonoDao;
import br.com.petshop.dao.DonoDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.entidade.Cachorro;
import br.com.petshop.entidade.Comportamento;
import br.com.petshop.entidade.Dono;


@ManagedBean(name = "cachorroC")
@ViewScoped
public class CachorroControle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Cachorro cachorro;
	private CachorroDao cachorroDao;
	private Dono dono;
	private DonoDao donoDao;
	private Session sessao;
	private List<Cachorro> cachorros;
	private DataModel<Cachorro> modelCachorros;
	private int aba;
	private Comportamento comportamento; 
	private List<SelectItem> comboComportamentos;

	public CachorroControle() {
		cachorroDao = new CachorroDaoImpl();
	}

	public void pesquisarPorNome() {
		sessao = HibernateUtil.abrirSessao();
		try {
			cachorros = cachorroDao.pesquisarPorNome(cachorro.getNome(), sessao);
			modelCachorros = new ListDataModel<>(cachorros);
			cachorro.setNome(null);
		} catch (HibernateException e) {
			System.out.println("Erro ao pesquisar: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}

	public void salvar() {
		sessao = HibernateUtil.abrirSessao();
		try {
			cachorro.setDono(dono);
			cachorro.setComportamento(comportamento);
			cachorroDao.salvarOuAlterar(cachorro, sessao);
			cachorro = null;
			dono = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", null));
			modelCachorros = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", null));
		} finally {
			sessao.close();
		}
	}

	public void buscarDono() {		
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
	
	public void loadComportamento() {
		sessao = HibernateUtil.abrirSessao();
		ComportamentoDao comportamentoDao = new ComportamentoDaoImpl();
		try {
			List<Comportamento> comportamentos = comportamentoDao.pesquisarTodos(sessao);
			comboComportamentos = new ArrayList<>();
			for (Comportamento comp : comportamentos) {
				comboComportamentos.add(new SelectItem(comp.getId(), comp.getTipo()));
			}
		} catch (HibernateException e) {
			System.out.println("Erro ao carregar combo box: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}

	public void prepAlterar() {
		cachorro = modelCachorros.getRowData();
		dono = cachorro.getDono();
		comportamento = cachorro.getComportamento();
		aba = 1;
	}

	@SuppressWarnings("rawtypes")
	public void onTabChange(TabChangeEvent event) {
		if (event.getTab().getTitle().equals("Novo")) {
			loadComportamento();
		}
	}

	@SuppressWarnings("rawtypes")
	public void onTabClose(TabCloseEvent event) {

	}


	// GET SET
	

	public Cachorro getCachorro() {
		if (cachorro == null) {
			cachorro = new Cachorro();
		}
		return cachorro;
	}

	public void setCachorro(Cachorro cachorro) {
		this.cachorro = cachorro;
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

	public DataModel<Cachorro> getModelCachorros() {
		return modelCachorros;
	}

	public Comportamento getComportamento() {
		if (comportamento == null) {
			comportamento = new Comportamento();
		}
		return comportamento;
	}

	public void setComportamento(Comportamento comportamento) {
		this.comportamento = comportamento;
	}

	public List<SelectItem> getComboComportamentos() {
		return comboComportamentos;
	}
	
	

}
