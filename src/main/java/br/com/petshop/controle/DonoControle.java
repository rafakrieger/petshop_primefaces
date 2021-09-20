package br.com.petshop.controle;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.petshop.dao.DonoDao;
import br.com.petshop.dao.DonoDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.entidade.Animal;
import br.com.petshop.entidade.Dono;
import br.com.petshop.entidade.Endereco;
import br.com.petshop.webservice.WebServiceEndereco;

@ManagedBean(name = "donoC")
@ViewScoped
public class DonoControle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Dono dono;
	private List<Animal> animais;
	private DonoDao donoDao;
	private Session sessao;
	private List<Dono> donos;
	private DataModel<Dono> modelDonos;
	private Endereco endereco;
	private int aba;

	public DonoControle() {
		donoDao = new DonoDaoImpl();
	}

	public void pesquisarPorNome() {
		sessao = HibernateUtil.abrirSessao();
		try {
			donos = donoDao.pesquisarPorNome(dono.getNome(), sessao);
			modelDonos = new ListDataModel<>(donos);
			dono.setNome(null);
		} catch (HibernateException e) {
			System.out.println("Erro ao pesquisar: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}

	public void salvar() {
		sessao = HibernateUtil.abrirSessao();
		try {
			endereco.setDono(dono);
			dono.setEndereco(endereco);
			donoDao.salvarOuAlterar(dono, sessao);
			endereco = null;
			dono = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", null));
			modelDonos = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", null));
		} finally {
			sessao.close();
		}
	}

	public void excluir() {
		dono = modelDonos.getRowData();
		sessao = HibernateUtil.abrirSessao();
		try {
			donoDao.excluir(dono, sessao);			
			dono = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", null));
			modelDonos = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", null));
		} finally {
			sessao.close();
		}
	}

	public void buscarCep() {
		WebServiceEndereco webService = new WebServiceEndereco();
		endereco = webService.pesquisarCep(endereco.getCep());
		if (endereco.getCep() == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "CEP não encontrado", null));
		}
	}

	public void prepAlterar() {
		dono = modelDonos.getRowData();
		endereco = dono.getEndereco();
		aba = 1;
	}


	// GET SET

	public Dono getDono() {
		if (dono == null) {
			dono = new Dono();
		}
		return dono;
	}

	public void setDono(Dono dono) {
		this.dono = dono;
	}

	public Endereco getEndereco() {
		if (endereco == null) {
			endereco = new Endereco();
		}
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public int getAba() {
		return aba;
	}

	public DataModel<Dono> getModelDonos() {
		return modelDonos;
	}

	public List<Animal> getAnimais() {
		return animais;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}
}
