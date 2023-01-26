package com.hoonyb.tdddemo.domain.order;

public enum OrderState {
    PAYMENT_WAITING {
        public boolean isShippingChangeable() {
            return true;
        }
    },
    PREPARING {
        public boolean isShippingChangeable() {
            return true;
        }
    },
    SHIPPED,
    DELIVERING,
    DELIVERING_COMPLETED,
    CANCELED;

    public boolean isShippingChangeable() {
        return false;
    }
}
