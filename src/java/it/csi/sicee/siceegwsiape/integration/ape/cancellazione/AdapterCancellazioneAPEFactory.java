/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.ape.cancellazione;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.log4j.Logger;

import it.csi.sicee.siceegwsiape.business.mgr.ISiceegwsiapeTraceManager;
import it.csi.sicee.siceegwsiape.util.APEConstants;


public class AdapterCancellazioneAPEFactory {
	
	private static final Logger log = Logger.getLogger(APEConstants.LOGGER_PREFIX);

	private Service s = null;

	private String endpoint;
	private String qname;
	private String wsSecurityUsername;
	private String wsSecurityPassword;
	private String wsSecurityEncryptionUsername;
	private String keystoreLocalPath;
	private String keystoreLocalEnabled;


	public String getEndpoint() throws Exception {
	    Properties properties = new Properties();
	    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
	    properties.load(stream);
	    endpoint = properties.getProperty("wsape.cancellazione.endpoint");
	    return endpoint;
	  }
	  
	  public String getQName() throws Exception {
		    Properties properties = new Properties();
		    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		    properties.load(stream);
		    qname = properties.getProperty("wsape.cancellazione.qname");
		    log.debug("[AdapterCancellazioneAPEFactory::getQName] qname = " + qname);
		    return qname;
	  }
	  
	  public String getWsSecurityUserName() throws Exception {
		    Properties properties = new Properties();
		    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		    properties.load(stream);
		    wsSecurityUsername = properties.getProperty("ws-security.username");
		    return wsSecurityUsername;
	  }
	  
	  public String getWsSecurityPassword() throws Exception {
		    Properties properties = new Properties();
		    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		    properties.load(stream);
		    wsSecurityPassword = properties.getProperty("ws-security.password");
		    log.debug("[AdapterCancellazioneAPEFactory::getWsSecurityPassword] wsSecurityPassword = " + wsSecurityPassword);
		    return wsSecurityPassword;
	  }
	  
	  public String getWsSecurityEncriptionUserName() throws Exception {
		    Properties properties = new Properties();
		    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		    properties.load(stream);
		    wsSecurityEncryptionUsername = properties.getProperty("ws-security.encryption.username");
			log.debug("[AdapterCancellazioneAPEFactory::getWsSecurityEncriptionUserName] wsSecurityEncryptionUsername = " + wsSecurityEncryptionUsername);
		    return wsSecurityEncryptionUsername;
	  }
	  
	  public String getKeyStoreLocalPath() throws Exception {
		    Properties properties = new Properties();
		    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		    properties.load(stream);
		    keystoreLocalPath = properties.getProperty("keystore.local.path");
		    return keystoreLocalPath;
	  }
	  
	  public String getKeyStoreLocalEnabled() throws Exception {
		    Properties properties = new Properties();
		    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		    properties.load(stream);
		    keystoreLocalEnabled = properties.getProperty("keystore.local.enabled");
		    return keystoreLocalEnabled;
	  }
	  
	  
	  public it.enea.siape.ws.soap.CancellaAPE getClientServizioCancellazione(String siapeUser, String siapePwd) throws Exception {

		if (s == null) {
		      log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Look up to APE....");
		      java.net.URL url=getClass().getResource("/CancellaAPE.wsdl");
		      
		      String qName= getQName();
		      
		      s = Service.create(url, new QName(qName, "CancellaAPEService"));
		}
		
	
		//creazione proxy per dialogare con il ws
		it.enea.siape.ws.soap.CancellaAPE client=null;
		try {
			log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Creazione client.... INIZIO");
			client = s.getPort(it.enea.siape.ws.soap.CancellaAPE.class);
			log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Creazione client.... FINE");
			/*
			 * 
			 * -----------------------------------------------------------------------------------------------------------------------------------------------
			 * NOTA:
			   Se nel file d'ambiente (es:tst-rp-01.properties) e' settata la property keystore.local.enabled = true, allora utilizza il keyStore del jboss locale 
			   e la property keystore.local.path che indica il keystore della jvm usata da jboss. 
			   
			   In questa jvm occorre caricare i certificati presenti in: conf/keystore/test--> digi-topchain.der e terena-test.der tramite utility keytool:
			   
			   Ad esempio per una jdk installata in questo percorso C:\Program Files\Java\jdk1.8.0_121\ occorre posizionarsi nella directory seguente ed eseguire:
			   
			   1) C:\Program Files\Java\jdk1.8.0_121\jre\lib\security> keytool -import -alias digi-topchain -keystore  cacerts -file digi-topchain.der 
	
			   2) C:\Program Files\Java\jdk1.8.0_121\jre\lib\security> keytool -import -alias terena-test -keystore  cacerts -file terena-test.der
			   
			   3) valorizzare nel file tst-rp-01.properties keystore.local.path = C:/Program Files/Java/jdk1.8.0_121/jre/lib/security/cacerts
			   
			   4) valorizzare nel file tst-rp-01.properties keystore.local.path = keystore.local.enabled = true
			   
			   5) eseguire il task di ant: build-ear-deploy-local
			   
			   Con il seguente comando si crea un file contenente tutti i certificati importati nel keystore:
			   
			   C:\Program Files\Java\jdk1.8.0_121\jre\lib\security>keytool -list -v -keystore cacerts  > java_cacerts.txt
			 * -----------------------------------------------------------------------------------------------------------------------------------------------
			 */
				if(getKeyStoreLocalEnabled().equalsIgnoreCase("true"))
				{	
					log.debug("[AdapterCancellazioneAPEFactory::Imposizione keyStore per esecuzione jboss locale]");
					String certificatesTrustStorePath = getKeyStoreLocalPath();
					System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
					System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
				}
				
				//opzioni di connessione
				BindingProvider bp = (BindingProvider) client;
				SOAPBinding binding = (SOAPBinding)bp.getBinding();
				binding.setMTOMEnabled(false); // disabilito esplicitamente MTOM in quanto non necessario per il servizio
				// Step 2 - Impostazione delle credenziali da validare secondo la specifica WS-Security
				
				Map<String, Object> ctx = ((BindingProvider) client).getRequestContext();
				ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getEndpoint());
				
			    log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Look up to APE completed to endpoint " + getEndpoint());
				ctx.put("ws-security.callback-handler", "it.csi.sicee.siceegwsiape.util.enea.siape.utils.ClientKeystorePasswordCallback");
				
				log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] User WS SIAPE = " + siapeUser);
				log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Pwd WS SIAPE = " + siapePwd);
				
				String properties = "clientKeystore.properties";
				ctx.put("ws-security.encryption.properties", properties);
				ctx.put("ws-security.signature.properties", properties);
				ctx.put("ws-security.encryption.username", getWsSecurityEncriptionUserName());
				ctx.put("ws-security.username", siapeUser);
				ctx.put("ws-security.password", siapePwd);
				
		//		ctx.put("ws-security.username", getWsSecurityUserName());
		//		ctx.put("ws-security.password", getWsSecurityPassword());
		} catch (Throwable t) {
			// TODO Auto-generated catch block
			log.error("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Creazione client.... Eccezione:", t);
			throw new Exception(t.getMessage());
		}
		return client;
	}
	  


}
