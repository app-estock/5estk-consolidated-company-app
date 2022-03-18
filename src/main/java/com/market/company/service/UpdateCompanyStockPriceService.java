package com.market.company.service;

import com.market.company.api.CompanyResponse;
import com.market.company.domain.mapper.StockMapper;

public interface UpdateCompanyStockPriceService {

    CompanyResponse processRequest(StockMapper stockMapper);
}
