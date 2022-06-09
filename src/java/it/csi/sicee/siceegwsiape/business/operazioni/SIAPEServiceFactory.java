/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.business.operazioni;


import it.csi.sicee.siceegwsiape.util.APEConstants;
import it.csi.sicee.siceegwsiape.util.GenericUtil;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;


import org.apache.log4j.Logger;



public class SIAPEServiceFactory {
	
	private static final Logger log = Logger.getLogger(APEConstants.LOGGER_PREFIX);

	//private Service s = null;

	private String mailHost;
	private String mailPort;
	private String mailUser;
	private String mailPwd;
	
	public String getMailHost() throws Exception {
		log.debug("[SIAPEServiceFactory::getMailHost] BEGIN");

		if (mailHost == null)
		{
			Properties properties = new Properties();
			InputStream stream = this.getClass().getResourceAsStream("/contants.properties");
			properties.load(stream);
			mailHost = properties.getProperty("mail.host");
			
			log.debug("[SIAPEServiceFactory::getMailHost] Stampo mailHost: "+mailHost);
		}

		log.debug("[SIAPEServiceFactory::getMailHost] END");

		return mailHost;
	}

	public String getMailPort() throws Exception {
		log.debug("[SIAPEServiceFactory::getMailPort] BEGIN");

		if (mailPort == null)
		{
			Properties properties = new Properties();
			InputStream stream = this.getClass().getResourceAsStream("/contants.properties");
			properties.load(stream);
			mailPort = properties.getProperty("mail.port");
			
			log.debug("[SIAPEServiceFactory::getMailPort] Stampo mailPort: "+mailPort);

		}
		
		log.debug("[SIAPEServiceFactory::getMailPort] END");

		return mailPort;
	}
	
	public String getMailUser() throws Exception {
		if (mailUser == null)
		{
			Properties properties = new Properties();
			InputStream stream = this.getClass().getResourceAsStream("/contants.properties");
			properties.load(stream);
			mailUser = properties.getProperty("mail.user");
			
			log.debug("[SIAPEServiceFactory::getMailUser] Stampo mailUser: "+mailUser);

		}
		return mailUser;
	}
	
	public String getMailPwd() throws Exception {
		if (mailPwd == null)
		{
			Properties properties = new Properties();
			InputStream stream = this.getClass().getResourceAsStream("/contants.properties");
			properties.load(stream);
			mailPwd = properties.getProperty("mail.pwd");
			
			log.debug("[SIAPEServiceFactory::getMailPwd] Stampo mailPwd: "+mailPwd);

		}
		return mailPwd;
	}
	/*
	private String actaHost;
	private int actaPort = 0;


	
	
	
	public String getActaHost() {
		log.debug("[ACTAServiceFactory::getActaHost] BEGIN");

		if (actaHost == null)
		{
			try
			{
				Properties properties = new Properties();
				InputStream stream = this.getClass().getResourceAsStream("/contants.properties");
				properties.load(stream);
				actaHost = properties.getProperty("acta.host");
			}
			catch (Exception e)
			{
				log.error("[ACTAServiceFactory::getActaHost] si e' verificato un errore nel reperimento della risorsa");
			}
		}
		log.debug("[ACTAServiceFactory::getActaHost] END");

		return actaHost;
	}

	public int getActaPort() {
		log.debug("[ACTAServiceFactory::getActaPort] BEGIN");

		if (actaPort == 0)
		{  
			try
			{
				Properties properties = new Properties();
				InputStream stream = this.getClass().getResourceAsStream("/contants.properties");
				properties.load(stream);
				actaPort = GenericUtil.convertToInt(properties.getProperty("acta.port"));
			}
			catch (Exception e)
			{
				log.error("[ACTAServiceFactory::getActaPort] si e' verificato un errore nel reperimento della risorsa");
			}
		}
		log.debug("[ACTAServiceFactory::getActaPort] END");

		return actaPort;
	}

*/
	  /*
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
		    return wsSecurityPassword;
	  }
	  
	  public String getWsSecurityEncriptionUserName() throws Exception {
		    Properties properties = new Properties();
		    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
		    properties.load(stream);
		    wsSecurityEncryptionUsername = properties.getProperty("ws-security.encryption.username");
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
	  
	  */

}
