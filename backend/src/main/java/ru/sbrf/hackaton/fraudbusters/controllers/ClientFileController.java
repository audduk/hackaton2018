package ru.sbrf.hackaton.fraudbusters.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/file")
public class ClientFileController {

  @PostMapping("/send")
  public String sendFile(@RequestParam("file") MultipartFile file){
    return "uri file";
  }

  @GetMapping(value = "/get", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
  public String getFile(@PathVariable("uri") String uri){
    return "crypt file";
  }

  @GetMapping(value = "/pass", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
  public String getPass(@PathVariable("sha") String sha){
    return "pass";
  }

  @GetMapping(value = "/validation", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
  public Boolean get(@PathVariable("sha") String sha, @PathVariable("shaZip") String shaZip, @PathVariable("pass") String pass){
    return false;
  }
}
