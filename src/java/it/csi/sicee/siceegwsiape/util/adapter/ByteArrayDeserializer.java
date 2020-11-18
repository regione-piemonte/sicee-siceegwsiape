/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util.adapter;

import java.lang.reflect.Type;
import java.util.Iterator;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

/**
 * Deserializer for byte[]. Transforms the underlying base64-string to a byte[].
 * 
 *
 */
public class ByteArrayDeserializer implements JsonDeserializer<byte[]> {

	@Override
	public byte[] deserialize(JsonElement jsonElement, Type typeOfSrc, JsonDeserializationContext context) {
		// Doppia gestione: se arrivo da un byte[] vero, allora avro' un byte[] nel JSON; se arrivo da un byte[] Base64-to, allora avro' una stringa
		return jsonElement.isJsonArray() ? convertFromJsonArray(jsonElement.getAsJsonArray()) : convertFromBase64String(jsonElement.getAsString());
	}

	/**
	 * Effettua la conversione a partire dal JSON array.
	 * 
	 * @param jsonArray il JSON array da convertire
	 * @return il byte array
	 */
	private byte[] convertFromJsonArray(JsonArray jsonArray) {
		byte[] res = new byte[jsonArray.size()];
		int idx = 0;
		for(Iterator<JsonElement> it = jsonArray.iterator(); it.hasNext();) {
			res[idx++] = it.next().getAsByte();
		}
		return res;
	}
	
	/**
	 * Effettua la conversione a partire dalla stringa in Base64-
	 * 
	 * @param base64 la stringa da convertire
	 * @return il byte array
	 */
	private byte[] convertFromBase64String(String base64) {
		return DatatypeConverter.parseBase64Binary(base64);
	}

}
