import React from 'react';
import {  render } from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import { Web3Provider } from 'react-web3';

render((
    <Web3Provider>
            <App />
  </Web3Provider>
), document.getElementById('root'));

registerServiceWorker();
