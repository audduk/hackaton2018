package ru.sbrf.hackaton.fraudbusters.utils;

import java.time.Instant;

public class PasswordGenerator {
  public static String generatePassword(){
    Integer i = Instant.now().getNano() % 319;
    return i.toString();
  }
}
