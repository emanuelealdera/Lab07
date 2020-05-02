package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		
		PowerOutageDAO podao = new PowerOutageDAO();
		List <Nerc> nerc = podao.getNercList();
		
		System.out.println(podao.getPowerOutagesForNerc(nerc.get(2)));

	}

}
