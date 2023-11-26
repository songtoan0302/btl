import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';

export const ProductDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);
const account = useAppSelector(state => state.authentication.account);
  console.log("account", account?.authorities?.length )
  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">Product</h2>
        <dl className="jh-entity-details">
          <dd>{productEntity.name}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{productEntity.price}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{productEntity.description}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{productEntity.quantity}</dd>
          <dt>
            <span id="urlImage">Url Image</span>
          </dt>
          <dd>  <img src={productEntity.urlImage} alt="img" style={{width: "150px", height: "150px"}}/>
</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Quay lại</span>
        </Button>
        &nbsp;
        {account?.authorities?.length >= 2 && <Button tag={Link} to={`/product/${productEntity.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                                <FontAwesomeIcon icon="pencil-alt" />{' '}
                                <span className="d-none d-md-inline">Sửa</span>
                              </Button>
      </Col>
    </Row>
  );
};

export default ProductDetail;
