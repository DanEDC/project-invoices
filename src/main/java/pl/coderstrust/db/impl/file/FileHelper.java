package pl.coderstrust.db.impl.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
class FileHelper {
  
  private static Logger logger = LoggerFactory.getLogger(FileHelper.class);
  
  
  public FileHelper() {
    logger.info("File helper initiated");
  }
  
  void createNewDir(String path) {
    File dir = new File(path);
    if (!dir.mkdir() && !dir.exists()) {
        logger.warn("Directory " + dir + " failed to create");
    }
  }
  
  boolean appendFile(File file, String string) {
    try (PrintWriter outputStream = new PrintWriter(
        new FileOutputStream(file, true))) {
      outputStream.println(string);
      return true;
    } catch (FileNotFoundException e1) {
      logger.error("Not able to append " + file + " with " + string, e1);
      return false;
    }
  }
  
  void appendFile(File file, List<String> stringList) {
    try (PrintWriter outputStream = new PrintWriter(
        new FileOutputStream(file, true))) {
      for (String string:stringList) {
        outputStream.println(string);
      }
    } catch (FileNotFoundException e1) {
      logger.error("Not able to append file " + file + " with " + stringList, e1);
    }
  }
  
  void overwriteFile(File file, String string) {
    try (PrintWriter outputStream = new PrintWriter(
        new FileOutputStream(file, false))) {
      outputStream.println(string);
    } catch (FileNotFoundException e1) {
      logger.error("Not able to overwrite file" + file + " with " + string, e1);
    }
  }
  
  boolean clearFile(File file) {
    try {
      PrintWriter printWriter = new PrintWriter(file.getPath());
      printWriter.close();
      logger.debug("File " + file + " flushed");
      return true;
    } catch (FileNotFoundException e) {
      logger.error("Not able to clear: " + file, e);
      return false;
    }
  }
  
  boolean deleteFile(File file) {
    try {
      if (file.delete()) {
        return true;
      } else {
        logger.warn("Not able to delete " + file);
        return false;
      }
    } catch (Exception e) {
      logger.error("Not able to locate or delete " + file, e);
      e.printStackTrace();
      return false;
    }
  }
  
  boolean deleteDirectoryIfEmpty(File file) {
    if (deleteFile(file)) {
      logger.debug("Directory " + file + " deleted");
      return true;
    } else {
      logger.warn("Directory " + file + " failed to be deleted");
      return false;
    }
  }

  
  List<String> readAsStringList(File file) {
    List<String> stringList = new ArrayList<>();
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        stringList.add(scanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      logger.error("Not able to locate file or read form: " + file, e);
    }
    return stringList;
  }
  
  /**
   * FILE BROWSING METHODS
   * This is implementation just for fun
   * - it's not efficient at all!
   *
   *
   * listSubDirContent - This method searches all files from sub-dirs.
   *
   * GivenDir (path parameter)
   * |----subDir1
   * |      FileInSubDir1   <-will find
   * |
   * |----subDir2
   * |      FileInSubDir2   <-will find
   * |
   * |----subDir3
   * |      FileInSubDir3   <-will find
   * |
   * |  FileInGivenDir      <-will IGNORE!
   */
  public List<File> listSubDirContent(File dir) {
    List<File> fileList = new ArrayList<>();
    
    File[] subDirs = listDirContent(dir, 1);
  
    if (subDirs != null) {
      for (File subDir : subDirs) {
        File[] tmpFiles = listDirContent(subDir, 2);
        fileList.addAll(Arrays.asList(tmpFiles));
      }
    }
    return fileList;
  }
  
  public File[] listDirContent(File givenDirectory, int all0Dir1File2) {
    switch (all0Dir1File2) {
      case 0:
        return givenDirectory.listFiles();
      case 1:
        return givenDirectory.listFiles(File::isDirectory);
      case 2:
        return givenDirectory.listFiles(File::isFile);
      default:
        return givenDirectory.listFiles();
    }
  }
}
