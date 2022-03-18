package com.market.company.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.List;

@Validated
@JsonRootName("companyResponse")
public class CompanyResponse implements Serializable {

    @JsonProperty("companyResponseList")
    private List<CompanyResponseListData> companyResponseList;

    @JsonProperty("successIndicator")
    private Boolean successIndicator;

    @JsonProperty("errorMessages")
    private List<ErrorMessage> errorMessages;

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }
    public CompanyResponse(List<CompanyResponseListData> companyResponseList, List<ErrorMessage> errorMessages) {
        this.companyResponseList = companyResponseList;
        this.errorMessages = errorMessages;
    }
    public CompanyResponse() {
    }

    public CompanyResponse(Boolean successIndicator) {
        this.successIndicator = successIndicator;
    }

    public Boolean getSuccessIndicator() {
        return successIndicator;
    }

    public void setSuccessIndicator(Boolean successIndicator) {
        this.successIndicator = successIndicator;
    }

    public List<CompanyResponseListData> getCompanyResponseList() {
        return companyResponseList;
    }

    public void setCompanyResponseList(List<CompanyResponseListData> companyResponseList) {
        this.companyResponseList = companyResponseList;
    }

    @Override
    public String toString() {
        return "CompanyResponse{" +
                "successIndicator:" + successIndicator +
                ", errorMessages:" + errorMessages +
                '}';
    }
}
