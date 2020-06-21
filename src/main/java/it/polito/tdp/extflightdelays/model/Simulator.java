package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {

	private List<StatoTuristi> statoTuristi;//mappa che contiene tutti gli stati con il relativo numero di turisti presenti
	private Integer turistiTot;//numero totale di turisti facenti parte della simulazione
	private Integer giorniSim;//numero totale di giorni di simulazione
	private Queue<Event> queue;
	private Graph<String,DefaultWeightedEdge> graph;
	
	public void init(Integer turisti, Integer giorni,String statoSelezionato,Graph<String,DefaultWeightedEdge> graph) {
		this.statoTuristi= new ArrayList<>();
		this.queue=new PriorityQueue<>();
		this.graph=graph;
		this.turistiTot=turisti;
		this.giorniSim=giorni;
		for(String stato:this.graph.vertexSet()) {
			this.statoTuristi.add(new StatoTuristi(stato,0));//inizializzo la mappa con zero turisti in ogni stato
		}
		for(StatoTuristi s:this.statoTuristi) {
			if(s.getStato().equals(statoSelezionato)) {
				s.setTuristi(this.turistiTot);
			}
		}//posiziono tutti i turisti nello stato selezionato
		int i=0;
		while(i<this.giorniSim) {
			this.queue.add(new Event(i+1));//aggiungo un evento per ogni giorno della simulazione
			i++;
		}
	}

	public void run() {
		while(!this.queue.isEmpty()) {
			Event e=this.queue.poll();
			processEvent(/*e*/);
		}
	}

	private void processEvent(/*Event e*/) {
		for(StatoTuristi s:this.statoTuristi) {
			for(int i=0;i<s.getTuristi();i++) {
				//scelgo un volo in modo casuale
				Double random;
				Double pesoTot=calcolaPeso(this.graph.outgoingEdgesOf(s.getStato()));
				for(DefaultWeightedEdge e:this.graph.outgoingEdgesOf(s.getStato())) {
					random=Math.random();
					Double pesoArco=this.graph.getEdgeWeight(e);
					if(random<=(pesoArco/pesoTot)) {
						//tolgo un passeggero dal vecchio stato e lo aggiungo nel nuovo
						for(StatoTuristi stato:this.statoTuristi) {
							if(this.graph.getEdgeSource(e).equals(stato.getStato()) && stato.getTuristi()>0) {
								stato.setTuristi(stato.getTuristi()-1);
							}
							if(this.graph.getEdgeTarget(e).equals(stato.getStato())) {
								stato.setTuristi(stato.getTuristi()+1);
							}
						}
					}
				}
				
			}
		}
	}

	private Double calcolaPeso(Set<DefaultWeightedEdge> outgoingEdgesOf) {
		Double pesoTot=0.0;
		for(DefaultWeightedEdge e:outgoingEdgesOf) {
			pesoTot+=this.graph.getEdgeWeight(e);
		}
		return pesoTot;
	}

	public List<StatoTuristi> getStatoTuristi(){
		return this.statoTuristi;
	}
}
