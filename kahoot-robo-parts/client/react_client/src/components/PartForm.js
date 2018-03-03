import React from 'react';
import { Button, FormGroup, ControlLabel, FormControl, HelpBlock } from 'react-bootstrap';
import { connect } from 'react-redux';
import { addPart } from '../reducer';

class PartForm extends React.Component {
    constructor() {
        super();
        this.state = {value: ''};
    }

    isNameValid(value) {
        return value.length >= 3;
    }

    partNameValidationState() {
        if (this.isNameValid(this.state.value)) return 'success';
        else return 'error';
    }

    handleChange(e) {
        this.setState({value: e.target.value});
    }

    render() {
        let feedback = this.isNameValid(this.state.value) ? 'All good' : 'At least 3 characters in length.';
        
        return (
        <form>
            <FormGroup controlId="part-name" validationState={this.partNameValidationState()}>
                <ControlLabel>Part name:</ControlLabel>
                <FormControl 
                    type="text"
                    value={this.state.value}
                    placeholder="Enter part name here"
                    onChange={this.handleChange.bind(this)}
                />
                <FormControl.Feedback />
                <HelpBlock>{feedback}</HelpBlock>
            </FormGroup>
            <Button 
                onClick={() => this.props.handleOnClick(this.props.urls.parts, this.state.value)}
                disabled={!this.isNameValid(this.state.value)}
            >
                Add part
            </Button>
        
        </form>
        );
    }
}

const mapState = state => {
    return {
        urls: state.get('urls')
    };
}

const mapDispatch = (dispatch, props) => {
    return {
        handleOnClick: (url, value) => addPart(url, value, dispatch)
    };
};

export default connect(mapState, mapDispatch)(PartForm);