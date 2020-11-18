/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SICEE_T_ACTA_LOG database table.
 * 
 */
@Entity
@Table(name="SICEE_T_ACTA_LOG")
public class SiceeTActaLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SiceeTActaLogPK id;

	@Column(name="DESC_LOG", length=500)
	private String descLog;

	//bi-directional many-to-one association to SiceeTActa
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="ANNO", referencedColumnName="ANNO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_CERTIFICATORE", referencedColumnName="ID_CERTIFICATORE", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="PROGR_CERTIFICATO", referencedColumnName="PROGR_CERTIFICATO", nullable=false, insertable=false, updatable=false)
		})
	private SiceeTActa siceeTActa;

	public SiceeTActaLog() {
	}

	public SiceeTActaLogPK getId() {
		return this.id;
	}

	public void setId(SiceeTActaLogPK id) {
		this.id = id;
	}

	public String getDescLog() {
		return this.descLog;
	}

	public void setDescLog(String descLog) {
		this.descLog = descLog;
	}

	public SiceeTActa getSiceeTActa() {
		return this.siceeTActa;
	}

	public void setSiceeTActa(SiceeTActa siceeTActa) {
		this.siceeTActa = siceeTActa;
	}

	
}