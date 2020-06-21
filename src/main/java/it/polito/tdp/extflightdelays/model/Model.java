package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	private ExtFlightDelaysDAO dao= new ExtFlightDelaysDAO();
	private Graph<String,DefaultWeightedEdge> graph;
	
	
	public void creaGrafo() {
		List<String> stati=dao.listState();
		this.graph=new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, stati);
		List<Adiacenza> adiacenze=dao.getAdiacenze();
		for(Adiacenza a: adiacenze) {
			if(this.graph.containsVertex(a.getA1()) && this.graph.containsVertex(a.getA2())
					&& !this.graph.containsEdge(a.getA1(), a.getA2())) {
				Graphs.addEdgeWithVertices(this.graph, a.getA1(), a.getA2(), a.getPeso());
			}
		}
	}
	
	public List<String>	getStati(){
		return dao.listState();
	}
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}

	public List<Adiacenza> visualizzaVelivoli(String stato) {
		List<DefaultWeightedEdge> uscenti=new ArrayList<>(this.graph.outgoingEdgesOf(stato));
		List<Adiacenza> adiacenze=new ArrayList<>();
		for(DefaultWeightedEdge e:uscenti) {
			adiacenze.add(new Adiacenza(this.graph.getEdgeSource(e),this.graph.getEdgeTarget(e),
					this.graph.getEdgeWeight(e)));
		}
		Collections.sort(adiacenze);
		return adiacenze;
	}

	public List<StatoTuristi> simula(Integer turisti, Integer giorni,String statoSelezionato) {
		Simulator sim= new Simulator();
		sim.init(turisti,giorni, statoSelezionato,this.graph);
		sim.run();
		return sim.getStatoTuristi();
	}
}
