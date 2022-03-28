package com.market.company.service;

import com.market.company.api.CompanyResponse;
import com.market.company.domain.CompanyDetails;
import com.market.company.dao.CompanyRepository;
import com.market.company.domain.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCompanyStockPriceServiceImpl implements UpdateCompanyStockPriceService {
    @Autowired
    private CompanyRepository companyRepository;
    @Override
    public CompanyResponse processRequest(StockMapper stockMapper) {
      CompanyDetails company =companyRepository.findCompanyByCompanyCode(stockMapper.getCode());
      if(company!=null)
      {
          company.setLatestStockPrice(stockMapper.getStockPrice());
          companyRepository.save(company);
          return new CompanyResponse(true);
      }
        return new CompanyResponse(false);
    }
}
