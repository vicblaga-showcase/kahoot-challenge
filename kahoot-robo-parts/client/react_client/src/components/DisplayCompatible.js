import React from 'react';
import {connect} from 'react-redux';
import {Table} from 'react-bootstrap';
import {Set} from 'immutable';

class DisplayCompatible extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: true
        };
    }

    componentWillMount() {
        let url = `${this.props.url}/${this.props.id}/compatible`;
        fetch(url)
        .then(response => response.json())
        .then((result) => {
            let part = result.data;
            this.setState({...this.state, isLoading: false, part, compatiblePartsIds: Set(part.compatible_parts.map(part => part.id))})
        }, (error) => {
            this.setState({...this.state, isLoading: false, error})
        });
    }

    onCheck(e, id) {
        let verb = e.target.checked ? 'add' : 'remove';
        let url = `${this.props.url}/${this.state.part.id}/compatibility/${id}/${verb}`;
        
        fetch(url, {
            method: 'POST'
        })
        .then(res => res.json())
        .then((res) => {
            let compatiblePartsIds = this.state.compatiblePartsIds;
            if (verb === 'add') {
                compatiblePartsIds = compatiblePartsIds.add(id);
            } else {
                compatiblePartsIds = compatiblePartsIds.remove(id);
            }
            this.setState({...this.state, compatiblePartsIds})
        }, (error) => {
            this.setState({...this.state, error})
        });
    }

    partRow(part, compatiblePartsIds) {
        if (part.id === this.state.part.id) {
            return null;
        }
        let compatible = compatiblePartsIds.has(part.id);
        let onCheck = this.onCheck.bind(this);
        return (
            <tr key={part.id}>
                <td><input type="checkbox" checked={compatible} onChange={(e) => onCheck(e, part.id)} /></td>
                <td>{part.id}</td>
                <td>{part.name}</td>
            </tr>
        );
    }

    render() {
        if (this.state.isLoading) {
            return <div>Loading data, please wait...</div>;
        }
        
        if (this.state.error) {
            return <div>Error happened</div>;
        }
        let part = this.state.part;
        let parts = this.props.parts;
        let {compatiblePartsIds} = this.state;
        let partRow = this.partRow.bind(this);
        
        return (
        <div>
            <p>Part ID: <strong>{part.id}</strong></p>
            <p>Part name: <strong>{part.name}</strong></p>
            <Table>
                <thead>
                    <tr>
                        <th>Compatibility</th>
                        <th>ID</th>
                        <th>Name</th>
                    </tr>
                </thead>
                <tbody>
                    {parts.map(part => partRow(part, compatiblePartsIds))}
                </tbody>
            </Table>
        </div>
        );
    }
}

const mapState = state => {
    return {
        parts: state.getIn(['parts', 'data']),
        id: state.get('id'),
        url: state.get('urls').parts
    }
}

export default connect(mapState)(DisplayCompatible);