import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col, Card, CardBody, CardTitle, CardText } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity } from './product.reducer';
import { createEntity } from '../shopping-cart/shopping-cart.reducer';

export const ProductDetail = () => {
  const dispatch = useAppDispatch();
  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const saveEntity = values => {
        const entity = {
          quantity: 1,
          userId: account.id
          product: values
        };

          dispatch(createEntity(entity));
      };

  const productEntity = useAppSelector(state => state.product.entity);
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="6">
        <img
          src={productEntity.urlImage}
          alt="Product"
          style={{ width: '100%', height: 'auto' }}
        />
      </Col>
      <Col md="6">
        <Card>
          <CardBody>
            <CardTitle tag="h5">{productEntity.name}</CardTitle>
            <CardText>{productEntity.description}</CardText>
            <dl className="row">
              <dt className="col-sm-4">Giá</dt>
              <dd className="col-sm-8">{productEntity.price}</dd>
              <dt className="col-sm-4">Số Lượng</dt>
              <dd className="col-sm-8">{productEntity.quantity}</dd>
            </dl>
            <div className="text-right">
              <Button tag={Link} to="/product" replace color="secondary">
                <FontAwesomeIcon icon="arrow-left" /> Quay lại
              </Button>
              {
                                    account?.authorities?.length === 1 && <Button color="primary"  data-cy="entityEditButton" onClick={() => saveEntity(productEntity)}>
                                                                                                  <span className="d-none d-md-inline">Thêm vào giỏ hàng</span>
                                                                                                </Button>
                                    }
              {' '}
              {account?.authorities?.length >= 2 && (
                <Button tag={Link} to={`/product/${productEntity.id}/edit`} color="primary">
                  <FontAwesomeIcon icon="pencil-alt" /> Sửa
                </Button>
              )}
            </div>
          </CardBody>
        </Card>
      </Col>
    </Row>
  );
};

export default ProductDetail;
