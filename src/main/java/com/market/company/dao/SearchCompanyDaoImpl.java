package com.market.company.dao;

import com.market.company.common.AppConstants;
import com.market.company.domain.CompanyDetails;
import com.market.company.domain.mapper.CompanyDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchCompanyDaoImpl implements SearchCompanyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public CompanyDetails searchCompanyDetails(String companyCode) {
       CompanyDetails companyDetails;

       try{
           companyDetails=jdbcTemplate.query(AppConstants.SEARCH_COMPANY_DETAILS,new CompanyDetailsMapper(),companyCode);

       }
       catch (IncorrectResultSizeDataAccessException e)
       {
           return null;
       }
       return companyDetails;
    }
}
