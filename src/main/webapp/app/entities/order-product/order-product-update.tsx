import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrderDetail } from 'app/shared/model/order-detail.model';
import { getEntities as getOrderDetails } from 'app/entities/order-detail/order-detail.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IOrderProduct } from 'app/shared/model/order-product.model';
import { getEntity, updateEntity, createEntity, reset } from './order-product.reducer';

export const OrderProductUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const orderDetails = useAppSelector(state => state.orderDetail.entities);
  const products = useAppSelector(state => state.product.entities);
  const orderProductEntity = useAppSelector(state => state.orderProduct.entity);
  const loading = useAppSelector(state => state.orderProduct.loading);
  const updating = useAppSelector(state => state.orderProduct.updating);
  const updateSuccess = useAppSelector(state => state.orderProduct.updateSuccess);

  const handleClose = () => {
    navigate('/order-product');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOrderDetails({}));
    dispatch(getProducts({}));
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
    if (values.quantity !== undefined && typeof values.quantity !== 'number') {
      values.quantity = Number(values.quantity);
    }
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }

    const entity = {
      ...orderProductEntity,
      ...values,
      order: orderDetails.find(it => it.id.toString() === values.order.toString()),
      product: products.find(it => it.id.toString() === values.product.toString()),
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
          ...orderProductEntity,
          order: orderProductEntity?.order?.id,
          product: orderProductEntity?.product?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="productServiceApp.orderProduct.home.createOrEditLabel" data-cy="OrderProductCreateUpdateHeading">
            Create or edit a Order Product
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
                  id="order-product-id"
                  label="Translation missing for global.field.id"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Product Name" id="order-product-productName" name="productName" data-cy="productName" type="text" />
              <ValidatedField label="Quantity" id="order-product-quantity" name="quantity" data-cy="quantity" type="text" />
              <ValidatedField label="Price" id="order-product-price" name="price" data-cy="price" type="text" />
              <ValidatedField id="order-product-order" name="order" data-cy="order" label="Order" type="select">
                <option value="" key="0" />
                {orderDetails
                  ? orderDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="order-product-product" name="product" data-cy="product" label="Product" type="select">
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/order-product" replace color="info">
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

export default OrderProductUpdate;
