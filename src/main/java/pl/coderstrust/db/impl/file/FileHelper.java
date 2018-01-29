package pl.coderstrust.db.impl.file;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Service
class FileHelper {
  
  private Set<File> filesSet;
  private Set<File> dirSet;
  
  public FileHelper() {
    this.filesSet = new HashSet<>();
    this.dirSet = new HashSet<>();
  }
  
  void createNewDir(String path) {
    File dir = new File(path);
    if (dirSet.contains(dir)) {
      System.out.println("Info: directory " + dir + " already exists");
    } else {
      if (dir.mkdir()) {
        System.out.println("Info: directory " + dir + " created");
        dirSet.add(dir);
      } else {
        System.out.println("Error: directory " + dir + " failed to create");
      }
    }
  
  }
  
  void appendFile(File file, String string) {
    try (PrintWriter outputStream = new PrintWriter(
        new FileOutputStream(file, true))) {
      outputStream.println(string);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
  }
  
  void appendFile(File file, List<String> stringList) {
    try (PrintWriter outputStream = new PrintWriter(
        new FileOutputStream(file, true))) {
      for (String string:stringList) {
        outputStream.println(string);
      }
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
  }
  
  
  void overwriteFile(File file, String string) {
    try (PrintWriter outputStream = new PrintWriter(
        new FileOutputStream(file, false))) {
      outputStream.println(string);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
  }
  
  boolean clearFile(File file) {
    try {
      PrintWriter printWriter = new PrintWriter(file.getPath());
      printWriter.close();
      System.out.println("Info: file  " + file + " emptied");
      return true;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }
  
  boolean deleteFile(File file) {
    try {
      if (file.delete()) {
        System.out.println(file.getName() + " deleted!");
        if (file.isDirectory()) {
          dirSet.remove(file);
        } else if (file.isFile()) {
          filesSet.remove(file);
        }
        return true;
      } else {
        System.out.println("Delete operation failed.");
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
  boolean deleteParentDirectoryIfEmpty(File file) {
    File parentDir = new File(file.getParent());
    return deleteFile(parentDir);
  }
  
  boolean deleteDirectoryIfEmpty(File file) {
    return deleteFile(file);
  }
  
  @SuppressWarnings("unused ")
  List<byte[]> readAsListOfByteArray(File file) {
    List<byte[]> listOfBytes = new ArrayList<>();
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        listOfBytes.add(scanner.nextLine().getBytes());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return listOfBytes;
  }
  
  List<String> readAsStringList(File file) {
    List<String> stringList = new ArrayList<>();
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        stringList.add(scanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
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
  
  public boolean isEmpty(File dir) {
    int numberOfDirs = listDirContent(dir, 1).length;
    int numberOfFiles = listDirContent(dir, 2).length;
    return numberOfDirs + numberOfFiles == 0;
  }
}
