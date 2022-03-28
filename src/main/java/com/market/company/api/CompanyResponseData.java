package com.market.company.api;
import com.market.company.api.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Validated
@JsonRootName("CompanyResponse")
public class CompanyResponseData {

    @JsonProperty("code")
    private  String companyCode;
    @JsonProperty("name")
    private  String companyName;
    @JsonProperty("ceo")
    private  String companyCEO;
    @JsonProperty("turnover")
    private  Double companyTurnOver;
    @JsonProperty("website")
    private  String companyWebsite;
    @JsonProperty("exchange")
    private  String stockExchangeType;
    @JsonProperty("stockPrice")
    private Double stockPrice;
    @JsonProperty("errorMessages")
    private List<ErrorMessage> errorMessages;

    public CompanyResponseData() {
    }

    public CompanyResponseData(String companyCode, String companyName, String companyCEO, Double companyTurnOver, String companyWebsite, String stockExchangeType,Double stockPrice) {
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.companyCEO = companyCEO;
        this.companyTurnOver = companyTurnOver;
        this.companyWebsite = companyWebsite;
        this.stockExchangeType = stockExchangeType;
        this.stockPrice=stockPrice==null?0.0:stockPrice;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCEO() {
        return companyCEO;
    }

    public void setCompanyCEO(String companyCEO) {
        this.companyCEO = companyCEO;
    }

    public Double getCompanyTurnOver() {
        return companyTurnOver;
    }

    public void setCompanyTurnOver(Double companyTurnOver) {
        this.companyTurnOver = companyTurnOver;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getStockExchangeType() {
        return stockExchangeType;
    }

    public void setStockExchangeType(String stockExchangeType) {
        this.stockExchangeType = stockExchangeType;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    @Override
    public String toString() {
        return "CompanyResponse{" +
                "companyCode='" + companyCode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyCEO='" + companyCEO + '\'' +
                ", companyTurnOver=" + companyTurnOver +
                ", companyWebsite='" + companyWebsite + '\'' +
                ", stockExchangeType='" + stockExchangeType + '\'' +
                ", stockPrice=" + stockPrice +
                ", errorMessages=" + errorMessages +
                '}';
    }


}
