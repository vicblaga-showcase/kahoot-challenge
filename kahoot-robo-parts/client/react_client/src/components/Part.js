import React from 'react';
import {Button, FormControl} from 'react-bootstrap';
import {connect} from 'react-redux';
import {finishEdit, deletePart} from '../reducer';

class Part extends React.Component {
    constructor(props) {
        super(props);
        this.state = {name: props.part.name, value: props.part.name, editing: false};
    }

    handleEdit() {
        this.setState({...this.state, editing: true});
    }

    onChange(e) {
        this.setState({...this.state, value: e.target.value});
    }

    finishEdit() {
        this.props.finishEdit(this.props.urls.parts, this.props.part.id, this.state.value);
        this.setState({name: this.state.value, value: this.state.value, editing: false})
    }

    cancelEdit() {
        this.setState({...this.state, editing: false})
    }

    handleDelete() {
        this.props.delete(this.props.urls.parts, this.props.part.id);
    }

    handleViewCompatibleParts() {
        this.props.displayCompatibleParts(this.props.part.id);
    }

    renderEditing() {
        let part = this.props.part;
    
        return (
            <tr>
                <td>{part.id}</td>
                <td>
                    <form>
                        <FormControl value={this.state.value} onChange={this.onChange.bind(this)} />
                    </form>
                </td>
                <td colSpan={3}>
                    <Button bsStyle='primary' onClick={this.finishEdit.bind(this)}>Save</Button>
                    <Button bsStyle='warning' onClick={this.cancelEdit.bind(this)}>Cancel</Button>
                    <Button bsStyle='danger'>Delete</Button>
                    <span>
                        <Button bsStyle='link' onClick={this.handleViewCompatibleParts.bind(this)}>
                            View compatible parts
                        </Button>
                    </span>
                </td>
            </tr>
        );
    }

    renderDisplay() {
        let part = this.props.part;
        return (
            <tr>
                <td>{part.id}</td>
                <td>{this.state.name}</td>
                <td colSpan={3}>
                    <Button bsStyle='primary' onClick={this.handleEdit.bind(this)}>Edit</Button>
                    <Button bsStyle='danger' onClick={this.handleDelete.bind(this)}>Delete</Button>
                    <span>
                        <Button bsStyle='link' onClick={this.handleViewCompatibleParts.bind(this)}>
                            View compatible parts
                        </Button>  
                    </span>
                </td>
            </tr>
        );
    }

    render() {
        if (this.state.editing) {
            return this.renderEditing();
        } else {
            return this.renderDisplay();
        }
    }
}

const mapState = (state, props) => {
    return {
        ...props,
        urls: state.get('urls')
    }
}
const mapDispatch = dispatch => {
    return {
        finishEdit: (url, partId, newName) => finishEdit(url, partId, newName, dispatch),
        delete: (url, id) => deletePart(url, id, dispatch),
        displayCompatibleParts: (id) => dispatch({type: 'DISPLAY_COMPATIBLE_PARTS', payload: id})
    };
}

export default connect(mapState, mapDispatch)(Part);