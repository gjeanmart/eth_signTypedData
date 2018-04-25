import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Alert } from 'react-bootstrap';

class App extends Component {

  constructor(props, context) {
    super(props, context);    
    this.state = { };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.sign = this.sign.bind(this);

  }

  handleChange(event) {
    switch(event.target.name) {
      case "field1":
        this.setState({"field1": event.target.value})
        break;
      case "field2":
        this.setState({"field2": event.target.value})
        break;
      default:
        break;
    }
  }

  async sign(data) {
      console.log("sign(data: "+JSON.stringify(data)+") with account " + window.web3.eth.accounts[0])

      return new Promise((resolve, reject) => {
        window.web3.currentProvider.sendAsync(
        {
            method  : 'eth_signTypedData',
            params  : [data, window.web3.eth.accounts[0]],
            from: window.web3.eth.accounts[0]
        },  (err, { result }) => (err ? reject(err) : resolve(result))
        )
      });

  }

  async handleSubmit() {
    console.log("handleSubmit()")
    this.setState({error1: null})
    this.setState({success1: null})

    const typedData = [
      {
        'type': 'string',
        'name': 'field1',
        'value': "val1"
      },
      {
        'type': 'string',
        'name': 'field2',
        'value': "val2"
      }
    ];

      var signature = await this.sign(typedData);
      console.log("signature="+signature)
      this.setState({signature: signature})

  }



  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro">

         <Form horizontal  className="Section">

              { this.state.error1 ? 
                <div>
                  <Col sm={2}></Col>
                  <Col sm={10}>
                    <Alert bsStyle="danger">{this.state.error1}</Alert> 
                  </Col> 
                </div>
               : null }

              { this.state.success1 ? 
                <div>
                  <Col sm={2}></Col>
                  <Col sm={10}>
                    <Alert bsStyle="success">{this.state.success1}</Alert> 
                  </Col> 
                </div>
               : null }
              

            <FormGroup controlId="formField1">
              <Col componentClass={ControlLabel} sm={2}>Field1</Col>
              <Col sm={8}>
                  <FormControl
                    type="text"
                    name="field1"
                    placeholder="Enter field1"
                    value={this.state.field1}
                    onChange={this.handleChange}
                  />
              </Col>
              <Col componentClass={ControlLabel} sm={2}>Field2</Col>
              <Col sm={8}>
                  <FormControl
                    type="text"
                    name="field2"
                    placeholder="Enter field2"
                    value={this.state.field2}
                    onChange={this.handleChange}
                  />
              </Col>
              <Col sm={2}>
                <Button type="button" onClick={this.handleSubmit}>Sign</Button>
              </Col>
            </FormGroup>


            <FormGroup>
              <Col componentClass={ControlLabel} sm={2}>Signature</Col>
              <Col sm={10}>
                <FormControl componentClass="textarea" value={this.state.signature} readOnly />
              </Col>
            </FormGroup>

          </Form>

        </p>
      </div>
    );
  }
}

export default App;
