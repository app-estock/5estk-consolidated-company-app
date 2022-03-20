package com.market.company.service;

import com.market.company.api.CompanyResponseData;
import com.market.company.api.ErrorMessage;
import com.market.company.common.AppConstants;
import com.market.company.common.CommonUtility;
import com.market.company.common.faulthandler.FaultCode;
import com.market.company.common.headers.Headers;
import com.market.company.common.logging.TransactionLog;
import com.market.company.dao.SearchCompanyDao;
import com.market.company.domain.CompanyDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SearchCompanyServiceImp  implements SearchCompanyService{

    @Autowired
    private SearchCompanyDao searchCompanyDao;
    private List<ErrorMessage> estkErrorList;
    private static final Logger logger = LoggerFactory.getLogger(SearchCompanyServiceImp.class);
    private TransactionLog transactionLog;
    @Override
    public CompanyResponseData processRequest(String companyCode, Headers requestHeaders) {
        CompanyResponseData response = null;
        CompanyDetails companyDetails =null;
        estkErrorList= new ArrayList<>();

        transactionLog = new TransactionLog("SearchCompanyV1", "searchCompanyV1", "Service");
        Map<String, String> extendedProperties = new HashMap<>();
        //region transaction log population
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setRequestLog("companyCode:"+companyCode);
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(requestHeaders.getEstk_transactionID());
        transactionLog.setMessageId(requestHeaders.getEstk_messageID());
        transactionLog.setSessionId(requestHeaders.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(requestHeaders.getEstk_creationtimestamp());
        try{
              companyDetails=searchCompanyDao.searchCompanyDetails(companyCode);
              if(companyDetails!=null)
              {
                  response=mapCompanyDetails(companyDetails);

                  transactionLog.setStatus(AppConstants.SUCCESS);
                  return response;
              }
              else
              {
                  CommonUtility.addtoEstkErrorlist(estkErrorList, CommonUtility.createFault(FaultCode.ESTK0404, AppConstants.ESTK0404_NO_DATA_RESPONSE_TEXT));
                  transactionLog.setErrorcode(FaultCode.ESTK0404.name());
                  return null;
              }

        }
        catch (DataAccessException e )
        {
            System.out.println(e);
        }
        catch (Exception e)
        {

        }
        finally{
            transactionLog.setResponseLog(response.toString());
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());
        }
return response;
    }
    private CompanyResponseData mapCompanyDetails(CompanyDetails companyDetails) {

        return new CompanyResponseData(companyDetails.getCompanyCode(), companyDetails.getCompanyName(), companyDetails.getCompanyCEO(), companyDetails.getCompanyTurnOver(), companyDetails.getCompanyWebsite(), companyDetails.getStockExchangeType(),companyDetails.getLatestStockPrice());
    }



}
