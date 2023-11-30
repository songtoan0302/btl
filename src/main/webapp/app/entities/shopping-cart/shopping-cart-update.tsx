import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IShoppingCart } from 'app/shared/model/shopping-cart.model';
import { getEntity, updateEntity, createEntity, reset } from './shopping-cart.reducer';

export const ShoppingCartUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const shoppingCartEntity = useAppSelector(state => state.shoppingCart.entity);
  const loading = useAppSelector(state => state.shoppingCart.loading);
  const updating = useAppSelector(state => state.shoppingCart.updating);
  const updateSuccess = useAppSelector(state => state.shoppingCart.updateSuccess);

  const handleClose = () => {
    navigate('/shopping-cart');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    if (values.userId !== undefined && typeof values.userId !== 'number') {
      values.userId = Number(values.userId);
    }

    const entity = {
      ...shoppingCartEntity,
      ...values,
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
          ...shoppingCartEntity,
          product: shoppingCartEntity?.product?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="productServiceApp.shoppingCart.home.createOrEditLabel" data-cy="ShoppingCartCreateUpdateHeading">
            Giỏ hàng
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Đang Tải ...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>

              <ValidatedField label="Số lượng" id="shopping-cart-quantity" name="quantity" data-cy="quantity" type="text" />

              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/shopping-cart" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Quay lại</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Lưu
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ShoppingCartUpdate;
