/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SICEE_T_SIAPE database table.
 * 
 */
@Entity
@Table(name="SICEE_T_SIAPE")
@NamedQuery(name="SiceeTSiape.findAll", query="SELECT s FROM SiceeTSiape s")
public class SiceeTSiape implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SiceeTSiapePK id;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	@Lob
	@Column(name="XML_SIAPE")
	private String xmlSiape;

	@Column(name="FLG_DA_ELABORARE")
	private String flgDaElaborare;

	//bi-directional many-to-one association to SiceeTSiapeLog
	@OneToMany(mappedBy="siceeTSiape")
	private List<SiceeTSiapeLog> siceeTSiapeLogs;

	public SiceeTSiape() {
	}

	public SiceeTSiapePK getId() {
		return this.id;
	}

	public void setId(SiceeTSiapePK id) {
		this.id = id;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getXmlSiape() {
		return this.xmlSiape;
	}

	public void setXmlSiape(String xmlSiape) {
		this.xmlSiape = xmlSiape;
	}

	public String getFlgDaElaborare() {
		return flgDaElaborare;
	}

	public void setFlgDaElaborare(String flgDaElaborare) {
		this.flgDaElaborare = flgDaElaborare;
	}

	public List<SiceeTSiapeLog> getSiceeTSiapeLogs() {
		return this.siceeTSiapeLogs;
	}

	public void setSiceeTSiapeLogs(List<SiceeTSiapeLog> siceeTSiapeLogs) {
		this.siceeTSiapeLogs = siceeTSiapeLogs;
	}

	public SiceeTSiapeLog addSiceeTSiapeLog(SiceeTSiapeLog siceeTSiapeLog) {
		getSiceeTSiapeLogs().add(siceeTSiapeLog);
		siceeTSiapeLog.setSiceeTSiape(this);

		return siceeTSiapeLog;
	}

	public SiceeTSiapeLog removeSiceeTSiapeLog(SiceeTSiapeLog siceeTSiapeLog) {
		getSiceeTSiapeLogs().remove(siceeTSiapeLog);
		siceeTSiapeLog.setSiceeTSiape(null);

		return siceeTSiapeLog;
	}

}