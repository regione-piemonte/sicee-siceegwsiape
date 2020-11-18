/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SICEE_T_PARAMETRI_SIAPE database table.
 * 
 */
@Entity
@Table(name="SICEE_T_PARAMETRI_SIAPE")
@NamedQuery(name="SiceeTParametriSiape.findAll", query="SELECT s FROM SiceeTParametriSiape s")
public class SiceeTParametriSiape implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRI_SIAPE", insertable=false, updatable=false, unique=true, nullable=false, precision=22)
	private Integer idParametriSiape;

	private String codice;

	private String valore;

	public SiceeTParametriSiape() {
	}

	public Integer getIdParametriSiape() {
		return this.idParametriSiape;
	}

	public void setIdParametriSiape(Integer idParametriSiape) {
		this.idParametriSiape = idParametriSiape;
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getValore() {
		return this.valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}