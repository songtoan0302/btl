//package org.ptit.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import jakarta.persistence.EntityManager;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.ptit.IntegrationTest;
//import org.ptit.domain.OrderDetail;
//import org.ptit.repository.OrderDetailRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration tests for the {@link OrderDetailResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class OrderDetailResourceIT {
//
//    private static final String DEFAULT_RECIPIENT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_RECIPIENT_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_RECEIVE_PHONE_NUMBER = "AAAAAAAAAA";
//    private static final String UPDATED_RECEIVE_PHONE_NUMBER = "BBBBBBBBBB";
//
//    private static final String DEFAULT_RECEIVE_ADDRESS = "AAAAAAAAAA";
//    private static final String UPDATED_RECEIVE_ADDRESS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_STATUS_PAYMENT = "AAAAAAAAAA";
//    private static final String UPDATED_STATUS_PAYMENT = "BBBBBBBBBB";
//
//    private static final String DEFAULT_STATUS_ORDER = "AAAAAAAAAA";
//    private static final String UPDATED_STATUS_ORDER = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
//    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";
//
//    private static final Long DEFAULT_USER_ID = 1L;
//    private static final Long UPDATED_USER_ID = 2L;
//
//    private static final String ENTITY_API_URL = "/api/order-details";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private OrderDetailRepository orderDetailRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restOrderDetailMockMvc;
//
//    private OrderDetail orderDetail;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static OrderDetail createEntity(EntityManager em) {
//        OrderDetail orderDetail = new OrderDetail()
//            .recipientName(DEFAULT_RECIPIENT_NAME)
//            .receivePhoneNumber(DEFAULT_RECEIVE_PHONE_NUMBER)
//            .receiveAddress(DEFAULT_RECEIVE_ADDRESS)
//            .statusPayment(DEFAULT_STATUS_PAYMENT)
//            .statusOrder(DEFAULT_STATUS_ORDER)
//            .paymentMethod(DEFAULT_PAYMENT_METHOD)
//            .userId(DEFAULT_USER_ID);
//        return orderDetail;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static OrderDetail createUpdatedEntity(EntityManager em) {
//        OrderDetail orderDetail = new OrderDetail()
//            .recipientName(UPDATED_RECIPIENT_NAME)
//            .receivePhoneNumber(UPDATED_RECEIVE_PHONE_NUMBER)
//            .receiveAddress(UPDATED_RECEIVE_ADDRESS)
//            .statusPayment(UPDATED_STATUS_PAYMENT)
//            .statusOrder(UPDATED_STATUS_ORDER)
//            .paymentMethod(UPDATED_PAYMENT_METHOD)
//            .userId(UPDATED_USER_ID);
//        return orderDetail;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        orderDetail = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createOrderDetail() throws Exception {
//        int databaseSizeBeforeCreate = orderDetailRepository.findAll().size();
//        // Create the OrderDetail
//        restOrderDetailMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetail)))
//            .andExpect(status().isCreated());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeCreate + 1);
//        OrderDetail testOrderDetail = orderDetailList.get(orderDetailList.size() - 1);
//        assertThat(testOrderDetail.getRecipientName()).isEqualTo(DEFAULT_RECIPIENT_NAME);
//        assertThat(testOrderDetail.getReceivePhoneNumber()).isEqualTo(DEFAULT_RECEIVE_PHONE_NUMBER);
//        assertThat(testOrderDetail.getReceiveAddress()).isEqualTo(DEFAULT_RECEIVE_ADDRESS);
//        assertThat(testOrderDetail.getStatusPayment()).isEqualTo(DEFAULT_STATUS_PAYMENT);
//        assertThat(testOrderDetail.getStatusOrder()).isEqualTo(DEFAULT_STATUS_ORDER);
//        assertThat(testOrderDetail.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
//        assertThat(testOrderDetail.getUserId()).isEqualTo(DEFAULT_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    void createOrderDetailWithExistingId() throws Exception {
//        // Create the OrderDetail with an existing ID
//        orderDetail.setId(1L);
//
//        int databaseSizeBeforeCreate = orderDetailRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restOrderDetailMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetail)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllOrderDetails() throws Exception {
//        // Initialize the database
//        orderDetailRepository.saveAndFlush(orderDetail);
//
//        // Get all the orderDetailList
//        restOrderDetailMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(orderDetail.getId().intValue())))
//            .andExpect(jsonPath("$.[*].recipientName").value(hasItem(DEFAULT_RECIPIENT_NAME)))
//            .andExpect(jsonPath("$.[*].receivePhoneNumber").value(hasItem(DEFAULT_RECEIVE_PHONE_NUMBER)))
//            .andExpect(jsonPath("$.[*].receiveAddress").value(hasItem(DEFAULT_RECEIVE_ADDRESS.toString())))
//            .andExpect(jsonPath("$.[*].statusPayment").value(hasItem(DEFAULT_STATUS_PAYMENT)))
//            .andExpect(jsonPath("$.[*].statusOrder").value(hasItem(DEFAULT_STATUS_ORDER)))
//            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
//            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
//    }
//
//    @Test
//    @Transactional
//    void getOrderDetail() throws Exception {
//        // Initialize the database
//        orderDetailRepository.saveAndFlush(orderDetail);
//
//        // Get the orderDetail
//        restOrderDetailMockMvc
//            .perform(get(ENTITY_API_URL_ID, orderDetail.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(orderDetail.getId().intValue()))
//            .andExpect(jsonPath("$.recipientName").value(DEFAULT_RECIPIENT_NAME))
//            .andExpect(jsonPath("$.receivePhoneNumber").value(DEFAULT_RECEIVE_PHONE_NUMBER))
//            .andExpect(jsonPath("$.receiveAddress").value(DEFAULT_RECEIVE_ADDRESS.toString()))
//            .andExpect(jsonPath("$.statusPayment").value(DEFAULT_STATUS_PAYMENT))
//            .andExpect(jsonPath("$.statusOrder").value(DEFAULT_STATUS_ORDER))
//            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD))
//            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingOrderDetail() throws Exception {
//        // Get the orderDetail
//        restOrderDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingOrderDetail() throws Exception {
//        // Initialize the database
//        orderDetailRepository.saveAndFlush(orderDetail);
//
//        int databaseSizeBeforeUpdate = orderDetailRepository.findAll().size();
//
//        // Update the orderDetail
//        OrderDetail updatedOrderDetail = orderDetailRepository.findById(orderDetail.getId()).orElseThrow();
//        // Disconnect from session so that the updates on updatedOrderDetail are not directly saved in db
//        em.detach(updatedOrderDetail);
//        updatedOrderDetail
//            .recipientName(UPDATED_RECIPIENT_NAME)
//            .receivePhoneNumber(UPDATED_RECEIVE_PHONE_NUMBER)
//            .receiveAddress(UPDATED_RECEIVE_ADDRESS)
//            .statusPayment(UPDATED_STATUS_PAYMENT)
//            .statusOrder(UPDATED_STATUS_ORDER)
//            .paymentMethod(UPDATED_PAYMENT_METHOD)
//            .userId(UPDATED_USER_ID);
//
//        restOrderDetailMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedOrderDetail.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedOrderDetail))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeUpdate);
//        OrderDetail testOrderDetail = orderDetailList.get(orderDetailList.size() - 1);
//        assertThat(testOrderDetail.getRecipientName()).isEqualTo(UPDATED_RECIPIENT_NAME);
//        assertThat(testOrderDetail.getReceivePhoneNumber()).isEqualTo(UPDATED_RECEIVE_PHONE_NUMBER);
//        assertThat(testOrderDetail.getReceiveAddress()).isEqualTo(UPDATED_RECEIVE_ADDRESS);
//        assertThat(testOrderDetail.getStatusPayment()).isEqualTo(UPDATED_STATUS_PAYMENT);
//        assertThat(testOrderDetail.getStatusOrder()).isEqualTo(UPDATED_STATUS_ORDER);
//        assertThat(testOrderDetail.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
//        assertThat(testOrderDetail.getUserId()).isEqualTo(UPDATED_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingOrderDetail() throws Exception {
//        int databaseSizeBeforeUpdate = orderDetailRepository.findAll().size();
//        orderDetail.setId(longCount.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restOrderDetailMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, orderDetail.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(orderDetail))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchOrderDetail() throws Exception {
//        int databaseSizeBeforeUpdate = orderDetailRepository.findAll().size();
//        orderDetail.setId(longCount.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restOrderDetailMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(orderDetail))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamOrderDetail() throws Exception {
//        int databaseSizeBeforeUpdate = orderDetailRepository.findAll().size();
//        orderDetail.setId(longCount.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restOrderDetailMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetail)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateOrderDetailWithPatch() throws Exception {
//        // Initialize the database
//        orderDetailRepository.saveAndFlush(orderDetail);
//
//        int databaseSizeBeforeUpdate = orderDetailRepository.findAll().size();
//
//        // Update the orderDetail using partial update
//        OrderDetail partialUpdatedOrderDetail = new OrderDetail();
//        partialUpdatedOrderDetail.setId(orderDetail.getId());
//
//        partialUpdatedOrderDetail
//            .recipientName(UPDATED_RECIPIENT_NAME)
//            .receiveAddress(UPDATED_RECEIVE_ADDRESS)
//            .statusPayment(UPDATED_STATUS_PAYMENT)
//            .paymentMethod(UPDATED_PAYMENT_METHOD);
//
//        restOrderDetailMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedOrderDetail.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderDetail))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeUpdate);
//        OrderDetail testOrderDetail = orderDetailList.get(orderDetailList.size() - 1);
//        assertThat(testOrderDetail.getRecipientName()).isEqualTo(UPDATED_RECIPIENT_NAME);
//        assertThat(testOrderDetail.getReceivePhoneNumber()).isEqualTo(DEFAULT_RECEIVE_PHONE_NUMBER);
//        assertThat(testOrderDetail.getReceiveAddress()).isEqualTo(UPDATED_RECEIVE_ADDRESS);
//        assertThat(testOrderDetail.getStatusPayment()).isEqualTo(UPDATED_STATUS_PAYMENT);
//        assertThat(testOrderDetail.getStatusOrder()).isEqualTo(DEFAULT_STATUS_ORDER);
//        assertThat(testOrderDetail.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
//        assertThat(testOrderDetail.getUserId()).isEqualTo(DEFAULT_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateOrderDetailWithPatch() throws Exception {
//        // Initialize the database
//        orderDetailRepository.saveAndFlush(orderDetail);
//
//        int databaseSizeBeforeUpdate = orderDetailRepository.findAll().size();
//
//        // Update the orderDetail using partial update
//        OrderDetail partialUpdatedOrderDetail = new OrderDetail();
//        partialUpdatedOrderDetail.setId(orderDetail.getId());
//
//        partialUpdatedOrderDetail
//            .recipientName(UPDATED_RECIPIENT_NAME)
//            .receivePhoneNumber(UPDATED_RECEIVE_PHONE_NUMBER)
//            .receiveAddress(UPDATED_RECEIVE_ADDRESS)
//            .statusPayment(UPDATED_STATUS_PAYMENT)
//            .statusOrder(UPDATED_STATUS_ORDER)
//            .paymentMethod(UPDATED_PAYMENT_METHOD)
//            .userId(UPDATED_USER_ID);
//
//        restOrderDetailMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedOrderDetail.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderDetail))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeUpdate);
//        OrderDetail testOrderDetail = orderDetailList.get(orderDetailList.size() - 1);
//        assertThat(testOrderDetail.getRecipientName()).isEqualTo(UPDATED_RECIPIENT_NAME);
//        assertThat(testOrderDetail.getReceivePhoneNumber()).isEqualTo(UPDATED_RECEIVE_PHONE_NUMBER);
//        assertThat(testOrderDetail.getReceiveAddress()).isEqualTo(UPDATED_RECEIVE_ADDRESS);
//        assertThat(testOrderDetail.getStatusPayment()).isEqualTo(UPDATED_STATUS_PAYMENT);
//        assertThat(testOrderDetail.getStatusOrder()).isEqualTo(UPDATED_STATUS_ORDER);
//        assertThat(testOrderDetail.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
//        assertThat(testOrderDetail.getUserId()).isEqualTo(UPDATED_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingOrderDetail() throws Exception {
//        int databaseSizeBeforeUpdate = orderDetailRepository.findAll().size();
//        orderDetail.setId(longCount.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restOrderDetailMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, orderDetail.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(orderDetail))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchOrderDetail() throws Exception {
//        int databaseSizeBeforeUpdate = orderDetailRepository.findAll().size();
//        orderDetail.setId(longCount.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restOrderDetailMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(orderDetail))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamOrderDetail() throws Exception {
//        int databaseSizeBeforeUpdate = orderDetailRepository.findAll().size();
//        orderDetail.setId(longCount.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restOrderDetailMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderDetail))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the OrderDetail in the database
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteOrderDetail() throws Exception {
//        // Initialize the database
//        orderDetailRepository.saveAndFlush(orderDetail);
//
//        int databaseSizeBeforeDelete = orderDetailRepository.findAll().size();
//
//        // Delete the orderDetail
//        restOrderDetailMockMvc
//            .perform(delete(ENTITY_API_URL_ID, orderDetail.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
//        assertThat(orderDetailList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
