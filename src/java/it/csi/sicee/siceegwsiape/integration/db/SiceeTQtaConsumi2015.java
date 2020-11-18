/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SICEE_T_QTA_CONSUMI_2015 database table.
 * 
 */
@Entity
@Table(name="SICEE_T_QTA_CONSUMI_2015")
public class SiceeTQtaConsumi2015 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_QTA_CONSUMI")
	private long idQtaConsumi;

	@Column(name="ALTRO_DESCR_COMB")
	private String altroDescrComb;

	@Column(name="FK_COMBUSTIBILE")
	private BigDecimal fkCombustibile;

	@Column(name="FK_UNITA_MISURA")
	private BigDecimal fkUnitaMisura;

	private BigDecimal quantita;

	//bi-directional many-to-one association to SiceeTCertificato
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="ANNO", referencedColumnName="ANNO", insertable = false, updatable = false),
		@JoinColumn(name="ID_CERTIFICATORE", referencedColumnName="ID_CERTIFICATORE", insertable = false, updatable = false),
		@JoinColumn(name="PROGR_CERTIFICATO", referencedColumnName="PROGR_CERTIFICATO", insertable = false, updatable = false)
		})
	private SiceeTCertificato siceeTCertificato;

	public SiceeTQtaConsumi2015() {
	}

	public long getIdQtaConsumi() {
		return this.idQtaConsumi;
	}

	public void setIdQtaConsumi(long idQtaConsumi) {
		this.idQtaConsumi = idQtaConsumi;
	}

	public String getAltroDescrComb() {
		return this.altroDescrComb;
	}

	public void setAltroDescrComb(String altroDescrComb) {
		this.altroDescrComb = altroDescrComb;
	}

	public BigDecimal getFkCombustibile() {
		return this.fkCombustibile;
	}

	public void setFkCombustibile(BigDecimal fkCombustibile) {
		this.fkCombustibile = fkCombustibile;
	}

	public BigDecimal getFkUnitaMisura() {
		return this.fkUnitaMisura;
	}

	public void setFkUnitaMisura(BigDecimal fkUnitaMisura) {
		this.fkUnitaMisura = fkUnitaMisura;
	}

	public BigDecimal getQuantita() {
		return this.quantita;
	}

	public void setQuantita(BigDecimal quantita) {
		this.quantita = quantita;
	}

	public SiceeTCertificato getSiceeTCertificato() {
		return this.siceeTCertificato;
	}

	public void setSiceeTCertificato(SiceeTCertificato siceeTCertificato) {
		this.siceeTCertificato = siceeTCertificato;
	}

}