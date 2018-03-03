import { connect } from 'react-redux';
import React, { Component } from 'react';
import { Table, Button } from 'react-bootstrap';
import Part from './Part';
import { loadParts, displayPartForm } from '../reducer';

class AllParts extends Component {
    componentWillMount() {
        if (!this.props.parts.isLoaded) {
            this.props.loadParts(this.props.urls.parts);
        }
    }

    renderLoading() {
        return <div>Loading parts from server, please wait...</div>;
    }

    renderParts() {
        let button = <Button onClick={this.props.displayPartForm} bsStyle="primary">Add new part</Button>;
        if (this.props.parts.data.length > 0) {
            let parts = this.props.parts.data;
            return (<div>
                <Table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {parts.map((part, index) => <Part key={index} part={part}/>)}
                    </tbody>
                </Table>
                {button}
            </div>
            );
        } else {
            return (
                <div>
                    <p>No parts in the system</p>
                    {button}
                </div>
            );
        }
    }

    renderError() {
        return <div>Error. Check out the console log.</div>;
    }

    render() {
        if (!this.props.parts.isLoaded) {
            return this.renderLoading();
        }

        if ('error' in this.props.parts) {
            return this.renderError();
        }

        return this.renderParts();
    }
}

const mapState = state => {
    return {
        urls: state.get('urls'),
        parts: state.get('parts').toObject()
    };
};

const mapDispatch = dispatch => {
    return {
        loadParts: (url) => loadParts(url, dispatch),
        displayPartForm: () => dispatch(displayPartForm())
    }
};

export default connect(mapState, mapDispatch)(AllParts);