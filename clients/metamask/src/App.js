import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col } from 'react-bootstrap';
import { RadioGroup, RadioButton } from 'react-radio-buttons';
import EthSignTypedDataV1 from './components/EthSignTypedDataV1.js'
import EthSignTypedDataV3 from './components/EthSignTypedDataV3.js'

class App extends Component {

  constructor(props, context) {
    super(props, context);    
    this.state = { };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  onChange(val) {
    this.setState({"version": val})
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

  async handleSubmit() {
    var signature;

    if(this.state.version === "v1") {
      signature = await new EthSignTypedDataV1().sign(this.state.field1, this.state.field2);
    
    } else if(this.state.version === "v3") {
      signature = await new EthSignTypedDataV3().sign(this.state.field1, this.state.field2);
    
    }
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

            <RadioGroup onChange={ this.onChange } horizontal>
              <RadioButton value="v1">
                V1
              </RadioButton>
              <RadioButton value="v3">
                V3
              </RadioButton>
            </RadioGroup>

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
