import { connect } from 'react-redux'
import React, { Component } from 'react';
import AppNav from './AppNav';
import AllParts from './AllParts';
import PartForm from './PartForm';
import DisplayCompatible from './DisplayCompatible';

class App extends Component {
  listAll() {
    return <AllParts />;
  }

  partForm() {
    return <PartForm />;
  }

  displayCompatible() {
    return <DisplayCompatible id={this.props.id} />
  }

  listUnknown() {
    return <div>Unknown things right here</div>;
  }

  displayComponent() {
    switch (this.props.component) {
      case 'list-all':
        return this.listAll();
      case 'add-part':
        return this.partForm();
      case 'display-compatible':
        return this.displayCompatible();
      default:
        return this.listUnknown();
    }
  }

  render() {
    return (
      <div className="container">
        <AppNav />
        <div className="App-body">
          {this.displayComponent()}
        </div>
      </div>
    );
  }
}

const mapState = state => {
  return {
    component: state.get('component'),
    id: state.get('id'),
    urls: state.get('urls')
  };
};

export default connect(mapState)(App);
