import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { createEntity } from '../shopping-cart/shopping-cart.reducer';

import { getEntities } from './product.reducer';

export const Product = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));
  const [keyword, setKeyword] = useState("")

  const productList = useAppSelector(state => state.product.entities);
  const loading = useAppSelector(state => state.product.loading);
  const account = useAppSelector(state => state.authentication.account);
  console.log("account", account?.authorities?.length )
  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
        keyword: keyword
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}&keyword=''`;
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
  getAllEntities()
  const endURL = `?sort=${sortState.sort},${sortState.order}&keyword=${keyword}`;
      if (pageLocation.search !== endURL) {
        navigate(`${pageLocation.pathname}${endURL}`);
      }
  };

  const saveEntity = values => {
      const entity = {
        quantity: 1,
        userId: account.id
        product: values
      };

        dispatch(createEntity(entity));
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
      <h2 id="product-heading" data-cy="ProductHeading">
        Sản phẩm
        <div className="d-flex justify-content-end">
        <input placeholder="Tìm kiếm sản phẩm" onChange={(e) => setKeyword(e.target.value)}/>
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="search" spin={loading} /> Tìm kiếm
          </Button>
         {account?.authorities?.length >= 2 && <Link to="/product/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Tạo mới sản phẩm
          </Link>}
        </div>
      </h2>
      <div className="table-responsive">
        {productList && productList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  STT <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  Tên <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('price')}>
                  Giá(VND) <FontAwesomeIcon icon={getSortIconByFieldName('price')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  Mô tả <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('quantity')}>
                  Số lượng <FontAwesomeIcon icon={getSortIconByFieldName('quantity')} />
                </th>
                <th className="hand" onClick={sort('urlImage')}>
                   Ảnh <FontAwesomeIcon icon={getSortIconByFieldName('urlImage')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productList.map((product, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                      {product.id}
                  </td>
                  <td>{product.name}</td>
                  <td>{product.price.toLocaleString('vi-VN', {
                                       style: 'currency',
                                       currency: 'VND'
                                     })}</td>
                  <td>{product.description}</td>
                  <td>{product.quantity}</td>
                  <td><img src={product.urlImage} alt="img" style={{width: "150px", height: "150px"}}/></td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/product/${product.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">Xem chi tiết</span>
                      </Button>
                      {
                      account?.authorities?.length === 1 && <Button color="primary" size="sm" data-cy="entityEditButton" onClick={() => saveEntity(product)}>
                                                                                    <span className="d-none d-md-inline">Thêm vào giỏ hàng</span>
                                                                                  </Button>
                      }
                      {account?.authorities?.length >= 2 && <Button tag={Link} to={`/product/${product.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">Sửa</span>
                      </Button>
                      <Button
                        onClick={() => (location.href = `/product/${product.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">Xóa</span>
                      </Button>}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Không có sản phẩm nào</div>
        )}
      </div>
    </div>
  );
};

export default Product;
