/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util.enea.siape.exception;

import javax.xml.ws.WebFault;

@WebFault(faultBean="SiapeFault", targetNamespace="http://www.enea.it/siape")
public class SiapeException extends Exception
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private SiapeFault mFault;

  protected SiapeException(SiapeFault fault)
  {
    super(fault.getFaultString());
    this.mFault = fault;
  }

  public SiapeException(String message, SiapeFault faultInfo)
  {
    super(message);
    this.mFault = faultInfo;
  }

  public SiapeException(String message, SiapeFault faultInfo, Throwable cause)
  {
    super(message, cause);
    this.mFault = faultInfo;
  }

  public SiapeFault getFaultInfo()
  {
    return this.mFault;
  }

  public SiapeException(String message)
  {
    super(message);
  }

  public SiapeException(int code, String message)
  {
    super(message);
    this.mFault = new SiapeFault();
    this.mFault.setFaultString(message);
    this.mFault.setFaultCode(code);
  }

  public SiapeException(int code, String message, Throwable cause)
  {
    super(message, cause);
    this.mFault = new SiapeFault();
    this.mFault.setFaultString(message);
    this.mFault.setFaultCode(code);
  }

  public SiapeException(Throwable cause)
  {
    super(cause);
  }

  public SiapeException(String message, Throwable cause)
  {
    super(message, cause);
  }
}