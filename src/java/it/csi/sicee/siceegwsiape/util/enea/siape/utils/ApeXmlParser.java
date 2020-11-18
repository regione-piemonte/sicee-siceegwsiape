/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util.enea.siape.utils;


import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import it.csi.sicee.siceegwsiape.jaxb.lib.Ape2015;
import it.csi.sicee.siceegwsiape.jaxb.lib.ObjectFactory;
import it.csi.sicee.siceegwsiape.util.APEConstants;
import it.csi.sicee.siceegwsiape.util.GenericUtil;
import it.csi.sicee.siceegwsiape.util.enea.siape.exception.SiapeException;
//import it.enea.siape.Ape2015;


public class ApeXmlParser
{
	private static final Logger logger = Logger.getLogger(APEConstants.LOGGER_PREFIX);

  private JAXBContext mContext;
  private ObjectFactory mObjectFactory;
  private Unmarshaller mUnmarshaller;
  private Marshaller mMarshaller;
  
  public ApeXmlParser()
    throws SiapeException
  {
    setUp();
  }

  private void setUp()
    throws SiapeException
  {
	  try
	  {
		  //this.mContext = JAXBContext.newInstance("it.enea.siape.model.ape");
		  this.mContext = JAXBContext.newInstance("it.csi.sicee.siceegwsiape.jaxb.lib");
		  this.mObjectFactory = new ObjectFactory();

		  SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		  Schema schema = schemaFactory.newSchema(new StreamSource(getClass().getClassLoader().getResourceAsStream("APEx2015_v12.xsd")));

		  logger.debug("Stampo lo schema: "+schema);

		  
	      this.mMarshaller = this.mContext.createMarshaller();
	      this.mMarshaller.setProperty("jaxb.encoding", "UTF-8");
		  this.mMarshaller.setEventHandler(new DefaultValidationEventHandler());
		  this.mMarshaller.setSchema(schema);
		  
		  this.mUnmarshaller = this.mContext.createUnmarshaller();
		  this.mUnmarshaller.setEventHandler(new DefaultValidationEventHandler());
		  this.mUnmarshaller.setSchema(schema);
	  } catch (JAXBException e) {
		  logger.error("Stampo l'eccezione: setUp - JAXBException - getMessage: "+e.getMessage());
		  logger.error("Stampo l'eccezione: setUp - JAXBException - getLocalizedMessage: "+e.getLocalizedMessage());

		  throw new SiapeException(CodeMessage.ERRORE_CREAZIONE_XML.getCodice(), CodeMessage.ERRORE_CREAZIONE_XML.getMessaggio(), e);
	  } catch (SAXException e) {
		  
		  logger.error("Stampo l'eccezione: setUp - SAXException - getMessage: "+e.getMessage());
		  logger.error("Stampo l'eccezione: setUp - SAXException - getLocalizedMessage: "+e.getLocalizedMessage());
		  
		  throw new SiapeException(CodeMessage.ERRORE_CREAZIONE_XML.getCodice(), CodeMessage.ERRORE_CREAZIONE_XML.getMessaggio(), e);
	  }
  }

  public Ape2015 parsing(String path)
    throws SiapeException
  {
    try
    {
      File file = new File(path);

      Object object = this.mUnmarshaller.unmarshal(file);
      Ape2015 ape = (Ape2015)object;

      return ape;
    } catch (JAXBException e) {
    	throw new SiapeException(CodeMessage.ERRORE_CREAZIONE_XML.getCodice(), CodeMessage.ERRORE_CREAZIONE_XML.getMessaggio(), e);
    }
    
  }
  
  public Ape2015 parseToApe2015(XMLStreamReader apeXml)
		    throws SiapeException
		  {
		    try
		    {

		      Object object = this.mUnmarshaller.unmarshal(apeXml);
		      Ape2015 ape = (Ape2015)object;

		      return ape;
		    } catch (JAXBException e) {
		    	throw new SiapeException(CodeMessage.ERRORE_CREAZIONE_XML.getCodice(), CodeMessage.ERRORE_CREAZIONE_XML.getMessaggio(), e);
		    }
		    
		  }
  
  
  public it.enea.siape.model.ape.Ape2015 parseXmlToApe2015(XMLStreamReader apeXml)
		    throws SiapeException
		  {
		    try
		    {

		      Object object = this.mUnmarshaller.unmarshal(apeXml);
		      it.enea.siape.model.ape.Ape2015 ape = (it.enea.siape.model.ape.Ape2015)object;

		      return ape;
		    } catch (JAXBException e) {
		    	throw new SiapeException(CodeMessage.ERRORE_CREAZIONE_XML.getCodice(), CodeMessage.ERRORE_CREAZIONE_XML.getMessaggio(), e);
		    }
		    
		  }

  public String marshallApe(Ape2015 ape) throws SiapeException
  {
    try {
      StringWriter sw = new StringWriter();


      this.mMarshaller.marshal(ape, sw);

      return sw.toString(); 
    } 
    catch (JAXBException e) {
    	
    	 if (e.getCause() instanceof SAXParseException)
		  {
    		 logger.error("E' un'eccezione nella validazione - getMessage: "+e.getCause().getMessage());
    		 logger.error("E' un'eccezione nella validazione - getLocalizedMessage: "+e.getCause().getLocalizedMessage());

			  throw new SiapeException(CodeMessage.ERRORE_CREAZIONE_XML.getCodice(), e.getCause().getMessage(), e);
		  }
		  else
		  {
			  throw new SiapeException(CodeMessage.ERRORE_CREAZIONE_XML.getCodice(), CodeMessage.ERRORE_CREAZIONE_XML.getMessaggio(), e);
		  }

  	  /*
    	if (e.getCause() instanceof SAXParseException)
    	{
    		System.out.println("E' un'eccezione nella validazione - getMessage: "+e.getCause().getMessage());
    		System.out.println("E' un'eccezione nella validazione - getLocalizedMessage: "+e.getCause().getLocalizedMessage());
    	}
    	
    	System.out.println("Stampo il messaggio dell'eccezione - getMessage: "+e.getMessage());
    	System.out.println("Stampo il messaggio dell'eccezione - getLocalizedMessage: "+e.getLocalizedMessage());
    	GenericUtil.stampa(e, false, 3);
    	  throw new SiapeException(CodeMessage.DATI_INCONGRUENTI.getCodice(), CodeMessage.DATI_INCONGRUENTI.getMessaggio(), e);
    	  */
    }
    
    
  }

  // Questo metodo serve se vogliamo stampare l'xml nel caso in cui fallisca il Marshaller
  public String stampaXmlErrMarshallApe(Ape2015 ape) throws SiapeException
  {
	  try {
		  StringWriter sw = new StringWriter();

		  JAXBContext mContextProva = JAXBContext.newInstance("it.csi.sicee.siceegwsiape.jaxb.lib");
		  this.mObjectFactory = new ObjectFactory();

		  Marshaller prova = mContextProva.createMarshaller();
		  prova.setProperty("jaxb.encoding", "UTF-8");
		  prova.marshal(ape, sw);

		  return sw.toString(); 
	  } 
	  catch (JAXBException e) {

		  logger.error("Stampo getCause: "+e.getCause());

		  if (e.getCause() instanceof SAXParseException)
		  {
			  throw new SiapeException(CodeMessage.ERRORE_CREAZIONE_XML.getCodice(), e.getCause().getMessage(), e);
		  }
		  else
		  {
			  throw new SiapeException(CodeMessage.ERRORE_CREAZIONE_XML.getCodice(), CodeMessage.ERRORE_CREAZIONE_XML.getMessaggio(), e);
		  }
	  }
	  catch (Exception e)
	  {
		  throw new SiapeException(CodeMessage.ERRORE_CREAZIONE_XML.getCodice(), CodeMessage.ERRORE_CREAZIONE_XML.getMessaggio(), e);
	  }
  }
}
