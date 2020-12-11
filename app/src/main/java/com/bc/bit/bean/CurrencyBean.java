package com.bc.bit.bean;

import java.util.List;

public class CurrencyBean {

    /**
     * baseCurrency : USD
     * baseSymbol : USD
     * rates : [{"name":"EUR","name_zh":"欧元","rate":0.8433,"symbol":"EUR"},{"name":"GBP","name_zh":"英镑","rate":0.7549,"symbol":"GBP"},{"name":"JPY","name_zh":"日元","rate":104.12,"symbol":"JPY"},{"name":"CNY","name_zh":"人民币","rate":6.5601,"symbol":"CNY"},{"name":"BTC","name_zh":"比特币","rate":5.7E-5,"symbol":"BTC"},{"name":"BITCNY","name_zh":"比特元","rate":6.510847,"symbol":"BITCNY"},{"name":"BCH","name_zh":"比特币现金","rate":0,"symbol":"BCH"}]
     */

    private String baseCurrency;
    private String baseSymbol;
    private List<RatesBean> rates;

    public int getSelectPosition() {
        return selectedPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectedPosition = selectPosition;
    }

    private int selectedPosition = -1;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getBaseSymbol() {
        return baseSymbol;
    }

    public void setBaseSymbol(String baseSymbol) {
        this.baseSymbol = baseSymbol;
    }

    public List<RatesBean> getRates() {
        return rates;
    }

    public void setRates(List<RatesBean> rates) {
        this.rates = rates;
    }

    public static class RatesBean {
        /**
         * name : EUR
         * name_zh : 欧元
         * rate : 0.8433
         * symbol : EUR
         */

        private String name;
        private String name_zh;
        private double rate;
        private String symbol;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_zh() {
            return name_zh;
        }

        public void setName_zh(String name_zh) {
            this.name_zh = name_zh;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }
}
