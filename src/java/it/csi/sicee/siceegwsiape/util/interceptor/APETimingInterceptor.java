/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
//package it.csi.sicee.siceegwsiape.util.interceptor;
//
//
//
//import java.util.concurrent.TimeUnit;
//
//import javax.interceptor.AroundInvoke;
//import javax.interceptor.InvocationContext;
//
//import org.apache.log4j.Level;
//
//import it.csi.sicee.siceegwsiape.util.BeanLogger;
//
//
//
///**
// * Interceptor to centralize the timing of the invocations.
// * 
// */
//public class APETimingInterceptor {
//	
//	/** The logger */
//	private final BeanLogger log = new BeanLogger(getClass());
//	
//	/**
//	 * Times the execution.
//	 * 
//	 * @param ctx the invocation context
//	 * @return the result of the invocation
//	 * @throws Exception in case an exception is thrown from the underlying invocation
//	 */
//	@AroundInvoke
//	public Object invocation(InvocationContext ctx) throws Exception {
//		final long initTime = System.nanoTime();
//		try {
//			return ctx.proceed();
//		} finally {
//			final long endTime = System.nanoTime();
//			log.logTime(ctx.getMethod().getName(), Level.TRACE, TimeUnit.NANOSECONDS, initTime, endTime);
//		}
//	}
//	
//}
