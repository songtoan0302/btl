package org.ptit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.ptit.web.rest.TestUtil.sameNumber;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ptit.IntegrationTest;
import org.ptit.domain.OrderProduct593;
import org.ptit.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrderProductController} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderProductResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/order-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderProductMockMvc;

    private OrderProduct593 orderProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderProduct593 createEntity(EntityManager em) {
        OrderProduct593 orderProduct = new OrderProduct593().productName(DEFAULT_PRODUCT_NAME).quantity(DEFAULT_QUANTITY).price(DEFAULT_PRICE);
        return orderProduct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderProduct593 createUpdatedEntity(EntityManager em) {
        OrderProduct593 orderProduct = new OrderProduct593().productName(UPDATED_PRODUCT_NAME).quantity(UPDATED_QUANTITY).price(UPDATED_PRICE);
        return orderProduct;
    }

    @BeforeEach
    public void initTest() {
        orderProduct = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderProduct() throws Exception {
        int databaseSizeBeforeCreate = orderProductRepository.findAll().size();
        // Create the OrderProduct
        restOrderProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderProduct)))
            .andExpect(status().isCreated());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeCreate + 1);
        OrderProduct593 testOrderProduct = orderProductList.get(orderProductList.size() - 1);
        assertThat(testOrderProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testOrderProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderProduct.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createOrderProductWithExistingId() throws Exception {
        // Create the OrderProduct with an existing ID
        orderProduct.setId(1L);

        int databaseSizeBeforeCreate = orderProductRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderProduct)))
            .andExpect(status().isBadRequest());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrderProducts() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        // Get all the orderProductList
        restOrderProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))));
    }

    @Test
    @Transactional
    void getOrderProduct() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        // Get the orderProduct
        restOrderProductMockMvc
            .perform(get(ENTITY_API_URL_ID, orderProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderProduct.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)));
    }

    @Test
    @Transactional
    void getNonExistingOrderProduct() throws Exception {
        // Get the orderProduct
        restOrderProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrderProduct() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();

        // Update the orderProduct
        OrderProduct593 updatedOrderProduct = orderProductRepository.findById(orderProduct.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrderProduct are not directly saved in db
        em.detach(updatedOrderProduct);
        updatedOrderProduct.productName(UPDATED_PRODUCT_NAME).quantity(UPDATED_QUANTITY).price(UPDATED_PRICE);

        restOrderProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderProduct.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrderProduct))
            )
            .andExpect(status().isOk());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
        OrderProduct593 testOrderProduct = orderProductList.get(orderProductList.size() - 1);
        assertThat(testOrderProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testOrderProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderProduct.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingOrderProduct() throws Exception {
        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();
        orderProduct.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderProduct.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderProduct))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderProduct() throws Exception {
        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();
        orderProduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderProduct))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderProduct() throws Exception {
        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();
        orderProduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderProduct)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderProductWithPatch() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();

        // Update the orderProduct using partial update
        OrderProduct593 partialUpdatedOrderProduct = new OrderProduct593();
        partialUpdatedOrderProduct.setId(orderProduct.getId());

        partialUpdatedOrderProduct.productName(UPDATED_PRODUCT_NAME);

        restOrderProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderProduct))
            )
            .andExpect(status().isOk());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
        OrderProduct593 testOrderProduct = orderProductList.get(orderProductList.size() - 1);
        assertThat(testOrderProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testOrderProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderProduct.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateOrderProductWithPatch() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();

        // Update the orderProduct using partial update
        OrderProduct593 partialUpdatedOrderProduct = new OrderProduct593();
        partialUpdatedOrderProduct.setId(orderProduct.getId());

        partialUpdatedOrderProduct.productName(UPDATED_PRODUCT_NAME).quantity(UPDATED_QUANTITY).price(UPDATED_PRICE);

        restOrderProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderProduct))
            )
            .andExpect(status().isOk());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
        OrderProduct593 testOrderProduct = orderProductList.get(orderProductList.size() - 1);
        assertThat(testOrderProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testOrderProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderProduct.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingOrderProduct() throws Exception {
        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();
        orderProduct.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderProduct))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderProduct() throws Exception {
        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();
        orderProduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderProduct))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderProduct() throws Exception {
        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();
        orderProduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderProductMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderProduct))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderProduct in the database
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderProduct() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        int databaseSizeBeforeDelete = orderProductRepository.findAll().size();

        // Delete the orderProduct
        restOrderProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderProduct.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderProduct593> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
