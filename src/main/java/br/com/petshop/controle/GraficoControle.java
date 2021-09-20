package br.com.petshop.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.hibernate.Session;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import br.com.petshop.dao.CachorroDao;
import br.com.petshop.dao.CachorroDaoImpl;
import br.com.petshop.dao.GatoDao;
import br.com.petshop.dao.GatoDaoImpl;
import br.com.petshop.dao.HibernateUtil;

@ManagedBean(name="graficoC")
public class GraficoControle {
	
    private PieChartModel pieModel;
    private CachorroDao cachorroDao;
    private GatoDao gatoDao;
	private Session sessao;

	
	@PostConstruct
    public void init() {
        createPieModel();
	}

    private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();
        
        cachorroDao = new CachorroDaoImpl();
        gatoDao = new GatoDaoImpl();
		sessao = HibernateUtil.abrirSessao();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(cachorroDao.pesquisarTodos(sessao).size());
        values.add(gatoDao.pesquisarTodos(sessao).size());
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Cachorro");
        labels.add("Gato");
        data.setLabels(labels);

        pieModel.setData(data);
    }

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}


}
