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
 * The persistent class for the SICEE_T_ALTRE_INFO database table.
 * 
 */
@Entity
@Table(name="SICEE_T_ALTRE_INFO")
public class SiceeTAltreInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SiceeTAltreInfoPK id;

	@Column(name="ALTRO_SERVIZI_ENERGIA")
	private Integer altroServiziEnergia;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_SOPRALLUOGO")
	private Date dataSopralluogo;

	@Column(name="DICH_INPIPENDENZA")
	private String dichInpipendenza;

	private String documentazione;

	@Column(name="FK_CLASSE_192_05")
	private BigDecimal fkClasse19205;

	@Column(name="FK_MOTIVO")
	private BigDecimal fkMotivo;

	@Column(name="FK_QUALITA_INVO")
	private BigDecimal fkQualitaInvo;

	@Column(name="FK_RUOLO_CERT")
	private BigDecimal fkRuoloCert;

	@Column(name="FLG_SOPRALLUOGO")
	private String flgSopralluogo;

	@Column(name="FLG_SW1")
	private String flgSw1;

	@Column(name="FLG_SW2")
	private String flgSw2;

	@Column(name="IND_RISC_EPI_NAZ")
	private BigDecimal indRiscEpiNaz;

	@Column(name="INFO_MIGL_PREST_ENERG")
	private String infoMiglPrestEnerg;

	@Column(name="LIMITE_NORMA_IMP")
	private BigDecimal limiteNormaImp;

	@Column(name="LIMITE_NORMA_PDC")
	private BigDecimal limiteNormaPdc;

	@Column(name="LIMITE_NORMA_RISC")
	private BigDecimal limiteNormaRisc;

	@Column(name="METODOLOGIA_CALCOLO")
	private String metodologiaCalcolo;

	@Column(name="MOTIVO_ALTRO")
	private String motivoAltro;

	@Column(name="NOME_PRODUTTORE")
	private String nomeProduttore;

	@Column(name="NOTE_SEGNALAZIONI")
	private String noteSegnalazioni;

	@Column(name="NOTE_SOPRALLUOGO")
	private String noteSopralluogo;

	@Column(name="NR_CERTIFICATO")
	private String nrCertificato;

	@Column(name="PRESTAZIONE_ENE")
	private BigDecimal prestazioneEne;

	@Column(name="SW_UTILIZZATO")
	private String swUtilizzato;

	@Column(name="ULT_INFO")
	private String ultInfo;

	@Column(name="UNI_TS_11300")
	private String uniTs11300;

	@Column(name="VALORE_PREST_PDC")
	private BigDecimal valorePrestPdc;

	//bi-directional many-to-one association to SiceeTCertificato
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="ANNO", referencedColumnName="ANNO", insertable = false, updatable = false),
		@JoinColumn(name="ID_CERTIFICATORE", referencedColumnName="ID_CERTIFICATORE", insertable = false, updatable = false),
		@JoinColumn(name="PROGR_CERTIFICATO", referencedColumnName="PROGR_CERTIFICATO", insertable = false, updatable = false)
		})
	private SiceeTCertificato siceeTCertificato;

	public SiceeTAltreInfo() {
	}

	public SiceeTAltreInfoPK getId() {
		return this.id;
	}

	public void setId(SiceeTAltreInfoPK id) {
		this.id = id;
	}

	public Integer getAltroServiziEnergia() {
		return this.altroServiziEnergia;
	}

	public void setAltroServiziEnergia(Integer altroServiziEnergia) {
		this.altroServiziEnergia = altroServiziEnergia;
	}

	public Date getDataSopralluogo() {
		return this.dataSopralluogo;
	}

	public void setDataSopralluogo(Date dataSopralluogo) {
		this.dataSopralluogo = dataSopralluogo;
	}

	public String getDichInpipendenza() {
		return this.dichInpipendenza;
	}

	public void setDichInpipendenza(String dichInpipendenza) {
		this.dichInpipendenza = dichInpipendenza;
	}

	public String getDocumentazione() {
		return this.documentazione;
	}

	public void setDocumentazione(String documentazione) {
		this.documentazione = documentazione;
	}

	public BigDecimal getFkClasse19205() {
		return this.fkClasse19205;
	}

	public void setFkClasse19205(BigDecimal fkClasse19205) {
		this.fkClasse19205 = fkClasse19205;
	}

	public BigDecimal getFkMotivo() {
		return this.fkMotivo;
	}

	public void setFkMotivo(BigDecimal fkMotivo) {
		this.fkMotivo = fkMotivo;
	}

	public BigDecimal getFkQualitaInvo() {
		return this.fkQualitaInvo;
	}

	public void setFkQualitaInvo(BigDecimal fkQualitaInvo) {
		this.fkQualitaInvo = fkQualitaInvo;
	}

	public BigDecimal getFkRuoloCert() {
		return this.fkRuoloCert;
	}

	public void setFkRuoloCert(BigDecimal fkRuoloCert) {
		this.fkRuoloCert = fkRuoloCert;
	}

	public String getFlgSopralluogo() {
		return this.flgSopralluogo;
	}

	public void setFlgSopralluogo(String flgSopralluogo) {
		this.flgSopralluogo = flgSopralluogo;
	}

	public String getFlgSw1() {
		return this.flgSw1;
	}

	public void setFlgSw1(String flgSw1) {
		this.flgSw1 = flgSw1;
	}

	public String getFlgSw2() {
		return this.flgSw2;
	}

	public void setFlgSw2(String flgSw2) {
		this.flgSw2 = flgSw2;
	}

	public BigDecimal getIndRiscEpiNaz() {
		return this.indRiscEpiNaz;
	}

	public void setIndRiscEpiNaz(BigDecimal indRiscEpiNaz) {
		this.indRiscEpiNaz = indRiscEpiNaz;
	}

	public String getInfoMiglPrestEnerg() {
		return this.infoMiglPrestEnerg;
	}

	public void setInfoMiglPrestEnerg(String infoMiglPrestEnerg) {
		this.infoMiglPrestEnerg = infoMiglPrestEnerg;
	}

	public BigDecimal getLimiteNormaImp() {
		return this.limiteNormaImp;
	}

	public void setLimiteNormaImp(BigDecimal limiteNormaImp) {
		this.limiteNormaImp = limiteNormaImp;
	}

	public BigDecimal getLimiteNormaPdc() {
		return this.limiteNormaPdc;
	}

	public void setLimiteNormaPdc(BigDecimal limiteNormaPdc) {
		this.limiteNormaPdc = limiteNormaPdc;
	}

	public BigDecimal getLimiteNormaRisc() {
		return this.limiteNormaRisc;
	}

	public void setLimiteNormaRisc(BigDecimal limiteNormaRisc) {
		this.limiteNormaRisc = limiteNormaRisc;
	}

	public String getMetodologiaCalcolo() {
		return this.metodologiaCalcolo;
	}

	public void setMetodologiaCalcolo(String metodologiaCalcolo) {
		this.metodologiaCalcolo = metodologiaCalcolo;
	}

	public String getMotivoAltro() {
		return this.motivoAltro;
	}

	public void setMotivoAltro(String motivoAltro) {
		this.motivoAltro = motivoAltro;
	}

	public String getNomeProduttore() {
		return this.nomeProduttore;
	}

	public void setNomeProduttore(String nomeProduttore) {
		this.nomeProduttore = nomeProduttore;
	}

	public String getNoteSegnalazioni() {
		return this.noteSegnalazioni;
	}

	public void setNoteSegnalazioni(String noteSegnalazioni) {
		this.noteSegnalazioni = noteSegnalazioni;
	}

	public String getNoteSopralluogo() {
		return this.noteSopralluogo;
	}

	public void setNoteSopralluogo(String noteSopralluogo) {
		this.noteSopralluogo = noteSopralluogo;
	}

	public String getNrCertificato() {
		return this.nrCertificato;
	}

	public void setNrCertificato(String nrCertificato) {
		this.nrCertificato = nrCertificato;
	}

	public BigDecimal getPrestazioneEne() {
		return this.prestazioneEne;
	}

	public void setPrestazioneEne(BigDecimal prestazioneEne) {
		this.prestazioneEne = prestazioneEne;
	}

	public String getSwUtilizzato() {
		return this.swUtilizzato;
	}

	public void setSwUtilizzato(String swUtilizzato) {
		this.swUtilizzato = swUtilizzato;
	}

	public String getUltInfo() {
		return this.ultInfo;
	}

	public void setUltInfo(String ultInfo) {
		this.ultInfo = ultInfo;
	}

	public String getUniTs11300() {
		return this.uniTs11300;
	}

	public void setUniTs11300(String uniTs11300) {
		this.uniTs11300 = uniTs11300;
	}

	public BigDecimal getValorePrestPdc() {
		return this.valorePrestPdc;
	}

	public void setValorePrestPdc(BigDecimal valorePrestPdc) {
		this.valorePrestPdc = valorePrestPdc;
	}

	public SiceeTCertificato getSiceeTCertificato() {
		return this.siceeTCertificato;
	}

	public void setSiceeTCertificato(SiceeTCertificato siceeTCertificato) {
		this.siceeTCertificato = siceeTCertificato;
	}

}