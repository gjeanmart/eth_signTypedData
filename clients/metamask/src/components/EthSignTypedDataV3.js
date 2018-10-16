
class EthSignTypedDataV3  {

    static DOMAIN = [
        { name: "name", type: "string" },
        { name: "version", type: "string" },
        { name: "chainId", type: "uint256" },
        { name: "verifyingContract", type: "address" },
        { name: "salt", type: "bytes32" },
    ];

    static VALUES = [
        { name: "field1", type: "string" },
        { name: "field2", type: "string" },
    ];

    static DOMAIN_DATA = {
        name: "EthSignTypedDataV3",
        version: "1",
        chainId: "4",
        verifyingContract: "0x1C56346CD2A2Bf3202F771f50d3D14a367B48070", //TODO change
        salt: "0xf2d857f4a3edcb9b78b4d503bfe733db1e3f6cdc2b7971ee739626c97e86a558" //TODO change
    };

    async sign(field1, field2) {

        return new Promise((resolve, reject) => {

            const message = {
                'field1': field1,
                'field2': field2,
            };

            const data = JSON.stringify({
                types: {
                    EIP712Domain: EthSignTypedDataV3.DOMAIN,
                    Values: EthSignTypedDataV3.VALUES
                },
                domain: EthSignTypedDataV3.DOMAIN_DATA,
                primaryType: "Values",
                message: message
            });

            console.log("EthSignTypedDataV3 - signing(data: "+JSON.stringify(data)+") with account " + window.web3.eth.accounts[0])

            window.web3.currentProvider.sendAsync({
                    method  : 'eth_signTypedData_v3',
                    params  : [window.web3.eth.accounts[0], data],
                    from: window.web3.eth.accounts[0]
            }, (err, { result }) => (err ? reject(err) : resolve(result)));
        });
    }

}

export default EthSignTypedDataV3;