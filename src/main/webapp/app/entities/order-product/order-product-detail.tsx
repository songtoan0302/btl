import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order-product.reducer';

export const OrderProductDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderProductEntity = useAppSelector(state => state.orderProduct.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderProductDetailsHeading">Order Product</h2>
        <dl className="jh-entity-details">
          <dd>{orderProductEntity.id}</dd>
          <dt>
            <span id="productName">Product Name</span>
          </dt>
          <dd>{orderProductEntity.productName}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{orderProductEntity.quantity}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{orderProductEntity.price}</dd>
          <dt>Order</dt>
          <dd>{orderProductEntity.order ? orderProductEntity.order.id : ''}</dd>
          <dt>Product</dt>
          <dd>{orderProductEntity.product ? orderProductEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Quay lại</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-product/${orderProductEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Sửa</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderProductDetail;
