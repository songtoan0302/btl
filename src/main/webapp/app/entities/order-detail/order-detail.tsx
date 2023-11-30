import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './order-detail.reducer';

export const OrderDetail = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const orderDetailList = useAppSelector(state => state.orderDetail.entities);
  const loading = useAppSelector(state => state.orderDetail.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="order-detail-heading" data-cy="OrderDetailHeading">
       Đơn Hàng
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
        </div>
      </h2>
      <div className="table-responsive">
        {orderDetailList && orderDetailList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  Mã <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('recipientName')}>
                  Tên Người Nhận <FontAwesomeIcon icon={getSortIconByFieldName('recipientName')} />
                </th>
                <th className="hand" onClick={sort('receivePhoneNumber')}>
                  Số Điện Thoại <FontAwesomeIcon icon={getSortIconByFieldName('receivePhoneNumber')} />
                </th>
                <th className="hand" onClick={sort('receiveAddress')}>
                  Địa Chỉ <FontAwesomeIcon icon={getSortIconByFieldName('receiveAddress')} />
                </th>
                <th className="hand" onClick={sort('statusPayment')}>
                  Trạng Thái Thanh Toán <FontAwesomeIcon icon={getSortIconByFieldName('statusPayment')} />
                </th>
                <th className="hand" onClick={sort('statusOrder')}>
                  Trạng Thái Đơn Hàng <FontAwesomeIcon icon={getSortIconByFieldName('statusOrder')} />
                </th>
                <th className="hand" onClick={sort('paymentMethod')}>
                  Phương Thức Thanh Toán <FontAwesomeIcon icon={getSortIconByFieldName('paymentMethod')} />
                </th>

                <th />
              </tr>
            </thead>
            <tbody>
              {orderDetailList.map((orderDetail, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/order-detail/${orderDetail.id}`} color="link" size="sm">
                      {orderDetail.id}
                    </Button>
                  </td>
                  <td>{orderDetail.recipientName}</td>
                  <td>{orderDetail.receivePhoneNumber}</td>
                  <td>{orderDetail.receiveAddress}</td>
                  <td>{orderDetail.statusPayment}</td>
                  <td>{orderDetail.statusOrder}</td>
                  <td>{orderDetail.paymentMethod}</td>
                  <td>{orderDetail.userId}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/order-detail/${orderDetail.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">Xem chi tiết</span>
                      </Button>
                      <Button tag={Link} to={`/order-detail/${orderDetail.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">Sửa</span>
                      </Button>

                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Không có đơn hàng</div>
        )}
      </div>
    </div>
  );
};

export default OrderDetail;
