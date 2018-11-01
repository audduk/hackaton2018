package ru.sbrf.hackaton.fraudbusters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sbrf.hackaton.fraudbusters.FileManager;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/api/file")
public class ClientFileController {

  private FileManager fileManager;

  @Autowired
  public ClientFileController(FileManager fileManager) {
    this.fileManager = fileManager;
  }

  @PostMapping("/send")
  public String sendFile(@RequestParam("file") MultipartFile file) throws IOException {
    String fileName = file.getOriginalFilename();
    fileManager.sendFile(fileName, file.getInputStream());
    return "uri file";
  }

  @GetMapping(value = "/get/{uri}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
  public String getFile(@PathVariable("uri") String uri, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
    return fileManager.getFile(uri, response.getOutputStream());
  }

  @GetMapping(value = "/pass", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
  public String getPass(@PathVariable("shaZip") String shaZip){
    fileManager.getPass(shaZip);
    return "pass";
  }

  @GetMapping(value = "/validation", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
  public Boolean validation(@PathVariable("sha") String sha, @PathVariable("shaZip") String shaZip, @PathVariable("pass") String pass){
    fileManager.validation(sha, shaZip, pass);
    return false;
  }
}
