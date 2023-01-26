package com.hoonyb.tdddemo.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private String id;
    private ProductType type;
}
