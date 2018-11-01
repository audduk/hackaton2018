package ru.sbrf.hackaton.fraudbusters;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class FileManager {
  public String sendFile(String fileName, InputStream inputStream){
    return null;
  }

  public String getFile(String uri, OutputStream out) throws IOException, NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] encodedhash = digest.digest("ssdadc".getBytes(StandardCharsets.UTF_8));
    return encodedhash.toString();
  }

  public String getPass(String shaZip) {
    return null;
  }

  public Boolean validation(String sha, String shaZip, String pass) {
    return false;
  }
}
