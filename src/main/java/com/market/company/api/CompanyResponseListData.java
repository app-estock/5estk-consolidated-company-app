package com.market.company.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Validated
@JsonRootName("CompanyResponseResponseListData")
public class CompanyResponseListData {

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


    public CompanyResponseListData() {
    }

    public CompanyResponseListData(String companyCode, String companyName, String companyCEO, Double companyTurnOver, String companyWebsite, String stockExchangeType,Double latestStockPrice) {
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.companyCEO = companyCEO;
        this.companyTurnOver = companyTurnOver;
        this.companyWebsite = companyWebsite;
        this.stockExchangeType = stockExchangeType;
        this.stockPrice=latestStockPrice;

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

    public Double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    @Override
    public String toString() {
        return "CompanyResponseListData{" +
                "companyCode:'" + companyCode + '\'' +
                ", companyName:'" + companyName + '\'' +
                ", companyCEO:'" + companyCEO + '\'' +
                ", companyTurnOver:" + companyTurnOver +
                ", companyWebsite:'" + companyWebsite + '\'' +
                ", stockExchangeType:'" + stockExchangeType + '\'' +
                ", stockPrice:" + stockPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyResponseListData that = (CompanyResponseListData) o;
        return Double.compare(that.stockPrice, stockPrice) == 0 && companyCode.equals(that.companyCode) && companyName.equals(that.companyName) && companyCEO.equals(that.companyCEO) && companyTurnOver.equals(that.companyTurnOver) && companyWebsite.equals(that.companyWebsite) && stockExchangeType.equals(that.stockExchangeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyCode, companyName, companyCEO, companyTurnOver, companyWebsite, stockExchangeType, stockPrice);
    }
}
