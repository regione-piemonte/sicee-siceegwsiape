/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.ape.cancellazione;

import org.apache.log4j.Logger;

import it.csi.sicee.siceegwsiape.util.APEConstants;
import it.csi.util.performance.StopWatch;
import it.enea.siape.ws.soap.WSAPEResponse;

public class AdapterCancellazioneAPEImpl {

	private static final Object LOCK = new Object();
	private Logger log = Logger.getLogger(APEConstants.LOGGER_PREFIX);

	private static AdapterCancellazioneAPEFactory factory;

	private static volatile AdapterCancellazioneAPEImpl instance;

	public static AdapterCancellazioneAPEImpl getInstance(String siapeUser, String siapePwd) throws Exception {
		if (instance == null) {
			synchronized (LOCK) {
				if (instance == null) {
					instance = new AdapterCancellazioneAPEImpl();
					factory = new AdapterCancellazioneAPEFactory(siapeUser, siapePwd);
				}
			}
		}
		return instance;
	}

	public WSAPEResponse cancellazioneApe(it.enea.siape.model.persistence.ApeAnnullati apeAnnullati) throws Exception {
		StopWatch watcher = new StopWatch(APEConstants.LOGGER_PREFIX);
		watcher.start();
		try {

			// //Step 1 - Invio della richiesta al WS

			return factory.cancellaAPE(apeAnnullati);
		} catch (Throwable t) {
			log.error("Eccezione nella chiamata a cancellazione APE", t);
			t.printStackTrace();
			throw new Exception(t.getMessage());
		} finally {
			watcher.dumpElapsed("AdapterCancellazioneAPEImpl", "cancellazioneApe()", "invocazione servizio [APE::cancellazioneApe]", "");
			watcher.stop();
		}
	}

}
