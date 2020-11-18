/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
/*
 * 
 */
package it.csi.sicee.siceegwsiape.util;

import it.csi.sicee.siceegwsiape.integration.db.SiceeTCertificato;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTCertificatore;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTParametriSiape;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTSiape;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException; 
import javax.xml.datatype.DatatypeFactory; 

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

//import acaris.dto.Certificato;


/**
 * The Class GenericUtil.
 */
public class GenericUtil {

	private static Logger logger = Logger.getLogger(APEConstants.LOGGER_PREFIX);

	/** The Constant BEGIN. */
	static final int BEGIN = 1;

	/** The Constant END. */
	static final int END = 2;

	/** The Constant VALUE. */
	static final int VALUE = 3;

	/** The Constant TEST. */
	static final int TEST = 4;

	/** The Constant SIMPLE. */
	static final int SIMPLE = 5;
	
	public final static java.text.SimpleDateFormat FORMATTER_ANNO_WEB = new java.text.SimpleDateFormat(
			"yyyy");

	public final static java.text.SimpleDateFormat FORMATTER_DATA_WEB = new java.text.SimpleDateFormat(
			"dd/MM/yyyy");

	public final static java.text.SimpleDateFormat FORMATTER_DATA_COMPLETA = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public final static java.text.SimpleDateFormat FORMATTER_DATA_ACTA = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	/**
	 * Stampa.
	 * 
	 * @param o
	 *            the o
	 * @param useLog4j
	 *            the use log4j
	 * @param depth
	 *            the depth
	 * @param testName
	 *            the test name
	 */
	public static void stampa(Object o, boolean useLog4j, int depth,
			String testName) {
		try {
			if (useLog4j) {
				logger.debug(testName + " BEGIN");
			} else {
				System.out.println(testName + " BEGIN");
			}
			if (o != null) {
				if (o.getClass().isArray()) {
					Object[] a = (Object[]) o;
					stampa(a, useLog4j, depth);
				} else {
					stampa(o, useLog4j, depth);
				}
			}
			if (useLog4j) {
				logger.debug(testName + " END");
			} else {
				System.out.println(testName + " END");
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * Stampa.
	 * 
	 * @param o
	 *            the o
	 * @param useLog4j
	 *            the use log4j
	 * @param depth
	 *            the depth
	 */
	public static void stampa(Object o, boolean useLog4j, int depth) {

		try {
			if (o == null) {
				print(o, null, useLog4j, depth, BEGIN);
			} else {
				if (o instanceof String) {
					print(o, o, useLog4j, depth, SIMPLE);
				} else {
					print(o, null, useLog4j, depth, BEGIN);
					callGetMethods(o, useLog4j, depth + 1);
					print(o, null, useLog4j, depth, END);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * Prints the.
	 * 
	 * @param o
	 *            the o
	 * @param value
	 *            the value
	 * @param useLog4j
	 *            the use log4j
	 * @param depth
	 *            the depth
	 * @param type
	 *            the type
	 * @throws Exception
	 *             the exception
	 */
	private static void print(Object o, Object value, boolean useLog4j,
			int depth, int type) throws Exception {

		StringBuffer tab = new StringBuffer();
		for (int i = 0; i < depth; i++) {
			tab.append("\t");
		}
		if (o != null) {
			String className = o.getClass().getName();
			switch (type) {
			case BEGIN:
				tab.append(className);
				tab.append(" BEGIN");
				break;
			case END:
				tab.append(className);
				tab.append(" END");
				break;
			case VALUE:
				tab.append(((Method) o).getName());
				tab.append(" == ");
				tab.append(value);
				break;
			case SIMPLE:
				tab.append(o);
				tab.append(" == ");
				tab.append(value);
				break;
			default:

			}
		} else if (type == TEST) {
			tab.append("");
		} else {
			tab.append("Oggetto nullo!!");
		}

		if (useLog4j) {
			logger.debug(tab);
		} else {
			System.out.println(tab);
		}

	}

	/**
	 * Call get methods.
	 * 
	 * @param o
	 *            the o
	 * @param useLog4j
	 *            the use log4j
	 * @param depth
	 *            the depth
	 */
	private static void callGetMethods(Object o, boolean useLog4j, int depth) {
		try {
			Method[] meth = o.getClass().getDeclaredMethods();
			for (int i = 0; i < meth.length; i++) {
				Method thisM = meth[i];
				if (thisM.getName().startsWith("get")) {
					if (!thisM.getName().equals("get")) {
						Object result = thisM.invoke(o, new Object[] {});
						if (result != null && result.getClass().isArray()) {
							Object[] a = (Object[]) result;
							stampa(a, useLog4j, depth);
						} else {
							print(thisM, result, useLog4j, depth, VALUE);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * Stampa.
	 * 
	 * @param o
	 *            the o
	 * @param useLog4j
	 *            the use log4j
	 * @param depth
	 *            the depth
	 * @throws Exception
	 *             the exception
	 */
	public static void stampa(Object[] o, boolean useLog4j, int depth)
			throws Exception {
		String className = o.getClass().getSimpleName();
		for (int i = 0; i < o.length; i++) {
			stampa(o[i], false, depth);
		}

		if (o.length == 0) {
			System.out.println(className + " vuoto");
		}

	}
	
	/**
	 * Gets the anno corrente.
	 * 
	 * @return the anno corrente
	 */
	public static String getAnnoCorrente() {
		return FORMATTER_ANNO_WEB.format(new Date(System
				.currentTimeMillis()));
	}

	/**
	 * Convert to timestamp.
	 *
	 * @param s the s
	 * @return the java.sql. timestamp
	 * @throws BEException the bE exception
	 */
	public static java.sql.Timestamp getInizioProcesso()
	{
		
		Date s = new Date();
		System.out.println("Stampo s: "+s);
		Timestamp time = null;
		if (s != null) {
			time = new Timestamp(s.getTime());
		}
		
		System.out.println("Stampo time: "+time);


		return time;
	}
	
	public static String convertToString(java.sql.Timestamp time) {
		if (time != null) {
			return FORMATTER_DATA_COMPLETA.format(time);
		} else
			return null;
	}
	
	/**
	 * Convert to string.
	 *
	 * @param i the Integer
	 * @return the string
	 */
	public static String convertToString(Integer i) {
		String converted = null;

		if(i != null) {
			try {
				converted = i.toString();
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + i + "' in String: " + i, e);
			}
		}
		return converted;
	}
	
	public static String convertToValidString(Integer i) {
		String converted = null;

		if(i != null) {
			try {
				converted = i.toString();
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + i + "' in String: " + i, e);
			}
		}
		else 
		{
			converted = "";
		}
		return converted;
	}
	
	/**
	 * Convert to string.
	 *
	 * @param i the String
	 * @return the string
	 */
	public static String convertToValidString(String i) {
		String converted = null;


		try {
			converted = StringUtils.trimToEmpty(i);
		}
		catch(Exception e) {
			logger.error("Errore durante la conversione di '" + i + "' in String: " + i, e);
		}

		return converted;
	}
	
	/**
	 * Convert to string.
	 *
	 * @param i the Integer
	 * @return the string
	 */
	public static String convertToString(BigDecimal b) {
		String converted = null;

		if(b != null) {
			try {
				converted = b.toString();
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + b + "' in BigDecimal: " + b, e);
			}
		}
		return converted;
	}
	
	public static java.util.Date convertToDateCompleta(String time) {
		if (time != null) {
			try {
				return FORMATTER_DATA_COMPLETA.parse(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return null;
			}
		} else
			return null;
	}
	
	public static Date convertToDateActa(String dataActa) {
		if (dataActa != null) {
			try {
				return FORMATTER_DATA_ACTA.parse(dataActa);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return null;
			}
		} else
			return null;
	}
	
	/**
	 * Convert to string.
	 *
	 * @param dt the dt
	 * @return the string
	 */
	public static String convertToString(java.util.Date dt) {
		if (dt != null) {
			
			return FORMATTER_DATA_WEB.format(dt);
		} else
			return null;
	}

	public static String convertToString(Long l) {
		String converted = null;

		if(l != null) {
			try {
				converted = l.toString();
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + l + "' in Integer: " + l);
			}
		}
		return converted;
	}
	
	public static Long convertToLong(String s) {
		Long converted = null;

		if(s != null) {
			try {
				converted = new Long(s);
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + s + "' in long: " + s);
			}
		}
		return converted;
	}
	
	public static Short convertToShort(String s) {
		Short converted = null;

		if(s != null) {
			try {
				converted = new Short(s);
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + s + "' in long: " + s);
			}
		}
		return converted;
	}
	
	public static Short convertToShort(Integer s) {
		Short converted = null;

		if(s != null) {
			try {
				converted = s.shortValue();
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + s + "' in long: " + s);
			}
		}
		return converted;
	}
	
	public static Short convertToShort(BigDecimal s) {
		Short converted = null;

		if(s != null) {
			try {
				converted = s.shortValue();
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + s + "' in long: " + s);
			}
		}
		return converted;
	}
	
	public static Integer convertToInteger(BigDecimal b) {
		Integer converted = null;

		if(b != null) {
			try {
				converted = b.intValue();
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + b + "' in Integer: " + b);
			}
		}
		return converted;
	}
	
	public static Integer convertToInteger(String s) {
		Integer converted = null;

		if(s != null) {
			try {
				converted = new Integer(s);
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + s + "' in Integer: " + s);
			}
		}
		return converted;
	}
	
	public static int convertToInt(String s) {
		int converted = 0;

		if(s != null) {
			try {
				converted = new Integer(s);
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + s + "' in Integer: " + s);
			}
		}
		return converted;
	}
	
	public static Boolean convertToBoolean(String s) {
		Boolean converted = null;

		if(s != null) {
			try {
				converted = APEConstants.COD_S.equalsIgnoreCase(s);
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + s + "' in Boolean: " + s);
			}
		}
		return converted;
	}
	
	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date d) {
		XMLGregorianCalendar converted = null;

		if(d != null) {
			try {
				GregorianCalendar c = new GregorianCalendar();
				c.setTime(d);
				converted = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + d + "' in XMLGregorianCalendar: " + d);
			}
		}
		return converted;
	}
	
	/**
	 * Riduce i decimali di un BigDecimal in base al numero di decimali massimo
	 * 
	 * @param number BigDecimal da trattare
	 * @param numeroCifreDecimali numero di decimali max
	 * @return Stringa convertita
	 */
	public static BigDecimal convertTo2BigDecimal(BigDecimal number)
	{
		if (number != null)
		{
			number = number.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		
		return number;
	}

	/**
	 * Riduce i decimali di un BigDecimal in base al numero di decimali massimo
	 * 
	 * @param number BigDecimal da trattare
	 * @param numeroCifreDecimali numero di decimali max
	 * @return Stringa convertita
	 */
	public static BigDecimal convertToScaleBigDecimal(int scale, BigDecimal number)
	{
		if (number != null)
		{
			number = number.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		
		return number;
	}

	public static Short convertToMotivazioneSiape(BigDecimal b) {
		Short converted = null;

		if(b != null) {
			try {
				converted = GenericUtil.convertToShort(b.intValue()-100);
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + b + "' in Motivazione SIAPE: " + b);
			}
		}
		return converted;
	}
	
	public static Short convertToCombustibileSiape(BigDecimal b) {
		// MATCH COME SU DB MENO 200 
		Short converted = null;

		if(b != null) {
			try {
				converted = GenericUtil.convertToShort(b.intValue()-200);
			}
			catch(Exception e) {
				logger.error("Errore durante la conversione di '" + b + "' in Combustibile SIAPE: " + b);
			}
		}
		return converted;
	}
	
	
	

	/**
	 * Checks if is null or empty.
	 * 
	 * @param s
	 *            the s
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	/**
	 * Checks if is null or empty.
	 * 
	 * @param s
	 *            the s
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(Integer s) {
		return s == null;
	}

	/**
	 * Checks if is null or empty.
	 * 
	 * @param s
	 *            the s
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(Double s) {
		return s == null;
	}

	/**
	 * Checks if is null or empty.
	 * 
	 * @param s
	 *            the s
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(Boolean s) {
		return s == null;
	}

	/**
	 * Checks if is null or empty.
	 * 
	 * @param s
	 *            the s
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(Object s) {
		return s == null;
	}

	public static boolean isNullOrEmpty(List<?> s) {
		if (s != null) {
			return s.isEmpty();
		}

		return s == null;
	}

	
	public static String recuperaValParametro(List<SiceeTParametriSiape> paramSiape, String codParam)
	{
		String val = null;
		for (SiceeTParametriSiape siceeTParametriSiape : paramSiape) {

			if (siceeTParametriSiape.getCodice().equalsIgnoreCase(codParam))
			{
				val = siceeTParametriSiape.getValore();
				break;
			}
		}

		return val;
	}

	public static SiceeTParametriSiape recuperaTParametriSiape(List<SiceeTParametriSiape> paramSiape, String codParam)
	{
		SiceeTParametriSiape val = null;
		for (SiceeTParametriSiape siceeTParametriSiape : paramSiape) {

			if (siceeTParametriSiape.getCodice().equalsIgnoreCase(codParam))
			{
				val = siceeTParametriSiape;
				break;
			}
		}

		return val;
	}
	
	
	
	public static String getOggettoCertificato(SiceeTSiape siceeTSiape, String numCertificatore)
	{
		String oggetto = null;
		
		if (siceeTSiape != null)
		{
			oggetto = siceeTSiape.getId().getAnno() + " " + numCertificatore + " " + siceeTSiape.getId().getProgrCertificato();
		}
		
		return oggetto;
	}
	
	public static String limitaString(String msg, int maxLunghezza)
	{
		if (msg != null && msg.length() > maxLunghezza)
		{
			// Il messaggio e' troppo lungo, lo limito a x caratteri (in base al parametro)
			msg = msg.substring(0, maxLunghezza);
		}
		
		return msg;
	}
}