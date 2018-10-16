
class EthSignTypedDataV1  {
    static MODEL = [
      {
        'type': 'string',
        'name': 'field1',
        'value': null
      },
      {
        'type': 'string',
        'name': 'field2',
        'value': null
      }
    ];

    async sign(field1, field2) {

        return new Promise((resolve, reject) => {

            var data = EthSignTypedDataV1.MODEL;
            data[0].value = field1;
            data[1].value = field2;

            console.log("EthSignTypedDataV1 - signing(data: "+JSON.stringify(data)+") with account " + window.web3.eth.accounts[0])

            window.web3.currentProvider.sendAsync({
                    method  : 'eth_signTypedData',
                    params  : [data, window.web3.eth.accounts[0]],
                    from: window.web3.eth.accounts[0]
            }, (err, { result }) => (err ? reject(err) : resolve(result)));
        });
    }

}

export default EthSignTypedDataV1;