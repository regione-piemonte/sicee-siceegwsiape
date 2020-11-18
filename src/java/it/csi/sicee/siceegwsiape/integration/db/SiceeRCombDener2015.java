/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SICEE_R_COMB_DENER_2015 database table.
 * 
 */
@Entity
@Table(name="SICEE_R_COMB_DENER_2015")
public class SiceeRCombDener2015 implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SiceeRCombDener2015PK id;

	@Column(name="DESCR_ALTRO")
	private String descrAltro;

	//bi-directional many-to-one association to SiceeTDatiEner2015
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="ANNO", referencedColumnName="ANNO", insertable = false, updatable = false),
		@JoinColumn(name="ID_CERTIFICATORE", referencedColumnName="ID_CERTIFICATORE", insertable = false, updatable = false),
		@JoinColumn(name="PROGR_CERTIFICATO", referencedColumnName="PROGR_CERTIFICATO", insertable = false, updatable = false)
		})
	private SiceeTDatiEner2015 siceeTDatiEner2015;

	//bi-directional many-to-one association to SiceeDCombustibile
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_COMBUSTIBILE", insertable = false, updatable = false)
	private SiceeDCombustibile siceeDCombustibile;
		
	public SiceeRCombDener2015() {
	}

	public SiceeRCombDener2015PK getId() {
		return this.id;
	}

	public void setId(SiceeRCombDener2015PK id) {
		this.id = id;
	}

	public String getDescrAltro() {
		return this.descrAltro;
	}

	public void setDescrAltro(String descrAltro) {
		this.descrAltro = descrAltro;
	}

	public SiceeTDatiEner2015 getSiceeTDatiEner2015() {
		return this.siceeTDatiEner2015;
	}

	public void setSiceeTDatiEner2015(SiceeTDatiEner2015 siceeTDatiEner2015) {
		this.siceeTDatiEner2015 = siceeTDatiEner2015;
	}
	public SiceeDCombustibile getSiceeDCombustibile() {
		return this.siceeDCombustibile;
	}

	public void setSiceeDCombustibile(SiceeDCombustibile siceeDCombustibile) {
		this.siceeDCombustibile = siceeDCombustibile;
	}
}