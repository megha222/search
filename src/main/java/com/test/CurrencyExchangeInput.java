package com.test;

/// This class represents each exchange input from the given list of currency exchanges
public class CurrencyExchangeInput {

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getBid() {
        return bid;
    }

    public double getAsk() {
        return ask;
    }

    private final String fromCurrency;
    private final String toCurrency;
    private final double bid;
    private final double ask;

    public CurrencyExchangeInput(String from, String to, double bid, double ask) {
        this.fromCurrency = from;
        this.toCurrency = to;
        this.bid = bid;
        this.ask = ask;
    }
}
