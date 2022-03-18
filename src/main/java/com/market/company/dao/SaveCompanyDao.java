package com.market.company.dao;

import com.market.company.common.headers.Headers;
import com.market.company.domain.CompanyDetails;
import com.market.company.exception.DuplicateCompanyCodeException;

public interface SaveCompanyDao {
    boolean saveCompanyDetails(CompanyDetails companyDetails, Headers headers) throws DuplicateCompanyCodeException;
}
