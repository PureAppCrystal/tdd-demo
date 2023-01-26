package com.hoonyb.tdddemo.domain.order;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Address {
    private String address1;
    private String address2;
    private String zipCode;

    public boolean isBlank() {
        return !StringUtils.hasText(address1) ||
                !StringUtils.hasText(address2) ||
                !StringUtils.hasText(zipCode);
    }
}
