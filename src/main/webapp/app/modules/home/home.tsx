import './home.scss';
import React from 'react';
import { Link } from 'react-router-dom';
import { Row, Col, Alert, Container } from 'reactstrap';
import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Container className="py-5 my-5">
      <Row className="align-items-center">
        <Col md="6" className="text-center">
          <img src="data:image/png;base64,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" alt="Cửa hàng" className="" />
        </Col>
        <Col md="6">
          <p className="display-4">Chào mừng đến với Cửa hàng Trực tuyến!</p>
          <p className="lead">Khám phá hàng loạt sản phẩm với giá cả không thể bỏ qua!</p>
          {account?.login ? (
            <Alert color="success">Chúc bạn mua sắm vui vẻ, {account.login}!</Alert>
          ) : (
            <Alert color="info">
              Bắt đầu mua sắm với chúng tôi.
              <Link to="/login" className="alert-link"> Đăng nhập </Link> hoặc
              <Link to="/account/register" className="alert-link"> Đăng ký </Link>
            </Alert>
          )}
          <Link to="/contact" className="btn btn-primary mt-3">Liên hệ với chúng tôi</Link>
        </Col>
      </Row>
      <Row className="mt-5">
        <Col>
          <p style={{fontSize: "28px"}}>Khám phá các danh mục:</p>
          {/* Danh sách các danh mục */}
          {/* ... */}
          <p style={{fontSize: "28px"}}>Tại sao chọn mua sắm tại đây?</p>
          {/* Danh sách các lợi ích */}
          {/* ... */}
          <p>
            Bạn yêu thích các ưu đãi của chúng tôi? <Link to="/referral">Giới thiệu bạn bè</Link> và nhận phần thưởng!
          </p>
        </Col>
      </Row>
    </Container>
  );
};

export default Home;
