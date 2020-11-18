/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util.adapter;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class XmlGregorianTypeAdapter implements JsonDeserializer<XMLGregorianCalendar> {

	@Override
	public XMLGregorianCalendar deserialize(JsonElement jsonElement, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {

		try
		{
			Date dt=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss SSS").parse(jsonElement.getAsString());
			
			GregorianCalendar gregory = new GregorianCalendar();
			gregory.setTime(dt);
			
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}

	}


}
