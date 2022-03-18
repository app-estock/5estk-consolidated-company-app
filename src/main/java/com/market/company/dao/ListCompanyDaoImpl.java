package com.market.company.dao;

import com.market.company.common.AppConstants;
import com.market.company.common.CommonUtility;
import com.market.company.common.headers.Headers;
import com.market.company.common.logging.TransactionLog;
import com.market.company.domain.CompanyDetails;
import com.market.company.domain.mapper.CompanyDetailsListMapper;
import com.market.company.kafka.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ListCompanyDaoImpl implements ListCompanyDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ListCompanyDaoImpl.class);
    private TransactionLog transactionLog;
    @Autowired
    private Producer producer;

    @Override
    public List<CompanyDetails> listAllCompanyDetails(Headers requestHeaders) {
        transactionLog = new TransactionLog("ListCompanyV1", "listCompanyV1", "Database");
        Map<String, String> extendedProperties = new HashMap<>();
        List<CompanyDetails> companyDetailsList;

        //region transaction log population
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(requestHeaders.getEstk_transactionID());
        transactionLog.setMessageId(requestHeaders.getEstk_messageID());
        transactionLog.setSessionId(requestHeaders.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(requestHeaders.getEstk_creationtimestamp());
        extendedProperties.put("calling", "mysqldb");
        extendedProperties.put("sqlName", "LIST_ALL_COMPANY_DETAILS");

        //endregion

        try{
              companyDetailsList=jdbcTemplate.query(AppConstants.LIST_ALL_COMPANY_DETAILS,new CompanyDetailsListMapper());

              if(!CommonUtility.isListEmpty(companyDetailsList)){
                  extendedProperties.put("Returned Data",companyDetailsList.toString());
                  transactionLog.setStatus(AppConstants.SUCCESS);
              }
        }
        catch (Exception e)
        {   transactionLog.setStatus(AppConstants.FAIL);
           throw new RuntimeException(e);
        }
        finally {
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());
            producer.sendMessage(transactionLog.toString());
        }
        return companyDetailsList;
    }

    @Override
    public List<CompanyDetails> listCompanyDetailsByUserId(String userId, Headers requestHeaders) {
        transactionLog = new TransactionLog("ListCompanyV1", "listCompanyV1", "Database");
        Map<String, String> extendedProperties = new HashMap<>();
        List<CompanyDetails> companyDetailsList;
        //region transaction log population
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(requestHeaders.getEstk_transactionID());
        transactionLog.setMessageId(requestHeaders.getEstk_messageID());
        transactionLog.setSessionId(requestHeaders.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(requestHeaders.getEstk_creationtimestamp());
        extendedProperties.put("calling", "mysqldb");
        extendedProperties.put("sqlName", "LIST_COMPANY_DETAILS_BY_USER");

        //endregion

        try{
            companyDetailsList=jdbcTemplate.query(AppConstants.LIST_COMPANY_DETAILS_BY_USER,new CompanyDetailsListMapper(),userId);

            if(!CommonUtility.isListEmpty(companyDetailsList)){
                extendedProperties.put("Returned Data",companyDetailsList.toString());
                transactionLog.setStatus(AppConstants.SUCCESS);
            }
        }
        catch (Exception e)
        {   transactionLog.setStatus(AppConstants.FAIL);
            throw new RuntimeException(e);
        }
        finally {
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());
        }
        return companyDetailsList;
    }

}
