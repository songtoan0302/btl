import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order-detail.reducer';

export const OrderDetailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderDetailEntity = useAppSelector(state => state.orderDetail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderDetailDetailsHeading">Order Detail</h2>
        <dl className="jh-entity-details">
          <dd>{orderDetailEntity.id}</dd>
          <dt>
            <span id="recipientName">Recipient Name</span>
          </dt>
          <dd>{orderDetailEntity.recipientName}</dd>
          <dt>
            <span id="receivePhoneNumber">Receive Phone Number</span>
          </dt>
          <dd>{orderDetailEntity.receivePhoneNumber}</dd>
          <dt>
            <span id="receiveAddress">Receive Address</span>
          </dt>
          <dd>{orderDetailEntity.receiveAddress}</dd>
          <dt>
            <span id="statusPayment">Status Payment</span>
          </dt>
          <dd>{orderDetailEntity.statusPayment}</dd>
          <dt>
            <span id="statusOrder">Status Order</span>
          </dt>
          <dd>{orderDetailEntity.statusOrder}</dd>
          <dt>
            <span id="paymentMethod">Payment Method</span>
          </dt>
          <dd>{orderDetailEntity.paymentMethod}</dd>
          <dt>
            <span id="userId">User Id</span>
          </dt>
          <dd>{orderDetailEntity.userId}</dd>
        </dl>
        <Button tag={Link} to="/order-detail" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Quay lại</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-detail/${orderDetailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Sửa</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderDetailDetail;
