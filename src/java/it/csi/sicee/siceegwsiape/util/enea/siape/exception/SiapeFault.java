/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util.enea.siape.exception;

import java.io.Serializable;

public class SiapeFault implements Serializable 
{

  private static final long serialVersionUID = 1L;
  
  private int mCodiceFault;
  private String mMessaggioFault;

  public int getFaultCode()
  {
    return this.mCodiceFault;
  }

  public void setFaultCode(int faultCode)
  {
    this.mCodiceFault = faultCode;
  }

  public String getFaultString()
  {
    return this.mMessaggioFault;
  }

  public void setFaultString(String faultString)
  {
    this.mMessaggioFault = faultString;
  }
}