/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.ape.test;

import java.io.Reader;
import java.io.StringReader;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

import it.csi.sicee.siceegwsiape.business.mgr.ISiceegwsiapeTraceManager;
import it.csi.sicee.siceegwsiape.business.mgr.SiceegwsiapeTraceManager;
import it.csi.sicee.siceegwsiape.integration.ape.cancellazione.AdapterCancellazioneAPEImpl;
import it.csi.sicee.siceegwsiape.integration.ape.caricamento.AdapterCaricamentoAPEImpl;
import it.csi.sicee.siceegwsiape.util.APEConstants;


@WebService(name = "TestMgrWebServiceWS", targetNamespace = "http://it/csi/sicee/siceegwsiape/integration/ape/test", serviceName = "TestMgrWebService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@Remote(ITestMgrWebService.class)
@Stateless(name = "TestMgrWebServiceSL")

public class TestMgrWebService implements ITestMgrWebService {
	
private static volatile TestMgrWebService instance; 
@EJB
private ISiceegwsiapeTraceManager traceMgr;

	private Logger log = Logger.getLogger(APEConstants.LOGGER_PREFIX);
	
	
	public static TestMgrWebService getInstance() {
		// Evito instanziazione multipla
		if(instance==null) {
			synchronized(SiceegwsiapeTraceManager.class) {
				if(instance == null) {
					
					instance = new TestMgrWebService();
				}
			}
		}	
		return instance;
	}


	@Override
	public it.enea.siape.ws.soap.WSAPEResponse testCaricamentoApe(String xmlProva) {
		// TODO Auto-generated method stub
	
		it.enea.siape.ws.soap.WSAPEResponse response = null;
		try {
			
			AdapterCaricamentoAPEImpl adapter = AdapterCaricamentoAPEImpl.getInstance();
			
			Reader reader = new StringReader(xmlProva);
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(reader);

			// Recupero la user e la pwd del servizio SIAPE
			String siapeUser = traceMgr.findSiceeTParametriSiapeByCodice(APEConstants.SIAPE_USER);
			String siapePwd = traceMgr.findSiceeTParametriSiapeByCodice(APEConstants.SIAPE_PWD);

			response=adapter.caricamentoApe(xmlReader, siapeUser, siapePwd);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
		
	}


	@Override
	public it.enea.siape.ws.soap.WSAPEResponse  testCancellazioneApe(it.enea.siape.model.persistence.ApeAnnullati apeAnnullati) {
		// TODO Auto-generated method stub
		it.enea.siape.ws.soap.WSAPEResponse response = null;
		
		try {
			AdapterCancellazioneAPEImpl adapter = AdapterCancellazioneAPEImpl.getInstance();

			// Recupero la user e la pwd del servizio SIAPE
			String siapeUser = traceMgr.findSiceeTParametriSiapeByCodice(APEConstants.SIAPE_USER);
			String siapePwd = traceMgr.findSiceeTParametriSiapeByCodice(APEConstants.SIAPE_PWD);
	
			response=adapter.cancellazioneApe(apeAnnullati, siapeUser, siapePwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}
