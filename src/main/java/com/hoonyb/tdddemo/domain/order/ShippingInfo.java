package com.hoonyb.tdddemo.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingInfo {
    private Receiver receiver;
    private Address address;

    public boolean isBlank() {
        return receiver == null || receiver.isBlank() || address == null || address.isBlank();
    }
}
