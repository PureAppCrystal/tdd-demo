package com.hoonyb.tdddemo.domain.order;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Receiver {
    private String name;
    private String phone;

    public boolean isBlank() {
        return !StringUtils.hasText(name) || !StringUtils.hasText(phone) ;
    }
}
