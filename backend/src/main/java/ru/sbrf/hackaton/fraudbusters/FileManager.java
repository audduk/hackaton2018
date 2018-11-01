package ru.sbrf.hackaton.fraudbusters;

import net.lingala.zip4j.exception.ZipException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static ru.sbrf.hackaton.fraudbusters.utils.PasswordGenerator.generatePassword;
import static ru.sbrf.hackaton.fraudbusters.utils.ZipUtils.zipFiles;

@Component
public class FileManager {
  public String sendFile(String fileName, InputStream inputStream){
    return null;
  }

  public void getFile(String uri, OutputStream out) throws IOException, NoSuchAlgorithmException, ZipException {
    String pass = generatePassword();
    String path = FileManager.class.getClassLoader().getResource("").getPath();
    File zip = zipFiles(path + "archive.zip", new File(path + "1.txt"), pass.toCharArray());
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] arByte = Files.readAllBytes(zip.toPath());
    byte[] encodedhash = digest.digest(arByte);
    out.write(arByte);
    out.flush();
    out.close();
  }

  public String getPass(String shaZip) {
    return null;
  }

  public Boolean validation(String sha, String shaZip, String pass) {
    return false;
  }
}
