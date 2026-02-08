package com.escrow.backend.blockchain;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.8.
 */
@SuppressWarnings("rawtypes")
public class DatasetEscrow extends Contract {
    public static final String BINARY = "0x60806040523480156200001157600080fd5b5060405162001fef38038062001fef8339818101604052810190620000379190620002b1565b336001600081905550600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603620000b55760006040517f1e4fbdf7000000000000000000000000000000000000000000000000000000008152600401620000ac9190620002f4565b60405180910390fd5b620000c6816200018160201b60201c565b50600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160362000139576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401620001309062000372565b60405180910390fd5b80600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505062000394565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905081600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600062000279826200024c565b9050919050565b6200028b816200026c565b81146200029757600080fd5b50565b600081519050620002ab8162000280565b92915050565b600060208284031215620002ca57620002c962000247565b5b6000620002da848285016200029a565b91505092915050565b620002ee816200026c565b82525050565b60006020820190506200030b6000830184620002e3565b92915050565b600082825260208201905092915050565b7f4f7261636c652072657175697265640000000000000000000000000000000000600082015250565b60006200035a600f8362000311565b9150620003678262000322565b602082019050919050565b600060208201905081810360008301526200038d816200034b565b9050919050565b611c4b80620003a46000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c80637dc0d1d01161008c578063a779761f11610066578063a779761f146101dd578063b6b55f25146101f9578063dcb04b6e14610215578063f2fde38b14610245576100cf565b80637dc0d1d0146101855780637ea7379d146101a35780638da5cb5b146101bf576100cf565b806303988f84146100d45780631cb44dfc14610109578063217328b614610125578063278ecde11461014357806337bdc99b1461015f578063715018a61461017b575b600080fd5b6100ee60048036038101906100e99190611380565b610261565b604051610100969594939291906114d3565b60405180910390f35b610123600480360381019061011e9190611560565b61030a565b005b61012d6103c5565b60405161013a919061158d565b60405180910390f35b61015d60048036038101906101589190611380565b6103cb565b005b61017960048036038101906101749190611380565b6104be565b005b610183610632565b005b61018d610646565b60405161019a91906115a8565b60405180910390f35b6101bd60048036038101906101b891906115fb565b61066c565b005b6101c761080a565b6040516101d491906115a8565b60405180910390f35b6101f760048036038101906101f29190611380565b610834565b005b610213600480360381019061020e9190611380565b610970565b005b61022f600480360381019061022a919061163b565b610b54565b60405161023c919061158d565b60405180910390f35b61025f600480360381019061025a9190611560565b610e8b565b005b60036020528060005260406000206000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060030154908060040154908060050160009054906101000a900460ff16905086565b610312610f11565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610381576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610378906116ff565b60405180910390fd5b80600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b60025481565b6103d3610f98565b60006003600083815260200190815260200160002090508060040154421015610431576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104289061176b565b60405180910390fd5b600160048111156104455761044461145c565b5b8160050160009054906101000a900460ff1660048111156104695761046861145c565b5b146104a9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104a0906117d7565b60405180910390fd5b6104b282610fde565b506104bb6110ca565b50565b6104c6610f98565b6000600360008381526020019081526020016000209050600260048111156104f1576104f061145c565b5b8160050160009054906101000a900460ff1660048111156105155761051461145c565b5b14610555576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161054c90611843565b60405180910390fd5b60038160050160006101000a81548160ff0219169083600481111561057d5761057c61145c565b5b02179055506105f98160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1682600301548360020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166110d49092919063ffffffff16565b817ffb81f9b30d73d830c3544b34d827c08142579ee75710b490bab0b3995468c56560405160405180910390a25061062f6110ca565b50565b61063a610f11565b6106446000611153565b565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146106fc576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106f3906118af565b60405180910390fd5b6000600360008481526020019081526020016000209050600160048111156107275761072661145c565b5b8160050160009054906101000a900460ff16600481111561074b5761074a61145c565b5b1461078b576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107829061191b565b60405180910390fd5b81156107c35760028160050160006101000a81548160ff021916908360048111156107b9576107b861145c565b5b02179055506107cd565b6107cc83610fde565b5b827f556669ce8c9ae20c25bb6011f81fabc84343b48b5b6800e3e18257fef3543fab836040516107fd919061194a565b60405180910390a2505050565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146108c4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108bb906118af565b60405180910390fd5b6108cc610f98565b6000600360008381526020019081526020016000209050600160048111156108f7576108f661145c565b5b8160050160009054906101000a900460ff16600481111561091b5761091a61145c565b5b1461095b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610952906117d7565b60405180910390fd5b61096482610fde565b5061096d6110ca565b50565b610978610f98565b60006003600083815260200190815260200160002090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610a21576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a18906119b1565b60405180910390fd5b60006004811115610a3557610a3461145c565b5b8160050160009054906101000a900460ff166004811115610a5957610a5861145c565b5b14610a99576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a90906117d7565b60405180910390fd5b610aee333083600301548460020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16611219909392919063ffffffff16565b60018160050160006101000a81548160ff02191690836004811115610b1657610b1561145c565b5b0217905550817f2a89b2e3d580398d6dc2db5e0f336b52602bbaa51afa9bb5cdf59239cf0d2bea60405160405180910390a250610b516110ca565b50565b60008073ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff1603610bc4576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610bbb90611a1d565b60405180910390fd5b428211610c06576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610bfd90611a89565b60405180910390fd5b60008311610c49576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c4090611af5565b60405180910390fd5b60026000815480929190610c5c90611b44565b91905055506040518060c001604052803373ffffffffffffffffffffffffffffffffffffffff1681526020018673ffffffffffffffffffffffffffffffffffffffff1681526020018573ffffffffffffffffffffffffffffffffffffffff16815260200184815260200183815260200160006004811115610ce057610cdf61145c565b5b81525060036000600254815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff02191690836004811115610e0c57610e0b61145c565b5b02179055509050508473ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff166002547f584834bba667bf1cd5aa00cfe8042067a3d6b5da7b1a555dc640e003ea7328df8686604051610e76929190611b8c565b60405180910390a46002549050949350505050565b610e93610f11565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610f055760006040517f1e4fbdf7000000000000000000000000000000000000000000000000000000008152600401610efc91906115a8565b60405180910390fd5b610f0e81611153565b50565b610f1961129b565b73ffffffffffffffffffffffffffffffffffffffff16610f3761080a565b73ffffffffffffffffffffffffffffffffffffffff1614610f9657610f5a61129b565b6040517f118cdaa7000000000000000000000000000000000000000000000000000000008152600401610f8d91906115a8565b60405180910390fd5b565b600260005403610fd4576040517f3ee5aeb500000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6002600081905550565b600060036000838152602001908152602001600020905060048160050160006101000a81548160ff0219169083600481111561101d5761101c61145c565b5b02179055506110998160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1682600301548360020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166110d49092919063ffffffff16565b817f3d2a04f53164bedf9a8a46353305d6b2d2261410406df3b41f99ce6489dc003c60405160405180910390a25050565b6001600081905550565b61114e838473ffffffffffffffffffffffffffffffffffffffff1663a9059cbb8585604051602401611107929190611bb5565b604051602081830303815290604052915060e01b6020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff83818316178352505050506112a3565b505050565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905081600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b611295848573ffffffffffffffffffffffffffffffffffffffff166323b872dd86868660405160240161124e93929190611bde565b604051602081830303815290604052915060e01b6020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff83818316178352505050506112a3565b50505050565b600033905090565b600080602060008451602086016000885af1806112c6576040513d6000823e3d81fd5b3d9250600051915050600082146112e15760018114156112fd565b60008473ffffffffffffffffffffffffffffffffffffffff163b145b1561133f57836040517f5274afe700000000000000000000000000000000000000000000000000000000815260040161133691906115a8565b60405180910390fd5b50505050565b600080fd5b6000819050919050565b61135d8161134a565b811461136857600080fd5b50565b60008135905061137a81611354565b92915050565b60006020828403121561139657611395611345565b5b60006113a48482850161136b565b91505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006113d8826113ad565b9050919050565b6113e8816113cd565b82525050565b6000819050919050565b600061141361140e611409846113ad565b6113ee565b6113ad565b9050919050565b6000611425826113f8565b9050919050565b60006114378261141a565b9050919050565b6114478161142c565b82525050565b6114568161134a565b82525050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b6005811061149c5761149b61145c565b5b50565b60008190506114ad8261148b565b919050565b60006114bd8261149f565b9050919050565b6114cd816114b2565b82525050565b600060c0820190506114e860008301896113df565b6114f560208301886113df565b611502604083018761143e565b61150f606083018661144d565b61151c608083018561144d565b61152960a08301846114c4565b979650505050505050565b61153d816113cd565b811461154857600080fd5b50565b60008135905061155a81611534565b92915050565b60006020828403121561157657611575611345565b5b60006115848482850161154b565b91505092915050565b60006020820190506115a2600083018461144d565b92915050565b60006020820190506115bd60008301846113df565b92915050565b60008115159050919050565b6115d8816115c3565b81146115e357600080fd5b50565b6000813590506115f5816115cf565b92915050565b6000806040838503121561161257611611611345565b5b60006116208582860161136b565b9250506020611631858286016115e6565b9150509250929050565b6000806000806080858703121561165557611654611345565b5b60006116638782880161154b565b94505060206116748782880161154b565b93505060406116858782880161136b565b92505060606116968782880161136b565b91505092959194509250565b600082825260208201905092915050565b7f5a65726f20616464726573730000000000000000000000000000000000000000600082015250565b60006116e9600c836116a2565b91506116f4826116b3565b602082019050919050565b60006020820190508181036000830152611718816116dc565b9050919050565b7f4e6f742065787069726564000000000000000000000000000000000000000000600082015250565b6000611755600b836116a2565b91506117608261171f565b602082019050919050565b6000602082019050818103600083015261178481611748565b9050919050565b7f496e76616c696420737461746500000000000000000000000000000000000000600082015250565b60006117c1600d836116a2565b91506117cc8261178b565b602082019050919050565b600060208201905081810360008301526117f0816117b4565b9050919050565b7f4e6f742076616c69646174656400000000000000000000000000000000000000600082015250565b600061182d600d836116a2565b9150611838826117f7565b602082019050919050565b6000602082019050818103600083015261185c81611820565b9050919050565b7f4e6f74206f7261636c6500000000000000000000000000000000000000000000600082015250565b6000611899600a836116a2565b91506118a482611863565b602082019050919050565b600060208201905081810360008301526118c88161188c565b9050919050565b7f4e6f74206465706f736974656400000000000000000000000000000000000000600082015250565b6000611905600d836116a2565b9150611910826118cf565b602082019050919050565b60006020820190508181036000830152611934816118f8565b9050919050565b611944816115c3565b82525050565b600060208201905061195f600083018461193b565b92915050565b7f4f6e6c7920627579657200000000000000000000000000000000000000000000600082015250565b600061199b600a836116a2565b91506119a682611965565b602082019050919050565b600060208201905081810360008301526119ca8161198e565b9050919050565b7f496e76616c69642073656c6c6572000000000000000000000000000000000000600082015250565b6000611a07600e836116a2565b9150611a12826119d1565b602082019050919050565b60006020820190508181036000830152611a36816119fa565b9050919050565b7f496e76616c696420657870697279000000000000000000000000000000000000600082015250565b6000611a73600e836116a2565b9150611a7e82611a3d565b602082019050919050565b60006020820190508181036000830152611aa281611a66565b9050919050565b7f416d6f756e74207a65726f000000000000000000000000000000000000000000600082015250565b6000611adf600b836116a2565b9150611aea82611aa9565b602082019050919050565b60006020820190508181036000830152611b0e81611ad2565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611b4f8261134a565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611b8157611b80611b15565b5b600182019050919050565b6000604082019050611ba1600083018561144d565b611bae602083018461144d565b9392505050565b6000604082019050611bca60008301856113df565b611bd7602083018461144d565b9392505050565b6000606082019050611bf360008301866113df565b611c0060208301856113df565b611c0d604083018461144d565b94935050505056fea26469706673582212207c8026dd2ac704ef742e66750d21d19833f90f351952f4cdc0e1d42beac14bcc64736f6c63430008140033";

    public static final String FUNC_CREATEDEAL = "createDeal";

    public static final String FUNC_DEALCOUNTER = "dealCounter";

    public static final String FUNC_DEALS = "deals";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_ORACLE = "oracle";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REFUND = "refund";

    public static final String FUNC_REFUNDBYORACLE = "refundByOracle";

    public static final String FUNC_RELEASE = "release";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SETVALIDATION = "setValidation";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPDATEORACLE = "updateOracle";

    public static final Event DEALCREATED_EVENT = new Event("DealCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DEPOSITED_EVENT = new Event("Deposited", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event REFUNDED_EVENT = new Event("Refunded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event RELEASED_EVENT = new Event("Released", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event VALIDATED_EVENT = new Event("Validated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bool>() {}));
    ;

    @Deprecated
    protected DatasetEscrow(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DatasetEscrow(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DatasetEscrow(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DatasetEscrow(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<DealCreatedEventResponse> getDealCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DEALCREATED_EVENT, transactionReceipt);
        ArrayList<DealCreatedEventResponse> responses = new ArrayList<DealCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DealCreatedEventResponse typedResponse = new DealCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dealId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.expiresAt = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DealCreatedEventResponse getDealCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DEALCREATED_EVENT, log);
        DealCreatedEventResponse typedResponse = new DealCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.dealId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.seller = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.expiresAt = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<DealCreatedEventResponse> dealCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDealCreatedEventFromLog(log));
    }

    public Flowable<DealCreatedEventResponse> dealCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEALCREATED_EVENT));
        return dealCreatedEventFlowable(filter);
    }

    public static List<DepositedEventResponse> getDepositedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DEPOSITED_EVENT, transactionReceipt);
        ArrayList<DepositedEventResponse> responses = new ArrayList<DepositedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositedEventResponse typedResponse = new DepositedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dealId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DepositedEventResponse getDepositedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DEPOSITED_EVENT, log);
        DepositedEventResponse typedResponse = new DepositedEventResponse();
        typedResponse.log = log;
        typedResponse.dealId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<DepositedEventResponse> depositedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDepositedEventFromLog(log));
    }

    public Flowable<DepositedEventResponse> depositedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSITED_EVENT));
        return depositedEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public static List<RefundedEventResponse> getRefundedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(REFUNDED_EVENT, transactionReceipt);
        ArrayList<RefundedEventResponse> responses = new ArrayList<RefundedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RefundedEventResponse typedResponse = new RefundedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dealId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RefundedEventResponse getRefundedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(REFUNDED_EVENT, log);
        RefundedEventResponse typedResponse = new RefundedEventResponse();
        typedResponse.log = log;
        typedResponse.dealId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<RefundedEventResponse> refundedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRefundedEventFromLog(log));
    }

    public Flowable<RefundedEventResponse> refundedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REFUNDED_EVENT));
        return refundedEventFlowable(filter);
    }

    public static List<ReleasedEventResponse> getReleasedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(RELEASED_EVENT, transactionReceipt);
        ArrayList<ReleasedEventResponse> responses = new ArrayList<ReleasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReleasedEventResponse typedResponse = new ReleasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dealId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ReleasedEventResponse getReleasedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(RELEASED_EVENT, log);
        ReleasedEventResponse typedResponse = new ReleasedEventResponse();
        typedResponse.log = log;
        typedResponse.dealId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ReleasedEventResponse> releasedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getReleasedEventFromLog(log));
    }

    public Flowable<ReleasedEventResponse> releasedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RELEASED_EVENT));
        return releasedEventFlowable(filter);
    }

    public static List<ValidatedEventResponse> getValidatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALIDATED_EVENT, transactionReceipt);
        ArrayList<ValidatedEventResponse> responses = new ArrayList<ValidatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ValidatedEventResponse typedResponse = new ValidatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dealId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.passed = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ValidatedEventResponse getValidatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VALIDATED_EVENT, log);
        ValidatedEventResponse typedResponse = new ValidatedEventResponse();
        typedResponse.log = log;
        typedResponse.dealId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.passed = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ValidatedEventResponse> validatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getValidatedEventFromLog(log));
    }

    public Flowable<ValidatedEventResponse> validatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALIDATED_EVENT));
        return validatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> createDeal(String seller, String token, BigInteger amount, BigInteger expiresAt) {
        final Function function = new Function(
                FUNC_CREATEDEAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, seller), 
                new org.web3j.abi.datatypes.Address(160, token), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(expiresAt)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> dealCounter() {
        final Function function = new Function(FUNC_DEALCOUNTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>> deals(BigInteger param0) {
        final Function function = new Function(FUNC_DEALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
        return new RemoteFunctionCall<Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<String, String, String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(BigInteger dealId) {
        final Function function = new Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(dealId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> oracle() {
        final Function function = new Function(FUNC_ORACLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> refund(BigInteger dealId) {
        final Function function = new Function(
                FUNC_REFUND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(dealId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> refundByOracle(BigInteger dealId) {
        final Function function = new Function(
                FUNC_REFUNDBYORACLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(dealId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> release(BigInteger dealId) {
        final Function function = new Function(
                FUNC_RELEASE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(dealId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setValidation(BigInteger dealId, Boolean passed) {
        final Function function = new Function(
                FUNC_SETVALIDATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(dealId), 
                new org.web3j.abi.datatypes.Bool(passed)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateOracle(String newOracle) {
        final Function function = new Function(
                FUNC_UPDATEORACLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOracle)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static DatasetEscrow load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DatasetEscrow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DatasetEscrow load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DatasetEscrow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DatasetEscrow load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DatasetEscrow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DatasetEscrow load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DatasetEscrow(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DatasetEscrow> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _oracle) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _oracle)));
        return deployRemoteCall(DatasetEscrow.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<DatasetEscrow> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _oracle) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _oracle)));
        return deployRemoteCall(DatasetEscrow.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DatasetEscrow> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _oracle) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _oracle)));
        return deployRemoteCall(DatasetEscrow.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DatasetEscrow> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _oracle) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _oracle)));
        return deployRemoteCall(DatasetEscrow.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class DealCreatedEventResponse extends BaseEventResponse {
        public BigInteger dealId;

        public String buyer;

        public String seller;

        public BigInteger amount;

        public BigInteger expiresAt;
    }

    public static class DepositedEventResponse extends BaseEventResponse {
        public BigInteger dealId;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class RefundedEventResponse extends BaseEventResponse {
        public BigInteger dealId;
    }

    public static class ReleasedEventResponse extends BaseEventResponse {
        public BigInteger dealId;
    }

    public static class ValidatedEventResponse extends BaseEventResponse {
        public BigInteger dealId;

        public Boolean passed;
    }
}
