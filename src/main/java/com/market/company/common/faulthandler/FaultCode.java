package com.market.company.common.faulthandler;

public enum FaultCode {


    ESTK0000("AN UNHANDLED SYSTEM EXCEPTION OCCURRED"),
    ESTK0001("EMPTY/INVALID REQUEST"),
    ESTK0401("DATA ACCESS EXCEPTION"),
    ESTK0404("DATA NOT SAVED"),
    ESTK0402("DUPLICATE COMPANY CODE"),
    ESTK0400("A SYSTEM EXCEPTION DURING DATABASE ACCESS"),
    ESTK0500("A SYSTEM EXCEPTION RETURNED FROM EXTERNAL API CALL");


    private String  value;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FaultCode{" +
                "value='" + value + '\'' +
                '}';
    }

    FaultCode(String value) {
        this.value = value;
    }
}
