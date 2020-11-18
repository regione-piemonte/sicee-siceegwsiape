/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SICEE_T_DATI_XML_2015 database table.
 * 
 */
@Entity
@Table(name="SICEE_T_DATI_XML_2015")
public class SiceeTDatiXml2015 implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SiceeTDatiXml2015PK id;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_SOPRALLUOGO")
	private Date dtSopralluogo;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TITOLO_ABILITATIVO")
	private Date dtTitoloAbilitativo;

	@Column(name="EFFICIENZA_RECUPERO_MEDIO")
	private BigDecimal efficienzaRecuperoMedio;

	@Column(name="FK_TIPO_VENTILAZIONE")
	private BigDecimal fkTipoVentilazione;

	@Column(name="FLG_SISTEMA_CONTABILIZZAZIONE")
	private String flgSistemaContabilizzazione;

	@Column(name="GRADI_GIORNO")
	private Integer gradiGiorno;

	private BigDecimal ht;

	@Column(name="NUM_CERTIF_SW")
	private Integer numCertifSw;

	@Column(name="PORTATA_VENTILAZIONE_TOT")
	private BigDecimal portataVentilazioneTot;

	@Column(name="SUP_DISP_TOT_OPACA")
	private BigDecimal supDispTotOpaca;

	@Column(name="SUP_DISP_TOT_TRASP")
	private BigDecimal supDispTotTrasp;

	@Column(name="SW_UTILIZZATO")
	private String swUtilizzato;

	@Column(name="TRASMITTANZA_MED_SUP_OPACHE")
	private BigDecimal trasmittanzaMedSupOpache;

	@Column(name="TRASMITTANZA_MED_SUP_TRASP")
	private BigDecimal trasmittanzaMedSupTrasp;

	//bi-directional one-to-one association to SiceeTCertificato
	//@OneToOne(mappedBy="siceeTDatiXml2015", fetch=FetchType.LAZY)
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="ANNO", referencedColumnName="ANNO", insertable = false, updatable = false),
		@JoinColumn(name="ID_CERTIFICATORE", referencedColumnName="ID_CERTIFICATORE", insertable = false, updatable = false),
		@JoinColumn(name="PROGR_CERTIFICATO", referencedColumnName="PROGR_CERTIFICATO", insertable = false, updatable = false)
		})
	private SiceeTCertificato siceeTCertificato;

	public SiceeTDatiXml2015() {
	}

	public SiceeTDatiXml2015PK getId() {
		return this.id;
	}

	public void setId(SiceeTDatiXml2015PK id) {
		this.id = id;
	}

	public Date getDtSopralluogo() {
		return this.dtSopralluogo;
	}

	public void setDtSopralluogo(Date dtSopralluogo) {
		this.dtSopralluogo = dtSopralluogo;
	}

	public Date getDtTitoloAbilitativo() {
		return this.dtTitoloAbilitativo;
	}

	public void setDtTitoloAbilitativo(Date dtTitoloAbilitativo) {
		this.dtTitoloAbilitativo = dtTitoloAbilitativo;
	}

	public BigDecimal getEfficienzaRecuperoMedio() {
		return this.efficienzaRecuperoMedio;
	}

	public void setEfficienzaRecuperoMedio(BigDecimal efficienzaRecuperoMedio) {
		this.efficienzaRecuperoMedio = efficienzaRecuperoMedio;
	}

	public BigDecimal getFkTipoVentilazione() {
		return this.fkTipoVentilazione;
	}

	public void setFkTipoVentilazione(BigDecimal fkTipoVentilazione) {
		this.fkTipoVentilazione = fkTipoVentilazione;
	}

	public String getFlgSistemaContabilizzazione() {
		return this.flgSistemaContabilizzazione;
	}

	public void setFlgSistemaContabilizzazione(String flgSistemaContabilizzazione) {
		this.flgSistemaContabilizzazione = flgSistemaContabilizzazione;
	}

	public Integer getGradiGiorno() {
		return this.gradiGiorno;
	}

	public void setGradiGiorno(Integer gradiGiorno) {
		this.gradiGiorno = gradiGiorno;
	}

	public BigDecimal getHt() {
		return this.ht;
	}

	public void setHt(BigDecimal ht) {
		this.ht = ht;
	}

	public Integer getNumCertifSw() {
		return this.numCertifSw;
	}

	public void setNumCertifSw(Integer numCertifSw) {
		this.numCertifSw = numCertifSw;
	}

	public BigDecimal getPortataVentilazioneTot() {
		return this.portataVentilazioneTot;
	}

	public void setPortataVentilazioneTot(BigDecimal portataVentilazioneTot) {
		this.portataVentilazioneTot = portataVentilazioneTot;
	}

	public BigDecimal getSupDispTotOpaca() {
		return this.supDispTotOpaca;
	}

	public void setSupDispTotOpaca(BigDecimal supDispTotOpaca) {
		this.supDispTotOpaca = supDispTotOpaca;
	}

	public BigDecimal getSupDispTotTrasp() {
		return this.supDispTotTrasp;
	}

	public void setSupDispTotTrasp(BigDecimal supDispTotTrasp) {
		this.supDispTotTrasp = supDispTotTrasp;
	}

	public String getSwUtilizzato() {
		return this.swUtilizzato;
	}

	public void setSwUtilizzato(String swUtilizzato) {
		this.swUtilizzato = swUtilizzato;
	}

	public BigDecimal getTrasmittanzaMedSupOpache() {
		return this.trasmittanzaMedSupOpache;
	}

	public void setTrasmittanzaMedSupOpache(BigDecimal trasmittanzaMedSupOpache) {
		this.trasmittanzaMedSupOpache = trasmittanzaMedSupOpache;
	}

	public BigDecimal getTrasmittanzaMedSupTrasp() {
		return this.trasmittanzaMedSupTrasp;
	}

	public void setTrasmittanzaMedSupTrasp(BigDecimal trasmittanzaMedSupTrasp) {
		this.trasmittanzaMedSupTrasp = trasmittanzaMedSupTrasp;
	}

	public SiceeTCertificato getSiceeTCertificato() {
		return this.siceeTCertificato;
	}

	public void setSiceeTCertificato(SiceeTCertificato siceeTCertificato) {
		this.siceeTCertificato = siceeTCertificato;
	}

}