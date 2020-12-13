var nameregistryabi = [
        {
                "inputs": [],
                "stateMutability": "nonpayable",
                "type": "constructor"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_name",
                                "type": "string"
                        },
                        {
                                "internalType": "address",
                                "name": "_newOwner",
                                "type": "address"
                        }
                ],
                "name": "changeOwner",
                "outputs": [],
                "stateMutability": "nonpayable",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "",
                                "type": "string"
                        }
                ],
                "name": "contracts",
                "outputs": [
                        {
                                "internalType": "address",
                                "name": "owner",
                                "type": "address"
                        },
                        {
                                "internalType": "address",
                                "name": "addr",
                                "type": "address"
                        },
                        {
                                "internalType": "string",
                                "name": "description",
                                "type": "string"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_name",
                                "type": "string"
                        }
                ],
                "name": "getAddr",
                "outputs": [
                        {
                                "internalType": "address",
                                "name": "",
                                "type": "address"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_name",
                                "type": "string"
                        }
                ],
                "name": "getDescription",
                "outputs": [
                        {
                                "internalType": "string",
                                "name": "",
                                "type": "string"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_name",
                                "type": "string"
                        }
                ],
                "name": "getOwner",
                "outputs": [
                        {
                                "internalType": "address",
                                "name": "",
                                "type": "address"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "numContracts",
                "outputs": [
                        {
                                "internalType": "uint256",
                                "name": "",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_name",
                                "type": "string"
                        }
                ],
                "name": "register",
                "outputs": [
                        {
                                "internalType": "bool",
                                "name": "",
                                "type": "bool"
                        }
                ],
                "stateMutability": "nonpayable",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_name",
                                "type": "string"
                        },
                        {
                                "internalType": "address",
                                "name": "_addr",
                                "type": "address"
                        }
                ],
                "name": "setAddr",
                "outputs": [],
                "stateMutability": "nonpayable",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_name",
                                "type": "string"
                        },
                        {
                                "internalType": "string",
                                "name": "_description",
                                "type": "string"
                        }
                ],
                "name": "setDescription",
                "outputs": [],
                "stateMutability": "nonpayable",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_name",
                                "type": "string"
                        }
                ],
                "name": "unregister",
                "outputs": [
                        {
                                "internalType": "bool",
                                "name": "",
                                "type": "bool"
                        }
                ],
                "stateMutability": "nonpayable",
                "type": "function"
        }
];

var cfabi = [
        {
                "inputs": [],
                "name": "checkGoalReached",
                "outputs": [],
                "stateMutability": "payable",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "fund",
                "outputs": [],
                "stateMutability": "payable",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "kill",
                "outputs": [],
                "stateMutability": "nonpayable",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "uint256",
                                "name": "_duration",
                                "type": "uint256"
                        },
                        {
                                "internalType": "uint256",
                                "name": "_goalAmount",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "nonpayable",
                "type": "constructor"
        },
        {
                "inputs": [],
                "name": "deadline",
                "outputs": [
                        {
                                "internalType": "uint256",
                                "name": "",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "ended",
                "outputs": [
                        {
                                "internalType": "bool",
                                "name": "",
                                "type": "bool"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "getBalance",
                "outputs": [
                        {
                                "internalType": "uint256",
                                "name": "",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "goalAmount",
                "outputs": [
                        {
                                "internalType": "uint256",
                                "name": "",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "uint256",
                                "name": "",
                                "type": "uint256"
                        }
                ],
                "name": "investors",
                "outputs": [
                        {
                                "internalType": "address payable",
                                "name": "addr",
                                "type": "address"
                        },
                        {
                                "internalType": "uint256",
                                "name": "amount",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "numInvestors",
                "outputs": [
                        {
                                "internalType": "uint256",
                                "name": "",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "owner",
                "outputs": [
                        {
                                "internalType": "address payable",
                                "name": "",
                                "type": "address"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "status",
                "outputs": [
                        {
                                "internalType": "string",
                                "name": "",
                                "type": "string"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "totalAmount",
                "outputs": [
                        {
                                "internalType": "uint256",
                                "name": "",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        }
];

var deAbi = [
        {
                "inputs": [],
                "stateMutability": "nonpayable",
                "type": "constructor"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_serialNumber",
                                "type": "string"
                        },
                        {
                                "internalType": "string",
                                "name": "_delivererName",
                                "type": "string"
                        }
                ],
                "name": "addDeliverer",
                "outputs": [],
                "stateMutability": "payable",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_serialNumber",
                                "type": "string"
                        },
                        {
                                "internalType": "string",
                                "name": "_productName",
                                "type": "string"
                        }
                ],
                "name": "addProduct",
                "outputs": [],
                "stateMutability": "payable",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "uint256",
                                "name": "",
                                "type": "uint256"
                        }
                ],
                "name": "deliverers",
                "outputs": [
                        {
                                "internalType": "string",
                                "name": "delivererName",
                                "type": "string"
                        },
                        {
                                "internalType": "uint256",
                                "name": "time",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "_serialNumber",
                                "type": "string"
                        }
                ],
                "name": "getProductInfo",
                "outputs": [
                        {
                                "internalType": "string",
                                "name": "",
                                "type": "string"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [],
                "name": "numDeliverer",
                "outputs": [
                        {
                                "internalType": "uint256",
                                "name": "",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        },
        {
                "inputs": [
                        {
                                "internalType": "string",
                                "name": "",
                                "type": "string"
                        }
                ],
                "name": "products",
                "outputs": [
                        {
                                "internalType": "string",
                                "name": "productName",
                                "type": "string"
                        },
                        {
                                "internalType": "uint256",
                                "name": "totDelivererCnt",
                                "type": "uint256"
                        }
                ],
                "stateMutability": "view",
                "type": "function"
        }
];
