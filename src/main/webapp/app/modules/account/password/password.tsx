import React, { useState, useEffect } from 'react';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Row, Col, Button } from 'reactstrap';
import { toast } from 'react-toastify';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { savePassword, reset } from './password.reducer';

export const PasswordPage = () => {
  const [password, setPassword] = useState('');
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(reset());
    dispatch(getSession());
    return () => {
      dispatch(reset());
    };
  }, []);

  const handleValidSubmit = ({ currentPassword, newPassword }) => {
    dispatch(savePassword({ currentPassword, newPassword }));
  };

  const updatePassword = event => setPassword(event.target.value);

  const account = useAppSelector(state => state.authentication.account);
  const successMessage = useAppSelector(state => state.password.successMessage);
  const errorMessage = useAppSelector(state => state.password.errorMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    } else if (errorMessage) {
      toast.error(errorMessage);
    }
    dispatch(reset());
  }, [successMessage, errorMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="password-title">
            Password for [<strong>{account.login}</strong>]
          </h2>
          <ValidatedForm id="password-form" onSubmit={handleValidSubmit}>
            <ValidatedField
              name="currentPassword"
              label="Mật khẩu hiện tại"
              placeholder="Current password"
              type="password"
              validate={{
                required: { value: true, message: 'Your password is required.' },
              }}
              data-cy="currentPassword"
            />
            <ValidatedField
              name="newPassword"
              label="Mật khẩu mới"
              placeholder="New password"
              type="password"
              validate={{
                required: { value: true, message: 'Your password is required.' },
                minLength: { value: 4, message: 'Your password is required to be at least 4 characters.' },
                maxLength: { value: 50, message: 'Your password cannot be longer than 50 characters.' },
              }}
              onChange={updatePassword}
              data-cy="newPassword"
            />
            <PasswordStrengthBar password={password} />
            <ValidatedField
              name="confirmPassword"
              label="Xác nhận mật khẩu mới"
              placeholder="Confirm the new password"
              type="password"
              validate={{
                required: { value: true, message: 'Your confirmation password is required.' },
                minLength: { value: 4, message: 'Mật khẩu xác nhận của bạn phải có ít nhất 4 ký tự.' },
                maxLength: { value: 50, message: 'Mật khẩu xác nhận của bạn không thể dài hơn 50 ký tự.' },
                validate: v => v === password || 'Mật khẩu và xác nhận của nó không khớp!',
              }}
              data-cy="confirmPassword"
            />
            <Button color="success" type="submit" data-cy="submit">
              Save
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default PasswordPage;
