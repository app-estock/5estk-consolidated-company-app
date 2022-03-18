package com.market.company.domain.mapper;

import com.market.company.domain.CompanyDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDetailsMapper implements ResultSetExtractor<CompanyDetails> {
    @Override
    public CompanyDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
        rs.next();
        return new CompanyDetails(rs.getString("CMP_CODE"),
                rs.getString("CMP_NAME"),
                rs.getString("CMP_CEO"),
                rs.getDouble("CMP_TUROVR"),
                rs.getString("CMP_WBSTE"),
                rs.getString("CMP_XCHG"),
                rs.getDouble("CMP_LSP")
        );


    }
}
