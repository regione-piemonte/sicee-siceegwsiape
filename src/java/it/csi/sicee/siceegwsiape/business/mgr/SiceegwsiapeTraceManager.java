/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.business.mgr;

import it.csi.sicee.siceegwsiape.integration.db.*;
import it.csi.sicee.siceegwsiape.util.APEConstants;
import it.csi.sicee.siceegwsiape.util.GenericUtil;
import it.csi.sicee.siceegwsiape.util.enea.siape.exception.SiapeException;
import it.csi.sicee.siceegwsiape.util.enea.siape.utils.CodeMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.jboss.ejb3.annotation.TransactionTimeout;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


@WebService(name = "SiceegwsiapeTraceManagerWS", targetNamespace = "http://it/csi/sicee/siceegwsiape/business/mgr", serviceName = "ISiceegwsiapeTraceManager")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@Remote(ISiceegwsiapeTraceManager.class)
@Stateless(name = "SiceegwsiapeTraceManagerSL")
public class SiceegwsiapeTraceManager implements ISiceegwsiapeTraceManager {
	
	@PersistenceContext(unitName = "SICEEGWSIAPE")
    private EntityManager entityManager;
	
	private static volatile SiceegwsiapeTraceManager instance; 
	
	private Logger logger = Logger.getLogger(APEConstants.LOGGER_PREFIX);
	
	
	
	public static SiceegwsiapeTraceManager getInstance() {
		// Evito instanziazione multipla
		if(instance==null) {
			synchronized(SiceegwsiapeTraceManager.class) {
				if(instance == null) {
					// FIXME: essendo un EJB dovrei farne il lookup corretto. Questa versione non inizializza aluni dati (datasource / entityManager)
					instance = new SiceegwsiapeTraceManager();
				}
			}
		}	
		return instance;
	}

	@Override
	public List<SiceeTSiape> findCertificatiDaGestire(int maxRow) {
		// TODO Auto-generated method stub
		
		logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] Stampo maxRow: "+maxRow);

		
		StringBuffer qB = new StringBuffer();
		
		qB.append("SELECT s FROM ");
		qB.append(SiceeTSiape.class.getName() + " s ");
		qB.append(" WHERE DATA_CREAZIONE IS NULL ");
		
		qB.append(" AND FLG_DA_ELABORARE = :daElaborare");

		qB.append(" ORDER BY ID_CERTIFICATORE, PROGR_CERTIFICATO, ANNO, ID_TIPO_AZIONE ");
		
		logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] Stampo la query findCertificatiDaGestire: "+qB);
		
		/*
		 Query:
		 select *
			from SICEE_T_SIAPE t
			WHERE DATA_CREAZIONE IS NULL
			AND FLG_DA_ELABORARE = 'S'
			ORDER BY ID_CERTIFICATORE, PROGR_CERTIFICATO, ANNO, ID_TIPO_AZIONE 
		 */
		// FUNZIONA
//		StringBuffer qB = new StringBuffer();
//		qB.append("SELECT c FROM ");
//		qB.append(SiceeTCertificato.class.getName() + " c ");
//		qB.append(" WHERE SICEE_T_CERTIFICATO.ID_CERTIFICATORE = 1441 ");

		
		
		
		
		// FUUNZIONA
//		StringBuffer qB = new StringBuffer();
//		qB.append("SELECT c FROM ");
//		qB.append(SiceeTCertificato.class.getName() + " c, "+ " "+SiceeTActa.class.getName()+ " a , "+SiceeTRifIndex2015.class.getName() + " i ");
//		qB.append("WHERE a = c.id");
//		qB.append(" AND i.siceeTCertificato = c.id");
//		//qB.append(" AND c.id.idCertificatore = 1441");
//		qB.append(" AND i.fkTipoDoc = 2 ");
//		qB.append(" AND (a.idDocActa IS NULL OR a.idProtocolloActa IS NULL) ");
//		
		
		
		
//		StringBuffer qB = new StringBuffer();
//		qB.append("SELECT c FROM ");
//		qB.append(SiceeTCertificato.class.getName() + " c, "+SiceeTActa.class.getName()+ " a, "+SiceeTRifIndex2015.class.getName() + " i ");
//		qB.append(" WHERE c.id.idCertificatore = a.id.idCertificatore ");
//		qB.append(" AND c.id.progrCertificato = a.id.progrCertificato ");
//		qB.append(" AND c.id.anno = a.id.anno ");
//		qB.append(" AND c.id.idCertificatore = i.siceeTCertificato.id.idCertificatore ");
//		qB.append(" AND c.id.progrCertificato = i.siceeTCertificato.id.progrCertificato ");
//		qB.append(" AND c.id.anno = i.siceeTCertificato.id.anno ");
//		qB.append(" AND i.fkTipoDoc = 2 ");
//		qB.append(" AND (a.idDocActa IS NULL OR a.idProtocolloActa IS NULL) ");
//		qB.append(" AND c.id.idCertificatore = 1441");
		
		
		
		Query query = entityManager.createQuery(qB.toString()).setParameter("daElaborare", APEConstants.COD_S);
		
		if (maxRow > 0)
		{
			query = query.setMaxResults(maxRow);
		}
		
		
		//TypedQuery<SiceeTSiape> query = entityManager.createQuery(qB.toString(), SiceeTSiape.class);
		
		//query.setParameter("idTipoDoc", 2);
        
		
		
		List<SiceeTSiape> list =query.getResultList();

		/*
		logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] Stampo il risultato: "+list);
		logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] Stampo il risultato.size(): "+list.size());

        for (SiceeTSiape siceeTSiape : list) {
        	
        	logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] #################");
        	logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] Stampo il siape: "+siceeTSiape);
        	logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] Stampo getIdCertificatore: "+siceeTSiape.getId().getIdCertificatore());
        	logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] Stampo getProgrCertificato: "+siceeTSiape.getId().getProgrCertificato());
        	logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] Stampo getAnno: "+siceeTSiape.getId().getAnno());
        	logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] Stampo getDataCreazione: "+siceeTSiape.getDataCreazione());
        	logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] Stampo getXmlSiape: "+siceeTSiape.getXmlSiape());

        	logger.debug("[SiceegwsiapeTraceManager::findCertificatiDaGestire] #################");
			
        }
        */
		
        return list;
	}

	@Override
	@TransactionTimeout(unit = TimeUnit.SECONDS, value = 60) 
	public SiceeTCertificato findDettaglioCertificatoDaGestire(SiceeTCertificatoPK certificatoPK) throws SiapeException {
		// TODO Auto-generated method stub

		SiceeTCertificato siceeTCertificato = null;

		//		StringBuffer qB = new StringBuffer();
		//		qB.append("SELECT c FROM ");
		//		qB.append(SiceeTCertificato.class.getName());
		//		qB.append(" WHERE AND c.id.idCertificatore = "+siapePk.getIdCertificatore());
		//		qB.append(" AND c.id.progrCertificato = "+siapePk.getProgrCertificato());
		//		qB.append(" AND c.id.anno = "+siapePk.getAnno());
		//		
		//		
		//		System.out.println("Stampo la query findCertificatiDaGestire: "+qB);
		//		
		//		TypedQuery<SiceeTCertificato> query = entityManager.createQuery(qB.toString(), SiceeTCertificato.class);

		try
		{


			siceeTCertificato = entityManager.find(SiceeTCertificato.class, certificatoPK);

			//System.out.println("Stampo il risultato: "+siceeTCertificato);

			//GenericUtil.stampa(siceeTCertificato, false, 3);

			// Forzo il recupero del Certificatore
			//siceeTCertificato.getSiceeTCertificatore().getCognome();

			// Forzo il recupero degli altri dati - NON CANCELLARE
			logger.debug("Stampo siceeTCertificato.getSiceeTCertificatore(): "+siceeTCertificato.getSiceeTCertificatore());
			logger.debug("Stampo siceeTCertificato.getSiceeTAltreInfos(): "+siceeTCertificato.getSiceeTAltreInfos());
			logger.debug("Stampo siceeTCertificato.getSiceeTDatiGeneralis(): "+siceeTCertificato.getSiceeTDatiGeneralis());
			logger.debug("Stampo siceeTCertificato.getSiceeTDatiEner2015s(): "+siceeTCertificato.getSiceeTDatiEner2015s());
			logger.debug("Stampo siceeTCertificato.getSiceeTDaticatastSecs(): "+siceeTCertificato.getSiceeTDaticatastSecs());
			logger.debug("Stampo siceeTCertificato.getSiceeTQtaConsumi2015s(): "+siceeTCertificato.getSiceeTQtaConsumi2015s());
			logger.debug("Stampo siceeTCertificato.getSiceeTRaccomand2015s(): "+siceeTCertificato.getSiceeTRaccomand2015s());
			logger.debug("Stampo siceeTCertificato.getSiceeRCertifServener2015s(): "+siceeTCertificato.getSiceeRCertifServener2015s());

			/*
        if (siceeTCertificato != null) {

        	logger.debug("#################");
        	logger.debug("Stampo il siceeTCertificato: "+siceeTCertificato);
        	logger.debug("Stampo getIdCertificatore: "+siceeTCertificato.getId().getIdCertificatore());
        	logger.debug("Stampo getProgrCertificato: "+siceeTCertificato.getId().getProgrCertificato());
        	logger.debug("Stampo getAnno: "+siceeTCertificato.getId().getAnno());
        	logger.debug("Stampo getFkStato: "+siceeTCertificato.getFkStato());
        	logger.debug("Stampo getDescIndirizzo: "+siceeTCertificato.getDescIndirizzo());

        	logger.info("#################");

        }
			 */
		}
		catch (Exception e)
		{
			logger.error("findDettaglioCertificatoDaGestire - Stampo l'eccezione: "+e.getCause());
			throw new SiapeException(CodeMessage.ERRORE_SISTEMA.getCodice(), CodeMessage.ERRORE_SISTEMA.getMessaggio(), e);
		}

		return siceeTCertificato;
	}

	
	
	@Override
	public String findSiceeTParametriSiapeByCodice(String codice) {
		// TODO Auto-generated method stub
		String q = "SELECT p from " + SiceeTParametriSiape.class.getName() + " p where codice=:codice";
        TypedQuery<SiceeTParametriSiape> query = entityManager.createQuery(q, SiceeTParametriSiape.class);
        query.setParameter("codice", codice);
        List<SiceeTParametriSiape> result=query.getResultList();
        if(result.size()>0) return result.get(0).getValore();
        return null;
        
	}
	

	@Override
	public List<SiceeTParametriSiape> findAllSiceeTParametriSiape() {
		// TODO Auto-generated method stub
		String q = "SELECT p from " + SiceeTParametriSiape.class.getName() + " p";
        TypedQuery<SiceeTParametriSiape> query = entityManager.createQuery(q, SiceeTParametriSiape.class);
        return query.getResultList();
	}

	@Override
	public String findNumCertificatoreById(String idCert) {
		// TODO Auto-generated method stub
		String q = "SELECT p from " + SiceeTCertificatore.class.getName() + " p where ID_CERTIFICATORE=:idCert";
        TypedQuery<SiceeTCertificatore> query = entityManager.createQuery(q, SiceeTCertificatore.class);
        query.setParameter("idCert", idCert);
        List<SiceeTCertificatore> result=query.getResultList();
        if(result.size()>0) return result.get(0).getNumCertificatore();
        return null;
        
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int updateParamDataElaborazione(String dataProc)
	{
		// FUNZIONA

		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("UPDATE " + SiceeTParametriSiape.class.getName()); 
		sbQuery.append(" SET VALORE = '"+dataProc+"'");
		sbQuery.append(" WHERE CODICE = 'DATA_ELABORAZIONE' ");
		sbQuery.append(" AND (VALORE is null "); 
		sbQuery.append(" OR (24 * (to_date('"+dataProc+"', 'DD/MM/YYYY hh24:mi:ss') - to_date(VALORE, 'DD/MM/YYYY hh24:mi:ss'))) > 5) ");

		Query query = entityManager.createQuery(sbQuery.toString());

		
		return query.executeUpdate();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateSiceeTParametriSiape(SiceeTParametriSiape siceeTParametriSiape)
	{
		entityManager.merge(siceeTParametriSiape);
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateSiceeTSiapeXml(SiceeTSiape siceeTSiape)
	{
		// Non funziona perche' c'e' l'oggetto CLOB
		//entityManager.merge(siceeTSiape);
		
		Query query = entityManager.createQuery("UPDATE " + SiceeTSiape.class.getName() + " SET XML_SIAPE = :xml WHERE ID_CERTIFICATORE = :idCert AND PROGR_CERTIFICATO = :progressivo AND ANNO = :anno AND ID_TIPO_AZIONE = :idAzione");
		
		query.setParameter("idCert", siceeTSiape.getId().getIdCertificatore());
		query.setParameter("progressivo", siceeTSiape.getId().getProgrCertificato());
		query.setParameter("anno", siceeTSiape.getId().getAnno());
		query.setParameter("idAzione", siceeTSiape.getId().getIdTipoAzione());
		query.setParameter("xml", siceeTSiape.getXmlSiape());
		query.executeUpdate();
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateSiceeTSiapeOK(SiceeTSiape siceeTSiape)
	{
		// Non funziona perche' c'e' l'oggetto CLOB
		//entityManager.merge(siceeTSiape);
		
		
		Query query = entityManager.createQuery("UPDATE " + SiceeTSiape.class.getName() + " SET DATA_CREAZIONE = :dataCreazione, FLG_DA_ELABORARE = 'N' WHERE ID_CERTIFICATORE = :idCert AND PROGR_CERTIFICATO = :progressivo AND ANNO = :anno AND ID_TIPO_AZIONE = :idAzione");
		
		query.setParameter("idCert", siceeTSiape.getId().getIdCertificatore());
		query.setParameter("progressivo", siceeTSiape.getId().getProgrCertificato());
		query.setParameter("anno", siceeTSiape.getId().getAnno());
		query.setParameter("idAzione", siceeTSiape.getId().getIdTipoAzione());
		query.setParameter("dataCreazione", siceeTSiape.getDataCreazione());
		query.executeUpdate();
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateSiceeTSiapeKO(SiceeTSiape siceeTSiape)
	{
		// Non funziona perche' c'e' l'oggetto CLOB
		//entityManager.merge(siceeTSiape);
		
		Query query = entityManager.createQuery("UPDATE " + SiceeTSiape.class.getName() + " SET FLG_DA_ELABORARE = 'N' WHERE ID_CERTIFICATORE = :idCert AND PROGR_CERTIFICATO = :progressivo AND ANNO = :anno AND ID_TIPO_AZIONE = :idAzione");
		
		query.setParameter("idCert", siceeTSiape.getId().getIdCertificatore());
		query.setParameter("progressivo", siceeTSiape.getId().getProgrCertificato());
		query.setParameter("anno", siceeTSiape.getId().getAnno());
		query.setParameter("idAzione", siceeTSiape.getId().getIdTipoAzione());
		query.executeUpdate();
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public SiceeTSiapeLog insertSiceeTSiapeLog(SiceeTSiapeLog siapeLog)
	{
		
		entityManager.persist(siapeLog);
		logger.debug("Stampo l'ID del LOG: "+siapeLog.getIdSiapeLog());
		return siapeLog;
	}
	
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateSiceeTSiapeLog(ArrayList<SiceeTSiapeLog> siapeLogList)
	{
		
		for (SiceeTSiapeLog siceeTSiapeLog : siapeLogList) {
			siceeTSiapeLog.setFlgMailInviata(APEConstants.COD_S);
			entityManager.merge(siceeTSiapeLog);
		}
	}
	
	
	

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean settaSemaforoRosso()
	{
		boolean result=false;
		// eseguo la query di update del semaforo
		Query query = entityManager.createQuery("UPDATE " + SiceeTParametriSiape.class.getName() + " SET VALORE='N' WHERE VALORE='S' and CODICE='SERVIZIO_ATTIVO'");
		
		int res = query.executeUpdate();
		
		if(res!=0)
		{
			return true;
		}	
		return result;
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean settaSemaforoVerde()
	{
		boolean result=false;
		// eseguo la query di update del semaforo
		Query query = entityManager.createQuery("UPDATE " + SiceeTParametriSiape.class.getName() + " SET VALORE='S' WHERE CODICE='SERVIZIO_ATTIVO'");
		
		int res = query.executeUpdate();
		
		if(res!=0)
		{
			return true;
		}	
		return result;
		
	}
	
	@Override
	public SiceeTCertificato findSiceeTCertificato(SiceeTCertificatoPK certificatoPK) {
		// TODO Auto-generated method stub
		
		
//		StringBuffer qB = new StringBuffer();
//		qB.append("SELECT c FROM ");
//		qB.append(SiceeTCertificato.class.getName());
//		qB.append(" WHERE AND c.id.idCertificatore = "+siapePk.getIdCertificatore());
//		qB.append(" AND c.id.progrCertificato = "+siapePk.getProgrCertificato());
//		qB.append(" AND c.id.anno = "+siapePk.getAnno());
//		
//		
//		System.out.println("Stampo la query findCertificatiDaGestire: "+qB);
//		
//		TypedQuery<SiceeTCertificato> query = entityManager.createQuery(qB.toString(), SiceeTCertificato.class);
        
		
		
		
		SiceeTCertificato siceeTCertificato = entityManager.find(SiceeTCertificato.class, certificatoPK);

		logger.debug("Stampo il risultato: "+siceeTCertificato);

		//GenericUtil.stampa(siceeTCertificato, false, 3);
		
		// Forzo il recupero del Certificatore
    	siceeTCertificato.getSiceeTCertificatore().getCognome();
    	
    	
        if (siceeTCertificato != null) {
        	
        	logger.debug("#################");
        	logger.debug("Stampo il siceeTCertificato: "+siceeTCertificato);
        	logger.debug("Stampo getIdCertificatore: "+siceeTCertificato.getId().getIdCertificatore());
        	logger.debug("Stampo getProgrCertificato: "+siceeTCertificato.getId().getProgrCertificato());
        	logger.debug("Stampo getAnno: "+siceeTCertificato.getId().getAnno());
        	logger.debug("Stampo getNumCertificatore: "+siceeTCertificato.getSiceeTCertificatore().getNumCertificatore());

        	logger.debug("#################");
			
        }
        
        return siceeTCertificato;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void provaUpdateSiceeTParametriSiape()
	{
		Query query = entityManager.createQuery("UPDATE " + SiceeTParametriSiape.class.getName() + " SET VALORE = :dataElaborazione WHERE CODICE = 'DATA_ELABORAZIONE' AND VALORE IS NULL");
		
		query.setParameter("dataElaborazione", new Date());
		int res = query.executeUpdate();
		
		logger.debug("\n\nSTAMPO IL RESULT DEL provaUpdateSiceeTSiape: "+res);
		
	}
}
