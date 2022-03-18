package com.market.company.dao;

import com.market.company.common.headers.Headers;
import com.market.company.exception.NoDataFoundException;


public interface DeleteCompanyDao {
    boolean deleteCompanyDetails(String CompanyCode, Headers requestHeaders) throws NoDataFoundException;
}
