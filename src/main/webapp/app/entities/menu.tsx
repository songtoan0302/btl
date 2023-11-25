import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/order-detail">
        Order Detail
      </MenuItem>
      <MenuItem icon="asterisk" to="/order-product">
        Order Product
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        Product
      </MenuItem>
      <MenuItem icon="asterisk" to="/shopping-cart">
        Shopping Cart
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
