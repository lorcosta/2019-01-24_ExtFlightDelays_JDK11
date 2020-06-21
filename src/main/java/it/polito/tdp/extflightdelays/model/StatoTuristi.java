package it.polito.tdp.extflightdelays.model;

public class StatoTuristi {
	private String stato;
	private Integer turisti;
	/**
	 * @param stato
	 * @param turisti
	 */
	public StatoTuristi(String stato, Integer turisti) {
		super();
		this.stato = stato;
		this.turisti = turisti;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Integer getTuristi() {
		return turisti;
	}
	public void setTuristi(Integer turisti) {
		this.turisti = turisti;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatoTuristi other = (StatoTuristi) obj;
		if (stato == null) {
			if (other.stato != null)
				return false;
		} else if (!stato.equals(other.stato))
			return false;
		return true;
	}
	
}
