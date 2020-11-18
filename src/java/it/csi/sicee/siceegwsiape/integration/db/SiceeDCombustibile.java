/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the SICEE_D_COMBUSTIBILE database table.
 * 
 */
@Entity
@Table(name="SICEE_D_COMBUSTIBILE")
public class SiceeDCombustibile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COMBUSTIBILE")
	private Integer idCombustibile;

	@Column(name="COD_XML")
	private Integer codXml;

	private String descr;

	@Column(name="FATTORE_EMISSIONE")
	private BigDecimal fattoreEmissione;

	@Column(name="FLG_COMBO_RISCALD")
	private String flgComboRiscald;

	@Column(name="FLG_CONSUMI")
	private String flgConsumi;

	@Column(name="FLG_VETTORE_DET_IMP")
	private String flgVettoreDetImp;

	@Column(name="FLG_VETTORE_EN_EXPORT")
	private String flgVettoreEnExport;

	//bi-directional many-to-one association to SiceeRCombDener2015
	@OneToMany(mappedBy="siceeDCombustibile")
	private Set<SiceeRCombDener2015> siceeRCombDener2015s;

	public SiceeDCombustibile() {
	}

	public Integer getIdCombustibile() {
		return this.idCombustibile;
	}

	public void setIdCombustibile(Integer idCombustibile) {
		this.idCombustibile = idCombustibile;
	}

	public Integer getCodXml() {
		return this.codXml;
	}

	public void setCodXml(Integer codXml) {
		this.codXml = codXml;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public BigDecimal getFattoreEmissione() {
		return this.fattoreEmissione;
	}

	public void setFattoreEmissione(BigDecimal fattoreEmissione) {
		this.fattoreEmissione = fattoreEmissione;
	}

	public String getFlgComboRiscald() {
		return this.flgComboRiscald;
	}

	public void setFlgComboRiscald(String flgComboRiscald) {
		this.flgComboRiscald = flgComboRiscald;
	}

	public String getFlgConsumi() {
		return this.flgConsumi;
	}

	public void setFlgConsumi(String flgConsumi) {
		this.flgConsumi = flgConsumi;
	}

	public String getFlgVettoreDetImp() {
		return this.flgVettoreDetImp;
	}

	public void setFlgVettoreDetImp(String flgVettoreDetImp) {
		this.flgVettoreDetImp = flgVettoreDetImp;
	}

	public String getFlgVettoreEnExport() {
		return this.flgVettoreEnExport;
	}

	public void setFlgVettoreEnExport(String flgVettoreEnExport) {
		this.flgVettoreEnExport = flgVettoreEnExport;
	}

	public Set<SiceeRCombDener2015> getSiceeRCombDener2015s() {
		return this.siceeRCombDener2015s;
	}

	public void setSiceeRCombDener2015s(Set<SiceeRCombDener2015> siceeRCombDener2015s) {
		this.siceeRCombDener2015s = siceeRCombDener2015s;
	}

	public SiceeRCombDener2015 addSiceeRCombDener2015(SiceeRCombDener2015 siceeRCombDener2015) {
		getSiceeRCombDener2015s().add(siceeRCombDener2015);
		siceeRCombDener2015.setSiceeDCombustibile(this);

		return siceeRCombDener2015;
	}

	public SiceeRCombDener2015 removeSiceeRCombDener2015(SiceeRCombDener2015 siceeRCombDener2015) {
		getSiceeRCombDener2015s().remove(siceeRCombDener2015);
		siceeRCombDener2015.setSiceeDCombustibile(null);

		return siceeRCombDener2015;
	}

}