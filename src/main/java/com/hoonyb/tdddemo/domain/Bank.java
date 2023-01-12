package com.hoonyb.tdddemo.domain;

public class Bank {
    
    public int rate(String currency, String to ) {
        int rate = 1;

        switch(currency) {
            case "CHF" :
                if(to.equals("CHF")) {
                    rate = 1;
                } else if (to.equals("USD")) {
                    rate = 2;
                }
                break;
            case "USD" :
                if(to.equals("USD")) {
                    rate = 1;
                } else if (to.equals("CHF")) {
                    rate = 1/2;
                }
                break;
            default:
        }
        
        return rate;
    }
}
