package com.market.company.common.logging;


import com.market.company.api.ErrorMessage;
import com.market.company.controller.CompanyController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class TransactionLog  {

    private Map<String,String> extendedProperties;
    private String serviceName;
    private String operationName;
    private String requestLog;
    private String responseLog;
    private String errorcode;
    private String status;
    private String logType;
    private String transactionId;
    private String creationTimeStamp;
    private String sessionId;
    private String messageId;

    private List<ErrorMessage> errorMessageList;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    private String methodName;

    public TransactionLog(String serviceName, String operationName, String logType) {
        this.serviceName = serviceName;
        this.operationName = operationName;
        this.logType = logType;
    }

    public Map<String, String> getExtendedProperties() {
        return extendedProperties;
    }

    public void setExtendedProperties(Map<String, String> extendedProperties) {
        this.extendedProperties = extendedProperties;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getRequestLog() {
        return requestLog;
    }

    public void setRequestLog(String requestLog) {
        this.requestLog = requestLog;
    }

    public String getResponseLog() {
        return responseLog;
    }

    public void setResponseLog(String responseLog) {
        this.responseLog = responseLog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public List<ErrorMessage> getErrorMessageList() {
        return errorMessageList;
    }

    public void setErrorMessageList(List<ErrorMessage> errorMessageList) {
        this.errorMessageList = errorMessageList;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(String creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "TransactionLog {" +
                "extendedProperties:" + extendedProperties +
                ", serviceName:'" + serviceName + '\'' +
                ", operationName:'" + operationName + '\'' +
                ", requestLog:'" + requestLog + '\'' +
                ", responseLog:'" + responseLog + '\'' +
                ", errorcode:'" + errorcode + '\'' +
                ", status:'" + status + '\'' +
                ", logEntry:'" + logType + '\'' +
                ", transactionId:'" + transactionId + '\'' +
                ", creationTimeStamp:'" + creationTimeStamp + '\'' +
                ", sessionId:'" + sessionId + '\'' +
                ", messageId:'" + messageId + '\'' +
                ", errorMessageList:" + errorMessageList +
                ", methodName:'" + methodName + '\'' +
                '}';
    }
}
