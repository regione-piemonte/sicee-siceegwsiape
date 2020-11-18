/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
//package it.csi.sicee.siceegwsiape.util.interceptor;
//
//
//
//import java.util.Arrays;
//
//import javax.interceptor.AroundInvoke;
//import javax.interceptor.InvocationContext;
//
//import it.csi.sicee.siceegwsiape.util.BeanLogger;
//
//
//
///**
// * Interceptor to centralize the EJB logging.
// * 
// */
//public class APELoggerInterceptor {
//	
//	/** The logger */
//	private final BeanLogger log = new BeanLogger(getClass());
//	
//	/**
//	 * Logs the execution.
//	 * 
//	 * @param ctx the invocation context
//	 * @return the result of the invocation
//	 * @throws Exception in case an exception is thrown from the underlying invocation
//	 */
//	@AroundInvoke
//	public Object invocation(InvocationContext ctx) throws Exception {
//		String methodName = ctx.getMethod().getName();
//		Object[] parameters = ctx.getParameters();
//		log.start(methodName);
//		log.debug(methodName, "Parameters: " + Arrays.toString(parameters));
//		Object res = ctx.proceed();
//		log.debug(methodName, "Result: " + res);
//		log.end(methodName);
//		return res;
//	}
//	
//}
