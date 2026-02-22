import java.io.*;
import java.util.*;

// MAIN

public class DZ5 {

    public static void main(String[] args) throws Exception {

        System.out.println("===== SINGLETON =====");
        testSingleton();

        System.out.println("\n===== BUILDER =====");
        testBuilder();

        System.out.println("\n===== PROTOTYPE =====");
        testPrototype();
    }

    //  SINGLETON TEST

    public static void testSingleton() throws Exception {

        Runnable task = () -> {
            ConfigurationManager config = ConfigurationManager.getInstance();
            System.out.println(Thread.currentThread().getName() +
                    " HashCode: " + config.hashCode());
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        ConfigurationManager config = ConfigurationManager.getInstance();
        config.setSetting("theme", "dark");
        config.saveToFile("config.txt");

        System.out.println("Theme: " + config.getSetting("theme"));
    }

    // BUILDER TEST

    public static void testBuilder() {

        ReportDirector director = new ReportDirector();

        IReportBuilder textBuilder = new TextReportBuilder();
        director.constructReport(textBuilder);
        System.out.println(textBuilder.getReport());

        IReportBuilder htmlBuilder = new HtmlReportBuilder();
        director.constructReport(htmlBuilder);
        System.out.println(htmlBuilder.getReport());
    }

    //  PROTOTYPE TEST

    public static void testPrototype() {

        Order template = new Order();
        template.addProduct(new Product("Laptop", 1000, 1));
        template.addDiscount(new Discount("New Year", 100));
        template.setDeliveryCost(20);
        template.setPaymentMethod("Card");

        Order cloned = template.clone();
        cloned.setPaymentMethod("Cash");

        System.out.println("TEMPLATE:");
        System.out.println(template);

        System.out.println("\nCLONED:");
        System.out.println(cloned);
    }
}

// SINGLETON

class ConfigurationManager {

    private static volatile ConfigurationManager instance;
    private Map<String, String> settings = new HashMap<>();

    private ConfigurationManager() {}

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    public String getSetting(String key) {
        if (!settings.containsKey(key))
            throw new RuntimeException("Setting not found");
        return settings.get(key);
    }

    public void saveToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (var entry : settings.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        }
    }
}

//  BUILDER

interface IReportBuilder {
    void setHeader(String header);
    void setContent(String content);
    void setFooter(String footer);
    Report getReport();
}

class Report {
    private String header;
    private String content;
    private String footer;

    public void setHeader(String header) { this.header = header; }
    public void setContent(String content) { this.content = content; }
    public void setFooter(String footer) { this.footer = footer; }

    public String toString() {
        return header + "\n" + content + "\n" + footer + "\n";
    }
}

class TextReportBuilder implements IReportBuilder {

    private Report report = new Report();

    public void setHeader(String header) {
        report.setHeader("TEXT HEADER: " + header);
    }

    public void setContent(String content) {
        report.setContent("TEXT CONTENT: " + content);
    }

    public void setFooter(String footer) {
        report.setFooter("TEXT FOOTER: " + footer);
    }

    public Report getReport() {
        return report;
    }
}

class HtmlReportBuilder implements IReportBuilder {

    private Report report = new Report();

    public void setHeader(String header) {
        report.setHeader("<h1>" + header + "</h1>");
    }

    public void setContent(String content) {
        report.setContent("<p>" + content + "</p>");
    }

    public void setFooter(String footer) {
        report.setFooter("<footer>" + footer + "</footer>");
    }

    public Report getReport() {
        return report;
    }
}

class ReportDirector {

    public void constructReport(IReportBuilder builder) {
        builder.setHeader("2026 Report");
        builder.setContent("Sales increased by 25%");
        builder.setFooter("End of report");
    }
}

//PROTOTYPE 

class Product implements Cloneable {

    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product clone() {
        return new Product(name, price, quantity);
    }

    public String toString() {
        return name + " x" + quantity + " = " + (price * quantity);
    }
}

class Discount implements Cloneable {

    private String description;
    private double amount;

    public Discount(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public Discount clone() {
        return new Discount(description, amount);
    }

    public String toString() {
        return description + ": -" + amount;
    }
}

class Order implements Cloneable {

    private List<Product> products = new ArrayList<>();
    private List<Discount> discounts = new ArrayList<>();
    private double deliveryCost;
    private String paymentMethod;

    public void addProduct(Product p) { products.add(p); }
    public void addDiscount(Discount d) { discounts.add(d); }
    public void setDeliveryCost(double cost) { deliveryCost = cost; }
    public void setPaymentMethod(String method) { paymentMethod = method; }

    public Order clone() {
        Order cloned = new Order();
        cloned.deliveryCost = this.deliveryCost;
        cloned.paymentMethod = this.paymentMethod;

        for (Product p : products)
            cloned.addProduct(p.clone());

        for (Discount d : discounts)
            cloned.addDiscount(d.clone());

        return cloned;
    }

    public String toString() {
        return "Products: " + products +
                "\nDiscounts: " + discounts +
                "\nDelivery: " + deliveryCost +
                "\nPayment: " + paymentMethod + "\n";
    }
}