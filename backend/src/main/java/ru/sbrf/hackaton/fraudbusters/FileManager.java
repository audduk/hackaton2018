package ru.sbrf.hackaton.fraudbusters;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbrf.hackaton.fraudbusters.api.ClientContract;

import java.io.*;
import java.net.URI;
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
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    IOUtils.copy(inputStream, byteArrayOutputStream);
    byte[] arByte = byteArrayOutputStream.toByteArray();
    URI uri = FileManager.class.getClassLoader().getResource("").toURI();
    String hashedFileName = String.valueOf(System.currentTimeMillis());
    Path path = Paths.get(uri.resolve(hashedFileName));
    Files.write(path, arByte);
    clientContract.registerFile(hashedFileName.getBytes());
    return hashedFileName;
  }

  public void getFile(String uri, OutputStream out) throws Exception {
    String pass = generatePassword();
    Path path = Paths.get(FileManager.class.getClassLoader().getResource("").toURI().resolve(uri));
    File zip = zipFiles(path + uri + ".zip", path.toFile(), pass.toCharArray());
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
