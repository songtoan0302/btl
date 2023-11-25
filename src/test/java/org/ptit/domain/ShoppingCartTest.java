package org.ptit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ptit.domain.ProductTestSamples.*;
import static org.ptit.domain.ShoppingCartTestSamples.*;

import org.junit.jupiter.api.Test;
import org.ptit.web.rest.TestUtil;

class ShoppingCartTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCart.class);
        ShoppingCart shoppingCart1 = getShoppingCartSample1();
        ShoppingCart shoppingCart2 = new ShoppingCart();
        assertThat(shoppingCart1).isNotEqualTo(shoppingCart2);

        shoppingCart2.setId(shoppingCart1.getId());
        assertThat(shoppingCart1).isEqualTo(shoppingCart2);

        shoppingCart2 = getShoppingCartSample2();
        assertThat(shoppingCart1).isNotEqualTo(shoppingCart2);
    }

    @Test
    void productTest() throws Exception {
        ShoppingCart shoppingCart = getShoppingCartRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        shoppingCart.setProduct(productBack);
        assertThat(shoppingCart.getProduct()).isEqualTo(productBack);

        shoppingCart.product(null);
        assertThat(shoppingCart.getProduct()).isNull();
    }
}
