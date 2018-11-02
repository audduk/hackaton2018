package ru.sbrf.hackaton.fraudbusters.contracts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.StaticGasProvider;
import ru.sbrf.hackaton.fraudbusters.api.ClientContract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class ClientContractImpl implements ClientContract {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeContract.class);
    private static final String PASS = "ksdjf5hgjks2dghsjhefj";
    private static final BigInteger DEFAULT_GAS_PRICE = new BigInteger("21");
    private static final BigInteger DEFAULT_GAS_LIMIT = new BigInteger("2100000");
    private ExchangeContract contract;

    public static ClientContractImpl create(String host, String walletPath, String contractAddress) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(host));
        Credentials credentials;
        try {
            credentials = WalletUtils.loadCredentials(PASS, walletPath);
            ExchangeContract contract = new ExchangeContract(
                    contractAddress,
                    web3j,
                    credentials,
                    new StaticGasProvider(
                            DEFAULT_GAS_PRICE,
                            DEFAULT_GAS_LIMIT));
            ClientContractImpl clientContractImpl = new ClientContractImpl();
            clientContractImpl.setContract(contract);
            return clientContractImpl;
        } catch (IOException | CipherException e) {
            String errorMessage = "Error creating contract object";
            LOGGER.error(errorMessage, e);
            throw new Exception(errorMessage);
        }
    }

    @Override
    public BigInteger ping(BigInteger val1, BigInteger val2) throws Exception {
        return contract.ping(val1, val2).send();
    }

    @Override
    public byte[] ping2(byte[] data) throws Exception {
        return contract.ping2(data).send();
    }

    @Override
    public void registerResponse(byte[] hash, byte[] encryptedHash, String password) throws Exception {
        contract.registerResponse(
                getFixedByteArray(hash),
                getFixedByteArray(encryptedHash),
                getFixedByteArray(password)).send();
    }

    @Override
    public TransactionReceipt approveReceiving(byte[] encryptedHash) throws Exception {
        return contract.approveReceiving(getFixedByteArray(encryptedHash)).send();
    }

    @Override
    public String findRepository(byte[] hash) throws Exception {
        byte[] data = contract.findRepository(getFixedByteArray(hash)).send();
        return new String(trim(data));
    }

    @Override
    public void registerRepo(byte[] repository) throws Exception {
        contract.registerRepo(getFixedByteArray(repository)).send();
    }

    @Override
    public String getResponse(byte[] encryptedHash) throws Exception {
        Tuple3<byte[], String, byte[]> response = contract.getResponse(getFixedByteArray(encryptedHash)).send();
        return new String(response.getValue3());
    }

    @Override
    public void registerFile(byte[] hash) throws Exception {
        contract.registerFile(getFixedByteArray(hash)).send();
    }

    public void setContract(ExchangeContract contract) {
        this.contract = contract;
    }

    private byte[] getFixedByteArray(String data) {
        return Arrays.copyOf(data.getBytes(), 32);
    }

    private byte[] getFixedByteArray(byte[] data) {
        return Arrays.copyOf(data, 32);
    }

    private byte[] trim(byte[] data) {
        for(int i = 0; i < data.length; ++i) {
            if (data[i] == 0) {
                byte[] result = new byte[32];
                System.arraycopy(data, 0, result, 0, i);
                return result;
            }
        }
        return data;
    }
}
