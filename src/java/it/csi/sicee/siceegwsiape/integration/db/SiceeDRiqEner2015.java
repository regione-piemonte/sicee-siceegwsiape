/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the SICEE_D_RIQ_ENER_2015 database table.
 * 
 */
@Entity
@Table(name="SICEE_D_RIQ_ENER_2015")
public class SiceeDRiqEner2015 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODICE_REN")
	private String codiceRen;

	private String descr;

	@Column(name="ID_XML")
	private Integer idXml;

	//bi-directional many-to-one association to SiceeTRaccomand2015
	@OneToMany(mappedBy="siceeDRiqEner2015")
	private Set<SiceeTRaccomand2015> siceeTRaccomand2015s;

	public SiceeDRiqEner2015() {
	}

	public String getCodiceRen() {
		return this.codiceRen;
	}

	public void setCodiceRen(String codiceRen) {
		this.codiceRen = codiceRen;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getIdXml() {
		return this.idXml;
	}

	public void setIdXml(Integer idXml) {
		this.idXml = idXml;
	}

	public Set<SiceeTRaccomand2015> getSiceeTRaccomand2015s() {
		return this.siceeTRaccomand2015s;
	}

	public void setSiceeTRaccomand2015s(Set<SiceeTRaccomand2015> siceeTRaccomand2015s) {
		this.siceeTRaccomand2015s = siceeTRaccomand2015s;
	}

	public SiceeTRaccomand2015 addSiceeTRaccomand2015(SiceeTRaccomand2015 siceeTRaccomand2015) {
		getSiceeTRaccomand2015s().add(siceeTRaccomand2015);
		siceeTRaccomand2015.setSiceeDRiqEner2015(this);

		return siceeTRaccomand2015;
	}

	public SiceeTRaccomand2015 removeSiceeTRaccomand2015(SiceeTRaccomand2015 siceeTRaccomand2015) {
		getSiceeTRaccomand2015s().remove(siceeTRaccomand2015);
		siceeTRaccomand2015.setSiceeDRiqEner2015(null);

		return siceeTRaccomand2015;
	}

}