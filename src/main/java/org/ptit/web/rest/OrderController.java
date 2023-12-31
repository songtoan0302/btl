package org.ptit.web.rest;

import org.ptit.domain.OrderDetail593;
import org.ptit.dto.OrderDetailsRequest;
import org.ptit.repository.OrderDetailRepository;
import org.ptit.repository.ProductRepository;
import org.ptit.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link OrderDetail593}.
 */
@RestController
@RequestMapping("/api/order-details")
@Transactional
public class OrderController {

    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    private static final String ENTITY_NAME = "orderDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderDetailRepository orderDetailRepository, ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
    }

    /**
     * {@code POST  /order-details} : Create a new orderDetail.
     *
     * @param orderDetail the orderDetail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDetail, or with status {@code 400 (Bad Request)} if the orderDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrderDetail593> createOrderDetail(@RequestBody OrderDetail593 orderDetail) throws URISyntaxException {
        log.debug("REST request to save OrderDetail : {}", orderDetail);
        if (orderDetail.getId() != null) {
            throw new BadRequestAlertException("A new orderDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderDetail593 result = orderDetailRepository.save(orderDetail);
        return ResponseEntity
            .created(new URI("/api/order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/new")
    @Transactional
    public ResponseEntity<OrderDetail593> create(@RequestBody OrderDetailsRequest request) {
        log.debug("(create) request: {}", request);

        OrderDetail593 result = new OrderDetail593();
        orderDetailRepository.saveCustom("", "", "", "", "", "", request.getUserId());
        request.getProduct().forEach(productRepository::save);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /order-details/:id} : Updates an existing orderDetail.
     *
     * @param id          the id of the orderDetail to save.
     * @param orderDetail the orderDetail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetail,
     * or with status {@code 400 (Bad Request)} if the orderDetail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDetail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail593> updateOrderDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderDetail593 orderDetail
    ) throws URISyntaxException {
        log.debug("REST request to update OrderDetail : {}, {}", id, orderDetail);
        if (orderDetail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDetail.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderDetail593 result = orderDetailRepository.save(orderDetail);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDetail.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-details/:id} : Partial updates given fields of an existing orderDetail, field will ignore if it is null
     *
     * @param id the id of the orderDetail to save.
     * @param orderDetail the orderDetail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetail,
     * or with status {@code 400 (Bad Request)} if the orderDetail is not valid,
     * or with status {@code 404 (Not Found)} if the orderDetail is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderDetail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderDetail593> partialUpdateOrderDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderDetail593 orderDetail
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderDetail partially : {}, {}", id, orderDetail);
        if (orderDetail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDetail.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderDetail593> result = orderDetailRepository
            .findById(orderDetail.getId())
            .map(existingOrderDetail -> {
                if (orderDetail.getRecipientName() != null) {
                    existingOrderDetail.setRecipientName(orderDetail.getRecipientName());
                }
                if (orderDetail.getReceivePhoneNumber() != null) {
                    existingOrderDetail.setReceivePhoneNumber(orderDetail.getReceivePhoneNumber());
                }
                if (orderDetail.getReceiveAddress() != null) {
                    existingOrderDetail.setReceiveAddress(orderDetail.getReceiveAddress());
                }
                if (orderDetail.getStatusPayment() != null) {
                    existingOrderDetail.setStatusPayment(orderDetail.getStatusPayment());
                }
                if (orderDetail.getStatusOrder() != null) {
                    existingOrderDetail.setStatusOrder(orderDetail.getStatusOrder());
                }
                if (orderDetail.getPaymentMethod() != null) {
                    existingOrderDetail.setPaymentMethod(orderDetail.getPaymentMethod());
                }
                if (orderDetail.getUser() != null) {
                    existingOrderDetail.setUser(orderDetail.getUser());
                }

                return existingOrderDetail;
            })
            .map(orderDetailRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDetail.getId().toString())
        );
    }

    /**
     * {@code GET  /order-details} : get all the orderDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderDetails in body.
     */
    @GetMapping("/user/{user_id}")
    public List<OrderDetail593> getAllOrderDetails(@PathVariable("user_id") Long id) {
        log.debug("REST request to get all OrderDetails");
        return orderDetailRepository.findByUserId(id);
    }

    /**
     * {@code GET  /order-details/:id} : get the "id" orderDetail.
     *
     * @param id the id of the orderDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDetail, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail593> getOrderDetail(@PathVariable Long id) {
        log.debug("REST request to get OrderDetail : {}", id);
        Optional<OrderDetail593> orderDetail = orderDetailRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderDetail);
    }

    /**
     * {@code DELETE  /order-details/:id} : delete the "id" orderDetail.
     *
     * @param id the id of the orderDetail to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        log.debug("REST request to delete OrderDetail : {}", id);
        orderDetailRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
