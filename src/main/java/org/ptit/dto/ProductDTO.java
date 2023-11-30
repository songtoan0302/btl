package org.ptit.dto;

import java.math.BigDecimal;


public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String urlImage;
    private Integer quantity;

    public ProductDTO(Long id, String name, BigDecimal price, String description, String urlImage, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.urlImage = urlImage;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
