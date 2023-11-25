package org.ptit.repository;

import org.ptit.domain.OrderDetail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {}
