import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OrderDetail from './order-detail';
import OrderProduct from './order-product';
import Product from './product';
import ShoppingCart from './shopping-cart';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="order-detail/*" element={<OrderDetail />} />
        <Route path="order-product/*" element={<OrderProduct />} />
        <Route path="product/*" element={<Product />} />
        <Route path="shopping-cart/*" element={<ShoppingCart />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
