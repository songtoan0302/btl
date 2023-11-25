//package org.ptit.domain;
//
//import java.util.Random;
//import java.util.UUID;
//import java.util.concurrent.atomic.AtomicLong;
//
//public class OrderDetailTestSamples {
//
//    private static final Random random = new Random();
//    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    public static OrderDetail getOrderDetailSample1() {
//        return new OrderDetail()
//            .id(1L)
//            .recipientName("recipientName1")
//            .receivePhoneNumber("receivePhoneNumber1")
//            .statusPayment("statusPayment1")
//            .statusOrder("statusOrder1")
//            .paymentMethod("paymentMethod1")
//            .userId(1L);
//    }
//
//    public static OrderDetail getOrderDetailSample2() {
//        return new OrderDetail()
//            .id(2L)
//            .recipientName("recipientName2")
//            .receivePhoneNumber("receivePhoneNumber2")
//            .statusPayment("statusPayment2")
//            .statusOrder("statusOrder2")
//            .paymentMethod("paymentMethod2")
//            .userId(2L);
//    }
//
//    public static OrderDetail getOrderDetailRandomSampleGenerator() {
//        return new OrderDetail()
//            .id(longCount.incrementAndGet())
//            .recipientName(UUID.randomUUID().toString())
//            .receivePhoneNumber(UUID.randomUUID().toString())
//            .statusPayment(UUID.randomUUID().toString())
//            .statusOrder(UUID.randomUUID().toString())
//            .paymentMethod(UUID.randomUUID().toString())
//            .userId(longCount.incrementAndGet());
//    }
//}
