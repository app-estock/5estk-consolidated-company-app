package com.market.company.service;

import com.market.company.api.CompanyResponse;
import com.market.company.api.ErrorMessage;
import com.market.company.common.AppConstants;
import com.market.company.common.CommonUtility;
import com.market.company.common.faulthandler.FaultCode;
import com.market.company.common.headers.Headers;
import com.market.company.kafka.Producer;
import com.market.company.common.logging.TransactionLog;
import com.market.company.dao.DeleteCompanyDao;
import com.market.company.dao.sac.DeleteStocksService;
import com.market.company.exception.DeleteStockServiceException;
import com.market.company.exception.NoDataFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeleteCompanyServiceImpl implements DeleteCompanyService {
    @Autowired
    private Producer producer;
    @Autowired
    private DeleteCompanyDao deleteCompanyDao;
    @Autowired
    private DeleteStocksService deleteStocksServiceSac;
    private List<ErrorMessage> estkErrorList;
    private static final Logger logger = LoggerFactory.getLogger(DeleteCompanyServiceImpl.class);
    private TransactionLog transactionLog;
    @Override
    public CompanyResponse processRequest(String companyCode, Headers requestHeaders) throws NoDataFoundException, DeleteStockServiceException {
        CompanyResponse response = new CompanyResponse();
        estkErrorList= new ArrayList<>();
        transactionLog = new TransactionLog("DeleteCompanyV1", "deleteCompanyV1", "Service");
        Map<String, String> extendedProperties = new HashMap<>();
        //region transaction log population
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setRequestLog("companycode:"+companyCode);
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(requestHeaders.getEstk_transactionID());
        transactionLog.setMessageId(requestHeaders.getEstk_messageID());
        transactionLog.setSessionId(requestHeaders.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(requestHeaders.getEstk_creationtimestamp());
        //endregion
        try {
            if (deleteCompanyDao.deleteCompanyDetails(companyCode,requestHeaders) ) {

                if( deleteStocksServiceSac.deleteStockService(companyCode,requestHeaders)) {
                    response.setSuccessIndicator(true);
                    extendedProperties.put("externalCallTriggered", "yes");
                    transactionLog.setStatus(AppConstants.SUCCESS);
                }
                else{
                    extendedProperties.put("externalCallTriggered", "no");
                }

            } else {
                CommonUtility.addtoEstkErrorlist(estkErrorList, CommonUtility.createFault(FaultCode.ESTK0404, AppConstants.ESTK0404_NO_DATA_RESPONSE_TEXT.replace("#COMPANYCODE#",companyCode)));
                transactionLog.setErrorcode(FaultCode.ESTK0404.name());
            }
            if (!CommonUtility.isListEmpty(estkErrorList)) {

                response = mapResponseFaults();
            }
        } catch (NoDataFoundException e) {
            transactionLog.setErrorcode(FaultCode.ESTK0404.name());
            throw new NoDataFoundException(e.toString());
        }
        catch (DeleteStockServiceException e)
        {
            transactionLog.setErrorcode(FaultCode.ESTK0500.name());
            throw new DeleteStockServiceException(e.toString());
        }
        finally {
            transactionLog.setResponseLog(response.toString());
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());
            producer.sendMessage(transactionLog.toString());
        }
        return response;
    }

    private CompanyResponse mapResponseFaults() {
        CompanyResponse response = new CompanyResponse();
        response.setErrorMessages(estkErrorList);
        response.setSuccessIndicator(false);
        return response;
    }
}

