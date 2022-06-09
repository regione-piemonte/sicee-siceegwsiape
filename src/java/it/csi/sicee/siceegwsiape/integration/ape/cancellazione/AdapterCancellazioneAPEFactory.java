/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.ape.cancellazione;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.log4j.Logger;

import it.csi.sicee.siceegwsiape.util.APEConstants;
import it.enea.siape.exception.SiapeException;
import it.enea.siape.model.persistence.ApeAnnullati;
import it.enea.siape.ws.soap.CancellaAPE;
import it.enea.siape.ws.soap.WSAPEResponse;

public class AdapterCancellazioneAPEFactory implements CancellaAPE {

	private static final Logger log = Logger.getLogger(APEConstants.LOGGER_PREFIX);

	private CancellaAPE client;

	private Service service;

	private String endpoint;
	private String qname;
	private String wsSecurityUsername;
	private String wsSecurityPassword;
	private String wsSecurityEncryptionUsername;
	private String keystoreLocalPath;
	private String keystoreLocalEnabled;

	public AdapterCancellazioneAPEFactory(String siapeUser, String siapePwd) throws Exception {

		if (this.service == null) {
			log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Look up to APE....");

			this.service = Service.create(new URL(getEndpoint()), new QName(getQName(), "CancellaAPEService"));
			
			log.debug("[AdapterCaricamentoAPEFactory::getClientServizioCancellazione] Stampo service dopo: " + this.service.getWSDLDocumentLocation());
		}

		// creazione proxy per dialogare con il ws
		try {
			log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Creazione client.... INIZIO");
			this.client = this.service.getPort(CancellaAPE.class);
			log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Creazione client.... FINE");
			/*
			 * 
			 * -----------------------------------------------------------------------------
			 * ------------------------------------------------------------------ NOTA: Se
			 * nel file d'ambiente (es:tst-rp-01.properties) e' settata la property
			 * keystore.local.enabled = true, allora utilizza il keyStore del jboss locale e
			 * la property keystore.local.path che indica il keystore della jvm usata da
			 * jboss.
			 * 
			 * In questa jvm occorre caricare i certificati presenti in:
			 * conf/keystore/test--> digi-topchain.der e terena-test.der tramite utility
			 * keytool:
			 * 
			 * Ad esempio per una jdk installata in questo percorso C:\Program
			 * Files\Java\jdk1.8.0_121\ occorre posizionarsi nella directory seguente ed
			 * eseguire:
			 * 
			 * 1) C:\Program Files\Java\jdk1.8.0_121\jre\lib\security> keytool -import
			 * -alias digi-topchain -keystore cacerts -file digi-topchain.der
			 * 
			 * 2) C:\Program Files\Java\jdk1.8.0_121\jre\lib\security> keytool -import
			 * -alias terena-test -keystore cacerts -file terena-test.der
			 * 
			 * 3) valorizzare nel file tst-rp-01.properties keystore.local.path = C:/Program
			 * Files/Java/jdk1.8.0_121/jre/lib/security/cacerts
			 * 
			 * 4) valorizzare nel file tst-rp-01.properties keystore.local.path =
			 * keystore.local.enabled = true
			 * 
			 * 5) eseguire il task di ant: build-ear-deploy-local
			 * 
			 * Con il seguente comando si crea un file contenente tutti i certificati
			 * importati nel keystore:
			 * 
			 * C:\Program Files\Java\jdk1.8.0_121\jre\lib\security>keytool -list -v
			 * -keystore cacerts > java_cacerts.txt
			 * -----------------------------------------------------------------------------
			 * ------------------------------------------------------------------
			 */
//			if (getKeyStoreLocalEnabled().equalsIgnoreCase("true")) {
//				log.debug("[AdapterCancellazioneAPEFactory::Imposizione keyStore per esecuzione jboss locale]");
//				String certificatesTrustStorePath = getKeyStoreLocalPath();
//				System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
//				System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//			}

			// opzioni di connessione
			BindingProvider bp = (BindingProvider) this.client;
			SOAPBinding binding = (SOAPBinding) bp.getBinding();
			binding.setMTOMEnabled(false); // disabilito esplicitamente MTOM in quanto non necessario per il servizio
			// Step 2 - Impostazione delle credenziali da validare secondo la specifica
			// WS-Security

			Map<String, Object> ctx = ((BindingProvider) this.client).getRequestContext();
			ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getEndpoint());

			log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Look up to APE completed to endpoint " + getEndpoint());

			log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] User WS SIAPE = " + siapeUser);
			log.debug("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Pwd WS SIAPE = " + siapePwd);

			ctx.put("ws-security.username", siapeUser);
			ctx.put("ws-security.password", siapePwd);

		} catch (Throwable t) {
			// TODO Auto-generated catch block
			log.error("[AdapterCancellazioneAPEFactory::getClientServizioCancellazione] Creazione client.... Eccezione:", t);
			throw new Exception(t.getMessage());
		}
	}

	public WSAPEResponse cancellaAPE(ApeAnnullati apeAnnullati) throws SiapeException {
		return this.client.cancellaAPE(apeAnnullati);
	}

	private String getEndpoint() throws Exception {
		Properties properties = new Properties();
		InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		properties.load(stream);
		this.endpoint = properties.getProperty("wsape.cancellazione.endpoint");
		return this.endpoint;
	}

	private String getQName() throws Exception {
		Properties properties = new Properties();
		InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		properties.load(stream);
		this.qname = properties.getProperty("wsape.cancellazione.qname");
		log.debug("[AdapterCancellazioneAPEFactory::getQName] qname = " + this.qname);
		return this.qname;
	}

	private String getWsSecurityUserName() throws Exception {
		Properties properties = new Properties();
		InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		properties.load(stream);
		this.wsSecurityUsername = properties.getProperty("ws-security.username");
		return this.wsSecurityUsername;
	}

	private String getWsSecurityPassword() throws Exception {
		Properties properties = new Properties();
		InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		properties.load(stream);
		this.wsSecurityPassword = properties.getProperty("ws-security.password");
		log.debug("[AdapterCancellazioneAPEFactory::getWsSecurityPassword] wsSecurityPassword = "
				+ this.wsSecurityPassword);
		return this.wsSecurityPassword;
	}

	private String getWsSecurityEncriptionUserName() throws Exception {
		Properties properties = new Properties();
		InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		properties.load(stream);
		this.wsSecurityEncryptionUsername = properties.getProperty("ws-security.encryption.username");
		log.debug("[AdapterCancellazioneAPEFactory::getWsSecurityEncriptionUserName] wsSecurityEncryptionUsername = "
				+ this.wsSecurityEncryptionUsername);
		return this.wsSecurityEncryptionUsername;
	}

	private String getKeyStoreLocalPath() throws Exception {
		Properties properties = new Properties();
		InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		properties.load(stream);
		this.keystoreLocalPath = properties.getProperty("keystore.local.path");
		return this.keystoreLocalPath;
	}

	private String getKeyStoreLocalEnabled() throws Exception {
		Properties properties = new Properties();
		InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		properties.load(stream);
		this.keystoreLocalEnabled = properties.getProperty("keystore.local.enabled");
		return this.keystoreLocalEnabled;
	}

}
