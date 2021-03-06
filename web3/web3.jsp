<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
  /**
  * @Class Name : egovSampleList.jsp
  * @Description : Sample List 화면
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2009.02.01            최초 생성
  *
  * author 실행환경 개발팀
  * since 2009.02.01
  *
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><spring:message code="title.sample" /></title>
    <!-- <link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/sample.css'/>"/> -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/gh/ethereum/web3.js@0.20.6/dist/web3.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    
    <script type="text/javaScript" language="javascript" defer="defer">
        <!--
            web3 = new Web3(new Web3.providers.HttpProvider("http://52.78.193.239:8545"));
        	web3.eth.defaultAccount = web3.eth.accounts[0];
        	 
    		
        	var ownedContract = web3.eth.contract([
        		{
        			"constant": true,
        			"inputs": [],
        			"name": "owner",
        			"outputs": [
        				{
        					"name": "",
        					"type": "address"
        				}
        			],
        			"payable": false,
        			"stateMutability": "view",
        			"type": "function"
        		},
        		{
        			"inputs": [],
        			"payable": false,
        			"stateMutability": "nonpayable",
        			"type": "constructor"
        		}
        	]).at("0xb7fb7f09f3c82dfe1a790b1829f158f9c1a9bced");
        	
        	var memberContract = web3.eth.contract([
        		{
        			"constant": true,
        			"inputs": [
        				{
        					"name": "_member",
        					"type": "address"
        				}
        			],
        			"name": "getMember",
        			"outputs": [
        				{
        					"name": "_nation",
        					"type": "string"
        				},
        				{
        					"name": "_sex",
        					"type": "string"
        				},
        				{
        					"name": "_age",
        					"type": "string"
        				}
        			],
        			"payable": false,
        			"stateMutability": "view",
        			"type": "function"
        		},
        		{
        			"constant": true,
        			"inputs": [
        				{
        					"name": "",
        					"type": "address"
        				}
        			],
        			"name": "memberDetail",
        			"outputs": [
        				{
        					"name": "nation",
        					"type": "string"
        				},
        				{
        					"name": "sex",
        					"type": "string"
        				},
        				{
        					"name": "age",
        					"type": "string"
        				}
        			],
        			"payable": false,
        			"stateMutability": "view",
        			"type": "function"
        		},
        		{
        			"constant": true,
        			"inputs": [],
        			"name": "owner",
        			"outputs": [
        				{
        					"name": "",
        					"type": "address"
        				}
        			],
        			"payable": false,
        			"stateMutability": "view",
        			"type": "function"
        		},
        		{
        			"constant": false,
        			"inputs": [
        				{
        					"name": "_nation",
        					"type": "string"
        				},
        				{
        					"name": "_sex",
        					"type": "string"
        				},
        				{
        					"name": "_age",
        					"type": "string"
        				}
        			],
        			"name": "setMember",
        			"outputs": [],
        			"payable": false,
        			"stateMutability": "nonpayable",
        			"type": "function"
        		}
        	]).at("0x120ae62b2f6301f1565410eaceb71c7894ed52ef");
        
            var cookCoinContract = web3.eth.contract([
            	{
            		"constant": true,
            		"inputs": [
            			{
            				"name": "",
            				"type": "address"
            			}
            		],
            		"name": "balanceOf",
            		"outputs": [
            			{
            				"name": "",
            				"type": "uint256"
            			}
            		],
            		"payable": false,
            		"stateMutability": "view",
            		"type": "function"
            	},
            	{
            		"constant": true,
            		"inputs": [],
            		"name": "decimals",
            		"outputs": [
            			{
            				"name": "",
            				"type": "uint8"
            			}
            		],
            		"payable": false,
            		"stateMutability": "view",
            		"type": "function"
            	},
            	{
            		"constant": true,
            		"inputs": [
            			{
            				"name": "",
            				"type": "uint256"
            			}
            		],
            		"name": "detail",
            		"outputs": [
            			{
            				"name": "fromAddress",
            				"type": "address"
            			},
            			{
            				"name": "toAddress",
            				"type": "address"
            			},
            			{
            				"name": "amount",
            				"type": "uint256"
            			}
            		],
            		"payable": false,
            		"stateMutability": "view",
            		"type": "function"
            	},
            	{
            		"constant": true,
            		"inputs": [
            			{
            				"name": "_rewardTp",
            				"type": "uint256"
            			}
            		],
            		"name": "getRewardAmount",
            		"outputs": [
            			{
            				"name": "rewardAmount",
            				"type": "uint256"
            			}
            		],
            		"payable": false,
            		"stateMutability": "view",
            		"type": "function"
            	},
            	{
            		"constant": true,
            		"inputs": [
            			{
            				"name": "",
            				"type": "address"
            			}
            		],
            		"name": "members",
            		"outputs": [
            			{
            				"name": "",
            				"type": "address"
            			}
            		],
            		"payable": false,
            		"stateMutability": "view",
            		"type": "function"
            	},
            	{
            		"constant": true,
            		"inputs": [],
            		"name": "name",
            		"outputs": [
            			{
            				"name": "",
            				"type": "string"
            			}
            		],
            		"payable": false,
            		"stateMutability": "view",
            		"type": "function"
            	},
            	{
            		"constant": true,
            		"inputs": [],
            		"name": "owner",
            		"outputs": [
            			{
            				"name": "",
            				"type": "address"
            			}
            		],
            		"payable": false,
            		"stateMutability": "view",
            		"type": "function"
            	},
            	{
            		"constant": true,
            		"inputs": [],
            		"name": "symbol",
            		"outputs": [
            			{
            				"name": "",
            				"type": "string"
            			}
            		],
            		"payable": false,
            		"stateMutability": "view",
            		"type": "function"
            	},
            	{
            		"constant": true,
            		"inputs": [],
            		"name": "totalSupply",
            		"outputs": [
            			{
            				"name": "",
            				"type": "uint256"
            			}
            		],
            		"payable": false,
            		"stateMutability": "view",
            		"type": "function"
            	},
            	{
            		"constant": true,
            		"inputs": [
            			{
            				"name": "",
            				"type": "address"
            			}
            		],
            		"name": "transferDetail",
            		"outputs": [
            			{
            				"name": "fromAddress",
            				"type": "address"
            			},
            			{
            				"name": "toAddress",
            				"type": "address"
            			},
            			{
            				"name": "amount",
            				"type": "uint256"
            			}
            		],
            		"payable": false,
            		"stateMutability": "view",
            		"type": "function"
            	},
            	{
            		"constant": false,
            		"inputs": [
            			{
            				"name": "_to",
            				"type": "address"
            			},
            			{
            				"name": "_value",
            				"type": "uint256"
            			}
            		],
            		"name": "transfer",
            		"outputs": [],
            		"payable": false,
            		"stateMutability": "nonpayable",
            		"type": "function"
            	},
            	{
            		"constant": false,
            		"inputs": [
            			{
            				"name": "_rewardTp",
            				"type": "uint256"
            			}
            		],
            		"name": "rewardTransfer",
            		"outputs": [],
            		"payable": false,
            		"stateMutability": "nonpayable",
            		"type": "function"
            	},
            	{
            		"constant": false,
            		"inputs": [
            			{
            				"name": "_fromAddress",
            				"type": "address"
            			},
            			{
            				"name": "_toAddress",
            				"type": "address"
            			},
            			{
            				"name": "_amount",
            				"type": "uint256"
            			}
            		],
            		"name": "addHistory",
            		"outputs": [],
            		"payable": false,
            		"stateMutability": "nonpayable",
            		"type": "function"
            	},
            	{
            		"inputs": [
            			{
            				"name": "_supply",
            				"type": "uint256"
            			},
            			{
            				"name": "_name",
            				"type": "string"
            			},
            			{
            				"name": "_symbol",
            				"type": "string"
            			},
            			{
            				"name": "_decimals",
            				"type": "uint8"
            			}
            		],
            		"payable": false,
            		"stateMutability": "nonpayable",
            		"type": "constructor"
            	},
            	{
            		"anonymous": false,
            		"inputs": [
            			{
            				"indexed": true,
            				"name": "from",
            				"type": "address"
            			},
            			{
            				"indexed": true,
            				"name": "to",
            				"type": "address"
            			},
            			{
            				"indexed": false,
            				"name": "value",
            				"type": "uint256"
            			}
            		],
            		"name": "Transfer",
            		"type": "event"
            	},
            	{
            		"anonymous": false,
            		"inputs": [
            			{
            				"indexed": true,
            				"name": "from",
            				"type": "address"
            			},
            			{
            				"indexed": true,
            				"name": "to",
            				"type": "address"
            			},
            			{
            				"indexed": false,
            				"name": "value",
            				"type": "uint256"
            			}
            		],
            		"name": "RewardTransfer",
            		"type": "event"
            	},
            	{
            		"anonymous": false,
            		"inputs": [
            			{
            				"indexed": true,
            				"name": "from",
            				"type": "address"
            			},
            			{
            				"indexed": true,
            				"name": "to",
            				"type": "address"
            			},
            			{
            				"indexed": false,
            				"name": "value",
            				"type": "uint256"
            			}
            		],
            		"name": "AddHistory",
            		"type": "event"
            	}
            ]).at("0x04ea243e6c957dd457f1d17847330d2b4bea7b6e");
            
        //-->
        
        function fn_balanceOf(){
        	$("#balanceVal").val(cookCoinContract.balanceOf($("#accounts").val()));
        }
        
        function fn_sendMoney(){
        	cookCoinContract.transfer($("#sendAccount").val(),$("#sendAmount").val());
        	//cookCoinContract.methods.rewardTransfer(1).send();
        	
        	
        	
        	//cookCoinContract.transfer($("#sendAccount").val(), $("#sendAmount").val());
        }
    </script>
</head>

<body>
	<div class="container">
		<div class="page-header">
			<h1>CookCoin Test</h1>
			<p class="lead">기본 기능 테스트</p>
		</div>
		<h3>사용자</h3>
		<div class="row">
			<div class="col-md-3">
				<label class="col-xs-6 control-label">사용자</label>
			</div>
			<div class="col-md-3">
				<select name="accounts" id="accounts" class="form-control">
					<option value="0x7ec0c5190266db744c65321538a287f835252911">관리자</option>
					<option value="0x0eb67277058db537fd9b3382a5c21f134a9017e7">사용자1</option>
					<option value="0xbaacf88b872a091fe6eeb0be5267dad7dd0b56cd">사용자2</option>
				</select>
			</div>
			<div class="col-md-3">
				
			</div>
			<div class="col-md-3">
				
			</div>
		</div>

		<h3>잔액 테스트</h3>
		<div class="row">
			<div class="col-md-3">
				<button class="btn btn-primary btn-block" onclick="fn_balanceOf()">잔고확인</button>
			</div>
			<div class="col-md-3">
				<input type="text" class="form-control" id="balanceVal"/>
			</div>
			<div class="col-md-3">
				
			</div>
			<div class="col-md-3">
				
			</div>
		</div>
		<h3>송금 테스트</h3>
		<div class="row">
			<div class="col-md-3">
				<label class="col-xs-6 control-label">수신자/금액</label>
			</div>
			<div class="col-md-3">
				<select id="sendAccount" class="form-control">
					<option value="0x7ec0c5190266db744c65321538a287f835252911">관리자</option>
					<option value="0x0eb67277058db537fd9b3382a5c21f134a9017e7">사용자1</option>
					<option value="0xbaacf88b872a091fe6eeb0be5267dad7dd0b56cd">사용자2</option>
				</select>
			</div>
			<div class="col-md-3">
				<input type="text" class="form-control" id="sendAmount"/>
			</div>
			<div class="col-md-3">
				<button class="btn btn-primary btn-block" onclick="fn_sendMoney()">송금</button>
			</div>
		</div>
	</div>
</body>
</html>
