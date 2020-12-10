package com.bc.bit.event;

public class IntentInfoSuccessEvent {
    private int tag;

    public IntentInfoSuccessEvent(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
