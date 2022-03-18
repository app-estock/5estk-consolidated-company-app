package com.market.company.dao;

import com.market.company.common.headers.Headers;
import com.market.company.domain.CompanyDetails;

import java.util.List;

public interface ListCompanyDao {
    List<CompanyDetails> listAllCompanyDetails(Headers requestHeaders);
    List<CompanyDetails> listCompanyDetailsByUserId(String userId,Headers requestHeaders);
}
