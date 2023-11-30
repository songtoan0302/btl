package org.ptit.repository;

import org.ptit.domain.ShoppingCart593;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the ShoppingCart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart593, Long> {
    List<ShoppingCart593> findByUserId(Long userId);
}
