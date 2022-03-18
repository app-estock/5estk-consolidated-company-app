package com.market.company.dao;

import com.market.company.domain.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CompanyRepository extends JpaRepository<CompanyDetails,String> {


    CompanyDetails findByCompanyCode(String code);
}
