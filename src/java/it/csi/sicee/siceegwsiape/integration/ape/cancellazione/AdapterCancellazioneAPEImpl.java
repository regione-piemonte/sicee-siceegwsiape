/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.ape.cancellazione;

import java.util.Date;

import org.apache.log4j.Logger;

import it.csi.sicee.siceegwsiape.util.APEConstants;
import it.csi.util.performance.StopWatch;
import it.enea.siape.ws.soap.WSAPEResponse;

public class AdapterCancellazioneAPEImpl {

  private static final Object LOCK = new Object();
  private Logger log = Logger.getLogger(APEConstants.LOGGER_PREFIX);

  public AdapterCancellazioneAPEFactory factory = new AdapterCancellazioneAPEFactory();

  private static volatile AdapterCancellazioneAPEImpl instance = null;
  
  
  public static AdapterCancellazioneAPEImpl getInstance() {
    if (instance == null) {
      synchronized(LOCK) {
        if(instance == null) {
        	instance = new AdapterCancellazioneAPEImpl();
        }
      }
    }
    return instance;
  }
  
  public WSAPEResponse cancellazioneApe(it.enea.siape.model.persistence.ApeAnnullati apeAnnullati, String siapeUser, String siapePwd) throws Exception {
	    StopWatch watcher = new StopWatch(APEConstants.LOGGER_PREFIX);
	    watcher.start();  
	    WSAPEResponse response;
	    try {

//			//Step 1 - Invio della richiesta al WS

			it.enea.siape.ws.soap.CancellaAPE client = factory.getClientServizioCancellazione(siapeUser, siapePwd);
		
		    if(client!=null)
		    {	
		    	response= client.cancellaAPE(apeAnnullati);
		    }
		    else
		    {
		    	response = new WSAPEResponse();
		    	response.setMessaggio("AdapterCancellazioneAPEImpl :: NON e' stato possibile reperire il client!");
		    }
			return response;
	    }
	    catch (Throwable t)
	    {
	    	log.error("Eccezione nella chiamata a cancellazione APE", t);
	    	t.printStackTrace();
	    	throw new Exception(t.getMessage());
	    }
	    finally {
	      watcher.dumpElapsed("AdapterCancellazioneAPEImpl", "cancellazioneApe()", "invocazione servizio [APE::cancellazioneApe]", "");
	      watcher.stop();
	    }
  }


}
