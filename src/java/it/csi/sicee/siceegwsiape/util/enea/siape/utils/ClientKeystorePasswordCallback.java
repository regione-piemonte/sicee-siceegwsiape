/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util.enea.siape.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wss4j.common.ext.WSPasswordCallback;

public class ClientKeystorePasswordCallback
  implements CallbackHandler
{
  private static final Logger logger = LogManager.getLogger(ClientKeystorePasswordCallback.class);

  private Map<String, String> passwords = new HashMap();
  
  private String wsSecurityEncryptionUsername;

  public ClientKeystorePasswordCallback()
  {
    this.passwords.put(getWsSecurityEncriptionUserName(), "");
  }

  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    for (int i = 0; i < callbacks.length; i++) {
      WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];
      String pass = (String)this.passwords.get(pc.getIdentifier());
      if (pass != null) {
        logger.info("Decifrando la connessione");
        pc.setPassword(pass);
        return;
      }
    }
  }
  
  public String getWsSecurityEncriptionUserName() {
	    try {
			Properties properties = new Properties();
			InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
			properties.load(stream);
			wsSecurityEncryptionUsername = properties.getProperty("ws-security.encryption.username");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
	    return wsSecurityEncryptionUsername;
}
}
