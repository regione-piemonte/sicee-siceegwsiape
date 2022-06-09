/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.ape.caricamento;

import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

import it.csi.sicee.siceegwsiape.jaxb.lib.Ape2015;
import it.csi.sicee.siceegwsiape.util.APEConstants;
import it.csi.sicee.siceegwsiape.util.GsonUtils;
import it.csi.sicee.siceegwsiape.util.enea.siape.utils.ApeXmlParser;
import it.csi.util.performance.StopWatch;

public class AdapterCaricamentoAPEImpl {

	private static final Object LOCK = new Object();
	private Logger log = Logger.getLogger(APEConstants.LOGGER_PREFIX);

	private static AdapterCaricamentoAPEFactory factory;

	private static volatile AdapterCaricamentoAPEImpl instance;

	public static AdapterCaricamentoAPEImpl getInstance(String siapeUser, String siapePwd) throws Exception {
		if (instance == null) {
			synchronized (LOCK) {
				if (instance == null) {
					instance = new AdapterCaricamentoAPEImpl();
					factory = new AdapterCaricamentoAPEFactory(siapeUser, siapePwd);
				}
			}
		}
		return instance;
	}

	public it.enea.siape.ws.soap.WSAPEResponse caricamentoApe(XMLStreamReader apeXml) throws Exception {
		StopWatch watcher = new StopWatch(APEConstants.LOGGER_PREFIX);
		watcher.start();
		try {

			// Step 1 - Parsing del file XML
			ApeXmlParser apeXmlParser = new ApeXmlParser();
			Ape2015 ape2015 = apeXmlParser.parseToApe2015(apeXml);

			// converto da jaxb a it.enea.siape.model.ape.Ape2015 per invio a WS

			it.enea.siape.model.ape.Ape2015 xmlApe2015 = GsonUtils.remap(ape2015,
					it.enea.siape.model.ape.Ape2015.class);

			// Step 2 - Invio della richiesta al WS

			return factory.caricaAPE(xmlApe2015);
		} catch (Throwable t) {
			log.error("Eccezione nella chiamata a caricamento APE", t);
			t.printStackTrace();
			throw new Exception(t.getMessage());
		} finally {
			watcher.dumpElapsed("AdapterCaricamentoAPEImpl", "caricamentoApe()", "invocazione servizio [APE::caricamentoApe]", "");
			watcher.stop();
		}
	}

}
