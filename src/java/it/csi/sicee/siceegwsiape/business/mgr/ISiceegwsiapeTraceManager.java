/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.business.mgr;

import java.util.ArrayList;
import java.util.List;

import it.csi.sicee.siceegwsiape.integration.db.*;
import it.csi.sicee.siceegwsiape.util.enea.siape.exception.SiapeException;

public interface ISiceegwsiapeTraceManager {


	List<SiceeTParametriSiape> findAllSiceeTParametriSiape();
	
	String findSiceeTParametriSiapeByCodice(String codice) ;
	
	int updateParamDataElaborazione(String dataProc);

	void updateSiceeTParametriSiape(SiceeTParametriSiape siceeTParametriSiape);
	
	void updateSiceeTSiapeXml(SiceeTSiape siceeTSiape);

	void updateSiceeTSiapeOK(SiceeTSiape siceeTSiape);

	void updateSiceeTSiapeKO(SiceeTSiape siceeTSiape);

	SiceeTSiapeLog insertSiceeTSiapeLog(SiceeTSiapeLog siapeLog);
	
	void updateSiceeTSiapeLog(ArrayList<SiceeTSiapeLog> siapeLogList);
	
	List<SiceeTSiape> findCertificatiDaGestire(int maxRow);
	
	SiceeTCertificato findDettaglioCertificatoDaGestire(SiceeTCertificatoPK certificatoPK) throws SiapeException;
	
	SiceeTCertificato findSiceeTCertificato(SiceeTCertificatoPK certificatoPK);

	String findNumCertificatoreById(String codice);
	
	void provaUpdateSiceeTParametriSiape();
	
	public boolean settaSemaforoRosso();
	
	public boolean settaSemaforoVerde();
	
}
