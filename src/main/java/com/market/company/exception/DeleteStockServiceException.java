package com.market.company.exception;

public class DeleteStockServiceException extends Exception{

    String message;

    public DeleteStockServiceException(String message) {

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
        return "DeleteStockServiceException: External call returned with exception["+"message="+message+"]";
    }
}
