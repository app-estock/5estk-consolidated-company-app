package com.market.company.service;

import com.market.company.api.CompanyResponseListData;
import com.market.company.api.ErrorMessage;
import com.market.company.common.AppConstants;
import com.market.company.common.CommonUtility;
import com.market.company.common.headers.Headers;
import com.market.company.common.logging.TransactionLog;
import com.market.company.dao.ListCompanyDao;
import com.market.company.domain.CompanyDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListCompanyServiceImpl implements ListCompanyService {

    @Autowired
    private ListCompanyDao listCompanyDao;
   // @Autowired
   // private Producer producer;
    private List<ErrorMessage> estkErrorList;
    private static final Logger logger = LoggerFactory.getLogger(ListCompanyServiceImpl.class);
    private TransactionLog transactionLog;

    @Override
    public List<CompanyResponseListData> processRequest(Headers requestHeaders) {

        List<CompanyResponseListData> response = null;
        List<CompanyDetails> companyDetailsList = null;
        estkErrorList = new ArrayList<>();
        transactionLog = new TransactionLog("ListCompanyV1", "listCompanyV1", "Service");
        Map<String, String> extendedProperties = new HashMap<>();
        //region transaction log population
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setRequestLog("path:/api/V1.0/market/company/getall");
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(requestHeaders.getEstk_transactionID());
        transactionLog.setMessageId(requestHeaders.getEstk_messageID());
        transactionLog.setSessionId(requestHeaders.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(requestHeaders.getEstk_creationtimestamp());
        //endregion
        try {
            companyDetailsList = listCompanyDao.listAllCompanyDetails(requestHeaders);
            if (!CommonUtility.isListEmpty(companyDetailsList)) {
                extendedProperties.put("companyDetailList", companyDetailsList.toString());
                response = mapCompanyResponse(companyDetailsList);
                transactionLog.setStatus(AppConstants.SUCCESS);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transactionLog.setResponseLog(response.toString());
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());
        }
        return response;
    }

    @Override
    public List<CompanyResponseListData> processRequest(String userId, Headers requestHeaders) {

        List<CompanyResponseListData> response = null;
        List<CompanyDetails> companyDetailsList = null;
        estkErrorList = new ArrayList<>();
        transactionLog = new TransactionLog("ListCompanyV1", "listCompanyV1", "Service");
        Map<String, String> extendedProperties = new HashMap<>();
        //region transaction log population
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setRequestLog("userId:" + userId);
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(requestHeaders.getEstk_transactionID());
        transactionLog.setMessageId(requestHeaders.getEstk_messageID());
        transactionLog.setSessionId(requestHeaders.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(requestHeaders.getEstk_creationtimestamp());
        //endregion
        try {
            companyDetailsList = listCompanyDao.listCompanyDetailsByUserId(userId, requestHeaders);
            if (!CommonUtility.isListEmpty(companyDetailsList)) {
                extendedProperties.put("companyDetailList", companyDetailsList.toString());
                response = mapCompanyResponse(companyDetailsList);
                transactionLog.setStatus(AppConstants.SUCCESS);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transactionLog.setResponseLog(response.toString());
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());
            //producer.sendMessage(transactionLog.toString());
        }
        return response;
    }

    private List<CompanyResponseListData> mapCompanyResponse(List<CompanyDetails> companyDetailsList) {
        List<CompanyResponseListData> companyResponseData = new ArrayList<>();
        for (CompanyDetails cd : companyDetailsList) {
            companyResponseData.add(new CompanyResponseListData(cd.getCompanyCode(), cd.getCompanyName(), cd.getCompanyCEO(), cd.getCompanyTurnOver(), cd.getCompanyWebsite(), cd.getStockExchangeType(), cd.getLatestStockPrice()));
        }


        return companyResponseData;
    }

}
