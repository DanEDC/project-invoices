package pl.coderstrust.db.impl.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileHelper {
  
  
  void appendEndLine(File file, String string) {
    try (PrintWriter outputStream = new PrintWriter(
        new FileOutputStream(file, true))) {
      outputStream.println(string);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
  }
  
  void overwriteFirstLine(File file, String string) {
    try (PrintWriter printWriter = new PrintWriter(file)) {
      printWriter.print(string);
      printWriter.close();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
  }
  
  List<byte[]> readAsListOfByteArray(File file) {
    List<byte[]> listOfBytes = new ArrayList<>();
    try {
      Scanner scanner = new Scanner(file);
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
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        stringList.add(scanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return stringList;
  }
  
  
}
