package beandto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BeanDtoGraficoQtdColabor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<String> perfils = new ArrayList<String>();
	List<Integer> qtdColabors = new ArrayList<Integer>(); 

	public List<String> getPerfils() {
		return perfils;
	}

	public void setPerfils(List<String> perfils) {
		this.perfils = perfils;
	}

	 public List<Integer> getQtdColabors() {
		return qtdColabors;
	}
	 
	 public void setQtdColabors(List<Integer> qtdColabors) {
		this.qtdColabors = qtdColabors;
	}

	 

}
