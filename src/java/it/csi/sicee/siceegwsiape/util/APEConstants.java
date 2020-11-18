/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util;

public interface APEConstants {

	String LOGGER_PREFIX = "siceegwsiape";

	public static final String DATA_ELABORAZIONE = "DATA_ELABORAZIONE";

	public static final String COD_ISTAT_PIEMONTE = "01";
	public static final String DESC_PIEMONTE = "PIEMONTE";

	public static final String INFO_COPYRIGHT = "FORMATO XML APE2015 STANDARD NAZIONALE";
	public static final String INFO_VERSIONE = "12.00";

	public static final String DESC_RIF_CATASTO_NCT = "NCT";
	public static final String DESC_RIF_CATASTO_NCEU = "NCEU";
	public static final String DESC_RIF_CATASTO_CT = "CT";

	public static final short ID_RIF_CATASTO_NCT = 0;
	public static final short ID_RIF_CATASTO_NCEU = 1;
	public static final short ID_RIF_CATASTO_CT = 2;

	public static final int MAX_ALTRI_SUBALTERNI = 20;

	public static final String COD_S = "S";
	public static final String COD_N = "N";

	public static final int ID_COMBUSTIBILE_ENERGIAELETTRICA = 200;
	public static final int ID_COMBUSTIBILE_GASNATURALE = 201;
	public static final int ID_COMBUSTIBILE_GPL = 202;
	public static final int ID_COMBUSTIBILE_CARBONE = 203;

	public static final int ID_COMBUSTIBILE_GASOLIO = 204;
	public static final int ID_COMBUSTIBILE_GASOL = 214;
	public static final int ID_COMBUSTIBILE_OLIO_COMBUSTIBILE = 215;

	public static final int ID_COMBUSTIBILE_BIOMASSESOLIDE = 205;
	public static final int ID_COMBUSTIBILE_BIOMASSELIQUIDE = 206;
	public static final int ID_COMBUSTIBILE_BIOMASSEGASSOSE = 207;
	public static final int ID_COMBUSTIBILE_SOLAREFOTOVOLTAICO = 208;
	public static final int ID_COMBUSTIBILE_SOLARETERMICO = 209;
	public static final int ID_COMBUSTIBILE_EOLICO = 210;
	public static final int ID_COMBUSTIBILE_TELERISCALDAMENTO = 211;
	public static final int ID_COMBUSTIBILE_TELERAFFRESCAMENTO = 212;
	public static final int ID_COMBUSTIBILE_ALTRO = 213;

	public static final String DESC_IMPIANTO_SIMULATO = "SIMULATO IN QUANTO ASSENTE";

	public static final int SEZIONE_CLIMAINVER = 1;
	public static final int SEZIONE_CLIMAEST = 2;
	public static final int SEZIONE_ACQUACALDA = 3;
	public static final int SEZIONE_IMPIANTICOMBINATI = 4;
	public static final int SEZIONE_PRODFONTIRINN = 5;
	public static final int SEZIONE_VENTMECC = 6;
	public static final int SEZIONE_ILLUMINAZIONE = 7;
	public static final int SEZIONE_TRASPORTO = 8;

	public static final short ID_DICHIARAZIONE_INDIPENDENZA = 1;

	public static final int ID_TIPO_AZIONE_INSERIMENTO = 1;
	public static final int ID_TIPO_AZIONE_CANCELLAZIONE = 2;

	public static final String SERVIZIO_ATTIVO = "SERVIZIO_ATTIVO";
	public static final String NUMERO_MAX_RECORD = "NUMERO_MAX_RECORD";
	public static final String MITTENTE_MAIL = "MITTENTE_MAIL";
	public static final String DESTINATARIO_MAIL_RIASSUNTIVA = "DESTINATARIO_MAIL_RIASSUNTIVA";

	public static final String SIAPE_USER = "SIAPE_USER";
	public static final String SIAPE_PWD = "SIAPE_PWD";

	public static final int MAX_LUNGHEZZA_200 = 200;


	public static final String LABEL_S = "S";
	public static final String LABEL_N = "N";

	// CODICI DI ERRORE INTERNI
	
	public static final int ERRORE_CREAZIONE_XML = 50;
	public static final int ERRORE_SISTEMA = 100;
	
	// CODICI DI RITORNO NELLA INVOCAZIONE DEL WS SIAPE - CARICAMENTO
	// --------------------------------------------------------------------
	public static final int RESULT_CODE_APE_INSERITO_CORRETTAMENTE=0;
	public static final int RESULT_CODE_APE_NON_CONFORME=1;
	public static final int RESULT_CODE_APE_SOGLIA_MASSIMA_SUPERATA=2;
	public static final int RESULT_CODE_APE_PROBLEMA_GENERICO=5;
	public static final int RESULT_CODE_APE_DUPLICATO=6;
	public static final int RESULT_CODE_APE_ISTAT_NON_COINCIDE=8;
	// --------------------------------------------------------------------
	
	// CODICI DI RITORNO NELLA INVOCAZIONE DEL WS SIAPE - CANCELLAZIONE
	// --------------------------------------------------------------------
	public static final int RESULT_CODE_APE_NON_PRESENTE=9;
	public static final int RESULT_CODE_APE_INVALIDATO_CORRETTAMENTE=10;
	public static final int RESULT_CODE_APE_INVALIDATO_ESISTE=11;
	public static final int RESULT_CODE_APE_INVALIDATO_ISTAT_NON_COINCIDE=12;
	// --------------------------------------------------------------------

	static final String PARAM_DISABILITA_VERIFICA_XSD = "DisabilitaVerificaXSD";


	static final String SAML2_UTENTE = "SAML2Utente";
	static final String SAML2_PWD_KEYSTORE = "SAML2PwdKeystore";
	static final String SAML2_ID_POSTAZIONE_SERVER = "SAML2IdPostazioneServer";
	static final String SAML2_ALIAS_CERTIFICATO_SERVER = "SAML2AliasCertificatoServer";
	static final String SAML2_USER_ANPR = "SAML2UserAnpr";
	static final String SAML2_PWD_CERTIFICATO_SERVER = "SAML2PwdCertificatoServer";
	static final String SAML2_UTENTI_TEST = "SAML2UtentiTest";

}
