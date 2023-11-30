package org.ptit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ptit.domain.ProductTestSamples.*;

import org.junit.jupiter.api.Test;
import org.ptit.web.rest.TestUtil;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product593.class);
        Product593 product1 = getProductSample1();
        Product593 product2 = new Product593();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }
}
