package ru.sbrf.hackaton.fraudbusters.api;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public interface ClientContract {
    BigInteger ping(BigInteger val1, BigInteger val2) throws Exception;

  byte[] ping2(byte[] data) throws Exception;

  void registerResponse(byte[] hash, byte[] encryptedHash, String password) throws Exception;

    TransactionReceipt approveReceiving(byte[] encryptedHash) throws Exception;

    String getResponse(byte[] encryptedHash) throws Exception;

    void registerFile(byte[] hash) throws Exception;
}
