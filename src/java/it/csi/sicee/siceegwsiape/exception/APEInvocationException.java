/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.exception;

/**
 * Eccezione rilanciata in caso di eccezione nell'invocazione dei servizi APE.
 * 
 * 
 *
 */
public class APEInvocationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @see Exception#Exception()
	 */
	public APEInvocationException() {
		super();
	}

	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public APEInvocationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @see Exception#Exception(String)
	 */
	public APEInvocationException(String message) {
		super(message);
	}

	/**
	 * @see Exception#Exception(Throwable)
	 */
	public APEInvocationException(Throwable cause) {
		super(cause);
	}

}
