package com.market.company.service;

import com.market.company.api.CompanyResponseData;
import com.market.company.common.headers.Headers;
import org.springframework.stereotype.Service;


public interface SearchCompanyService {
    CompanyResponseData processRequest(String CompanyCode, Headers headers);
}
