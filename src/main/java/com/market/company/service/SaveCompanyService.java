package com.market.company.service;

import com.market.company.api.CompanyRequest;
import com.market.company.api.CompanyResponse;
import com.market.company.common.headers.Headers;
import com.market.company.exception.DuplicateCompanyCodeException;
import org.springframework.stereotype.Service;

@Service
public interface SaveCompanyService {

    CompanyResponse processRequest(CompanyRequest request, Headers RequestHeaders) throws DuplicateCompanyCodeException;
}
