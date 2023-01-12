package com.hoonyb.tdddemo.domain;

public class Money {
    protected int amount;
    protected String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

 
    public Money times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    public Money plus(Money money) {
        Money originMoney = reduce(this);
        Money newMoney = reduce(money);
        return new Money(originMoney.amount + newMoney.amount, this.currency);
    }

    public Money reduce(Money money) {
        Bank bank = new Bank();
        int rate = bank.rate(money.currency, this.currency);
        return new Money(money.amount / rate, this.currency);
    }

    public static Money dollar(int amount) {
        return new Dollar(amount, "USD");
    }

    public static Money france(int amount) {
        return new France(amount, "CHF");
    }


    public boolean equals(Object object) {
        Money money = (Money)object;
        return this.amount == money.amount && 
                currency().equals(money.currency());
    }

    public String currency() {
        return this.currency;
    }

    public String toString() {
        return amount + " " + currency;
    }
}
