import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrderDetail } from 'app/shared/model/order-detail.model';
import { getEntity, updateEntity, createEntity, reset } from './order-detail.reducer';

export const OrderDetailUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const orderDetailEntity = useAppSelector(state => state.orderDetail.entity);
  const loading = useAppSelector(state => state.orderDetail.loading);
  const updating = useAppSelector(state => state.orderDetail.updating);
  const updateSuccess = useAppSelector(state => state.orderDetail.updateSuccess);

  const handleClose = () => {
    navigate('/order-detail');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.userId !== undefined && typeof values.userId !== 'number') {
      values.userId = Number(values.userId);
    }

    const entity = {
      ...orderDetailEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...orderDetailEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="productServiceApp.orderDetail.home.createOrEditLabel" data-cy="OrderDetailCreateUpdateHeading">
            Create or edit a Order Detail
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="order-detail-id"
                  label="Translation missing for global.field.id"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Recipient Name"
                id="order-detail-recipientName"
                name="recipientName"
                data-cy="recipientName"
                type="text"
              />
              <ValidatedField
                label="Receive Phone Number"
                id="order-detail-receivePhoneNumber"
                name="receivePhoneNumber"
                data-cy="receivePhoneNumber"
                type="text"
              />
              <ValidatedField
                label="Receive Address"
                id="order-detail-receiveAddress"
                name="receiveAddress"
                data-cy="receiveAddress"
                type="textarea"
              />
              <ValidatedField
                label="Status Payment"
                id="order-detail-statusPayment"
                name="statusPayment"
                data-cy="statusPayment"
                type="text"
              />
              <ValidatedField label="Status Order" id="order-detail-statusOrder" name="statusOrder" data-cy="statusOrder" type="text" />
              <ValidatedField
                label="Payment Method"
                id="order-detail-paymentMethod"
                name="paymentMethod"
                data-cy="paymentMethod"
                type="text"
              />
              <ValidatedField label="User Id" id="order-detail-userId" name="userId" data-cy="userId" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/order-detail" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Translation missing for entity.action.back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Translation missing for entity.action.save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OrderDetailUpdate;
