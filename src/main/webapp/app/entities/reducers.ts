import orderDetail from 'app/entities/order-detail/order-detail.reducer';
import orderProduct from 'app/entities/order-product/order-product.reducer';
import product from 'app/entities/product/product.reducer';
import shoppingCart from 'app/entities/shopping-cart/shopping-cart.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  orderDetail,
  orderProduct,
  product,
  shoppingCart,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
