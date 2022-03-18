package com.market.company.common;

import com.market.company.api.ErrorMessage;
import com.market.company.common.faulthandler.FaultCode;
import com.market.company.common.faulthandler.FaultHandler;

import java.util.List;

public class CommonUtility {
  private CommonUtility()
  {

  }
    public static FaultHandler createFault(FaultCode faultCode,String responsefaultMessage)
    {
        return new FaultHandler(faultCode.name(), faultCode.getValue(),responsefaultMessage);
    }
    public static List<ErrorMessage> addtoEstkErrorlist(List<ErrorMessage> errorlist, FaultHandler fault){
        ErrorMessage em = new ErrorMessage();
        em.setErrorCode(fault.getFaultCode());
        em.setErrorDef(fault.getFaultMessage()+":"+fault.getResponseFaultMessage());
        errorlist.add(em);
        return errorlist;
    }
    public static <T> boolean isListEmpty(List<T> list )
    {
        return list.isEmpty();
    }


}
