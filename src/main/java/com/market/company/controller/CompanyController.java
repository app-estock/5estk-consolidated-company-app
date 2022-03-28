package com.market.company.controller;


import com.market.company.api.*;
import com.market.company.common.AppConstants;
import com.market.company.common.CommonUtility;
import com.market.company.common.faulthandler.FaultCode;
import com.market.company.common.faulthandler.FaultHandler;
import com.market.company.common.headers.Headers;
import com.market.company.common.logging.TransactionLog;
import com.market.company.domain.validator.CompanyValidator;
import com.market.company.exception.DeleteStockServiceException;
import com.market.company.exception.DuplicateCompanyCodeException;
import com.market.company.exception.NoDataFoundException;
import com.market.company.service.SaveCompanyService;
import com.market.company.service.DeleteCompanyService;
import com.market.company.service.ListCompanyService;
import com.market.company.service.SearchCompanyService;
import com.market.company.domain.mapper.StockMapper;
;
import com.market.company.service.UpdateCompanyStockPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@Validated
@RestController
public class CompanyController {
    //region private variable declaration
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private SaveCompanyService saveCompanyService;

    @Autowired
    private UpdateCompanyStockPriceService updateStockService;

    @Autowired
    private DeleteCompanyService deleteCompanyService;

    @Autowired
    private ListCompanyService listCompanyService;

    @Autowired
    private SearchCompanyService searchCompanyService;

    private TransactionLog transactionLog;
    //private final Producer producer;
    //@Autowired
    //CompanyController(Producer producer) {
    //    this.producer = producer;
    //}
    //endRegion

    //region Implementation method
    @PostMapping(path = "/api/v1.0/market/company/register", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<CompanyResponse> saveCompanyV1(@RequestHeader Map<String, String> requestHeaders, @RequestBody CompanyRequest companyRequest, Error error) {

        CompanyResponse response = null;
        Map<String, String> responseHeaders = null;
        Headers headers = null;
        HttpHeaders respHeaders = new HttpHeaders();
        transactionLog = new TransactionLog("SaveCompanyV1", "saveCompanyV1", "Controller");

        List<ErrorMessage> estkErrorList = new ArrayList<>();

        try {

            if (CompanyValidator.areHeadersValid(requestHeaders)) {
                headers = mapHeaders(requestHeaders);
                responseHeaders = mapHeadersToObject(requestHeaders);
                respHeaders.setAll(responseHeaders);

                //region transaction log population
                String methodName = new Object() {
                }.getClass().getEnclosingMethod().getName();
                transactionLog.setMethodName(methodName);
                transactionLog.setRequestLog(companyRequest.toString());
                transactionLog.setErrorcode("NONE");
                transactionLog.setTransactionId(headers.getEstk_transactionID());
                transactionLog.setMessageId(headers.getEstk_messageID());
                transactionLog.setSessionId(headers.getEstk_sessionID());
                transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
                //endregion

                if (CompanyValidator.isRequestValid(companyRequest)) {
                    response = saveCompanyService.processRequest(companyRequest, headers);

                } else {
                    response = mapEmptyInvalidRequest(estkErrorList);
                    transactionLog.setResponseLog(response.toString());
                    transactionLog.setStatus(AppConstants.FAIL);
                    logger.error(transactionLog.toString());
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST).badRequest().headers(respHeaders).body(response);

                }
                if (response.getSuccessIndicator().equals(Boolean.TRUE) && CommonUtility.isListEmpty(estkErrorList)) {
                    transactionLog.setResponseLog(response.toString());
                    transactionLog.setStatus(AppConstants.SUCCESS);
                    logger.info(transactionLog.toString());
                    return new ResponseEntity<>(response, HttpStatus.CREATED).ok().headers(respHeaders).body(response);
                } else {
                    transactionLog.setResponseLog(response.toString());
                    transactionLog.setStatus(AppConstants.SUCCESS);
                    logger.info(transactionLog.toString());
                    return new ResponseEntity<>(response, HttpStatus.OK).ok().headers(respHeaders).body(response);
                }
            } else {
                transactionLog.setResponseLog(response.toString());
                transactionLog.setStatus(AppConstants.FAIL);
                logger.error(transactionLog.toString());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST).badRequest().headers(respHeaders).body(response);
            }
        } catch (DuplicateCompanyCodeException e) {
            response = mapDuplicateCodeException(e, companyRequest.getCompanyCode(), estkErrorList);
            transactionLog.setResponseLog(response.toString());
            transactionLog.setStatus(AppConstants.FAIL);
            logger.error(transactionLog.toString());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST).ok().headers(respHeaders).body(response);
        } catch (Exception e) {
            response = mapInternalServerException(e, estkErrorList);
            transactionLog.setResponseLog(response.toString());
            transactionLog.setStatus(AppConstants.FAIL);
            logger.error(transactionLog.toString());


            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR).internalServerError().headers(respHeaders).body(response);
        }
        finally{

        }
    }

    @DeleteMapping(path = "/api/v1.0/market/company/delete/{companycode}", produces = {"application/json"})
    public ResponseEntity<CompanyResponse> deleteCompanyV1(@RequestHeader Map<String,String> requestHeaders, @PathVariable("companycode") final String companyCode, Error error) {

        CompanyResponse response = null;

        Map<String, String> responseHeaders = null;
        Headers headers = null;
        HttpHeaders respHeaders = new HttpHeaders();
        transactionLog = new TransactionLog("DeleteCompanyV1", "deleteCompanyV1", "Controller");
        List<ErrorMessage> estkErrorList = new ArrayList<>();
        try {
            if (companyCode != null && CompanyValidator.areHeadersValid(requestHeaders)) {

                headers = mapHeaders(requestHeaders);
                responseHeaders = mapHeadersToObject(requestHeaders);
                respHeaders.setAll(responseHeaders);

                //region transaction log population
                String methodName = new Object() {
                }.getClass().getEnclosingMethod().getName();
                transactionLog.setMethodName(methodName);
                transactionLog.setRequestLog("CompanyCode"+companyCode);
                transactionLog.setErrorcode("NONE");
                transactionLog.setTransactionId(headers.getEstk_transactionID());
                transactionLog.setMessageId(headers.getEstk_messageID());
                transactionLog.setSessionId(headers.getEstk_sessionID());
                transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
                //endregion
                response = deleteCompanyService.processRequest(companyCode,headers);


            } else {
                response = mapEmptyInvalidRequest(estkErrorList);
                transactionLog.setResponseLog(response.toString());
                transactionLog.setStatus(AppConstants.FAIL);
                logger.error(transactionLog.toString());
                return ResponseEntity.badRequest().headers(respHeaders).body(response);
            }
            if (response.getSuccessIndicator().equals(Boolean.TRUE) && CommonUtility.isListEmpty(estkErrorList)) {
                transactionLog.setResponseLog(response.toString());
                transactionLog.setStatus(AppConstants.SUCCESS);
                logger.info(transactionLog.toString());
                return ResponseEntity.ok().headers(respHeaders).body(response);

            } else {
                if (response.getErrorMessages()!=null) {
                    transactionLog.setResponseLog(response.toString());
                    transactionLog.setStatus(AppConstants.FAIL);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                } else {
                    transactionLog.setResponseLog(response.toString());
                    transactionLog.setStatus(AppConstants.FAIL);
                    logger.error(transactionLog.toString());
                    return ResponseEntity.accepted().headers(respHeaders).body(response);
                }
            }
        }catch (NoDataFoundException e)
        {
            response=mapNoDataFoundException(e,estkErrorList);
            transactionLog.setResponseLog(response.toString());
            transactionLog.setStatus(AppConstants.FAIL);
            transactionLog.setResponseLog(response.toString());
            logger.error(transactionLog.toString());
            return ResponseEntity.ok().headers(respHeaders).body(response);
        }
        catch (DeleteStockServiceException e)
        {
            response=mapDeleteStockServiceException(e,estkErrorList);
            transactionLog.setResponseLog(response.toString());
            transactionLog.setStatus(AppConstants.FAIL);
            transactionLog.setResponseLog(response.toString());
            logger.error(transactionLog.toString());
            return ResponseEntity.ok().headers(respHeaders).body(response);
        }
        catch (Exception e) {
            response = mapInternalServerException(e, estkErrorList);
            transactionLog.setResponseLog(response.toString());
            transactionLog.setStatus(AppConstants.FAIL);
            transactionLog.setResponseLog(response.toString());
            logger.error(transactionLog.toString());
            return ResponseEntity.internalServerError().headers(respHeaders).body(response);
        }
        finally{
            //this.producer.sendMessage(transactionLog.toString());
        }

    }

    @GetMapping(value= "/api/v1.0/market/company/getall", produces = {"application/json"})
    public ResponseEntity listCompanyV1(@RequestHeader Map<String,String> requestHeaders)
    {
        List<CompanyResponseListData> response=null;
        List<ErrorMessage> estkErrorList = new ArrayList<>();
        Map<String, String> responseHeaders = null;
        Headers headers = null;
        HttpHeaders respHeaders = new HttpHeaders();
        transactionLog = new TransactionLog("ListCompanyV1", "listCompanyV1", "Controller");
        try {
            if (CompanyValidator.areHeadersValid(requestHeaders)) {
                headers = mapHeaders(requestHeaders);
                responseHeaders = mapHeadersToObject(requestHeaders);
                respHeaders.setAll(responseHeaders);

                //region transaction log population
                String methodName = new Object() {
                }.getClass().getEnclosingMethod().getName();
                transactionLog.setMethodName(methodName);
                transactionLog.setRequestLog("path:/api/v1.0/market/company/getall");
                transactionLog.setErrorcode("NONE");
                transactionLog.setTransactionId(headers.getEstk_transactionID());
                transactionLog.setMessageId(headers.getEstk_messageID());
                transactionLog.setSessionId(headers.getEstk_sessionID());
                transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
                //endregion

                response = listCompanyService.processRequest(headers);
                if(response!=null && response.size()>0)
                {   transactionLog.setResponseLog(response.toString());
                    transactionLog.setStatus(AppConstants.SUCCESS);
                    logger.info(transactionLog.toString());
                    return ResponseEntity.ok().headers(respHeaders).body(response);

                }
                else{
                    CompanyResponse responsebody=mapNoDataFoundResponse(estkErrorList);
                    transactionLog.setErrorcode(FaultCode.ESTK0404.name());
                    transactionLog.setErrorMessageList(estkErrorList);
                    transactionLog.setResponseLog(responsebody.toString());
                    transactionLog.setStatus(AppConstants.SUCCESS);
                    logger.info(transactionLog.toString());
                    return ResponseEntity.ok().headers(respHeaders).body(responsebody);
                }

            }
            else{
                //bad request
                ErrorMessage errorMessage= new ErrorMessage();
                errorMessage.setErrorDef("Headers Mismatch");

                transactionLog.setStatus(AppConstants.FAIL);
                logger.error(transactionLog.toString());
                return ResponseEntity.badRequest().headers(respHeaders).body(errorMessage);
            }
        }
        catch(Exception e)
        {  CompanyResponse responsebody= mapInternalServerException(e, estkErrorList);
            transactionLog.setResponseLog(responsebody.toString());
            transactionLog.setStatus(AppConstants.FAIL);
            logger.error(transactionLog.toString());
            return ResponseEntity.badRequest().headers(respHeaders).body(responsebody);
        }
        finally {
            //this.producer.sendMessage(transactionLog.toString());
        }


    }
    @GetMapping(value= "/api/v1.0/market/company/get/{userid}", produces = {"application/json"})
    public ResponseEntity listCompanyV1(@RequestHeader Map<String,String> requestHeaders, @PathVariable("userid") final String userId)
    {
        List<CompanyResponseListData> response=null;
        List<ErrorMessage> estkErrorList = new ArrayList<>();
        Map<String, String> responseHeaders = null;
        Headers headers = null;
        HttpHeaders respHeaders = new HttpHeaders();
        transactionLog = new TransactionLog("ListCompanyV1", "listCompanyV1", "Controller");
        try {
            if (CompanyValidator.areHeadersValid(requestHeaders)) {
                headers = mapHeaders(requestHeaders);
                responseHeaders = mapHeadersToObject(requestHeaders);
                respHeaders.setAll(responseHeaders);

                //region transaction log population
                String methodName = new Object() {
                }.getClass().getEnclosingMethod().getName();
                transactionLog.setMethodName(methodName);
                transactionLog.setRequestLog("userId:"+userId);
                transactionLog.setErrorcode("NONE");
                transactionLog.setTransactionId(headers.getEstk_transactionID());
                transactionLog.setMessageId(headers.getEstk_messageID());
                transactionLog.setSessionId(headers.getEstk_sessionID());
                transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
                //endregion

                response = listCompanyService.processRequest(userId,headers);
                if(response!=null && response.size()>0)
                {   transactionLog.setResponseLog(response.toString());
                    transactionLog.setStatus(AppConstants.SUCCESS);
                    logger.info(transactionLog.toString());
                    return ResponseEntity.ok().headers(respHeaders).body(response);

                }
                else{
                    CompanyResponse responsebody=mapNoDataFoundResponse(estkErrorList);
                    transactionLog.setErrorcode(FaultCode.ESTK0404.name());
                    transactionLog.setErrorMessageList(estkErrorList);
                    transactionLog.setResponseLog(responsebody.toString());
                    transactionLog.setStatus(AppConstants.SUCCESS);
                    logger.info(transactionLog.toString());
                    return ResponseEntity.ok().headers(respHeaders).body(responsebody);
                }

            }
            else{
                //bad request
                ErrorMessage errorMessage= new ErrorMessage();
                errorMessage.setErrorDef("Headers Mismatch");

                transactionLog.setStatus(AppConstants.FAIL);
                logger.error(transactionLog.toString());
                return ResponseEntity.badRequest().headers(respHeaders).body(errorMessage);
            }
        }
        catch(Exception e)
        {  CompanyResponse responsebody= mapInternalServerException(e, estkErrorList);
            transactionLog.setResponseLog(responsebody.toString());
            transactionLog.setStatus(AppConstants.FAIL);
            logger.error(transactionLog.toString());
            return ResponseEntity.badRequest().headers(respHeaders).body(responsebody);
        }


    }

    @PutMapping(path = "/api/v1.0/market/company/update/{companycode}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<CompanyResponse> updateCompanyStockV1(@RequestBody StockRequest request, @PathVariable("companycode") final String companyCode, Error error) {

        CompanyResponse response = null;
        try{

            response=updateStockService.processRequest(new StockMapper(companyCode,request.getStockPrice()));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/api/v1.0/market/company/info/{companycode}", produces = {"application/json"})
    public ResponseEntity searchCompanyV1(@RequestHeader Map<String,String> requestHeaders, @PathVariable("companycode") final String companyCode, Error error) {
        CompanyResponseData responsedata = null;
        CompanyResponse response=null;
        Map<String, String> responseHeaders = null;
        Headers headers = null;
        HttpHeaders respHeaders = new HttpHeaders();
        List<ErrorMessage> estkErrorList = new ArrayList<>();
        transactionLog = new TransactionLog("SearchCompanyV1", "searchCompanyV1", "Controller");
        try {
            if (CompanyValidator.areHeadersValid(requestHeaders)) {
                headers = mapHeaders(requestHeaders);
                responseHeaders = mapHeadersToObject(requestHeaders);
                respHeaders.setAll(responseHeaders);

                //region transaction log population
                String methodName = new Object() {
                }.getClass().getEnclosingMethod().getName();
                transactionLog.setMethodName(methodName);
                transactionLog.setRequestLog("companycode" + companyCode);
                transactionLog.setErrorcode("NONE");
                transactionLog.setTransactionId(headers.getEstk_transactionID());
                transactionLog.setMessageId(headers.getEstk_messageID());
                transactionLog.setSessionId(headers.getEstk_sessionID());
                transactionLog.setCreationTimeStamp(headers.getEstk_creationtimestamp());
                //endregion

                if (companyCode != null) {
                    responsedata = searchCompanyService.processRequest(companyCode,headers);
                    return ResponseEntity.ok().headers(respHeaders).body(responsedata);
                } else {
                    response = mapEmptyInvalidRequest(estkErrorList);
                    ResponseEntity.badRequest().headers(respHeaders).body(response);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }
            else{
                ErrorMessage errorMessage= new ErrorMessage();
                errorMessage.setErrorDef("Headers Mismatch");

                transactionLog.setStatus(AppConstants.FAIL);
                logger.error(transactionLog.toString());
                return ResponseEntity.badRequest().headers(respHeaders).body(errorMessage);
            }

        } catch (Exception e) {
            response = mapInternalServerException(e, estkErrorList);
            return ResponseEntity.internalServerError().headers(respHeaders).body(response);
        }
        finally {
            logger.info(transactionLog.toString());
        }


    }



    //endregion

//region Private Methods
private CompanyResponse mapNoDataFoundResponse(List<ErrorMessage> estkErrorList){
    CompanyResponse response = new CompanyResponse();
    FaultHandler faultHandler = CommonUtility.createFault(FaultCode.ESTK0404, AppConstants.ESTK0404_NO_DATA_RESPONSE_TEXT);
    response.setErrorMessages(CommonUtility.addtoEstkErrorlist(estkErrorList, faultHandler));
    return response;
}

private CompanyResponse mapNoDataFoundException(Exception e, List<ErrorMessage> estkErrorList) {
    CompanyResponse response = new CompanyResponse();
    FaultHandler faultHandler = CommonUtility.createFault(FaultCode.ESTK0404, AppConstants.ESTK0404_NO_DATA_RESPONSE_TEXT);
    response.setErrorMessages(CommonUtility.addtoEstkErrorlist(estkErrorList, faultHandler));
    response.setSuccessIndicator(false);

    return response;
}
    private CompanyResponse mapDeleteStockServiceException(Exception e, List<ErrorMessage> estkErrorList) {
        CompanyResponse response = new CompanyResponse();
        FaultHandler faultHandler = CommonUtility.createFault(FaultCode.ESTK0000, AppConstants.ESTK0000_INTERNAL_SERVER_RESPONSE_TEXT);
        response.setErrorMessages(CommonUtility.addtoEstkErrorlist(estkErrorList, faultHandler));
        response.setSuccessIndicator(false);
        return response;
    }



    /**
     * Method to map Duplicate Exception response
     *
     * @param e             Exception object
     * @param companyCode   string
     * @param estkErrorList
     * @return CompanyResponse
     */
    private CompanyResponse mapDuplicateCodeException(DuplicateCompanyCodeException e, String companyCode, List<ErrorMessage> estkErrorList) {
        CompanyResponse response = new CompanyResponse();
        FaultHandler faultHandler = CommonUtility.createFault(FaultCode.ESTK0402, AppConstants.ESTK0402_DUPLICATE_CODE_RESPONSE_TEXT.replace("#COMPANYCODE#", companyCode));
        response.setErrorMessages(CommonUtility.addtoEstkErrorlist(estkErrorList, faultHandler));
        response.setSuccessIndicator(false);

        return response;
    }

    /**
     * @param e
     * @param estkErrorList
     * @return
     */
    private CompanyResponse mapInternalServerException(Exception e, List<ErrorMessage> estkErrorList) {
        CompanyResponse response = new CompanyResponse();
        FaultHandler faultHandler = CommonUtility.createFault(FaultCode.ESTK0000, AppConstants.ESTK0000_INTERNAL_SERVER_RESPONSE_TEXT);
        response.setErrorMessages(CommonUtility.addtoEstkErrorlist(estkErrorList, faultHandler));
        response.setSuccessIndicator(false);

        return response;
    }

    /**
     * @param estkErrorList
     * @return
     */
    private CompanyResponse mapEmptyInvalidRequest(List<ErrorMessage> estkErrorList) {
        CompanyResponse response = new CompanyResponse();
        FaultHandler faultHandler = CommonUtility.createFault(FaultCode.ESTK0001, AppConstants.ESTK0001_INVALID_EMPTY_REQUEST_RESPONSE_TEXT);
        response.setErrorMessages(CommonUtility.addtoEstkErrorlist(estkErrorList, faultHandler));
        response.setSuccessIndicator(false);

        return response;
    }

    /**
     * @param requestHeaders
     * @return
     */
    private Map<String, String> mapHeadersToObject(Map<String, String> requestHeaders) {
        Map<String, String> objectHeaders = new HashMap<>();
        if (requestHeaders.containsKey(AppConstants.ESTK_TRANSACTIONID)) {
            if (requestHeaders.get(AppConstants.ESTK_TRANSACTIONID) == null) {
                objectHeaders.put(AppConstants.ESTK_TRANSACTIONID, UUID.randomUUID().toString());
            }
            objectHeaders.put(AppConstants.ESTK_TRANSACTIONID, requestHeaders.get(AppConstants.ESTK_TRANSACTIONID));
        }
        if (requestHeaders.containsKey(AppConstants.ESTK_SESSIONID)) {
            if (requestHeaders.get(AppConstants.ESTK_SESSIONID) == null) {
                objectHeaders.put(AppConstants.ESTK_SESSIONID, UUID.randomUUID().toString());
            }
            objectHeaders.put(AppConstants.ESTK_SESSIONID, requestHeaders.get(AppConstants.ESTK_SESSIONID));
        }
        if (requestHeaders.containsKey(AppConstants.ESTK_MESSAGEID)) {
            if (requestHeaders.get(AppConstants.ESTK_MESSAGEID) == null) {
                objectHeaders.put(AppConstants.ESTK_MESSAGEID, UUID.randomUUID().toString());
            }
            objectHeaders.put(AppConstants.ESTK_MESSAGEID, requestHeaders.get(AppConstants.ESTK_MESSAGEID));
        }
        if (requestHeaders.containsKey(AppConstants.ESTK_CREATIONTIMESTAMP)) {
            if (requestHeaders.get(AppConstants.ESTK_CREATIONTIMESTAMP) == null) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                objectHeaders.put(AppConstants.ESTK_CREATIONTIMESTAMP, timestamp.toString());
            }
            objectHeaders.put(AppConstants.ESTK_CREATIONTIMESTAMP, requestHeaders.get(AppConstants.ESTK_CREATIONTIMESTAMP));
        }


        return objectHeaders;
    }

    /**
     * @param objectHeaders
     * @return
     */
    private Headers mapHeaders(Map<String, String> objectHeaders) {
        Headers headers = new Headers();
        if (objectHeaders.containsKey(AppConstants.ESTK_TRANSACTIONID)) {
            if (objectHeaders.get(AppConstants.ESTK_TRANSACTIONID) == null) {
                headers.setEstk_transactionID(UUID.randomUUID().toString());
            }
            headers.setEstk_transactionID(objectHeaders.get(AppConstants.ESTK_TRANSACTIONID));
        }
        if (objectHeaders.containsKey(AppConstants.ESTK_SESSIONID)) {
            if (objectHeaders.get(AppConstants.ESTK_SESSIONID) == null) {
                headers.setEstk_sessionID(UUID.randomUUID().toString());
            }
            headers.setEstk_sessionID(objectHeaders.get(AppConstants.ESTK_SESSIONID));
        }
        if (objectHeaders.containsKey(AppConstants.ESTK_MESSAGEID)) {
            if (objectHeaders.get(AppConstants.ESTK_MESSAGEID) == null) {
                headers.setEstk_messageID(UUID.randomUUID().toString());
            }
            headers.setEstk_messageID(objectHeaders.get(AppConstants.ESTK_MESSAGEID));
        }
        if (objectHeaders.containsKey(AppConstants.ESTK_CREATIONTIMESTAMP)) {
            if (objectHeaders.get(AppConstants.ESTK_CREATIONTIMESTAMP) == null) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                headers.setEstk_creationtimestamp(timestamp.toString());
            }
            headers.setEstk_creationtimestamp(objectHeaders.get(AppConstants.ESTK_CREATIONTIMESTAMP));
        }
        return headers;
    }
//endregion
}
