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

interface IDropdownOption {
    label: string;
    value: string;
}

const paymentMethods: IDropdownOption[] = [
    { label: "Thanh Toán Khi Nhận Hàng", value: "Thanh Toán Khi Nhận Hàng" },
    { label: "Chuyển Khoản Ngân Hàng", value: "Chuyển Khoản Ngân Hàng" }
];

const PaymentDropdown: React.FC = () => {
    return (
        <select name="paymentMethod" id="paymentMethod">
            {paymentMethods.map(method => (
                <option key={method.value} value={method.value}>
                    {method.label}
                </option>
            ))}
        </select>
    );
};

export const OrderDetailUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const orderDetailEntity = useAppSelector(state => state.orderDetail.entity);
  const loading = useAppSelector(state => state.orderDetail.loading);
  const updating = useAppSelector(state => state.orderDetail.updating);
  const updateSuccess = useAppSelector(state => state.orderDetail.updateSuccess);
    const shoppingCartList = useAppSelector(state => state.shoppingCart.entities);
  console.log("shoppingCartList", shoppingCartList)

  const [selectedMethod, setSelectedMethod] = React.useState<string>('Thanh Toán Khi Nhận Hàng');

      const handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
          setSelectedMethod(event.target.value);
      };

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
      paymentMethod: selectedMethod,
      shoppingCartList: shoppingCartList
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
            Đơn Hàng
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <ValidatedField
                label="Tên người nhận"
                id="order-detail-recipientName"
                name="recipientName"
                data-cy="recipientName"
                type="text"
              />
              <ValidatedField
                label=" Số điện thoại"
                id="order-detail-receivePhoneNumber"
                name="receivePhoneNumber"
                data-cy="receivePhoneNumber"
                type="text"
              />
              <ValidatedField
                label="Địa chỉ"
                id="order-detail-receiveAddress"
                name="receiveAddress"
                data-cy="receiveAddress"
                type="textarea"
              />

             <div>
             <p>Phương thức thanh toán</p>
               <select
                                        name="paymentMethod"
                                        id="paymentMethod"
                                        value={selectedMethod}
                                        onChange={handleChange}
                                        style={{padding: "6px", marginBottom: "16px"}}>
                                        {paymentMethods.map(method => (
                                            <option key={method.value} value={method.value}>
                                                {method.label}
                                            </option>
                                        ))}
                                    </select>
             </div>


              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/order-detail" replace color="info">
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

export default OrderDetailUpdate;
