/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SICEE_T_ACTA database table.
 * 
 */
@Embeddable
public class SiceeTActaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_CERTIFICATORE", unique=true, nullable=false, length=5)
	private String idCertificatore;

	@Column(name="PROGR_CERTIFICATO", unique=true, nullable=false, length=4)
	private String progrCertificato;

	@Column(unique=true, nullable=false, length=4)
	private String anno;

	public SiceeTActaPK() {
	}
	public String getIdCertificatore() {
		return this.idCertificatore;
	}
	public void setIdCertificatore(String idCertificatore) {
		this.idCertificatore = idCertificatore;
	}
	public String getProgrCertificato() {
		return this.progrCertificato;
	}
	public void setProgrCertificato(String progrCertificato) {
		this.progrCertificato = progrCertificato;
	}
	public String getAnno() {
		return this.anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SiceeTActaPK)) {
			return false;
		}
		SiceeTActaPK castOther = (SiceeTActaPK)other;
		return 
			this.idCertificatore.equals(castOther.idCertificatore)
			&& this.progrCertificato.equals(castOther.progrCertificato)
			&& this.anno.equals(castOther.anno);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idCertificatore.hashCode();
		hash = hash * prime + this.progrCertificato.hashCode();
		hash = hash * prime + this.anno.hashCode();
		
		return hash;
	}
}