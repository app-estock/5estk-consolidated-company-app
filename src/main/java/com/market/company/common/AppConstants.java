package com.market.company.common;

import java.security.Timestamp;

public  class AppConstants {

    private AppConstants(){}

    public static final String DELETE_COMPANY_DETAILS = "DELETE FROM COMPANY WHERE CMP_CODE = ?";
    public static final String LIST_ALL_COMPANY_DETAILS = "SELECT * FROM COMPANY";
    public static final String LIST_COMPANY_DETAILS_BY_USER = "SELECT * FROM COMPANY WHERE CMP_UID=?";
    public static final String SEARCH_COMPANY_DETAILS = "SELECT * FROM COMPANY WHERE CMP_CODE = ?";
    public static final String INSERT_COMPANY_DETAILS="INSERT INTO COMPANY (CMP_CODE,CMP_NAME,CMP_CEO,CMP_TUROVR,CMP_XCHG,CMP_WBSTE) VALUES (?,?,?,?,?,?)";
    public static final String DUPLICATEKEYEXCEPTIONCLASSNAME="org.springframework.dao.DuplicateKeyException";


    public static final String ESTK0000_INTERNAL_SERVER_RESPONSE_TEXT="Internal server error. An Unhandled system exception occurred.";
    public static final String ESTK0001_INVALID_EMPTY_REQUEST_RESPONSE_TEXT="Request cannot be empty or incomplete. All field are mandatory.";
    public static final String ESTK0402_DUPLICATE_CODE_RESPONSE_TEXT="Duplicate company code #COMPANYCODE#,#COMPANYCODE# already exists.";
    public static final String ESTK0001_INVALID_TURNOVER_RESPONSE_TEXT="Company Turn over should be greater than 10cr.";
    public static final String ESTK0404_DATA_NOT_SAVED="Query Returned 0, data not saved.";
    public static final String ESTK0500_EXTERNAL_CALL_RETURNED_EXCEPTION="External REST API returned with exception.";

    public static final String ESTK0404_NO_DATA_RESPONSE_TEXT = "No Data Found.";
    public static final  String  ESTK_TRANSACTIONID="estk_transactionid";
    public static final String ESTK_SESSIONID="estk_sessionid";
    public static final String ESTK_MESSAGEID="estk_messageid";
    public static final String ESTK_CREATIONTIMESTAMP="estk_creationtimestamp";

    public static final String SUCCESS="Success";
    public static final String FAIL="Failed";
}
