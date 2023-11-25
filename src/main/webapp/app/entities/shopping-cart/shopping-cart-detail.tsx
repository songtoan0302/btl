import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './shopping-cart.reducer';

export const ShoppingCartDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const shoppingCartEntity = useAppSelector(state => state.shoppingCart.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="shoppingCartDetailsHeading">Shopping Cart</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Translation missing for global.field.id</span>
          </dt>
          <dd>{shoppingCartEntity.id}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{shoppingCartEntity.quantity}</dd>
          <dt>
            <span id="userId">User Id</span>
          </dt>
          <dd>{shoppingCartEntity.userId}</dd>
          <dt>Product</dt>
          <dd>{shoppingCartEntity.product ? shoppingCartEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/shopping-cart" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Translation missing for entity.action.back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/shopping-cart/${shoppingCartEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Translation missing for entity.action.edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ShoppingCartDetail;
