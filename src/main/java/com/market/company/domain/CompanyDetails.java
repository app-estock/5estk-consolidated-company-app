package com.market.company.domain;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "company")
public class CompanyDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "CMP_ID")
    private Integer companyId;
    @Column(name = "CMP_CODE")
    private String companyCode;
    @Column(name = "CMP_NAME")
    private String companyName;
    @Column(name = "CMP_CEO")
    private String companyCEO;
    @Column(name = "CMP_TUROVR")
    private Double companyTurnOver;
    @Column(name = "CMP_WBSTE")
    private String companyWebsite;
    @Column(name = "CMP_XCHG")
    private String stockExchangeType;
    @Column(name = "CMP_LSP")
    private Double latestStockPrice;
    @Column(name = "CMP_UID")
    private String userId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Double getLatestStockPrice() {
        return latestStockPrice;
    }

    public void setLatestStockPrice(Double latestStockPrice) {
        this.latestStockPrice = latestStockPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CompanyDetails(String companyCode, String companyName, String companyCEO, Double companyTurnOver, String companyWebsite, String stockExchangeType, String userId) {
        super();
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.companyCEO = companyCEO;
        this.companyTurnOver = companyTurnOver;
        this.companyWebsite = companyWebsite;
        this.stockExchangeType = stockExchangeType;
        this.userId = userId;

    }
    public CompanyDetails(String companyCode, String companyName, String companyCEO, Double companyTurnOver, String companyWebsite, String stockExchangeType,Double latestStockPrice) {
        super();
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.companyCEO = companyCEO;
        this.companyTurnOver = companyTurnOver;
        this.companyWebsite = companyWebsite;
        this.stockExchangeType = stockExchangeType;
        this.latestStockPrice=latestStockPrice;


    }
 public CompanyDetails(){super();}
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
    public String toString() {
        return "CompanyDetails{" +
                "companyId:" + companyId +
                ", companyCode:'" + companyCode + '\'' +
                ", companyName:'" + companyName + '\'' +
                ", companyCEO:'" + companyCEO + '\'' +
                ", companyTurnOver:" + companyTurnOver +
                ", companyWebsite:'" + companyWebsite + '\'' +
                ", stockExchangeType:'" + stockExchangeType + '\'' +
                ", latestStockPrice:'" + latestStockPrice + '\'' +
                ", userId:'" + userId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDetails details = (CompanyDetails) o;
        return companyId.equals(details.companyId) && companyCode.equals(details.companyCode) && companyName.equals(details.companyName) && companyCEO.equals(details.companyCEO) && companyTurnOver.equals(details.companyTurnOver) && companyWebsite.equals(details.companyWebsite) && stockExchangeType.equals(details.stockExchangeType) && latestStockPrice.equals(details.latestStockPrice) && userId.equals(details.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyCode, companyName, companyCEO, companyTurnOver, companyWebsite, stockExchangeType, latestStockPrice, userId);
    }
}
