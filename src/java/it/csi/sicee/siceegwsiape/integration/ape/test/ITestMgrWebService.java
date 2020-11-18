/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.integration.ape.test;

import it.enea.siape.ws.soap.WSAPEResponse;

public interface ITestMgrWebService {
	
	public WSAPEResponse testCaricamentoApe(String xmlProva);
	public WSAPEResponse testCancellazioneApe(it.enea.siape.model.persistence.ApeAnnullati apeAnnullati);

}
