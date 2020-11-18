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
 * The persistent class for the SICEE_T_SIAPE_LOG database table.
 * 
 */
@Entity
@Table(name="SICEE_T_SIAPE_LOG")
@NamedQuery(name="SiceeTSiapeLog.findAll", query="SELECT s FROM SiceeTSiapeLog s")
public class SiceeTSiapeLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SICEE_T_SIAPE_LOG_IDSIAPELOG_GENERATOR", sequenceName="SEQ_SICEE_T_SIAPE_LOG", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SICEE_T_SIAPE_LOG_IDSIAPELOG_GENERATOR")
	@Column(name="ID_SIAPE_LOG")
	private long idSiapeLog;

	@Column(name="CODICE_ESITO")
	private Integer codiceEsito;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_OPERAZIONE")
	private Date dataOperazione;

	@Column(name="FLG_MAIL_INVIATA")
	private String flgMailInviata;

	@Column(name="MSG_ESITO")
	private String msgEsito;

	//bi-directional many-to-one association to SiceeTSiape
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="ANNO", referencedColumnName="ANNO"),
		@JoinColumn(name="FK_CERTIFICATORE", referencedColumnName="ID_CERTIFICATORE"),
		@JoinColumn(name="FK_TIPO_AZIONE", referencedColumnName="ID_TIPO_AZIONE"),
		@JoinColumn(name="PROGR_CERTIFICATO", referencedColumnName="PROGR_CERTIFICATO")
		})
	private SiceeTSiape siceeTSiape;

	public SiceeTSiapeLog() {
	}

	public long getIdSiapeLog() {
		
		return this.idSiapeLog;
	}

	public void setIdSiapeLog(long idSiapeLog) {
		this.idSiapeLog = idSiapeLog;
	}

	public Integer getCodiceEsito() {
		return this.codiceEsito;
	}

	public void setCodiceEsito(Integer codiceEsito) {
		this.codiceEsito = codiceEsito;
	}

	public Date getDataOperazione() {
		return this.dataOperazione;
	}

	public void setDataOperazione(Date dataOperazione) {
		this.dataOperazione = dataOperazione;
	}

	public String getFlgMailInviata() {
		return this.flgMailInviata;
	}

	public void setFlgMailInviata(String flgMailInviata) {
		this.flgMailInviata = flgMailInviata;
	}

	public String getMsgEsito() {
		return this.msgEsito;
	}

	public void setMsgEsito(String msgEsito) {
		this.msgEsito = msgEsito;
	}

	public SiceeTSiape getSiceeTSiape() {
		return this.siceeTSiape;
	}

	public void setSiceeTSiape(SiceeTSiape siceeTSiape) {
		this.siceeTSiape = siceeTSiape;
	}

}