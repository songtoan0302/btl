import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        {/* Consider adding a logo or a relevant image here */}
        <span className="rounded" style={{ backgroundImage: 'url(path-to-your-image.png)' }} />
      </Col>
      <Col md="9">
        <h1 className="display-4">Welcome to Your Online Store!</h1>
        <p className="lead">Discover a wide range of products at unbeatable prices!</p>
        {account?.login ? (
          <Alert color="success">Happy shopping, {account.login}!</Alert>
        ) : (
          <Alert color="info">
            Start shopping with us.
            <Link to="/login" className="alert-link"> Sign in </Link> or
            <Link to="/account/register" className="alert-link"> Register </Link>
          </Alert>
        )}

        {/* You can introduce different categories or popular products here */}
        <p>Explore our categories:</p>
        <ul>
          <li><Link to="/category/electronics">Electronics</Link></li>
          <li><Link to="/category/fashion">Fashion</Link></li>
          <li><Link to="/category/home">Home and Living</Link></li>
          // Add more categories as needed
        </ul>

        {/* Include any special offers or policies */}
        <p>Why shop with us?</p>
        <ul>
          <li>24/7 Customer Support</li>
          <li>Easy Return Policies</li>
          <li>Secure Payment Process</li>
          // Add more benefits
        </ul>

        {/* Optional: Add a section for testimonials or featured products */}

        <p>
          Love our deals? Spread the word and <Link to="/referral">refer a friend</Link>!
        </p>
      </Col>
    </Row>
  );
};

export default Home;
