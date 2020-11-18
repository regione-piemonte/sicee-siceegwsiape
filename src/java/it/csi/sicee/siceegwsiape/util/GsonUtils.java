/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util;

import javax.activation.DataHandler;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.csi.sicee.siceegwsiape.util.adapter.ByteArrayDeserializer;
import it.csi.sicee.siceegwsiape.util.adapter.DataHandlerAdapter;
import it.csi.sicee.siceegwsiape.util.adapter.DateTypeAdapter;
import it.csi.sicee.siceegwsiape.util.adapter.XmlGregorianTypeAdapter;


public class GsonUtils {

  public static String toGsonString(Object o) {
    GsonBuilder gb = new GsonBuilder();
    gb.registerTypeAdapter(XMLGregorianCalendar.class,new DateTypeAdapter());
    gb.registerTypeAdapter(DataHandler.class, new DataHandlerAdapter());
    gb.setPrettyPrinting();
    gb.setDateFormat("dd/MM/yyyy HH:mm:ss SSS"); 
    // gb.serializeNulls(); //per default non stampa i campi nulli, usare questa istruzione
    Gson gson = gb.create();
    return gson.toJson(o);
  }

  public static <T> T toGsonObject(String gsonString, Class<T> objectClass, boolean useDate) {
    GsonBuilder gb = new GsonBuilder();
    if(!useDate) 
    {
    	gb.registerTypeAdapter(XMLGregorianCalendar.class,new XmlGregorianTypeAdapter());
    }
    gb.registerTypeAdapter(DataHandler.class, new DataHandlerAdapter());
    gb.registerTypeAdapter(byte[].class, new ByteArrayDeserializer());
    gb.setDateFormat("dd/MM/yyyy HH:mm:ss SSS");
//    System.out.println(gsonString);
    return gb.create().fromJson(gsonString, objectClass);
  }

  public static <T> T remap(Object in, Class<T> outClass,boolean usedate) {
	  
    return toGsonObject(toGsonString(in), outClass,usedate);
  }
  
  public static <T> T remap(Object in, Class<T> outClass) {
	    return toGsonObject(toGsonString(in), outClass, false);
  }
  
}
