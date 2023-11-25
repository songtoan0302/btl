//package org.ptit.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.ptit.domain.OrderDetailTestSamples.*;
//
//import org.junit.jupiter.api.Test;
//import org.ptit.web.rest.TestUtil;
//
//class OrderDetailTest {
//
//    @Test
//    void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(OrderDetail.class);
//        OrderDetail orderDetail1 = getOrderDetailSample1();
//        OrderDetail orderDetail2 = new OrderDetail();
//        assertThat(orderDetail1).isNotEqualTo(orderDetail2);
//
//        orderDetail2.setId(orderDetail1.getId());
//        assertThat(orderDetail1).isEqualTo(orderDetail2);
//
//        orderDetail2 = getOrderDetailSample2();
//        assertThat(orderDetail1).isNotEqualTo(orderDetail2);
//    }
//}
