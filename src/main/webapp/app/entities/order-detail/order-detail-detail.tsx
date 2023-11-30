import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import axios from 'axios';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order-detail.reducer';

export const OrderDetailDetail = () => {
  const dispatch = useAppDispatch();
  const [productList, setProductList] = useState<any[]>([])

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
    axios.get(`http://localhost:9000/api/products/order/${id}`).then((res) => {
		setProductList(res.data)
	}
  }, []);

  const orderDetailEntity = useAppSelector(state => state.orderDetail.entity);
  const ProductByCartId = useAppSelector(state => state.orderDetail.ProductByCartId);
  console.log("ProductByCartId", ProductByCartId)
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderDetailDetailsHeading">Chi Tiết Đơn Hàng</h2>
        <div style={{display: "flex", gap: "32px"}}>
			<dl className="jh-entity-details">
          <dd>{orderDetailEntity.id}</dd>
          <dt>
            <span id="recipientName">Tên Người Nhận</span>
          </dt>
          <dd>{orderDetailEntity.recipientName}</dd>
          <dt>
            <span id="receivePhoneNumber">Số điện thoại</span>
          </dt>
          <dd>{orderDetailEntity.receivePhoneNumber}</dd>
          <dt>
            <span id="receiveAddress">Địa chỉ gửi</span>
          </dt>
          <dd>{orderDetailEntity.receiveAddress}</dd>
          <dt>
            <span id="statusPayment">Trạng thái thanh toán</span>
          </dt>
          <dd>{orderDetailEntity.statusPayment}</dd>
          <dt>
            <span id="statusOrder">Trạng thái đơn hàng</span>
          </dt>
          <dd>{orderDetailEntity.statusOrder}</dd>
          <dt>
            <span id="paymentMethod">Payment Method</span>
          </dt>
          <dd>{orderDetailEntity.paymentMethod}</dd>
        </dl>
		          <dd>
            <span style={{fontWeight: "600"; fontSize: "22px"}}>
              Danh sách sản phẩm
            </span>
			{
				productList ? (productList.map((item) => (
				<div style={{display: "flex"; gap: "20px"}}>
					<img src={item.urlImage} style={{width: "80px", height: "80px"}}/>
					<div>
						<p style={{fontWeight: "600", marginBottom: "6px"}}>{item.name}</p>
						<p style={{color: "red" , marginBottom: "6px"}}>{item.quantity}</p>
					<p>{item.price.toLocaleString('vi-VN', {
  style: 'currency', 
  currency: 'VND'
})}</p>
					</div>
				</div>
					
				))) : null
			}
			<p style={{fontWeight: "600", marginBottom: "6px"}}>Tổng tiền: {productList.reduce((total, product) => {
    return total + product.price; 
  }, 0).toLocaleString('vi-VN', {
  style: 'currency', 
  currency: 'VND'
})}</p>
          </dd>
		</div>
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
