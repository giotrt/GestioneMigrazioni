package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
//	Quando parliamo di simualzione ci sono SEMPRE 4 tipi di dati coinvolti:
	
//	Coda degli eventi
	private PriorityQueue<Evento> queue;
	
//	Parametri della simulazione ( quali sono le cose che gli servono per iniziare )
	private int nInizialeMigranti;
	private Country nazioneIniziale;
	
//	Output della simulazione
	private int nPassi; // T
	private Map<Country, Integer> persone; // Per ogni nazione, quanti migranti si sono fermati (sono stanziali in quella nazione) alla fine della simulazione
//	Oppure List<CountryAndNumber> personeStanziali;
	
	
//	STATO DEL MONDO SIMULATO
	private Graph<Country, DefaultEdge> grafo;
//	Cosa caratterizza lo stato del mondo in un certo istante di tempo? Quante persone sono in ciascuno stato: Map persone Country --> Integer

	
//	---------------------METODI---------------------
	
//	Costruttore, caricamento di eventi inziali, esecuzione della simulazione, elaboratore dell'evento ed estrazione
	
//					---COSTRUTTORE---
	public Simulatore(Graph<Country, DefaultEdge> grafo) {
		super();
		this.grafo = grafo;
	}
	
//			---METODO CHE PREPARA LA SIMULAZIONE---
	public void inizializza(Country partenza, int migranti) {
		this.nazioneIniziale = partenza;
		this.nInizialeMigranti = migranti;
//		inizializzo anche la struttura dati che tiene traccia della simulazione (map)
//		questo fa si che ogni volta che faccio una nuoba simulazioni non mi porto
//		dietro i residui della simualzione precedente
		this.persone = new HashMap<Country, Integer>();
//		e poi la riempio
		for(Country c : this.grafo.vertexSet()) {
			this.getPersone().put(c, 0);
		}
		this.queue = new PriorityQueue<>();
//		Poi inietto il primo evento nella coda degli eventi (tempo 1, nazione iniziale e # migranti iniziali)
		this.queue.add(new Evento(1, this.nazioneIniziale, this.nInizialeMigranti));
	}
	
//			---METODO DI ESECUZIONE DELLA SIMULAZIONE---
	public void run() {
		while(!this.queue.isEmpty()) { //finchè la coda non è vuota
			Evento e = this.queue.poll(); //estrai il primo evento ed eleboralo
//			System.out.println(e.toString());
			processEvent(e); //metodo che elabaro l'evento, aggiorna lo stato del mondo e crea nuovi eventi
		}
	}

	private void processEvent(Evento e) {
		
		int stanziali = e.getPersone()/2;
		int migranti = e.getPersone() - stanziali;
//		quanti stati confinanti ho? me lo dice il grafo
		int confinanti = this.grafo.degreeOf(e.getNazione());
		int gruppiMigranti = migranti / confinanti;
		stanziali += migranti % confinanti;
		
//		Aggiorno lo stato del mondo
		
		this.getPersone().put(e.getNazione(), this.getPersone().get(e.getNazione())+stanziali);
		
		this.nPassi = e.getTime();
		
//		Genero nuovi eventi
		if(gruppiMigranti != 0) {
			for(Country vicino : Graphs.neighborListOf(this.grafo, e.getNazione())) {
				this.queue.add(new Evento(e.getTime()+1, vicino, gruppiMigranti));
			}
		}
		
	}

	public int getnPassi() {
		return nPassi;
	}

	public Map<Country, Integer> getPersone() {
		return persone;
	}
}
