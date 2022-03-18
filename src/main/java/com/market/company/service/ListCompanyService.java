package com.market.company.service;

import com.market.company.api.CompanyResponse;
import com.market.company.api.CompanyResponseListData;
import com.market.company.common.headers.Headers;

import java.util.List;

public interface ListCompanyService {
    List<CompanyResponseListData> processRequest(Headers headers);
    List<CompanyResponseListData> processRequest(String userId, Headers headers);
}
