package com.market.company.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Objects;

@Validated
@JsonRootName("CompanyRequest")
public class CompanyRequest implements Serializable {
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
    @JsonProperty("userId")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyRequest that = (CompanyRequest) o;
        return companyCode.equals(that.companyCode) && companyName.equals(that.companyName) && companyCEO.equals(that.companyCEO) && companyTurnOver.equals(that.companyTurnOver) && companyWebsite.equals(that.companyWebsite) && stockExchangeType.equals(that.stockExchangeType) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyCode, companyName, companyCEO, companyTurnOver, companyWebsite, stockExchangeType, userId);
    }

    @Override
    public String toString() {
        return "CompanyRequest{" +
                "companyCode:'" + companyCode + '\'' +
                ", companyName:'" + companyName + '\'' +
                ", companyCEO:'" + companyCEO + '\'' +
                ", companyTurnOver:=" + companyTurnOver +
                ", companyWebsite:'" + companyWebsite + '\'' +
                ", stockExchangeType:'" + stockExchangeType + '\'' +
                ", userId:'" + userId + '\'' +
                '}';
    }
}
