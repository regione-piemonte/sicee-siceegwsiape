/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the SICEE_D_ISTRUZIONE database table.
 * 
 */
@Entity
@Table(name="SICEE_D_ISTRUZIONE")
public class SiceeDIstruzione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ISTRUZIONE")
	private long idIstruzione;

	private String codice;

	private String denominazione;

	@Column(name="FK_TIPO_ISTRUZIONE")
	private java.math.BigDecimal fkTipoIstruzione;

	@Column(name="SIGLA_NON_ORDINE")
	private String siglaNonOrdine;

	@Column(name="SIGLA_ORDINE")
	private String siglaOrdine;

	public SiceeDIstruzione() {
	}

	public long getIdIstruzione() {
		return this.idIstruzione;
	}

	public void setIdIstruzione(long idIstruzione) {
		this.idIstruzione = idIstruzione;
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public java.math.BigDecimal getFkTipoIstruzione() {
		return this.fkTipoIstruzione;
	}

	public void setFkTipoIstruzione(java.math.BigDecimal fkTipoIstruzione) {
		this.fkTipoIstruzione = fkTipoIstruzione;
	}

	public String getSiglaNonOrdine() {
		return this.siglaNonOrdine;
	}

	public void setSiglaNonOrdine(String siglaNonOrdine) {
		this.siglaNonOrdine = siglaNonOrdine;
	}

	public String getSiglaOrdine() {
		return this.siglaOrdine;
	}

	public void setSiglaOrdine(String siglaOrdine) {
		this.siglaOrdine = siglaOrdine;
	}

	
}