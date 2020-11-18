/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SICEE_T_DATICATAST_SEC database table.
 * 
 */
@Entity
@Table(name="SICEE_T_DATICATAST_SEC")
public class SiceeTDaticatastSec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_DATICATAST_SEC")
	private long idDaticatastSec;

	@Column(name="CODICE_COMUNE_CATASTALE")
	private String codiceComuneCatastale;

	@Column(name="DESC_COMUNE")
	private String descComune;

	@Column(name="DESC_PROV")
	private String descProv;

	@Column(name="FLG_SIGMATER")
	private String flgSigmater;

	private String foglio;

	@Column(name="ID_COMUNE")
	private String idComune;

	@Column(name="ID_PROV")
	private String idProv;

	private String particella;

	private String sezione;

	private String subalterno;

	//bi-directional many-to-one association to SiceeTCertificato
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="ANNO", referencedColumnName="ANNO", insertable = false, updatable = false),
		@JoinColumn(name="ID_CERTIFICATORE", referencedColumnName="ID_CERTIFICATORE", insertable = false, updatable = false),
		@JoinColumn(name="PROGR_CERTIFICATO", referencedColumnName="PROGR_CERTIFICATO", insertable = false, updatable = false)
		})
	private SiceeTCertificato siceeTCertificato;

	public SiceeTDaticatastSec() {
	}

	public long getIdDaticatastSec() {
		return this.idDaticatastSec;
	}

	public void setIdDaticatastSec(long idDaticatastSec) {
		this.idDaticatastSec = idDaticatastSec;
	}

	public String getCodiceComuneCatastale() {
		return this.codiceComuneCatastale;
	}

	public void setCodiceComuneCatastale(String codiceComuneCatastale) {
		this.codiceComuneCatastale = codiceComuneCatastale;
	}

	public String getDescComune() {
		return this.descComune;
	}

	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}

	public String getDescProv() {
		return this.descProv;
	}

	public void setDescProv(String descProv) {
		this.descProv = descProv;
	}

	public String getFlgSigmater() {
		return this.flgSigmater;
	}

	public void setFlgSigmater(String flgSigmater) {
		this.flgSigmater = flgSigmater;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getIdComune() {
		return this.idComune;
	}

	public void setIdComune(String idComune) {
		this.idComune = idComune;
	}

	public String getIdProv() {
		return this.idProv;
	}

	public void setIdProv(String idProv) {
		this.idProv = idProv;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSezione() {
		return this.sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public SiceeTCertificato getSiceeTCertificato() {
		return this.siceeTCertificato;
	}

	public void setSiceeTCertificato(SiceeTCertificato siceeTCertificato) {
		this.siceeTCertificato = siceeTCertificato;
	}

}