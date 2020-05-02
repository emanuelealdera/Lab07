package it.polito.tdp.poweroutages.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List <PowerOutage> result;
	private int massimo;
	private int maxAnni;
	private int maxOre;
	private List <PowerOutage> all;
	private int totaleOre;
	

	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	
	public String worstCaseScenario (String nerc, int maxAnni, int maxOre) {
		this.result = new ArrayList <> (); 
		massimo = 0;
		
		Nerc temp = null;
		for (Nerc n : this.getNercList()) {
			if (n.getValue().equals(nerc))
				temp=n;
		}
		if (temp == null) {
			return "Errore";
		}
		
		String str = "";
		this.maxAnni=maxAnni;
		this.totaleOre = 0;
		this.maxOre=maxOre;
		this.all = podao.getPowerOutagesForNerc(temp);
		List <PowerOutage> parziale = new ArrayList <> ();
		cerca(parziale, 0, 0);
		str+= "massimo: "+this.massimo+"\n"+"totaleOre: "+this.totaleOre+"\n";
		for (PowerOutage p : result) {
			str+= p.getStart().getYear() +" "+p.getEventType()+" "+p.getStart().toString()+" "+p.getFinish()+" "+p.getCustomersAffected()+"\n";
		}
		
		return str;
	}
	
	
	
	private void cerca (List <PowerOutage> parziale, int totaleOre, int totaleClienti) {
		
		//caso terminale
		if  (isTerminal(parziale, totaleOre)) {
			if (totaleClienti > this.massimo) {
				this.result = new ArrayList <> (parziale);
				this.massimo = totaleClienti;
				this.totaleOre = totaleOre;
				return;
			}
		}
		//creazione sottoproblemi
		for (PowerOutage po : this.all) {
			if (!parziale.contains(po)) {
				//controllo su differenza anni e massimo ore
				if ((getMassimo(parziale, po).getYear() - getMinimo(parziale, po).getYear()) <= this.maxAnni  && 
						(po.getDurata() + totaleOre) <= this.maxOre ) {
					parziale.add(po);
					cerca (parziale, totaleOre+(int)po.getDurata(), totaleClienti + po.getCustomersAffected());
					parziale.remove(parziale.size()-1);
				}
				else if ((getMassimo(parziale, po).getYear() - getMinimo(parziale, po).getYear()) > this.maxAnni)
					return;
			}
		}
	}
		
		
		
	
	private LocalDate getMinimo (List <PowerOutage> list, PowerOutage p) {
		LocalDate min = p.getStart().toLocalDate();
		for (PowerOutage po : list) {
			if (po.getStart().toLocalDate().isBefore(min))
				min = po.getStart().toLocalDate();
		}
		return min;
	}
	
	
	private LocalDate getMassimo (List <PowerOutage> list, PowerOutage p) {
		LocalDate max = p.getFinish().toLocalDate();
		for (PowerOutage po : list) {
			if (po.getStart().toLocalDate().isAfter(max))
				max = po.getStart().toLocalDate();
		}
		return max;
	}
	
	private boolean isTerminal (List <PowerOutage> parziale, int totaleOre) {
		for (PowerOutage po : this.all) {
			if (!parziale.contains(po) && (getMassimo(parziale, po).getYear() - getMinimo(parziale, po).getYear()) 
					<= this.maxAnni && (po.getDurata() + totaleOre) <= this.maxOre) 
				return false;
			
		}
		return true;
	}
	

}
