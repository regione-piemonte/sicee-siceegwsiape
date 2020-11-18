/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.ape.caricamento;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.AddressingFeature;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.log4j.Logger;

import it.csi.sicee.siceegwsiape.util.APEConstants;


/**
 * Factory for the services underlying the adapter towards the APE services for the CaricamentoAPE.
 *
 */
public class AdapterCaricamentoAPEFactory {
	
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
	    endpoint = properties.getProperty("wsape.caricamento.endpoint");
	    return endpoint;
	  }
	  
	  public String getQName() throws Exception {
		    Properties properties = new Properties();
		    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		    properties.load(stream);
		    qname = properties.getProperty("wsape.caricamento.qname");
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
		    log.debug("[AdapterCaricamentoAPEFactory::getWsSecurityPassword] wsSecurityPassword = " + wsSecurityPassword);
		    return wsSecurityPassword;
	  }
	  
	  public String getWsSecurityEncriptionUserName() throws Exception {
		    Properties properties = new Properties();
		    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		    properties.load(stream);
		    wsSecurityEncryptionUsername = properties.getProperty("ws-security.encryption.username");
		    log.debug("[AdapterCaricamentoAPEFactory::getWsSecurityEncriptionUserName] wsSecurityEncryptionUsername = " + wsSecurityEncryptionUsername);
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
	  
	  
	  public it.enea.siape.ws.soap.CaricamentoAPE getClientServizioCaricamento(String siapeUser, String siapePwd) throws Exception {

	      log.debug("[AdapterCaricamentoAPEFactory::getClientServizioCaricamento] Stampo s: "+s);

			if (s == null) {
			      log.debug("[AdapterCaricamentoAPEFactory::getClientServizioCaricamento] Look up to APE....");
			      
			      java.net.URL url=getClass().getResource("/CaricamentoAPE.wsdl");
			      
			      log.debug("[AdapterCaricamentoAPEFactory::getClientServizioCaricamento] Stampo url: "+url);
			      
			      
			      String qName= getQName();
			      
			      s = Service.create(url, new QName(qName, "CaricamentoAPEService"));

			      log.debug("[AdapterCaricamentoAPEFactory::getClientServizioCaricamento] Stampo s dopo: "+s.getWSDLDocumentLocation());

			}
			
			it.enea.siape.ws.soap.CaricamentoAPE client=null;
			try {
			
				log.debug("[AdapterCaricamentoAPEFactory::getClientServizioCaricamento] Creazione client.... INIZIO");
				
				WebServiceFeature addressingFeature = new AddressingFeature();
				
				client = ((it.enea.siape.ws.soap.CaricamentoAPE)s.getPort(it.enea.siape.ws.soap.CaricamentoAPE.class, new WebServiceFeature[] { addressingFeature }));
				Client clientListenter = ClientProxy.getClient(client);
				clientListenter.getInInterceptors().add(new LoggingInInterceptor());
				clientListenter.getOutInterceptors().add(new LoggingOutInterceptor());
				
				log.debug("[AdapterCaricamentoAPEFactory::getClientServizioCaricamento] Creazione client.... FINE");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("[AdapterCaricamentoAPEFactory::getClientServizioCaricamento] Creazione client.... Eccezione:", e);
				e.printStackTrace();
			}
			
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
				log.debug("[AdapterCaricamentoAPEFactory::Imposizione keyStore per esecuzione jboss locale]");
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
			
		    log.debug("[AdapterCaricamentoAPEFactory::getClientServizioCaricamento] Look up to APE completed to endpoint " + getEndpoint());

			ctx.put("ws-security.callback-handler", "it.csi.sicee.siceegwsiape.util.enea.siape.utils.ClientKeystorePasswordCallback");
	    
			log.debug("[AdapterCaricamentoAPEFactory::getClientServizioCaricamento] User WS SIAPE = " + siapeUser);
			log.debug("[AdapterCaricamentoAPEFactory::getClientServizioCaricamento] Pwd WS SIAPE = " + siapePwd);
			
			String properties = "clientKeystore.properties";
			ctx.put("ws-security.encryption.properties", properties);
			ctx.put("ws-security.signature.properties", properties);
			ctx.put("ws-security.encryption.username", getWsSecurityEncriptionUserName());
			ctx.put("ws-security.username", siapeUser);
			ctx.put("ws-security.password", siapePwd);
//			ctx.put("ws-security.username", getWsSecurityUserName());
//			ctx.put("ws-security.password", getWsSecurityPassword());

			/*
		    log.debug("[AdapterCaricamentoAPEFactory::getService] stampo properties: " + properties);
		    log.debug("[AdapterCaricamentoAPEFactory::getService] stampo getWsSecurityEncriptionUserName: " + getWsSecurityEncriptionUserName());
		    log.debug("[AdapterCaricamentoAPEFactory::getService] stampo getWsSecurityUserName: " + getWsSecurityUserName());
		    log.debug("[AdapterCaricamentoAPEFactory::getService] stampo getWsSecurityPassword: " + getWsSecurityPassword());

		    log.debug("[AdapterCaricamentoAPEFactory::getService] stampo client.recuperaDimensioneMassimaLottoAPE(): "+client.recuperaDimensioneMassimaLottoAPE());
			*/
			
			return client;
		}
	  
	
}
