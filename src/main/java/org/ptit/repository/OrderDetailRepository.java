package org.ptit.repository;

import org.ptit.domain.OrderDetail593;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail593, Long> {
    List<OrderDetail593> findByUserId(Long userId);

    @Modifying
    @Query(value = "INSERT INTO order_detail " +
        " (recipient_name, receive_phone_number, receive_address, status_payment, status_order, payment_method, user_id) " +
        " VALUES(:recipient_name, :receive_phone_number, :receive_address," +
        " :status_payment, :status_order, :payment_method, :user_id);", nativeQuery = true)
    int saveCustom(@Param("recipient_name") String recipient_name,
                   @Param("receive_phone_number") String receive_phone_number,
                   @Param("receive_address") String receive_address,
                   @Param("status_payment") String status_payment,
                   @Param("status_order") String status_order,
                   @Param("payment_method") String payment_method,
                   @Param("user_id") Integer user_id);
}
