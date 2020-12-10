package com.scrb.klinechart.request.bean;


public class KLineBean {

    @Override
    public String toString() {
        return "KLineBean{" +
                "symbol='" + symbol + '\'' +
                ", dateTime=" + dateTime +
                ", high=" + high +
                ", open=" + open +
                ", low=" + low +
                ", close=" + close +
                ", vol=" + vol +
                '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getVol() {
        return vol;
    }

    public void setVol(float vol) {
        this.vol = vol;
    }

    /**
     * symbol : BTCUSDT
     * dateTime : 1590364800000
     * high : 62814.6876990279
     * open : 62087.2438459225
     * low : 61592.3826285726
     * close : 62536.88461817
     * vol : 4842.8177241936
     */

    private String symbol;
    private long dateTime;
    private float high;
    private float open;
    private float low;
    private float close;
    private float vol;

}
