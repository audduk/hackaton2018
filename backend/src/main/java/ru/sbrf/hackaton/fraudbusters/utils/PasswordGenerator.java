package ru.sbrf.hackaton.fraudbusters.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGenerator {
  public static String generatePassword(){
    return RandomStringUtils.random(32, true, true);
  }
}
