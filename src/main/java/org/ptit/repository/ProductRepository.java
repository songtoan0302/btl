package org.ptit.repository;

import org.ptit.domain.Product593;
import org.ptit.repository.projection.ProductProjection;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product593, Long> {
    @Query(value = "SELECT * FROM PRODUCT p WHERE p.NAME IS NULL OR p.NAME LIKE %:name%", nativeQuery = true)
    List<Product593> findByNameContaining(@Param("name") String name);

    @Query(value = "Select p.id as id, p.name as name, op.price as price, p.description as description, p.url_image as urlImage,op.quantity as quantity  " +
        "from product p, order_detail od, order_product op   where od.id = op.order_id and op.product_id = p.id and od.id = :orderId ", nativeQuery = true)
    List<ProductProjection> findListProductByOrderId(@Param("orderId") Long orderId);
}
