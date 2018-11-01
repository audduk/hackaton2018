package ru.sbrf.hackaton.fraudbusters;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbrf.hackaton.fraudbusters.api.ClientContract;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

import static ru.sbrf.hackaton.fraudbusters.utils.PasswordGenerator.generatePassword;
import static ru.sbrf.hackaton.fraudbusters.utils.ZipUtils.zipFiles;

@Component
public class FileManager {

  private ClientContract clientContract;

  @Autowired
  public FileManager(ClientContract clientContract) {
    this.clientContract = clientContract;
  }

  public String sendFile(String fileName, InputStream inputStream) throws Exception {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    IOUtils.copy(inputStream, byteArrayOutputStream);
    byte[] arByte = byteArrayOutputStream.toByteArray();
    String resourcePath = FileManager.class.getClassLoader().getResource("").getPath();
    String hashedFileName = String.valueOf(System.currentTimeMillis());
    Path path = Paths.get(resourcePath, hashedFileName);
    Files.copy(inputStream, path);
    Files.write(path, arByte);
    clientContract.registerFile(hashedFileName.getBytes());
    return hashedFileName;
  }

  public void getFile(String uri, OutputStream out) throws Exception {
    String pass = generatePassword();
    String path = FileManager.class.getClassLoader().getResource("").getPath();
    File zip = zipFiles(path + uri + ".zip", Paths.get(path, uri).toFile(), pass.toCharArray());
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] arByte = Files.readAllBytes(zip.toPath());
    byte[] encodedhash = digest.digest(arByte);
    Files.write(Paths.get("tmp"), encodedhash);
    out.write(arByte);
    out.flush();
    out.close();
    clientContract.registerResponse(uri.getBytes(), encodedhash, pass);
  }

  public String getPass(String shaZip) throws Exception {
    byte[] encodedHash = Files.readAllBytes(Paths.get("tmp"));
    clientContract.approveReceiving(encodedHash);
    return clientContract.getResponse(encodedHash);
  }

  public Boolean validation(String sha, String shaZip, String pass) {
    return true;
  }
}
