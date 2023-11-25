package org.ptit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ptit.domain.OrderDetailTestSamples.*;
import static org.ptit.domain.OrderProductTestSamples.*;
import static org.ptit.domain.ProductTestSamples.*;

import org.junit.jupiter.api.Test;
import org.ptit.web.rest.TestUtil;

class OrderProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderProduct.class);
        OrderProduct orderProduct1 = getOrderProductSample1();
        OrderProduct orderProduct2 = new OrderProduct();
        assertThat(orderProduct1).isNotEqualTo(orderProduct2);

        orderProduct2.setId(orderProduct1.getId());
        assertThat(orderProduct1).isEqualTo(orderProduct2);

        orderProduct2 = getOrderProductSample2();
        assertThat(orderProduct1).isNotEqualTo(orderProduct2);
    }

    @Test
    void orderTest() throws Exception {
        OrderProduct orderProduct = getOrderProductRandomSampleGenerator();
        OrderDetail orderDetailBack = getOrderDetailRandomSampleGenerator();

        orderProduct.setOrder(orderDetailBack);
        assertThat(orderProduct.getOrder()).isEqualTo(orderDetailBack);

        orderProduct.order(null);
        assertThat(orderProduct.getOrder()).isNull();
    }

    @Test
    void productTest() throws Exception {
        OrderProduct orderProduct = getOrderProductRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        orderProduct.setProduct(productBack);
        assertThat(orderProduct.getProduct()).isEqualTo(productBack);

        orderProduct.product(null);
        assertThat(orderProduct.getProduct()).isNull();
    }
}
