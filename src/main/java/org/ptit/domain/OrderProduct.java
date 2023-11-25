package org.ptit.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A OrderProduct.
 */
@Entity
@Table(name = "order_product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderDetail order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderProduct id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public OrderProduct productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public OrderProduct quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public OrderProduct price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderDetail getOrder() {
        return this.order;
    }

    public void setOrder(OrderDetail orderDetail) {
        this.order = orderDetail;
    }

    public OrderProduct order(OrderDetail orderDetail) {
        this.setOrder(orderDetail);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderProduct product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderProduct)) {
            return false;
        }
        return getId() != null && getId().equals(((OrderProduct) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderProduct{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            "}";
    }
}
