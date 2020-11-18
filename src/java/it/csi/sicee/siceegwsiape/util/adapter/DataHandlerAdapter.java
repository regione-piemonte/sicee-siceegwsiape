/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Level;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import it.csi.sicee.siceegwsiape.util.BeanLogger;




/**
 * Serializer for DataHandler. Transforms the underlying stream to a base64-string.
 * 
 *
 */
public class DataHandlerAdapter implements JsonSerializer<DataHandler>, JsonDeserializer<DataHandler> {

	private static final int BUFFER_SIZE = 1024;
	private final BeanLogger log = new BeanLogger(getClass());
	
	@Override
	public JsonElement serialize(DataHandler dataHandler, Type type, JsonSerializationContext context) {
		final String methodName = "serialize";
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			is = dataHandler.getInputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int n = 0;
			while (-1 != (n = is.read(buffer))) {
				baos.write(buffer, 0, n);
			}
			String string = DatatypeConverter.printBase64Binary(baos.toByteArray());
			return new JsonPrimitive(string);
		} catch (IOException e) {
			String msg = "IOException in byte write: " + e.getMessage();
			log.warn(methodName, msg);
			// Logs the exception only at a TRACE level: since it's not necessarily interesting for the application
			log.log(methodName, Level.TRACE, msg, e);
		} finally {
			close(is);
			close(baos);
		}
		
		log.debug(methodName, "Error in serialization: returning null");
		return null;
	}
	
	@Override
	public DataHandler deserialize(JsonElement jsonElement, Type arg1, JsonDeserializationContext arg2) {
		final String methodName = "deserialize";
		String base64 = jsonElement.getAsString();
		byte[] arr = DatatypeConverter.parseBase64Binary(base64);
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(arr);
			return new DataHandler(new ByteArrayDataSource(bais, null));
		} catch (IOException e) {
			log.warn(methodName, "IOException in byte write: " + (e.getMessage()));
		} finally {
			close(bais);
		}
		return null;
	}

	/**
	 * Closes the closeable, logging but otherwise ignoring the exception.
	 * 
	 * @param closeable the closeable to close
	 */
	private void close(Closeable closeable) {
		if(closeable != null) {
			try {
				closeable.close();
			} catch(IOException ioe) {
				final String methodName = "close";
				log.warn(methodName, "IOException in closing: " + (ioe.getMessage()));
			}
		}
	}
}
