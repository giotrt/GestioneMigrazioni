package it.polito.tdp.borders.model;

import java.util.ArrayList;

public class TestSimulatore {
	
	public static void main(String[] args) {
		Model model = new Model();
		model.creaGrafo(2000);
		model.simula(new ArrayList<Country>(model.getCountries()).get(0));
	}

}
