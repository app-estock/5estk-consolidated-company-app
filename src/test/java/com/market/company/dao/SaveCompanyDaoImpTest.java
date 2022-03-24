package com.market.company.dao;

import com.market.company.api.CompanyRequest;
import com.market.company.common.headers.Headers;
import com.market.company.common.logging.TransactionLog;
import com.market.company.domain.CompanyDetails;
import com.market.company.exception.DuplicateCompanyCodeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SaveCompanyDaoImpTest {
SaveCompanyDaoImp saveCompanyDaoImp;
@Autowired
CompanyRepository companyRepository;
TransactionLog transactionLog;

    @BeforeEach
    void setUp() {
        saveCompanyDaoImp= mock(SaveCompanyDaoImp.class);
        companyRepository=mock(CompanyRepository.class);
        transactionLog=mock(TransactionLog.class);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveCompanyDetails() throws DuplicateCompanyCodeException {
        Headers headers = new Headers();
        headers.setEstk_transactionID("1232-214214-12412-1421223-324234");
        headers.setEstk_messageID("32434-1231-1221412-123123-12312");
        headers.setEstk_sessionID("4234234-23423-234234-234234");
        headers.setEstk_creationtimestamp("24234234232");

        CompanyRequest request=new CompanyRequest();
        request.setCompanyCEO("ABC");
        request.setCompanyCode("ABC");
        request.setCompanyTurnOver(1000.0);
        request.setCompanyWebsite("www.www.com");
        request.setStockExchangeType("BSE");
        request.setUserId("ABC");
        CompanyDetails companyDetails = new CompanyDetails(request.getCompanyCode(),request.getCompanyName(),request.getCompanyCEO(),request.getCompanyTurnOver(),request.getCompanyWebsite(),request.getStockExchangeType(),request.getUserId());



        when(saveCompanyDaoImp.saveCompanyDetails(companyDetails,headers)).thenReturn(true);
        boolean result=saveCompanyDaoImp.saveCompanyDetails(companyDetails,headers);
        assertTrue(result);
    }
}