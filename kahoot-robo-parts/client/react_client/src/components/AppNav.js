import React, { Component } from 'react';
import { Navbar, Nav, NavItem } from 'react-bootstrap';
import {connect} from 'react-redux';

class AppNav extends Component {
    handleOnClick() {
        this.props.dispatch({type: 'REDIRECT_TO_PARTS'})
    }

    render() {
        const handleOnClick = this.handleOnClick.bind(this);

        return (
            <Navbar>
                <Navbar.Header>
                    <Navbar.Brand>
                        <a onClick={handleOnClick}>Robo Parts</a>
                    </Navbar.Brand>
                </Navbar.Header>
                <Nav>
                    <NavItem eventKey={1} onClick={handleOnClick}>
                        See all parts
                    </NavItem>
                </Nav>
            </Navbar>
        );
    }
}

const mapState = state => state.toObject();

export default connect(mapState)(AppNav);