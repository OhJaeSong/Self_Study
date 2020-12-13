//SPDX-License-Identifier: UNLICENSED

pragma solidity >=0.7.0;
contract Delivery {
    
	struct Product {
		string productName;
		uint totDelivererCnt;
		uint[] indexDeliverer;
	}
    
	struct Deliverer {
		string delivererName;
		uint time;
	}
	
	uint public numDeliverer;
	
	mapping (string => Product) public products;
	mapping (uint => Deliverer) public deliverers;
	
	constructor () {
		numDeliverer = 0;
	}
	
	function addProduct(string memory _serialNumber, string memory _productName) payable public {
	    Product storage prod = products[_serialNumber];
	    prod.productName = _productName;
	    prod.totDelivererCnt = 0;
	}
	
	function addDeliverer(string memory _serialNumber, string memory _delivererName) payable public {
		Deliverer storage deliver = deliverers[numDeliverer];
		deliver.delivererName = _delivererName;
		deliver.time = block.timestamp;
		
		Product storage prod = products[_serialNumber];
		prod.indexDeliverer.push(numDeliverer++);
		prod.totDelivererCnt++;
	}

	
	function getProductInfo(string memory _serialNumber) view public returns ( string memory){
		Product storage prod = products[_serialNumber];
		uint tempIndex = 0;
		string memory retVal = "";
		for (uint i = 0; i < prod.indexDeliverer.length; i++) {
		    if (i > 0){
		        retVal = concatenate(retVal, " / ");
		    }
		    
            tempIndex = prod.indexDeliverer[i];
            
            Deliverer storage deliver = deliverers[tempIndex];
            
            retVal = concatenate(retVal, uintToStr(i+1));
            retVal = concatenate(retVal, " : ");
            retVal = concatenate(retVal, deliver.delivererName);
            retVal = concatenate(retVal, "(");
            retVal = concatenate(retVal, uintToStr(deliver.time));
            retVal = concatenate(retVal, ")");
            
        }
        
        return retVal;
	}
	
	function concatenate(
        string memory a,
        string memory b)
        internal 
        pure
        returns(string memory) {
            return string(abi.encodePacked(a, b));
    }
	
	function uintToStr(uint _i) internal pure returns (string memory _uintAsString) {
        uint number = _i;
        if (number == 0) {
            return "0";
        }
        uint j = number;
        uint len;
        while (j != 0) {
            len++;
            j /= 10;
        }
        bytes memory bstr = new bytes(len);
        uint k = len - 1;
        while (number != 0) {
            bstr[k--] = byte(uint8(48 + number % 10));
            number /= 10;
        }
        return string(bstr);
    }
}