/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.business.timer;

import it.csi.sicee.siceegwsiape.business.dto.Mail;
import it.csi.sicee.siceegwsiape.business.mgr.ISiceegwsiapeTraceManager;
import it.csi.sicee.siceegwsiape.business.operazioni.SIAPEService;
import it.csi.sicee.siceegwsiape.integration.ape.cancellazione.AdapterCancellazioneAPEImpl;
import it.csi.sicee.siceegwsiape.integration.ape.caricamento.AdapterCaricamentoAPEImpl;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTCertificato;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTCertificatore;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTParametriSiape;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTSiape;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTSiapeLog;
import it.csi.sicee.siceegwsiape.jaxb.lib.Ape2015;
import it.csi.sicee.siceegwsiape.util.APEConstants;
import it.csi.sicee.siceegwsiape.util.GenericUtil;
import it.csi.sicee.siceegwsiape.util.MapDto;
import it.csi.sicee.siceegwsiape.util.enea.siape.utils.ApeXmlParser;
import it.csi.sicee.siceegwsiape.util.enea.siape.utils.CodeMessage;
import it.enea.siape.model.persistence.ApeAnnullati.AnnullatiKey;
import it.csi.sicee.siceegwsiape.util.enea.siape.exception.SiapeException;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;
import org.xml.sax.SAXParseException;


import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;

/**
 * Bean per le operazioni schedulate.
 */
@Singleton
public class KronosMgrSessionBean {

  private static Logger logger = Logger.getLogger(APEConstants.LOGGER_PREFIX);


  @EJB
  private ISiceegwsiapeTraceManager traceMgr;

  //private Logger log = Logger.getLogger(APEConstants.LOGGER_PREFIX);

//  @Resource(lookup = "java:jboss/mail/Default")
//  private Session mailSession;

  public static void main(String[] args) {
	  System.out.println("[KronosMgrSessionBean::main] START");

	  try
	  {
		  BigDecimal number = new BigDecimal(123.55123);
		  number = number.setScale(0, BigDecimal.ROUND_HALF_UP);
		  System.out.println("Stampo il number: "+number);

	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
		  logger.error(e);
	  }
	  System.out.println("[KronosMgrSessionBean::main] END");

  }
  
  /**
   * Metodo richiamato automaticamente ogni X minuti o altro da configurare tramite annotation.
   */
  //@Schedule(minute = "*/1", hour = "*", dayOfWeek = "Sat,Sun", persistent = false)
  //@Schedule(hour="*/1", persistent=false)
  
  // Commentato per provare ogni 30 minuti
  //@Schedule(hour = "*/1", dayOfWeek = "Mon-Fri", persistent = false)
  
  //@Schedule(minute = "*/1", hour = "*", dayOfWeek = "Mon-Fri", persistent = false) // PER TEST

  
  // Commentato per provare ogni 5 minuti
  //@Schedule(minute = "*/5", hour = "*", persistent = false)
  @Schedule(minute = "*/30", hour = "*", dayOfWeek = "Mon-Fri", persistent = false)
  public void iniziaElaborazione() {

	  Timestamp inizioProcesso = GenericUtil.getInizioProcesso();
	  String inizioProcessoFormat = GenericUtil.convertToString(inizioProcesso);
	  
	  SIAPEService siapeSrv = new SIAPEService();

	  Calendar now = Calendar.getInstance();
	  int hour = now.get(Calendar.HOUR_OF_DAY);
	  String numMaxRecords = null;
	  String mittente = null;
	  String destinatarioMailRiass = null;
	  List<SiceeTParametriSiape> paramSiape = null;

	  logger.info("[KronosMgrSessionBean::iniziaElaborazione] STARTED! " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));

	  
	  try
	  {
		  int apeGestiti = 0;
		  int apeGeneratoXml = 0;
		  int apeInviati = 0;
		  int apeCancellati = 0;

		  // Recupero tutti i parametri della configurazione
		  paramSiape = traceMgr.findAllSiceeTParametriSiape();
		  //logger.debug(paramSiape.size());

		  boolean isServizioAttivo = APEConstants.COD_S.equalsIgnoreCase(GenericUtil.recuperaValParametro(paramSiape, APEConstants.SERVIZIO_ATTIVO));

		  if (isServizioAttivo)
		  {
			  boolean isGestibile = isGestibile(inizioProcesso);
			  
			  if (isGestibile)
			  {
				  logger.info("E' GESTIBILE - non c'e' nessun processo attivo");

				  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - aggiorno DATA_ELABORAZIONE con: "+GenericUtil.convertToString(inizioProcesso));

				  aggiornaParametro(paramSiape, APEConstants.DATA_ELABORAZIONE, GenericUtil.convertToString(inizioProcesso));

				  mittente = GenericUtil.recuperaValParametro(paramSiape, APEConstants.MITTENTE_MAIL);
				  destinatarioMailRiass = GenericUtil.recuperaValParametro(paramSiape, APEConstants.DESTINATARIO_MAIL_RIASSUNTIVA);

				  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - stampo mittente (DB): "+mittente);
				  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - stampo destinatarioMailRiass (DB): "+destinatarioMailRiass);


				  numMaxRecords = GenericUtil.recuperaValParametro(paramSiape, APEConstants.NUMERO_MAX_RECORD);

				  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - stampo numMaxRecords (DB): "+numMaxRecords);

				  List<SiceeTSiape> siapeList = traceMgr.findCertificatiDaGestire(GenericUtil.convertToInt(numMaxRecords));
				  logger.debug("Stampo la lista di siape: "+siapeList);

				  ApeXmlParser apeXmlParser = new ApeXmlParser();

				  Ape2015 ape2015 = null;
				  ArrayList<SiceeTSiapeLog> siapeLogList = new ArrayList<SiceeTSiapeLog>();
				  SiceeTSiapeLog siapeLog = null;
				  ArrayList<String> elencoErrori = new ArrayList<String>();
				  SiceeTCertificato siceeTCertificato = null;
				  String numCertificatore = null;

				  apeGestiti = siapeList.size();

				  for (SiceeTSiape siceeTSiape : siapeList) {
					  // Sono tutti i certificati che devo gestire

					  numCertificatore = null;
					  siceeTCertificato = null;
					  try
					  {

						  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - --------------------------------------");
						  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - gestisco siceeTSiape.getId: "+siceeTSiape.getId());
						  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - gestisco siceeTSiape.getId.getIdCertificatore(): "+siceeTSiape.getId().getIdCertificatore());
						  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - gestisco siceeTSiape.getId.getProgrCertificato(): "+siceeTSiape.getId().getProgrCertificato());
						  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - gestisco siceeTSiape.getId.getAnno(): "+siceeTSiape.getId().getAnno());
						  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - gestisco siceeTSiape.getId.getIdTipoAzione(): "+siceeTSiape.getId().getIdTipoAzione());

						  // Devo verificare il tipo di azione

						  if (siceeTSiape.getId().getIdTipoAzione() == APEConstants.ID_TIPO_AZIONE_INSERIMENTO)
						  {
							  // Devo inserire
							  String xml = null;


							  // Devo capire se ho gia' creato l'XML
							  if (GenericUtil.isNullOrEmpty(siceeTSiape.getXmlSiape()))
							  {

								  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - l'XML e' nullo");

								  // Devo creare l'XML

								  // Recupero il certificato completo
								  siceeTCertificato = traceMgr.findDettaglioCertificatoDaGestire(MapDto.mapToCertificatoPK(siceeTSiape.getId()));

								  try
								  {
									  ape2015 = MapDto.mapToApe2015(siceeTCertificato);

									  xml = apeXmlParser.marshallApe(ape2015);

									  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - Stampo l'XML da passare a SIAPE: "+xml);
									  
									  // Setto l'xml
									  siceeTSiape.setXmlSiape(xml);
									  //siceeTSiape.setDataCreazione(new Date());

									  logger.debug("## prima del salvataggio dell'xml sul DB");

									  traceMgr.updateSiceeTSiapeXml(siceeTSiape);

									  apeGeneratoXml++;
								  }
								  catch (SiapeException se)
								  {
									  // C'e' stata un'eccezione nel marshal

									  logger.error("ECCEZIONE nella creazione dell'XML (SiapeException) - getMessage: "+se.getMessage());

									  // Questo metodo serve se vogliamo stampare l'xml nel caso in cui fallisca il Marshaller
									  xml = apeXmlParser.stampaXmlErrMarshallApe(ape2015);
									  logger.error("Stampo l'xml: "+xml);
									  //se.printStackTrace();

									  // Setto il numCertificatore, perche' mi servira' per fare la mail con le eccezioni
									  numCertificatore = siceeTCertificato.getSiceeTCertificatore().getNumCertificatore();

									  // Se ricevo un'eccezione nella generazione dell'xml e' inutile che riprovi "subito" l'operazione, c'e' bisogno dell'intervento dell'operatore/sviluppatore 
									  traceMgr.updateSiceeTSiapeKO(siceeTSiape);
									  
									  throw se;
								  }
								  catch (Exception e)
								  {
									  // C'e' stata un'eccezione nel marshal

									  logger.error("ECCEZIONE nella creazione dell'XML (Exception) - getMessage: "+e.getMessage());

									  // Setto il numCertificatore, perche' mi servira' per fare la mail con le eccezioni
									  numCertificatore = siceeTCertificato.getSiceeTCertificatore().getNumCertificatore();

									  // Se ricevo un'eccezione nella generazione dell'xml e' inutile che riprovi "subito" l'operazione, c'e' bisogno dell'intervento dell'operatore/sviluppatore 
									  traceMgr.updateSiceeTSiapeKO(siceeTSiape);
									  
									  throw e;
								  }

							  }
							  else
							  {
								  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - l'XML e' gia' valorizzato");
							  }

							  // Devo sicuramente inviare a SIAPE (altrimenti il FLG_DA_ELABORARE sarebbe stato a 'N')
							  //NOTA:1789 ::RICHIAMO WS SIAPE PER CARICAMENTO ********************************************************************************
							  //inizializzo oggetto di risposta, contenuto nella lib wsape-12.0-classes.jar
							  it.enea.siape.ws.soap.WSAPEResponse response = null;
							  
							  logger.debug("## prima dell'istanza AdapterCaricamentoAPEImpl");
							  
							  // reperisco un'istanza dell'adapter per il caricamento
							  AdapterCaricamentoAPEImpl adapter = AdapterCaricamentoAPEImpl.getInstance();
							  
							  logger.debug("## trasformo l'xml in string");
							  // se xml in formato stringa
							  Reader reader = new StringReader(siceeTSiape.getXmlSiape());


							  logger.debug("## prima dell'istanza XMLInputFactory");
							  XMLInputFactory inputFactory = XMLInputFactory.newInstance();
							  
							  logger.debug("## prima della creazione di XMLStreamReader");
							  XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(reader);
							  
							  logger.debug("## prima del richiamo del WS");
							  // richiamo il WS e gestisco l'esito in base al codice ed al messaggio della response
							  response = adapter.caricamentoApe(xmlReader,
									  GenericUtil.recuperaValParametro(paramSiape, APEConstants.SIAPE_USER),
									  GenericUtil.recuperaValParametro(paramSiape, APEConstants.SIAPE_PWD));

							  logger.debug("## stampo il responde: "+response);
							  
								if (logger.isDebugEnabled())
									GenericUtil.stampa(response, true, 3);
							  
							  
							  siapeLog = gestisciResponseCaricamentoSiape(siceeTSiape, response, inizioProcesso);
							  if (siapeLog != null)
							  {
								  siapeLogList.add(siapeLog);
							  }
							  
							  //*******************************************************************************
							  
							  // questo pezzo e' stato inserito nella gestione del response
							  //siceeTSiape.setDataCreazione(new Date());
							  //traceMgr.updateSiceeTSiape(siceeTSiape);

							  // Se non ci sono state eccezioni
							  apeInviati++;

						  }
						  else if (siceeTSiape.getId().getIdTipoAzione() == APEConstants.ID_TIPO_AZIONE_CANCELLAZIONE)
						  {
							  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - Devo cancellare l'APE");

							  // Devo cancellare

							  // NOTA:1789 :: RICHIAMO WS SIAPE PER CANCELLAZIONE **********************************************************************************************
							  // inizializzo oggetto di risposta, contenuto nella lib wsape-12.0-classes.jar
							  it.enea.siape.ws.soap.WSAPEResponse response = null;
							  
							  // inizializzo oggetto apeAnnullati, contenuto nella lib wsape-12.0-classes.jar
							  it.enea.siape.model.persistence.ApeAnnullati apeAnnullati = new it.enea.siape.model.persistence.ApeAnnullati();

							  //Date dataCancellazione = new java.util.Date();
							  // eseguo i set opportuni
							  //apeAnnullati.setDataCancellazione(dataCancellazione); //impostare la data opportuna

							  //Date dataOperazione = new java.util.Date();
							  //apeAnnullati.setDataOperazione(dataOperazione); //impostare la data opportuna

							  // imposto la chiave univoca it.enea.siape.model.persistence.ApeAnnullati.AnnullatiKey
							  AnnullatiKey compositeKey = new AnnullatiKey();
							  compositeKey.setCodiceIstatRegione(APEConstants.COD_ISTAT_PIEMONTE); //impostare codiceIstatRegione

							  // Recupero il certificato parziale (solo per creare il codiceIdentificativo
							  siceeTCertificato = traceMgr.findDettaglioCertificatoDaGestire(MapDto.mapToCertificatoPK(siceeTSiape.getId()));
							  String idRegionaleApe = siceeTCertificato.getId().getAnno() + " " + siceeTCertificato.getSiceeTCertificatore().getNumCertificatore() + " " + siceeTCertificato.getId().getProgrCertificato();

							  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - idRegionaleApe: "+idRegionaleApe);

							  compositeKey.setIdRegionaleApe(idRegionaleApe); //impostare idRegionaleApe
							

							  apeAnnullati.setCompositeKey(compositeKey);

							  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - prima della creazione dell'adapter (AdapterCancellazioneAPEImpl)");

							  // reperisco un'istanza dell'adapter per la cancellazione
							  AdapterCancellazioneAPEImpl adapter = AdapterCancellazioneAPEImpl.getInstance();

							  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - dopo la creazione dell'adapter (AdapterCancellazioneAPEImpl)");

							  // richiamo il WS e gestisco l'esito in base al codice ed al messaggio della response
							  response = adapter.cancellazioneApe(apeAnnullati,
									  GenericUtil.recuperaValParametro(paramSiape, APEConstants.SIAPE_USER),
									  GenericUtil.recuperaValParametro(paramSiape, APEConstants.SIAPE_PWD));

							  //*********************************************************************************************

								if (logger.isDebugEnabled())
									GenericUtil.stampa(response, true, 3);

							  siapeLog = gestisciResponseCancellazioneSiape(siceeTSiape, response, inizioProcesso);
							  if (siapeLog != null)
							  {
								  siapeLogList.add(siapeLog);
							  }

							  // Se non ci sono state eccezioni
							  apeCancellati++;


						  }


					  }
					  catch (SiapeException se)
					  {
						  logger.error("Si e' verificata un'eccezione gestita: "+se.getMessage(), se);

						  siapeLog = traceMgr.insertSiceeTSiapeLog(MapDto.mapToSiceeTSiapeLog(siceeTSiape, inizioProcesso, se));
						  siapeLogList.add(siapeLog);
						  
						  
						  if (numCertificatore == null)
						  {
							  numCertificatore = traceMgr.findNumCertificatoreById(siceeTSiape.getId().getIdCertificatore());
						  }

						  elencoErrori.add(GenericUtil.getOggettoCertificato(siceeTSiape, numCertificatore) + ": " + inizioProcessoFormat + " " + se.getFaultInfo().getFaultString());

					  }
					  catch (Exception ex)
					  {
						  // C'e' stata un'eccezione nella Generazione XML/Invio/Cancellazione

						  logger.error("Si e' verificata un'eccezione NON gestita - getMessage: *"+ex.getMessage()+"*");
						  logger.error("Si e' verificata un'eccezione NON gestita - getLocalizedMessage: *"+ex.getLocalizedMessage()+"*");

						  logger.error("Si e' verificata un'eccezione NON gestita: "+ex.getMessage(), ex);

						  if (numCertificatore == null)
						  {
							  numCertificatore = traceMgr.findNumCertificatoreById(siceeTSiape.getId().getIdCertificatore());
						  }

						  siapeLog = traceMgr.insertSiceeTSiapeLog(MapDto.mapToSiceeTSiapeLog(siceeTSiape, inizioProcesso, ex));
						  siapeLogList.add(siapeLog);

						  elencoErrori.add(GenericUtil.getOggettoCertificato(siceeTSiape, numCertificatore) + ": " + inizioProcessoFormat + " " + ex.getMessage());

					  }

				  }

				  // devo gestire il risultato mandando una mail

				  Mail mail = new Mail();

				  mail.setInizioProcesso(inizioProcesso);
				  mail.setMittente(mittente);
				  mail.setDestinatario(destinatarioMailRiass);
				  mail.setApeGestiti(apeGestiti);
				  mail.setApeGeneratoXml(apeGeneratoXml);
				  mail.setApeInviati(apeInviati);
				  mail.setApeCancellati(apeCancellati);
				  mail.setElencoErrori(elencoErrori);

				  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - mando mail di riepilogo");

				  siapeSrv.sendMailRiepilogo(mail);
				  
				  
				  // Devo andare settare sulla tabella dei log che ho inviato la mail
				  traceMgr.updateSiceeTSiapeLog(siapeLogList);
				  
				  logger.debug("[KronosMgrSessionBean::iniziaElaborazione] - aggiorno DATA_ELABORAZIONE con NULL");


				  // Ripulisco la data elaborazione, in modo che il prossimo processo possa lavorare
				  aggiornaParametro(paramSiape, APEConstants.DATA_ELABORAZIONE, null);
			  }
			  else 
			  {
				  logger.info("NON E' GESTIBILE - c'e' ancora un processo attivo");
			  }
		  } else
		  {
			  logger.info("NON E' ATTIVO IL SERVIZIO (paramentro sul DB)");
		  }
	  } catch (Exception ex)
	  {
		  logger.error("[KronosMgrSessionBean::iniziaElaborazione] - eccezione GRAVE: ", ex);

		  // E fallito qualcosa a livello generale, devo mandare una mail ad assistenza
		  Mail mail = new Mail();
		  mail.setOggetto("SIPEE - ERRORE grave - invio a SIAPE "+GenericUtil.convertToString(inizioProcesso));
		  mail.setInizioProcesso(inizioProcesso);
		  mail.setMittente(mittente);
		  mail.setDestinatario(destinatarioMailRiass);
		  mail.setTesto("Si e' verificato un errore GRAVE, prima dell'elaborazione dei certificati: \n"+ex.getMessage());

		  try
		  {
			  // Ripulisco la data elaborazione, in modo che il prossimo processo possa lavorare
			  aggiornaParametro(paramSiape, APEConstants.DATA_ELABORAZIONE, null);

			  siapeSrv.sendMail(mail, null, null);
		  }
		  catch (Exception e)
		  {
			  // C'e' stata un'eccezione nell'invio della mail di errore grave, l'unica cosa che posso fare e' scriverlo nei log
			  logger.fatal("Si e' verificato un errore grave a livello generale", e);

		  }
	  }
	  finally
	  {
		  logger.info("[KronosMgrSessionBean::iniziaElaborazione] FINISHED! " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));

	  }


  }

  private boolean isGestibile(Timestamp inizioProcesso)
  {
	  boolean isProcessabile = false;

	  int resUpdate = traceMgr.updateParamDataElaborazione(GenericUtil.convertToString(inizioProcesso));

	  if (resUpdate == 1)
	  {
		  // e' stato fatto l'update con il nuovo inizioProcesso,
		  // questo vuol dire che non c'era nessun processo attivo (DATA_ELABORAZIONE = null), oppure c'era un processo piu' vecchio di 5 ore, quindi procedo ugualmente  

		  isProcessabile = true;
	  }
	  else
	  {
		  // c'e' un processo attivo
		  isProcessabile = false;
	  }

	  return isProcessabile;
  }
  
  private SiceeTSiapeLog gestisciResponseCaricamentoSiape(SiceeTSiape siceeTSiape, it.enea.siape.ws.soap.WSAPEResponse response, Timestamp inizioProcesso) throws SiapeException
  {
	  SiceeTSiapeLog siapeLog = null;
	  if (response != null)
	  {
		  /*
		   	public static final int RESULT_CODE_APE_INSERITO_CORRETTAMENTE=0;
			public static final int RESULT_CODE_APE_NON_CONFORME=1;
			public static final int RESULT_CODE_APE_SOGLIA_MASSIMA_SUPERATA=2;
			public static final int RESULT_CODE_APE_PROBLEMA_GENERICO=5;
			public static final int RESULT_CODE_APE_DUPLICATO=6;
			public static final int RESULT_CODE_APE_ISTAT_NON_COINCIDE=8;
		   */
		  if (response.getCodice() == APEConstants.RESULT_CODE_APE_INSERITO_CORRETTAMENTE ||
				  response.getCodice() == APEConstants.RESULT_CODE_APE_NON_CONFORME ||
				  response.getCodice() == APEConstants.RESULT_CODE_APE_DUPLICATO ||
				  response.getCodice() == APEConstants.RESULT_CODE_APE_ISTAT_NON_COINCIDE)
		  {
			  // Se ricevo questi codici non devo ripetere l'operazione
			  siceeTSiape.setFlgDaElaborare(APEConstants.LABEL_N);
			  
			  if (response.getCodice() == APEConstants.RESULT_CODE_APE_INSERITO_CORRETTAMENTE)
			  {
				  // setto la data creazione solo quando invio con esito positivo a SIAPE
				  siceeTSiape.setDataCreazione(inizioProcesso);
				  traceMgr.updateSiceeTSiapeOK(siceeTSiape);
			  }
			  else
			  {
				  traceMgr.updateSiceeTSiapeKO(siceeTSiape);
			  }
			
		  }
		  
		  if (response.getCodice() == APEConstants.RESULT_CODE_APE_INSERITO_CORRETTAMENTE)
		  {
			  siapeLog = traceMgr.insertSiceeTSiapeLog(MapDto.mapToSiceeTSiapeLog(siceeTSiape, inizioProcesso, response));

		  }
		  else
		  {
			  throw new SiapeException(response.getCodice(), response.getMessaggio());
		  }
	  }
	  
	  return siapeLog;
	  
  }
  
  private SiceeTSiapeLog gestisciResponseCancellazioneSiape(SiceeTSiape siceeTSiape, it.enea.siape.ws.soap.WSAPEResponse response, Timestamp inizioProcesso) throws SiapeException
  {
	  SiceeTSiapeLog siapeLog = null;

	  if (response != null)
	  {
		  
		  if (response.getCodice() == APEConstants.RESULT_CODE_APE_INVALIDATO_CORRETTAMENTE)
		  {
			  // setto la data creazione solo quando invio con esito positivo a SIAPE
			  siceeTSiape.setDataCreazione(inizioProcesso);
			  traceMgr.updateSiceeTSiapeOK(siceeTSiape);
		  }
		  else
		  {
			  // Se ricevo questi codici non devo ripetere l'operazione
			  traceMgr.updateSiceeTSiapeKO(siceeTSiape);
		  }
		  
		  /*
		   	public static final int RESULT_CODE_APE_NON_PRESENTE=9;
			public static final int RESULT_CODE_APE_INVALIDATO_CORRETTAMENTE=10;
			public static final int RESULT_CODE_APE_INVALIDATO_ESISTE=11;
			public static final int RESULT_CODE_APE_INVALIDATO_ISTAT_NON_COINCIDE=12;
		   */
		  
		  if (response.getCodice() == APEConstants.RESULT_CODE_APE_INVALIDATO_CORRETTAMENTE)
		  {
			  siapeLog = traceMgr.insertSiceeTSiapeLog(MapDto.mapToSiceeTSiapeLog(siceeTSiape, inizioProcesso, response));

		  }
		  else
		  {
			  throw new SiapeException(response.getCodice(), response.getMessaggio());
		  }
	  }
	  
	  return siapeLog;
	  
  }
  
  /*
  private boolean isGestibileOld(SiceeTParametriSiape siapeDataElab, Timestamp inizioProcesso)
  {
	  boolean isProcessabile = false;

	  String dataProcessoDb = siapeDataElab.getValore();

	  if (!GenericUtil.isNullOrEmpty(dataProcessoDb))
	  {
		  try
		  {
			  String strDate1 = dataProcessoDb;
			  //String strDate2 = "2007/04/15 13:45:00";
			  //String strDate2 = "2009/08/02 20:45:07";

//			  SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//			  fmt.setLenient(false);

			  // Parses the two strings.
			  Date d1 = GenericUtil.convertToDateCompleta(strDate1);
			  Date d2 = inizioProcesso;

			  // Calculates the difference in milliseconds.
			  long millisDiff = d2.getTime() - d1.getTime();

			  // Calculates days/hours/minutes/seconds.
			  int seconds = (int) (millisDiff / 1000 % 60);
			  int minutes = (int) (millisDiff / 60000 % 60);
			  int hours = (int) (millisDiff / 3600000 % 24);
			  int days = (int) (millisDiff / 86400000);

			  System.out.println("Between " + strDate1 + " and " + inizioProcesso + " there are:");
			  System.out.print(days + " days, ");
			  System.out.print(hours + " hours, ");
			  System.out.print(minutes + " minutes, ");
			  System.out.println(seconds + " seconds");

			  long diffHours = millisDiff / (60 * 60 * 1000); //differenza in ore
			  
			  if (diffHours < 5)
			  {
				  isProcessabile = false;
			  }
			  else
			  {
				  // E' passato troppo tempo dall'ultimo processo evidentemente c'e' stato un problema
				  // Cosa devo fare???
				  
				  // Per adesso procedo con l'elaborazione
				  
				  
				  isProcessabile = true;
			  }
			  System.out.println(diffHours + " diffHours");
		  } catch (Exception e) {
			  System.err.println(e);
			  e.printStackTrace();
		  }
	  }
	  else
	  {
		  // Non c'e' nessun processo attivo
		  isProcessabile = true;
	  }
	  
	  
	  return isProcessabile;
  }
  */
  
  private SiceeTParametriSiape aggiornaParametroOK(List<SiceeTParametriSiape> paramSiape, String codice, String valoreNew)
  {

	  for (SiceeTParametriSiape siceeTParametriSiape : paramSiape) {
		  if (siceeTParametriSiape.getCodice().equals(codice))
		  {
			  siceeTParametriSiape.setValore(valoreNew);

			  return siceeTParametriSiape;
		  }
	  }

	  return null;
  }

  private void aggiornaParametro(List<SiceeTParametriSiape> paramSiape, String codice, String valoreNew)
  {

	  for (SiceeTParametriSiape siceeTParametriSiape : paramSiape) {
		  if (siceeTParametriSiape.getCodice().equals(codice))
		  {
			  siceeTParametriSiape.setValore(valoreNew);

			  traceMgr.updateSiceeTParametriSiape(siceeTParametriSiape);
		  }
	  }
  }
  
  private void sendMail(String subject, String text) throws Exception {
//    String addressFrom = traceMgr.findParametro(RECUPERO_EVENTI_NAO_MAIL_FROM, null);
//    if (addressFrom == null) {
//      logger.info("[KronosMgrSessionBean::sendMail] RECUPERO_EVENTI_NAO_MAIL_FROM non configurato, impossibile inviare la mail");
//      return;
//    }
//    String addressTo = traceMgr.findParametro(RECUPERO_EVENTI_NAO_MAIL_TO, null);
//    if (addressTo == null) {
//      logger.info("[KronosMgrSessionBean::sendMail] RECUPERO_EVENTI_NAO_MAIL_TO non configurato, impossibile inviare la mail");
//      return;
//    }
//    Properties properties = new Properties();
//    String[] hostAndPort = getMailHostAndPort();
//    String host = hostAndPort[0];
//    String port = hostAndPort[1];
//    properties.setProperty("mail.smtp.host", host);
//    properties.setProperty("mail.smtp.port", port);
//    logger.info("[KronosMgrSessionBean::sendMail] Invio mail su "+host+":"+port+" a " + addressTo);
//    Session session = Session.getDefaultInstance(properties);
//    MimeMessage m = new MimeMessage(session);
//    Address from = new InternetAddress(addressFrom);
//    Address[] to = new InternetAddress[] { new InternetAddress(addressTo) };
//    m.setFrom(from);
//    m.setRecipients(Message.RecipientType.TO, to);
//    m.setSubject(subject);
//    m.setSentDate(new Date());
//    m.setContent(text, "text/plain");
//    Transport.send(m);
  }

  /**
   * Verifica se il range (nel formato "from-to,interval" con valori espressi in
   * ore) include l'istante corrente.
   */
  private boolean checkRange(String range, int lastExec, int hourNow) {
    int[] rangeData = splitRange(range);
    int from = rangeData[0];
    int to = rangeData[1];
    int interval = rangeData[2];
    int elapsed = hourNow-lastExec;
    if(elapsed<0) elapsed += 24;
    if(elapsed<interval) return false;
    if(hourNow<from && hourNow>to) return false;
    return true;
  }
  
  private int[] splitRange(String range) {
    String[] s1 = range.split(",");
    String[] s2 = s1[0].split("-");
    int[] result = new int[] {Integer.parseInt(s2[0]),Integer.parseInt(s2[1]),Integer.parseInt(s1[1])};
    return result;
  }

  private String[] getMailHostAndPort() throws Exception {
    Properties properties = new Properties();
    InputStream stream = this.getClass().getResourceAsStream("/wsEndpointUrls.properties");
    properties.load(stream);
    return new String[] {properties.getProperty("mail.host"),properties.getProperty("mail.port")};
  }
  
}
