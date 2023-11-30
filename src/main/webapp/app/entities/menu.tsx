import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      <MenuItem icon="asterisk" to="/order-detail">
        Đơn hàng
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        Sản phẩm
      </MenuItem>
      <MenuItem icon="asterisk" to="/shopping-cart">
        Giỏ hàng
      </MenuItem>
    </>
  );
};

export default EntitiesMenu;
