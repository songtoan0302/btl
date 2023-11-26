package org.ptit.repository;

import org.ptit.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM PRODUCT p WHERE p.NAME IS NULL OR p.NAME LIKE %:name%", nativeQuery = true)
    List<Product> findByNameContaining(@Param("name") String name);
}
