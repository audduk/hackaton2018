package ru.sbrf.hackaton.fraudbusters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import ru.sbrf.hackaton.fraudbusters.api.ClientContract;
import ru.sbrf.hackaton.fraudbusters.contracts.ClientContractImpl;

@org.springframework.context.annotation.Configuration
public class Configuration {

  @Bean
  public ClientContract clientContract(
      @Value("${ethereum.host}") String host,
      @Value("${ethereum.wallet_path}") String walletPath,
      @Value("${ethereum.contract}") String contract,
      @Value("${ethereum.repository}") String repository) throws Exception {
    ClientContract clientContract = ClientContractImpl.create(host, walletPath, contract);
    clientContract.registerRepo(repository.getBytes());
    return clientContract;
  }
}
