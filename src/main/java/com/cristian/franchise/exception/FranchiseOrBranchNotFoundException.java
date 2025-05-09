package com.cristian.franchise.exception;

public class FranchiseOrBranchNotFoundException extends RuntimeException {
    public FranchiseOrBranchNotFoundException(String message) {
        super(message);
    }
}
