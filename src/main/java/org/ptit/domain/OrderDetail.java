package org.ptit.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A OrderDetail.
 */
@Entity
@Table(name = "order_detail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "receive_phone_number")
    private String receivePhoneNumber;

    @Lob
    @Column(name = "receive_address")
    private String receiveAddress;

    @Column(name = "status_payment")
    private String statusPayment;

    @Column(name = "status_order")
    private String statusOrder;

    @Column(name = "payment_method")
    private String paymentMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipientName() {
        return this.recipientName;
    }

    public OrderDetail recipientName(String recipientName) {
        this.setRecipientName(recipientName);
        return this;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getReceivePhoneNumber() {
        return this.receivePhoneNumber;
    }

    public OrderDetail receivePhoneNumber(String receivePhoneNumber) {
        this.setReceivePhoneNumber(receivePhoneNumber);
        return this;
    }

    public void setReceivePhoneNumber(String receivePhoneNumber) {
        this.receivePhoneNumber = receivePhoneNumber;
    }

    public String getReceiveAddress() {
        return this.receiveAddress;
    }

    public OrderDetail receiveAddress(String receiveAddress) {
        this.setReceiveAddress(receiveAddress);
        return this;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getStatusPayment() {
        return this.statusPayment;
    }

    public OrderDetail statusPayment(String statusPayment) {
        this.setStatusPayment(statusPayment);
        return this;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getStatusOrder() {
        return this.statusOrder;
    }

    public OrderDetail statusOrder(String statusOrder) {
        this.setStatusOrder(statusOrder);
        return this;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public OrderDetail paymentMethod(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public User getUser() {
        return this.user;
    }

    public OrderDetail userId(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((OrderDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDetail{" +
            "id=" + getId() +
            ", recipientName='" + getRecipientName() + "'" +
            ", receivePhoneNumber='" + getReceivePhoneNumber() + "'" +
            ", receiveAddress='" + getReceiveAddress() + "'" +
            ", statusPayment='" + getStatusPayment() + "'" +
            ", statusOrder='" + getStatusOrder() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", userId=" + getUser().getId() +
            "}";
    }
}
