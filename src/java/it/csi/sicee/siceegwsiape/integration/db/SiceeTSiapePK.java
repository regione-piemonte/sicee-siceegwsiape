/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SICEE_T_SIAPE database table.
 * 
 */
@Embeddable
public class SiceeTSiapePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_CERTIFICATORE", insertable=false, updatable=false)
	private String idCertificatore;

	@Column(name="PROGR_CERTIFICATO", insertable=false, updatable=false)
	private String progrCertificato;

	@Column(insertable=false, updatable=false)
	private String anno;

	@Column(name="ID_TIPO_AZIONE", insertable=false, updatable=false)
	private Integer idTipoAzione;

	public SiceeTSiapePK() {
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
	public Integer getIdTipoAzione() {
		return this.idTipoAzione;
	}
	public void setIdTipoAzione(Integer idTipoAzione) {
		this.idTipoAzione = idTipoAzione;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SiceeTSiapePK)) {
			return false;
		}
		SiceeTSiapePK castOther = (SiceeTSiapePK)other;
		return 
			this.idCertificatore.equals(castOther.idCertificatore)
			&& this.progrCertificato.equals(castOther.progrCertificato)
			&& this.anno.equals(castOther.anno)
			&& (this.idTipoAzione == castOther.idTipoAzione);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idCertificatore.hashCode();
		hash = hash * prime + this.progrCertificato.hashCode();
		hash = hash * prime + this.anno.hashCode();
		hash = hash * prime + ((int) (this.idTipoAzione ^ (this.idTipoAzione >>> 32)));
		
		return hash;
	}
}