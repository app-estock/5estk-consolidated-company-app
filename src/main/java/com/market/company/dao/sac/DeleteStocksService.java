package com.market.company.dao.sac;

import com.market.company.common.headers.Headers;
import com.market.company.exception.DeleteStockServiceException;

public interface DeleteStocksService {

    public boolean deleteStockService(String companycode, Headers RequestHeaders) throws DeleteStockServiceException;
}
