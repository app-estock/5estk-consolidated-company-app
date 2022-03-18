package com.market.company.domain.mapper;

import com.market.company.domain.CompanyDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDetailsListMapper implements ResultSetExtractor<List<CompanyDetails>> {
    @Override
    public List<CompanyDetails> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<CompanyDetails> companyDetailsList = new ArrayList<>();
        while (rs.next()) {
            companyDetailsList.add(new CompanyDetails(rs.getString("CMP_CODE"),
                    rs.getString("CMP_NAME"),
                    rs.getString("CMP_CEO"),
                    rs.getDouble("CMP_TUROVR"),
                    rs.getString("CMP_WBSTE"),
                    rs.getString("CMP_XCHG"),
                    rs.getDouble("CMP_LSP")
            ));
        }
        return companyDetailsList;
    }
}
