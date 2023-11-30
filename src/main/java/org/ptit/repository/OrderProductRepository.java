package org.ptit.repository;

import org.ptit.domain.OrderProduct593;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct593, Long> {

    @Query("SELECT op FROM OrderProduct593 op JOIN op.order o JOIN o.user u WHERE u.id = :userId")
    List<OrderProduct593> findAllByUserId(@Param("userId") Long userId);

}
