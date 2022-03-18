package com.market.company.domain.validator;

import com.market.company.api.CompanyRequest;
import com.market.company.domain.CompanyDetails;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CompanyValidatorTest {

    @Test
    void isRequestValid() {
        CompanyRequest request=new CompanyRequest();
        request.setCompanyCEO("ABC");
        request.setCompanyName("ABC");
        request.setCompanyCode("ABC");
        request.setCompanyTurnOver(1000.0);
        request.setCompanyWebsite("www.www.com");
        request.setStockExchangeType("BSE");
        request.setUserId("ABC");
       boolean result=CompanyValidator.isRequestValid(request);
       assertTrue(result);
    }
    @Test
    void isRequestNotValid() {
        CompanyRequest request=new CompanyRequest();
        boolean result=CompanyValidator.isRequestValid(request);
        assertFalse(result);
    }
    @Test
    void isTurnOverValid() {
        CompanyRequest request=new CompanyRequest();
        request.setCompanyCEO("ABC");
        request.setCompanyCode("ABC");
        request.setCompanyTurnOver(1000.0);
        request.setCompanyWebsite("www.www.com");
        request.setStockExchangeType("BSE");
        request.setUserId("ABC");
        CompanyDetails companyDetails = new CompanyDetails(request.getCompanyCode(),request.getCompanyName(),request.getCompanyCEO(),request.getCompanyTurnOver(),request.getCompanyWebsite(),request.getStockExchangeType(),request.getUserId());
       boolean result=CompanyValidator.isTurnOverValid(companyDetails);
       assertTrue(result);
    }
    @Test
    void isTurnOverNotValid() {
        CompanyRequest request=new CompanyRequest();
        request.setCompanyCEO("ABC");
        request.setCompanyCode("ABC");
        request.setCompanyTurnOver(8.0);
        request.setCompanyWebsite("www.www.com");
        request.setStockExchangeType("BSE");
        request.setUserId("ABC");
        CompanyDetails companyDetails = new CompanyDetails(request.getCompanyCode(),request.getCompanyName(),request.getCompanyCEO(),request.getCompanyTurnOver(),request.getCompanyWebsite(),request.getStockExchangeType(),request.getUserId());
      boolean result=  CompanyValidator.isTurnOverValid(companyDetails);
      assertFalse(result);
    }
    @Test
    void areHeadersValid() {
        Map<String,String> requestHeaders= new HashMap<String,String>();
        requestHeaders.put("estk_sessionid","123-1231qwe-123123-234123123");
        requestHeaders.put("estk_transactionid","123-dqw1231-123123qwe-123123");
        requestHeaders.put("estk_creationtimestamp","1232131123");
        requestHeaders.put("estk_messageid","123-dqw1231-123123qwe-123123");
        boolean result=CompanyValidator.areHeadersValid(requestHeaders);
        assertTrue(result);
    }
    @Test
    void areHeadersNotValid() {
        Map<String,String> requestHeaders= new HashMap<String,String>();
        requestHeaders.put("estk_sessionId","123-1231qwe-123123-234123123");
        requestHeaders.put("estk_transactionid","123-dqw1231-123123qwe-123123");
        requestHeaders.put("estk_creationtimestamp","1232131123");
        requestHeaders.put("estk_messageid","123-dqw1231-123123qwe-123123");
        boolean result=CompanyValidator.areHeadersValid(requestHeaders);
        assertFalse(result);
    }
}