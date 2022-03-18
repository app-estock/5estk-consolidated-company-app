package com.market.company.service;

import com.market.company.api.CompanyRequest;
import com.market.company.api.CompanyResponse;
import com.market.company.api.ErrorMessage;
import com.market.company.common.AppConstants;
import com.market.company.common.CommonUtility;
import com.market.company.common.faulthandler.FaultCode;
import com.market.company.common.headers.Headers;
import com.market.company.common.logging.TransactionLog;
import com.market.company.dao.SaveCompanyDao;
import com.market.company.domain.CompanyDetails;
import com.market.company.domain.validator.CompanyValidator;
import com.market.company.exception.DuplicateCompanyCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaveCompanyServiceImp implements SaveCompanyService {
    @Autowired
    private SaveCompanyDao saveCompanyDao;

    private List<ErrorMessage> estkErrorList;
    private static final Logger logger = LoggerFactory.getLogger(SaveCompanyServiceImp.class);
    private TransactionLog transactionLog;


    @Override
    public CompanyResponse processRequest(CompanyRequest request, Headers requestHeaders) throws DuplicateCompanyCodeException {


        CompanyResponse response = new CompanyResponse();
        estkErrorList = new ArrayList<>();
        transactionLog = new TransactionLog("SaveCompanyV1", "saveCompanyV1", "Service");
        Map<String, String> extendedProperties = new HashMap<>();

       //region transaction log population
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setRequestLog(request.toString());
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(requestHeaders.getEstk_transactionID());
        transactionLog.setMessageId(requestHeaders.getEstk_messageID());
        transactionLog.setSessionId(requestHeaders.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(requestHeaders.getEstk_creationtimestamp());
        //endregion

        try {
            CompanyDetails details = mapCompanyDetails(request);
            extendedProperties.put("companyDetails", details.toString());
            if (CompanyValidator.isTurnOverValid(details)) {
                if (savecompanydao(details,requestHeaders)) {
                    response.setSuccessIndicator(true);
                    transactionLog.setStatus(AppConstants.SUCCESS);
                } else {

                    CommonUtility.addtoEstkErrorlist(estkErrorList, CommonUtility.createFault(FaultCode.ESTK0404, AppConstants.ESTK0404_DATA_NOT_SAVED));
                    transactionLog.setErrorcode(FaultCode.ESTK0404.name());
                }

            } else {

                CommonUtility.addtoEstkErrorlist(estkErrorList, CommonUtility.createFault(FaultCode.ESTK0001, AppConstants.ESTK0001_INVALID_TURNOVER_RESPONSE_TEXT));
                transactionLog.setErrorcode(FaultCode.ESTK0001.name());
            }
            if (!CommonUtility.isListEmpty(estkErrorList)) {

                response = mapResponseFaults();


            }
        } catch (DuplicateCompanyCodeException e) {
            transactionLog.setErrorcode(FaultCode.ESTK0402.name());
            throw new DuplicateCompanyCodeException(e.toString());
        } finally {
            transactionLog.setResponseLog(response.toString());
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());

        }

        return response;
    }
    public boolean savecompanydao(CompanyDetails details,Headers requestHeaders) throws DuplicateCompanyCodeException {
       return saveCompanyDao.saveCompanyDetails(details,requestHeaders);
    }
    private CompanyDetails mapCompanyDetails(CompanyRequest request) {
        return new CompanyDetails(request.getCompanyCode(), request.getCompanyName(), request.getCompanyCEO(), request.getCompanyTurnOver(), request.getCompanyWebsite(), request.getStockExchangeType(),request.getUserId());
    }

    private CompanyResponse mapResponseFaults() {
        CompanyResponse response = new CompanyResponse();
        response.setErrorMessages(estkErrorList);
        response.setSuccessIndicator(false);

        transactionLog.setErrorMessageList(estkErrorList);
        transactionLog.setStatus(AppConstants.FAIL);
        return response;
    }
}
