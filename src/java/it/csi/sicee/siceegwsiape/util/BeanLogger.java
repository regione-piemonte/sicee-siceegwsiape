/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * Logger standard per le classi di bean.
 * Delega all'implementazione definita del logger in maniera trasparente per l'utente, mantenendo un'interfaccia simile a quella fornita da Apache Log4j.
 * Richiede il passaggio della String <code>methodName</code> ad ogni metodo per evitare di doverla scrivere a mano ogni volta nel messaggio o evitare di dover utilizzare le utilities interne
 * del logger per il recupero del metodo.
 * 
 * @author Marchino Alessandro
 * @version 1.0.0 - 06/11/2015
 *
 */
public class BeanLogger {

	/** Il template del log */
	private static final String TEMPLATE = "[%s::%s] - %s";
	/** Il numero dei minuti in un'ora */
	private static final int MINUTES_PER_HOUR = 60;
	/** Il numero dei secondi in un minuto */
	private static final int SECONDS_PER_MINUTE = 60;
	/** Il numero dei millisecondi in un secondo */
	private static final int MILLIS_PER_SECOND = 1000;
	
	private final Logger logger;
	private final String name;
	
	/**
	 * Constructor via given name.
	 * 
	 * @param name the name for the logger
	 */
	public BeanLogger(String name) {
		this.name = name;
		logger = Logger.getLogger(APEConstants.LOGGER_PREFIX);
	}
	
	/**
	 * Constructor via default name.
	 */
	public BeanLogger() {
		this(APEConstants.LOGGER_PREFIX);
	}
	
	/**
	 * Constructor via class.
	 * 
	 * @param clazz the class from which to evince the name
	 */
	public BeanLogger(Class<?> clazz) {
		this(clazz.getName());
	}

	/**
	 * Logs the given message with level INFO.
	 * 
	 * @param methodName the method to log for
	 * @param message the message to log
	 */
	public void info(String methodName, Object message) {
		if(logger.isInfoEnabled()) {
			logger.info(String.format(TEMPLATE, name, methodName, message));
		}
	}
	
	/**
	 * Logs the given message with level ERROR.
	 * 
	 * @param methodName the method to log for
	 * @param message the message to log
	 */
	public void error(String methodName, Object message) {
		if(logger.isEnabledFor(Level.ERROR)) {
			logger.error(String.format(TEMPLATE, name, methodName, message));
		}
	}
	
	/**
	 * Logs the given message with level WARN.
	 * 
	 * @param methodName the method to log for
	 * @param message the message to log
	 */
	public void warn(String methodName, Object message) {
		if(logger.isEnabledFor(Level.WARN)) {
			logger.warn(String.format(TEMPLATE, name, methodName, message));
		}
	}
	
	/**
	 * Logs the given message and throwable with level ERROR.
	 * 
	 * @param methodName the method to log for
	 * @param message the message to log
	 * @param throwable the throwable to log
	 */
	public void error(String methodName, Object message, Throwable throwable) {
		if(logger.isEnabledFor(Level.ERROR)) {
			logger.error(String.format(TEMPLATE, name, methodName, message), throwable);
		}
	}
	
	/**
	 * Logs the given message with level DEBUG.
	 * 
	 * @param methodName the method to log for
	 * @param message the message to log
	 */
	public void debug(String methodName, Object message) {
		if(logger.isDebugEnabled()) {
			logger.debug(String.format(TEMPLATE, name, methodName, message));
		}
	}
	
	/**
	 * Logs the given message with the given priority if enabled.
	 * 
	 * @param methodName the method to log for
	 * @param priority the priority for the log
	 * @param message the message to log
	 */
	public void log(String methodName, Priority priority, Object message) {
		if(logger.isEnabledFor(priority)) {
			logger.log(priority, String.format(TEMPLATE, name, methodName, message));
		}
	}
	
	/**
	 * Logs the given message and throwable with the given priority if enabled.
	 * 
	 * @param methodName the method to log for
	 * @param priority the priority for the log
	 * @param message the message to log
	 * @param throwable the throwable to log
	 */
	public void log(String methodName, Priority priority, Object message, Throwable throwable) {
		if(logger.isEnabledFor(priority)) {
			logger.log(priority, String.format(TEMPLATE, name, methodName, message), throwable);
		}
	}
	
	/**
	 * Returns whether the logger is enabled for the given priority.
	 * 
	 * @param priority the priority to check
	 * @return <code>true</code> il the logger is enabled for the given priority; <code>false</code> otherwise
	 */
	public boolean isEnabledFor(Priority priority) {
		return logger.isEnabledFor(priority);
	}
	
	/**
	 * Logs the excution time.
	 * 
	 * @param methodName the method requesting the log
	 * @param priority the priority of thelog
	 * @param timeUnit the unit of time used by the times
	 * @param initTime the initial time
	 * @param endTime the final time
	 */
	public void logTime(String methodName, Priority priority, TimeUnit timeUnit, long initTime, long endTime) {
		if(logger.isEnabledFor(priority)) {
			long elapsed = endTime - initTime;
			long minutes = timeUnit.toMinutes(elapsed) % MINUTES_PER_HOUR;
			long seconds = timeUnit.toSeconds(elapsed) % SECONDS_PER_MINUTE;
			long millis = timeUnit.toMillis(elapsed) % MILLIS_PER_SECOND;
			String timeString = String.format("elapsed time: %02d:%02d.%03d mm:ss.ddd", minutes, seconds, millis);
			
			logger.log(priority, String.format(TEMPLATE, name, methodName, timeString));
		}
	}
	
	/**
	 * Logs the start of the method.
	 * 
	 * @param methodName the method to log for
	 */
	public void start(String methodName) {
		if(logger.isInfoEnabled()) {
			info(methodName, "START");
		}
	}
	
	/**
	 * Logs the end of the method.
	 * 
	 * @param methodName the method to log for
	 */
	public void end(String methodName) {
		if(logger.isInfoEnabled()) {
			info(methodName, "END");
		}
	}
	
	/**
	 * isDebugEnabled verify if level is DEBUG.
	 */
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	
}
