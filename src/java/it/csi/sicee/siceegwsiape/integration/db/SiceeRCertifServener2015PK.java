/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SICEE_R_CERTIF_SERVENER2015 database table.
 * 
 */
@Embeddable
public class SiceeRCertifServener2015PK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_CERTIFICATORE")
	private String idCertificatore;

	@Column(name="PROGR_CERTIFICATO")
	private String progrCertificato;

	private String anno;

	@Column(name="ID_SERV_ENER")
	private long idServEner;

	public SiceeRCertifServener2015PK() {
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
	public long getIdServEner() {
		return this.idServEner;
	}
	public void setIdServEner(long idServEner) {
		this.idServEner = idServEner;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SiceeRCertifServener2015PK)) {
			return false;
		}
		SiceeRCertifServener2015PK castOther = (SiceeRCertifServener2015PK)other;
		return 
			this.idCertificatore.equals(castOther.idCertificatore)
			&& this.progrCertificato.equals(castOther.progrCertificato)
			&& this.anno.equals(castOther.anno)
			&& (this.idServEner == castOther.idServEner);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idCertificatore.hashCode();
		hash = hash * prime + this.progrCertificato.hashCode();
		hash = hash * prime + this.anno.hashCode();
		hash = hash * prime + ((int) (this.idServEner ^ (this.idServEner >>> 32)));
		
		return hash;
	}
}