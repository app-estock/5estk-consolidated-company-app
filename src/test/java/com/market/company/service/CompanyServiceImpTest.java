package com.market.company.service;

import com.market.company.api.CompanyRequest;
import com.market.company.api.CompanyResponse;
import com.market.company.common.headers.Headers;
import com.market.company.common.logging.TransactionLog;
import com.market.company.dao.CompanyRepository;
import com.market.company.dao.SaveCompanyDao;
import com.market.company.dao.SaveCompanyDaoImp;
import com.market.company.domain.CompanyDetails;
import com.market.company.domain.validator.CompanyValidator;
import com.market.company.exception.DuplicateCompanyCodeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class CompanyServiceImpTest {

    SaveCompanyServiceImp saveCompanyServiceImp;
    SaveCompanyService saveCompanyService;

    CompanyResponse companyResponse;

    TransactionLog transactionLog;


    @BeforeEach
    void setUp() {
        saveCompanyServiceImp= mock(SaveCompanyServiceImp.class);
        saveCompanyService=mock(SaveCompanyServiceImp.class);


         transactionLog=mock(TransactionLog.class);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenRequestIsValid() throws DuplicateCompanyCodeException {
        companyResponse=new CompanyResponse(true);
        saveCompanyServiceImp= new SaveCompanyServiceImp();
        CompanyRequest request=new CompanyRequest();
        request.setCompanyCEO("ABC");
        request.setCompanyName("ABC");
        request.setCompanyCode("ABC");
        request.setCompanyTurnOver(1000.0);
        request.setCompanyWebsite("www.www.com");
        request.setStockExchangeType("BSE");
        request.setUserId("ABC");
        CompanyDetails companyDetails = new CompanyDetails(request.getCompanyCode(),request.getCompanyName(),request.getCompanyCEO(),request.getCompanyTurnOver(),request.getCompanyWebsite(),request.getStockExchangeType(),request.getUserId());
        Headers headers = new Headers();
        headers.setEstk_transactionID("1232-214214-12412-1421223-324234");
        headers.setEstk_messageID("32434-1231-1221412-123123-12312");
        headers.setEstk_sessionID("4234234-23423-234234-234234");
        headers.setEstk_creationtimestamp("24234234232");

       when(saveCompanyService.processRequest(request,headers)).thenReturn(companyResponse);

       saveCompanyService.processRequest(request,headers);
        assertTrue(companyResponse!=null && companyResponse.getSuccessIndicator());

    }
}