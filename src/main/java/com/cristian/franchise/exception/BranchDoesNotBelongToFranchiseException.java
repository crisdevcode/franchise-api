package com.cristian.franchise.exception;

public class BranchDoesNotBelongToFranchiseException extends RuntimeException {
    public BranchDoesNotBelongToFranchiseException(String message) {
        super(message);
    }
}