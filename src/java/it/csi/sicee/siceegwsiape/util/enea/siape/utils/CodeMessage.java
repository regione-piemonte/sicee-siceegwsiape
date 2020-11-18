/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util.enea.siape.utils;

public enum CodeMessage
{
  
	ERRORE_CREAZIONE_XML(50, "errore creazione XML"),
	ERRORE_SISTEMA(100, "errore di sistema");
	
	/*
	OK(0, "APE inserito correttamente."), 
  DATI_INCONGRUENTI(1, "I dati inseriti nell'Ape in XML non rispettano i vincoli dati."), 
  NUMERO_APE_ECCESSIVI(2, "Il numero di Ape inseriti supera la soglia massima fissata."), 
  FILE_INESISTENTE(3, "Verificare il percorso del file."), 
  PARSER_NON_INIZIALIZZATO(4, "I dati inseriti nell'Ape in XML non rispettano i vincoli dati"), 
  DATABASE_NON_DISPONIBILE(5, "Problemi nel caricamento dell'APE. Riprovare o contattare il gestore del SIAPE."), 
  DATO_DUPLICATO(6, "E' stato rilevato un APE duplicato e pertanto non inserito nel SIAPE."), 
  APE(7, "Ape in allegato"), 
  CODICI_ISTAT_INCONGRUENTI(8, "Il codice ISTAT dell'APE e dell'utenza in uso non coincidono, pertanto non inserito nel SIAPE"), 
  INVALIDAZIONE_KO(9, "APE da invalidare non presente nella banca dati"), 
  INVALIDAZIONE_OK(10, "APE correttamente invalidato"), 
  INVALIDAZIONE_DUPLICATO(11, "APE gia' invalidato, verificare dati"), 
  INVALIDAZIONE_CODICI_ISTAT_INCONGRUENTI(12, "Il codice ISTAT dell'APE da invalidare e dell'utenza in uso non coincidono, invalidazione non inserita nel SIAPE");
	*/
	
  private final int mCodice;
  private final String mMessaggio;

  private CodeMessage(int codice, String messaggio) { this.mCodice = codice;
    this.mMessaggio = messaggio; }

  public int getCodice()
  {
    return this.mCodice;
  }

  public String getMessaggio() {
    return this.mMessaggio;
  }

  public String toString()
  {
    return "Codice " + getCodice() + ": " + getMessaggio();
  }
}