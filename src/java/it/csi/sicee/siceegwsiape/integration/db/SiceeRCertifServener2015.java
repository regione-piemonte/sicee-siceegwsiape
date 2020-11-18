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
 * The persistent class for the SICEE_R_CERTIF_SERVENER2015 database table.
 * 
 */
@Entity
@Table(name="SICEE_R_CERTIF_SERVENER2015")
public class SiceeRCertifServener2015 implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SiceeRCertifServener2015PK id;

	private BigDecimal efficienza;

	private BigDecimal epnren;

	private BigDecimal epren;

	//bi-directional many-to-one association to SiceeTCertificato
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="ANNO", referencedColumnName="ANNO", insertable=false, updatable=false),
		@JoinColumn(name="ID_CERTIFICATORE", referencedColumnName="ID_CERTIFICATORE", insertable=false, updatable=false),
		@JoinColumn(name="PROGR_CERTIFICATO", referencedColumnName="PROGR_CERTIFICATO", insertable=false, updatable=false)
		})
	private SiceeTCertificato siceeTCertificato;

	//bi-directional many-to-one association to SiceeTDetImp2015
	@OneToMany(mappedBy="siceeRCertifServener2015", fetch=FetchType.EAGER)
	private Set<SiceeTDetImp2015> siceeTDetImp2015s;

	public SiceeRCertifServener2015() {
	}

	public SiceeRCertifServener2015PK getId() {
		return this.id;
	}

	public void setId(SiceeRCertifServener2015PK id) {
		this.id = id;
	}

	public BigDecimal getEfficienza() {
		return this.efficienza;
	}

	public void setEfficienza(BigDecimal efficienza) {
		this.efficienza = efficienza;
	}

	public BigDecimal getEpnren() {
		return this.epnren;
	}

	public void setEpnren(BigDecimal epnren) {
		this.epnren = epnren;
	}

	public BigDecimal getEpren() {
		return this.epren;
	}

	public void setEpren(BigDecimal epren) {
		this.epren = epren;
	}

	public SiceeTCertificato getSiceeTCertificato() {
		return this.siceeTCertificato;
	}

	public void setSiceeTCertificato(SiceeTCertificato siceeTCertificato) {
		this.siceeTCertificato = siceeTCertificato;
	}

	public Set<SiceeTDetImp2015> getSiceeTDetImp2015s() {
		return this.siceeTDetImp2015s;
	}

	public void setSiceeTDetImp2015s(Set<SiceeTDetImp2015> siceeTDetImp2015s) {
		this.siceeTDetImp2015s = siceeTDetImp2015s;
	}

	public SiceeTDetImp2015 addSiceeTDetImp2015(SiceeTDetImp2015 siceeTDetImp2015) {
		getSiceeTDetImp2015s().add(siceeTDetImp2015);
		siceeTDetImp2015.setSiceeRCertifServener2015(this);

		return siceeTDetImp2015;
	}

	public SiceeTDetImp2015 removeSiceeTDetImp2015(SiceeTDetImp2015 siceeTDetImp2015) {
		getSiceeTDetImp2015s().remove(siceeTDetImp2015);
		siceeTDetImp2015.setSiceeRCertifServener2015(null);

		return siceeTDetImp2015;
	}

}