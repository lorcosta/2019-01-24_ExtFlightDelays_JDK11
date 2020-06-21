package it.polito.tdp.extflightdelays.model;

public class Event implements Comparable<Event>{
	private Integer giorno;

	/**
	 * @param giorno
	 */
	public Event(Integer giorno) {
		super();
		this.giorno = giorno;
	}

	@Override
	public int compareTo(Event other) {
		return this.giorno.compareTo(other.giorno);
	}
}
