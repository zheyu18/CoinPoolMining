package com.bc.bit.event;

public class ChangeSignatureEvent {

    private String signature;

    public ChangeSignatureEvent(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
