/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SICEE_T_PARAMETRI_ACTA database table.
 * 
 */
@Entity
@Table(name="SICEE_T_PARAMETRI_ACTA")
public class SiceeTParametriActa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_PARAMETRI_ACTA", insertable=false, updatable=false)
	private Integer idParametriActa;

	private String codice;

	private String valore;

	public SiceeTParametriActa() {
	}

	public Integer getIdParametriActa() {
		return this.idParametriActa;
	}

	public void setIdParametriActa(Integer idParametriActa) {
		this.idParametriActa = idParametriActa;
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