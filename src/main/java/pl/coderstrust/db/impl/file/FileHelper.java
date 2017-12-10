package pl.coderstrust.db.impl.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

class FileHelper {
  
  void append(File file, String string) {
    try (PrintWriter outputStream = new PrintWriter(
        new FileOutputStream(file, true))) {
      outputStream.println(string);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
  }
  
  void overwrite(File file, String string) {
    try (PrintWriter printWriter = new PrintWriter(file)) {
      printWriter.print(string);
      printWriter.close();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
  }
  
  String readObject(File file) {
    String string = null;
    try {
      Scanner scanner = new Scanner(file);
      string = scanner.nextLine();
      System.out.println(string);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return string;
  }
}
