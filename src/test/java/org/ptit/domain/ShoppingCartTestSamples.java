package org.ptit.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ShoppingCartTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ShoppingCart593 getShoppingCartSample1() {
        return new ShoppingCart593().id(1L).quantity(1).userId(1L);
    }

    public static ShoppingCart593 getShoppingCartSample2() {
        return new ShoppingCart593().id(2L).quantity(2).userId(2L);
    }

    public static ShoppingCart593 getShoppingCartRandomSampleGenerator() {
        return new ShoppingCart593().id(longCount.incrementAndGet()).quantity(intCount.incrementAndGet()).userId(longCount.incrementAndGet());
    }
}
