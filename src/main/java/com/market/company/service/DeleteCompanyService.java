package com.market.company.service;

import com.market.company.api.CompanyResponse;
import com.market.company.common.headers.Headers;
import com.market.company.exception.DeleteStockServiceException;
import com.market.company.exception.NoDataFoundException;

public interface DeleteCompanyService {

    CompanyResponse processRequest(String companyCode, Headers requestHeaders) throws NoDataFoundException, DeleteStockServiceException;

}
