package com.market.company.dao.sac;

import com.market.company.common.AppConstants;
import com.market.company.common.headers.Headers;
import com.market.company.common.logging.TransactionLog;
import com.market.company.exception.DeleteStockServiceException;
import com.market.company.kafka.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeleteStocksServiceImpl implements DeleteStocksService {
    @Autowired
    private Producer producer;
    private static final Logger logger = LoggerFactory.getLogger(DeleteStocksServiceImpl.class);
    private TransactionLog transactionLog;
    @Override
    public boolean deleteStockService(String companycode, Headers headers) throws DeleteStockServiceException {
        transactionLog = new TransactionLog("DeleteCompanyV1", "deleteCompanyV1", "extCall");
        Map<String, String> extendedProperties = new HashMap<>();
        String updateStockServiceV1Endpoint="http://localhost:8081/StockV1/delete/"+companycode;
        //region transaction log population
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setRequestLog("companycode:"+companycode);
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(headers.getEstk_transactionID());
        transactionLog.setMessageId(headers.getEstk_messageID());
        transactionLog.setSessionId(headers.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
        extendedProperties.put("calling", "DeleteStockV1");
        extendedProperties.put("callType", "External Rest API");

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(updateStockServiceV1Endpoint);
            transactionLog.setStatus(AppConstants.SUCCESS);
            return true;
        }
        catch(Exception e){
            transactionLog.setStatus(AppConstants.FAIL);
           throw new DeleteStockServiceException(e.getMessage());
        }
        finally {
            //log
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());
            producer.sendMessage(transactionLog.toString());
        }
    }
}
