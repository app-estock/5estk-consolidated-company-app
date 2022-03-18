package com.market.company.exception;

public class DuplicateCompanyCodeException extends Exception {
    String message;

    public DuplicateCompanyCodeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DuplicateCompanyCodeException: Company Code already exists [" + "message=" + message+ "]";

    }
}
