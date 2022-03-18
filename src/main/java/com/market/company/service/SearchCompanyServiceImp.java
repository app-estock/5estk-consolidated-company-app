package com.market.company.service;

import com.market.company.api.CompanyResponseData;
import com.market.company.api.ErrorMessage;
import com.market.company.dao.SearchCompanyDao;
import com.market.company.domain.CompanyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class SearchCompanyServiceImp  implements SearchCompanyService{

    @Autowired
    private SearchCompanyDao searchCompanyDao;
    private List<ErrorMessage> estkErrorList;

    @Override
    public CompanyResponseData processRequest(String companyCode) {
        CompanyResponseData response = null;
        CompanyDetails companyDetails =null;
        estkErrorList= new ArrayList<>();
        try{
              companyDetails=searchCompanyDao.searchCompanyDetails(companyCode);
              if(companyDetails!=null)
              {
                  response=mapCompanyDetails(companyDetails);
                  return response;
              }
        }
        catch (DataAccessException e )
        {
            System.out.println(e);
        }
        catch (Exception e)
        {

        }
return response;
    }
    private CompanyResponseData mapCompanyDetails(CompanyDetails companyDetails) {

        return new CompanyResponseData(companyDetails.getCompanyCode(), companyDetails.getCompanyName(), companyDetails.getCompanyCEO(), companyDetails.getCompanyTurnOver(), companyDetails.getCompanyWebsite(), companyDetails.getStockExchangeType(),companyDetails.getLatestStockPrice());
    }
}
