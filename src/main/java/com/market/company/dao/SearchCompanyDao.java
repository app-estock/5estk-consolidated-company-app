package com.market.company.dao;

import com.market.company.domain.CompanyDetails;

public interface SearchCompanyDao {
    CompanyDetails searchCompanyDetails(String CompanyCode);
}
