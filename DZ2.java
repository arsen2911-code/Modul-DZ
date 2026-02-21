import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class DZ2 {

    public static void main(String[] args) {
        System.out.println("Demonstration of Clean Code Principles");

        KISS_Examples kiss = new KISS_Examples();
        System.out.println("Division: " + kiss.divide(10, 0));
    }
}
// 1. Принцип DRY

class Logger {
    public void log(String level, String message) {
        System.out.println(level.toUpperCase() + ": " + message);
    }
}

class AppConfig {
    public static final String CONNECTION_STRING = "Server=myServer;Database=myDb;User Id=myUser;Password=myPass;";
}

class DatabaseService {
    public void connect() {
        String conn = AppConfig.CONNECTION_STRING;
    }
}

class LoggingService {
    public void saveLogToDb(String message) {
        String conn = AppConfig.CONNECTION_STRING;
    }
}
// 2. Принцип KISS

class KISS_Examples {


    public void processNumbers(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return;
        }

        for (int number : numbers) {
            if (number > 0) {
                System.out.println(number);
            }
        }
    }

    public void printPositiveNumbers(int[] numbers) {

        for (int number : numbers) {
            if (number > 0) {
                System.out.println(number);
            }
        }
    }
    public int divide(int a, int b) {
        if (b == 0) {
            return 0;
        }
        return a / b;
    }
}
// 3. Принцип YAGNI

class User {
    private String name;
    private String email;
    private String address;


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void saveToDatabase() {

    }
}

class FileReader {
    public String readFile(String filePath) {
        return "file content";
    }
}
class ReportGenerator {
    public void generatePdfReport() {
        System.out.println("Generating PDF...");
    }
}