package org.ptit.repository.projection;

import java.math.BigDecimal;

public interface ProductProjection {
    Long getId();
    String getName();
    BigDecimal getPrice();
    String getDescription();
    String getUrlImage();
    Integer getQuantity();
}
