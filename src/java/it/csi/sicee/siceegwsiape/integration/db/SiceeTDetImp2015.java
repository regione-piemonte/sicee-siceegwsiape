/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SICEE_T_DET_IMP_2015 database table.
 * 
 */
@Entity
@Table(name="SICEE_T_DET_IMP_2015")
public class SiceeTDetImp2015 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_DETTAGLIO_IMP")
	private long idDettaglioImp;

	@Column(name="ANNO_INSTALL")
	private Integer annoInstall;

	@Column(name="CODICE_IMPIANTO_CIT")
	private Integer codiceImpiantoCit;

	@Column(name="FK_COMBUSTIBILE")
	private BigDecimal fkCombustibile;

	@Column(name="FK_TIPO_IMPIANTO")
	private BigDecimal fkTipoImpianto;

	@Column(name="POTENZA_NOMIN_KW")
	private BigDecimal potenzaNominKw;

	@Column(name="PROGRESSIVO_DETT")
	private Integer progressivoDett;

	@Column(name="TIPO_IMPIANTO")
	private String tipoImpianto;

	//bi-directional many-to-one association to SiceeRCertifServener2015
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="ANNO", referencedColumnName="ANNO", insertable=false, updatable=false),
		@JoinColumn(name="FK_SERV_ENER", referencedColumnName="ID_SERV_ENER", insertable=false, updatable=false),
		@JoinColumn(name="ID_CERTIFICATORE", referencedColumnName="ID_CERTIFICATORE", insertable=false, updatable=false),
		@JoinColumn(name="PROGR_CERTIFICATO", referencedColumnName="PROGR_CERTIFICATO", insertable=false, updatable=false)
		})
	private SiceeRCertifServener2015 siceeRCertifServener2015;

	public SiceeTDetImp2015() {
	}

	public long getIdDettaglioImp() {
		return this.idDettaglioImp;
	}

	public void setIdDettaglioImp(long idDettaglioImp) {
		this.idDettaglioImp = idDettaglioImp;
	}

	public Integer getAnnoInstall() {
		return this.annoInstall;
	}

	public void setAnnoInstall(Integer annoInstall) {
		this.annoInstall = annoInstall;
	}

	public Integer getCodiceImpiantoCit() {
		return this.codiceImpiantoCit;
	}

	public void setCodiceImpiantoCit(Integer codiceImpiantoCit) {
		this.codiceImpiantoCit = codiceImpiantoCit;
	}

	public BigDecimal getFkCombustibile() {
		return this.fkCombustibile;
	}

	public void setFkCombustibile(BigDecimal fkCombustibile) {
		this.fkCombustibile = fkCombustibile;
	}

	public BigDecimal getFkTipoImpianto() {
		return this.fkTipoImpianto;
	}

	public void setFkTipoImpianto(BigDecimal fkTipoImpianto) {
		this.fkTipoImpianto = fkTipoImpianto;
	}

	public BigDecimal getPotenzaNominKw() {
		return this.potenzaNominKw;
	}

	public void setPotenzaNominKw(BigDecimal potenzaNominKw) {
		this.potenzaNominKw = potenzaNominKw;
	}

	public Integer getProgressivoDett() {
		return this.progressivoDett;
	}

	public void setProgressivoDett(Integer progressivoDett) {
		this.progressivoDett = progressivoDett;
	}

	public String getTipoImpianto() {
		return this.tipoImpianto;
	}

	public void setTipoImpianto(String tipoImpianto) {
		this.tipoImpianto = tipoImpianto;
	}

	public SiceeRCertifServener2015 getSiceeRCertifServener2015() {
		return this.siceeRCertifServener2015;
	}

	public void setSiceeRCertifServener2015(SiceeRCertifServener2015 siceeRCertifServener2015) {
		this.siceeRCertifServener2015 = siceeRCertifServener2015;
	}

}