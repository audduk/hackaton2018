package ru.sbrf.hackaton.fraudbusters.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;

public class ZipUtils {/**
 *
 * @param zipFileName
 * @param file
 * @param password
 *            Password for zip file
 * @return true if files are zipped successfully, else false.
 */
  public static File zipFiles(String zipFileName, File file,
                              char[] password) throws ZipException {
   /* Instantiate ZipFile */
    ZipFile zipFile = new ZipFile(zipFileName);

   /* Instantiate ZipParameters */
    ZipParameters zipParameters = new ZipParameters();

   /* Set compression type */
    zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

   /* Set Compression level */
    zipParameters
        .setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

   /* Set encryptFiles to true */
    zipParameters.setEncryptFiles(true);

   /* Select the Encryption method to encrypt fies */
    zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);

   /* Set AES key length */
    zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);

   /* Set the password for zip file */
    zipParameters.setPassword(password);

    zipFile.addFile(file, zipParameters);

    return zipFile.getFile();
  }
}
