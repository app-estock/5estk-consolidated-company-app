package com.market.company.domain.validator;

import com.market.company.api.CompanyRequest;
import com.market.company.common.AppConstants;
import com.market.company.domain.CompanyDetails;

import java.util.Map;

public class CompanyValidator {

    public static boolean isRequestValid(CompanyRequest request)
    {
        return request.getCompanyCode() != null && request.getCompanyCEO() != null && request.getCompanyName() != null && request.getCompanyTurnOver() != null && request.getCompanyWebsite() != null && request.getStockExchangeType() != null;
    }
    public static boolean isTurnOverValid(CompanyDetails companyDetails)
    {
        return companyDetails.getCompanyTurnOver() >= 10;

    }
    public static boolean areHeadersValid(Map<String,String> requestHeader)
    {
           if(requestHeader.containsKey(AppConstants.ESTK_TRANSACTIONID) && requestHeader.containsKey(AppConstants.ESTK_SESSIONID) && requestHeader.containsKey(AppConstants.ESTK_MESSAGEID) && requestHeader.containsKey(AppConstants.ESTK_CREATIONTIMESTAMP)){
               return true;
           }
           return false;
    }

}
