import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/order-detail">
        Đơn hàng
      </MenuItem>
      <MenuItem icon="asterisk" to="/order-product">
        Chi tiết đơn hàng
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        Sản phẩm
      </MenuItem>
      <MenuItem icon="asterisk" to="/shopping-cart">
        Giỏ hàng
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
