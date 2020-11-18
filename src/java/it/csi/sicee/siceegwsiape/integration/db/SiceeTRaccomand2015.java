/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SICEE_T_RACCOMAND_2015 database table.
 * 
 */
@Entity
@Table(name="SICEE_T_RACCOMAND_2015")
public class SiceeTRaccomand2015 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_RACCOMANDAZIONE")
	private long idRaccomandazione;

	@Column(name="EPGL_NREN_SINGOLO_INT")
	private BigDecimal epglNrenSingoloInt;

	@Column(name="FLG_RISTRUTTURAZIONE")
	private String flgRistrutturazione;

	@Column(name="NR_ANNI_RIT_INVEST")
	private BigDecimal nrAnniRitInvest;

	@Column(name="TIPO_INTERVENTO")
	private String tipoIntervento;

	//bi-directional many-to-one association to SiceeDClasseEnergetica
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FK_CLASSE_ENERGETICA")
	private SiceeDClasseEnergetica siceeDClasseEnergetica;

	//bi-directional many-to-one association to SiceeTCertificato
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="ANNO", referencedColumnName="ANNO", insertable = false, updatable = false),
		@JoinColumn(name="ID_CERTIFICATORE", referencedColumnName="ID_CERTIFICATORE", insertable = false, updatable = false),
		@JoinColumn(name="PROGR_CERTIFICATO", referencedColumnName="PROGR_CERTIFICATO", insertable = false, updatable = false)
		})
	private SiceeTCertificato siceeTCertificato;

	//bi-directional many-to-one association to SiceeDRiqEner2015
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CODICE_REN")
	private SiceeDRiqEner2015 siceeDRiqEner2015;

	public SiceeTRaccomand2015() {
	}

	public long getIdRaccomandazione() {
		return this.idRaccomandazione;
	}

	public void setIdRaccomandazione(long idRaccomandazione) {
		this.idRaccomandazione = idRaccomandazione;
	}

	public BigDecimal getEpglNrenSingoloInt() {
		return this.epglNrenSingoloInt;
	}

	public void setEpglNrenSingoloInt(BigDecimal epglNrenSingoloInt) {
		this.epglNrenSingoloInt = epglNrenSingoloInt;
	}

	public String getFlgRistrutturazione() {
		return this.flgRistrutturazione;
	}

	public void setFlgRistrutturazione(String flgRistrutturazione) {
		this.flgRistrutturazione = flgRistrutturazione;
	}

	public BigDecimal getNrAnniRitInvest() {
		return this.nrAnniRitInvest;
	}

	public void setNrAnniRitInvest(BigDecimal nrAnniRitInvest) {
		this.nrAnniRitInvest = nrAnniRitInvest;
	}

	public String getTipoIntervento() {
		return this.tipoIntervento;
	}

	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}

	public SiceeDClasseEnergetica getSiceeDClasseEnergetica() {
		return this.siceeDClasseEnergetica;
	}

	public void setSiceeDClasseEnergetica(SiceeDClasseEnergetica siceeDClasseEnergetica) {
		this.siceeDClasseEnergetica = siceeDClasseEnergetica;
	}

	public SiceeTCertificato getSiceeTCertificato() {
		return this.siceeTCertificato;
	}

	public void setSiceeTCertificato(SiceeTCertificato siceeTCertificato) {
		this.siceeTCertificato = siceeTCertificato;
	}

	public SiceeDRiqEner2015 getSiceeDRiqEner2015() {
		return this.siceeDRiqEner2015;
	}

	public void setSiceeDRiqEner2015(SiceeDRiqEner2015 siceeDRiqEner2015) {
		this.siceeDRiqEner2015 = siceeDRiqEner2015;
	}

}