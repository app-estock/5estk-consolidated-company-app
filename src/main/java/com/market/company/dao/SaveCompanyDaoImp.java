package com.market.company.dao;

import com.market.company.common.AppConstants;
import com.market.company.common.headers.Headers;
import com.market.company.common.logging.TransactionLog;
import com.market.company.domain.CompanyDetails;
import com.market.company.dao.CompanyRepository;
import com.market.company.exception.DuplicateCompanyCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SaveCompanyDaoImp implements SaveCompanyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CompanyRepository companyRepository;


    private static final Logger logger = LoggerFactory.getLogger(SaveCompanyDaoImp.class);
    private TransactionLog transactionLog;

    @Override
    public boolean saveCompanyDetails(CompanyDetails companyDetails, Headers headers) throws DuplicateCompanyCodeException {
        boolean recordSaved = false;
        int result;
        transactionLog = new TransactionLog("SaveCompanyV1", "saveCompanyV1", "Database");
        Map<String, String> extendedProperties = new HashMap<>();

        //region transaction log population
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        transactionLog.setMethodName(methodName);
        transactionLog.setRequestLog(companyDetails.toString());
        transactionLog.setErrorcode("NONE");
        transactionLog.setTransactionId(headers.getEstk_transactionID());
        transactionLog.setMessageId(headers.getEstk_messageID());
        transactionLog.setSessionId(headers.getEstk_sessionID());
        transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
        extendedProperties.put("calling", "mysqldb");
        extendedProperties.put("sqlName", "INSERT_COMPANY_DETAILS");

        //endregion

        try {
            CompanyDetails cd=companyRepository.findCompanyByCompanyCode(companyDetails.getCompanyCode());
            if (cd!=null) {
                recordSaved = false;
                extendedProperties.put("recordSaved", "false");
                throw new DuplicateCompanyCodeException("Company Already Exist");
            }
            else {
                recordSaved = true;
                transactionLog.setStatus(AppConstants.SUCCESS);
                extendedProperties.put("recordSaved", "true");
                companyRepository.save(companyDetails);
            }

            return recordSaved;

        } catch (Exception e) {

            throw new DuplicateCompanyCodeException(e.getMessage());
            }

        finally {
            //log
            transactionLog.setExtendedProperties(extendedProperties);
            logger.info(transactionLog.toString());

        }

    }
}
