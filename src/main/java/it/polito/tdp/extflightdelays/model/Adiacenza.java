package it.polito.tdp.extflightdelays.model;

public class Adiacenza implements Comparable<Adiacenza>{
	private String a1;
	private String a2;
	private Double peso;
	/**
	 * @param a1
	 * @param a2
	 * @param peso
	 */
	public Adiacenza(String a1, String a2,Double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso=peso;
	}
	public String getA1() {
		return a1;
	}
	public void setA1(String a1) {
		this.a1 = a1;
	}
	public String getA2() {
		return a2;
	}
	public void setA2(String a2) {
		this.a2 = a2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenza other) {
		return this.peso.compareTo(other.getPeso());
	}
}
