package org.ptit.dto;

import org.ptit.domain.Product593;

import java.util.List;

public class OrderDetailsRequest {

    private Integer userId;
    private List<Product593> product;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Product593> getProduct() {
        return product;
    }

    public void setProduct(List<Product593> product) {
        this.product = product;
    }
}
