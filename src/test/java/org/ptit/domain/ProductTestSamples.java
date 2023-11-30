package org.ptit.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Product593 getProductSample1() {
        return new Product593().id(1L).name("name1").quantity(1);
    }

    public static Product593 getProductSample2() {
        return new Product593().id(2L).name("name2").quantity(2);
    }

    public static Product593 getProductRandomSampleGenerator() {
        return new Product593().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).quantity(intCount.incrementAndGet());
    }
}
