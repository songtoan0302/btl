package org.ptit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.ptit.domain.OrderProduct593;
import org.ptit.repository.OrderProductRepository;
import org.ptit.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link OrderProduct593}.
 */
@RestController
@RequestMapping("/api/order-products")
@Transactional
public class OrderProductController {

    private final Logger log = LoggerFactory.getLogger(OrderProductController.class);

    private static final String ENTITY_NAME = "orderProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderProductRepository orderProductRepository;

    public OrderProductController(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    /**
     * {@code POST  /order-products} : Create a new orderProduct.
     *
     * @param orderProduct the orderProduct to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderProduct, or with status {@code 400 (Bad Request)} if the orderProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrderProduct593> createOrderProduct(@RequestBody OrderProduct593 orderProduct) throws URISyntaxException {
        log.debug("REST request to save OrderProduct : {}", orderProduct);
        if (orderProduct.getId() != null) {
            throw new BadRequestAlertException("A new orderProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderProduct593 result = orderProductRepository.save(orderProduct);
        return ResponseEntity
            .created(new URI("/api/order-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-products/:id} : Updates an existing orderProduct.
     *
     * @param id the id of the orderProduct to save.
     * @param orderProduct the orderProduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderProduct,
     * or with status {@code 400 (Bad Request)} if the orderProduct is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderProduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderProduct593> updateOrderProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderProduct593 orderProduct
    ) throws URISyntaxException {
        log.debug("REST request to update OrderProduct : {}, {}", id, orderProduct);
        if (orderProduct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderProduct.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderProduct593 result = orderProductRepository.save(orderProduct);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderProduct.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-products/:id} : Partial updates given fields of an existing orderProduct, field will ignore if it is null
     *
     * @param id the id of the orderProduct to save.
     * @param orderProduct the orderProduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderProduct,
     * or with status {@code 400 (Bad Request)} if the orderProduct is not valid,
     * or with status {@code 404 (Not Found)} if the orderProduct is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderProduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderProduct593> partialUpdateOrderProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderProduct593 orderProduct
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderProduct partially : {}, {}", id, orderProduct);
        if (orderProduct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderProduct.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderProduct593> result = orderProductRepository
            .findById(orderProduct.getId())
            .map(existingOrderProduct -> {
                if (orderProduct.getProductName() != null) {
                    existingOrderProduct.setProductName(orderProduct.getProductName());
                }
                if (orderProduct.getQuantity() != null) {
                    existingOrderProduct.setQuantity(orderProduct.getQuantity());
                }
                if (orderProduct.getPrice() != null) {
                    existingOrderProduct.setPrice(orderProduct.getPrice());
                }

                return existingOrderProduct;
            })
            .map(orderProductRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderProduct.getId().toString())
        );
    }

    /**
     * {@code GET  /order-products} : get all the orderProducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderProducts in body.
     */
    @GetMapping("/user/{user_id}")
    public List<OrderProduct593> getAllOrderProducts(@PathVariable("user_id") Long userId) {
        log.debug("REST request to get all OrderProducts");
        return orderProductRepository.findAllByUserId(userId);
    }

    /**
     * {@code GET  /order-products/:id} : get the "id" orderProduct.
     *
     * @param id the id of the orderProduct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderProduct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct593> getOrderProduct(@PathVariable Long id) {
        log.debug("REST request to get OrderProduct : {}", id);
        Optional<OrderProduct593> orderProduct = orderProductRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderProduct);
    }

    /**
     * {@code DELETE  /order-products/:id} : delete the "id" orderProduct.
     *
     * @param id the id of the orderProduct to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderProduct(@PathVariable Long id) {
        log.debug("REST request to delete OrderProduct : {}", id);
        orderProductRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
