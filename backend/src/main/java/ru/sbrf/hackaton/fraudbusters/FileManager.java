package ru.sbrf.hackaton.fraudbusters;

import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class FileManager {
  public String sendFile(String fileName, InputStream inputStream){
    return null;
  }

  public String getFile(String uri) {
    return null;
  }

  public String getPass(String shaZip) {
    return null;
  }

  public Boolean validation(String sha, String shaZip, String pass) {
    return false;
  }
}
