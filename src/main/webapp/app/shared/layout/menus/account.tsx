import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

const accountMenuItemsAuthenticated = () => (
  <>
    <MenuItem icon="wrench" to="/account/settings" data-cy="settings">
      Cài đặt
    </MenuItem>
    <MenuItem icon="lock" to="/account/password" data-cy="passwordItem">
       Mật Khẩu
    </MenuItem>
    <MenuItem icon="sign-out-alt" to="/logout" data-cy="logout">
      Đăng Xuất
    </MenuItem>
  </>
);

const accountMenuItems = () => (
  <>
    <MenuItem id="login-item" icon="sign-in-alt" to="/login" data-cy="login">
      Đăng Nhập
    </MenuItem>
    <MenuItem icon="user-plus" to="/account/register" data-cy="register">
      Đăng Kí
    </MenuItem>
  </>
);

export const AccountMenu = ({ isAuthenticated = false }) => (
  <NavDropdown icon="user" name="Tài khoản" id="account-menu" data-cy="accountMenu">
    {isAuthenticated && accountMenuItemsAuthenticated()}
    {!isAuthenticated && accountMenuItems()}
  </NavDropdown>
);

export default AccountMenu;
