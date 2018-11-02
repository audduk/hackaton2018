package ru.sbrf.hackaton.fraudbusters.contracts;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ClientContractImplTest {
  private static ClientContractImpl clientContractImpl;
  private static final String HOST = "http://174.138.14.158:7001/";
  private static final String WALLET_PATH = "/home/bot/blockchain/keystore/UTC_2018_11_01T12_30_17_502905348Z";
  private static final String CONTRACT = "0x57921e31ccb1a8c3d638766854e2c1a5e228a337";

  @BeforeClass
  public static void setUp() throws Exception {
    clientContractImpl = ClientContractImpl.create(HOST, WALLET_PATH, CONTRACT);
  }

  @Test
  public void ping2() throws Exception {
    String data = "123";
    byte[] byteHash = Files.readAllBytes(Paths.get("/media/bot/D8896AEA5D7051AF/develop/hackaton2018/tmp"));
    byte[] data1 = Arrays.copyOf(byteHash, 32);
    assertArrayEquals(byteHash, data1);
    byte[] bytes = clientContractImpl.ping2(data1);
    assertArrayEquals(bytes, data1);
  }

  @Test
  public void approveReceiving() throws Exception {
    String hash = "123";
    String enHash = "456";
    String password = "111";
    byte[] byteHash = Files.readAllBytes(Paths.get("/media/bot/D8896AEA5D7051AF/develop/hackaton2018/tmp"));
    clientContractImpl.registerFile(hash.getBytes());
    clientContractImpl.registerResponse(hash.getBytes(), byteHash, password);
    clientContractImpl.approveReceiving(byteHash);
    String pass = clientContractImpl.getResponse(byteHash);
    Assert.assertEquals(password, pass);
  }
}