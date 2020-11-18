/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util.adapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTypeAdapter implements JsonSerializer<XMLGregorianCalendar> {

  @Override
  public JsonElement serialize(XMLGregorianCalendar d, Type t, JsonSerializationContext c) {
    if(d==null) return null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss SSS");
    return new JsonPrimitive(df.format(d.toGregorianCalendar().getTime()));
  }

}
