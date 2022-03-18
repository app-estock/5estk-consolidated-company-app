package com.market.company.dao;

import com.market.company.common.AppConstants;
import com.market.company.common.headers.Headers;
import com.market.company.common.logging.TransactionLog;
import com.market.company.exception.NoDataFoundException;
import com.market.company.kafka.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeleteCompanyDaoImp implements DeleteCompanyDao {
    @Autowired
    private Producer producer;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(DeleteCompanyDaoImp.class);
    private TransactionLog transactionLog;

    @Override
    public boolean deleteCompanyDetails(String companyCode, Headers headers) throws NoDataFoundException {
        boolean recordDeleted = false;
        int result;
        transactionLog = new TransactionLog("DeleteCompanyV1", "deleteCompanyV1", "Database");
        Map<String, String> extendedProperties = new HashMap<>();

        //region transaction log population
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setRequestLog("companycode:"+companyCode);
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(headers.getEstk_transactionID());
        transactionLog.setMessageId(headers.getEstk_messageID());
        transactionLog.setSessionId(headers.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
        extendedProperties.put("calling", "mysqldb");
        extendedProperties.put("sqlName", "DELETE_COMPANY_DETAILS");

        //endregion

        try {
            result = jdbcTemplate.update(AppConstants.DELETE_COMPANY_DETAILS, companyCode);
            if (result == 1) {
                transactionLog.setStatus(AppConstants.SUCCESS);
                recordDeleted=true;


            }
            else {
                transactionLog.setStatus(AppConstants.FAIL);
                recordDeleted=false;

            }
            extendedProperties.put("recordDeleted", recordDeleted ?"yes":"no");
        }
        catch (Exception e) {
            transactionLog.setStatus(AppConstants.FAIL);
            throw new NoDataFoundException(e.getMessage());
        }
        finally{
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());
            producer.sendMessage(transactionLog.toString());
        }


        return recordDeleted;
    }
}
