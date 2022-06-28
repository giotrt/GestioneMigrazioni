package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{
	
	private int time;
	private Country nazione;
	private int persone;
	
	
	public Evento(int time, Country nazione, int persone) {
		super();
		this.time = time;
		this.nazione = nazione;
		this.persone = persone;
	}

	public int getTime() {
		return time;
	}

	public Country getNazione() {
		return nazione;
	}

	public int getPersone() {
		return persone;
	}

	@Override
	public int compareTo(Evento other) {
		return this.time - other.time;
	}

	@Override
	public String toString() {
		return "Evento [time=" + time + ", nazione=" + nazione + ", persone=" + persone + "]";
	}
	

}
