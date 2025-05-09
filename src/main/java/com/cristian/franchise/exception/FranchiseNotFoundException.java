package com.cristian.franchise.exception;

public class FranchiseNotFoundException extends RuntimeException{
    public FranchiseNotFoundException(String message) {
        super(message);
    }
}
