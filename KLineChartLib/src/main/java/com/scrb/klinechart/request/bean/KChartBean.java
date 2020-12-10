package com.scrb.klinechart.request.bean;

public class KChartBean {

    /**
     * ticker : BINANCE:BTCUSDT
     * exchangeName : Binance
     * base : BTC
     * currency : USDT
     * symbol : BTCUSDT
     * high : 65552.2671041184
     * open : 65452.8849973943
     * close : 65253.940326
     * low : 62619.9621710535
     * vol : 100899.727196
     * degree : -0.739
     * dateTime : 1590156874000
     */

    private String ticker;
    private String exchangeName;
    private String base;
    private String currency;
    private String symbol;
    private double high;
    private double open;
    private double close;
    private double low;
    private double vol;
    private double degree;
    private long dateTime;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
