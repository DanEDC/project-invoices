//package pl.coderstrust.db.impl.file;
//
//import pl.coderstrust.config.ObjectMapperProvider;
//import pl.coderstrust.db.Database;
//import pl.coderstrust.db.DatabaseTest;
//
//public class InFileTest extends DatabaseTest { //TODO - use AppParams
////TODO                      for selecting separate path for this test
//  @Override
//  public Database provideImplementation() {
//    String path = "src\\test\\resources\\_InFileTest";
//    return new InFile(new FileHelper(), new FileNameManager(),
//        new JsonConverter(new ObjectMapperProvider()), path);
//  }
//
//}