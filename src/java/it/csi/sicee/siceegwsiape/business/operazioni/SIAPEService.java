/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.business.operazioni;

import java.util.ArrayList;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import it.csi.sicee.siceegwsiape.business.dto.Mail;
import it.csi.sicee.siceegwsiape.util.APEConstants;
import it.csi.sicee.siceegwsiape.util.GenericUtil;

import org.apache.log4j.Logger;
//import javax.swing.text.AbstractDocument.Content;
//import it.doqui.index.ecmengine.client.webservices.dto.Node;
//import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
//import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;
//import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegate;
//import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegateServiceLocator;

public class SIAPEService {


	private static Logger logger = Logger.getLogger(APEConstants.LOGGER_PREFIX);

	   public static SIAPEServiceFactory factory = new SIAPEServiceFactory();

	public void sendMailRiepilogo(Mail mail) throws Exception {
		logger.debug("[SIAPEService::sendMailRiepilogo] BEGIN");
		//GenericUtil.stampaVO(emailVO);	
		// Create a mail session
		try {
			//mail.setDestinatario("giuseppe.todaro@csi.it");
			
			mail.setOggetto("SIPEE invio a SIAPE "+GenericUtil.convertToString(mail.getInizioProcesso()));

			StringBuffer messaggio = new StringBuffer();
			messaggio.append("Il Timer Service di Generazione XML, Invio e Cancellazione su SIAPE avviato alle ");
			messaggio.append(GenericUtil.convertToString(mail.getInizioProcesso()));
			messaggio.append(" ha eseguito le seguenti operazioni:");

			messaggio.append("\n\n");
			messaggio.append("Numero APE elaborati: ");
			messaggio.append(mail.getApeGestiti());

			messaggio.append("\n");
			messaggio.append("Numero APE generato XML: ");
			messaggio.append(mail.getApeGeneratoXml());

			messaggio.append("\n");
			messaggio.append("Numero APE inviati a SIAPE: ");
			messaggio.append(mail.getApeInviati());

			messaggio.append("\n");
			messaggio.append("Numero APE cancellati su SIAPE: ");
			messaggio.append(mail.getApeCancellati());


			ArrayList<String> listError = mail.getElencoErrori();

			if (listError != null && listError.size() > 0)
			{
				messaggio.append("\n\n\n");
				messaggio.append("Sono stati rilevati i seguenti errori in fase di Generazione XML/Invio/Cancellazione:");

				for (String errore : listError) {
					messaggio.append("\n");
					messaggio.append(errore);
				}
			}

			mail.setTesto(messaggio.toString());

			// Send the message
			sendMail(mail, null, null);

		} catch (Exception e) {
			logger.error("Errore nell'invio della mail");
			throw e;
		} finally {
			logger.debug("[SIAPEService::sendMailRiepilogo] END");
		}

	}
	
	public void sendMail(Mail mail, String nomeFile, byte[] doc) throws Exception {
		logger.debug("[SIAPEService::sendMail] BEGIN");
		//GenericUtil.stampaVO(emailVO);	
        // Create a mail session
		try {
        java.util.Properties props = new java.util.Properties();        
        props.put("mail.smtp.host", factory.getMailHost());
        props.put("mail.smtp.port", factory.getMailPort());
        Session session = Session.getDefaultInstance(props, null);

        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mail.getMittente()));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getDestinatario()));
//        if(StringUtils.isNotEmpty(emailVo.getDestinatarioCC())){
//        	msg.setRecipient(Message.RecipientType.CC, new InternetAddress(emailVo.getDestinatarioCC()));
//        }
        msg.setSubject(mail.getOggetto());
        MimeMultipart  mp = new MimeMultipart();
        
        
        
        MimeBodyPart html = new MimeBodyPart();
        html.setText(mail.getTesto(), "text/plain");                
        html.setContent(mail.getTesto().replace("\n", "</br>"),"text/html");
        
       
        /*
     // create and fill the second message part
        if (doc != null) {
        	MimeBodyPart attachmentPart = new MimeBodyPart();
        	FileDataSource fileDataSource = new FileDataSource(createFileWithData(nomeFile, doc)) {
        	                @Override
        	                public String getContentType() {
        	                    return "application/pdf";
        	                }
        	            };
        	            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
        	            attachmentPart.setFileName(nomeFile);        	            
        	mp.addBodyPart(attachmentPart);
        }
     // create the Multipart and its parts to it
        */
        
        //mp.addBodyPart(text);
        mp.addBodyPart(html);
        
        // add the Multipart to the message
        msg.setContent(mp);        
        
		// Aggiunto questo comando per risolvere il problema di invio mail
		Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

        // Send the message
        Transport.send(msg);
		} catch (Exception e) {
			logger.error("Errore nell'invio della mail");
			throw e;
		} finally {
			logger.debug("[SIAPEService::sendMail] END");
		}
                
	}
	
	
	
}
