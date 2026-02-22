import java.util.ArrayList;
import java.util.List;

public class DZ3 {
    public static void main(String[] args) {
        // --- Проверка Задания 1 (SRP) ---
        System.out.println("--- Задание 1: SRP (Заказ) ---");
        OrderData order = new OrderData("Ноутбук", 1, 1000.0);
        PriceCalculator calculator = new PriceCalculator();
        PaymentProcessor payment = new PaymentProcessor();
        EmailService email = new EmailService();

        System.out.println("Сумма заказа: " + calculator.calculateTotal(order));
        payment.processPayment("Credit Card");
        email.sendConfirmation("client@example.com");
        System.out.println();

        // --- Проверка Задания 2 (OCP) ---
        System.out.println("--- Задание 2: OCP (Зарплата) ---");
        List<Employee> employees = new ArrayList<>();
        employees.add(new PermanentEmployee("Иван", 1000));
        employees.add(new ContractEmployee("Петр", 1000));
        employees.add(new InternEmployee("Мария", 1000));

        for (Employee e : employees) {
            System.out.println(e.name + " зарплата: " + e.calculateSalary());
        }
        System.out.println();

        // --- Проверка Задания 3 (ISP) ---
        System.out.println("--- Задание 3: ISP (Принтеры) ---");
        IPrinter basicPrinter = new BasicPrinter();
        basicPrinter.print("Документ 1");


        IScanner scanner = new AllInOnePrinter();
        scanner.scan("Фото");
        System.out.println();

        // --- Проверка Задания 4 (DIP) ---
        System.out.println("--- Задание 4: DIP (Уведомления) ---");
        List<IMessageSender> senders = new ArrayList<>();
        senders.add(new EmailSender());
        senders.add(new SmsSender());

        NotificationService service = new NotificationService(senders);
        service.sendNotification("Привет, это тест!");
    }
}

// ЗАДАНИЕ 1: Single Responsibility Principle

class OrderData {
    String productName;
    int quantity;
    double price;

    public OrderData(String productName, int quantity, double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}


class PriceCalculator {
    public double calculateTotal(OrderData order) {
        return order.quantity * order.price * 0.9; // Скидка 10%
    }
}


class PaymentProcessor {
    public void processPayment(String paymentDetails) {
        System.out.println("Оплата проведена через: " + paymentDetails);
    }
}


class EmailService {
    public void sendConfirmation(String email) {
        System.out.println("Письмо отправлено на: " + email);
    }
}

// ЗАДАНИЕ 2: Open-Closed Principle


abstract class Employee {
    String name;
    double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public abstract double calculateSalary();
}


class PermanentEmployee extends Employee {
    public PermanentEmployee(String name, double salary) { super(name, salary); }

    @Override
    public double calculateSalary() {
        return baseSalary * 1.2; // +20%
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String name, double salary) { super(name, salary); }

    @Override
    public double calculateSalary() {
        return baseSalary * 1.1; // +10%
    }
}


class InternEmployee extends Employee {
    public InternEmployee(String name, double salary) { super(name, salary); }

    @Override
    public double calculateSalary() {
        return baseSalary * 0.8; // 80% от ставки
    }
}

// ЗАДАНИЕ 3: Interface Segregation Principle



interface IPrinter {
    void print(String content);
}

interface IScanner {
    void scan(String content);
}

interface IFax {
    void fax(String content);
}


class BasicPrinter implements IPrinter {
    public void print(String content) {
        System.out.println("Печать: " + content);
    }
}


class AllInOnePrinter implements IPrinter, IScanner, IFax {
    public void print(String content) { System.out.println("МФУ Печать: " + content); }
    public void scan(String content) { System.out.println("МФУ Сканирование: " + content); }
    public void fax(String content) { System.out.println("МФУ Факс: " + content); }
}

// ЗАДАНИЕ 4: Dependency Inversion Principle



interface IMessageSender {
    void sendMessage(String message);
}

class EmailSender implements IMessageSender {
    public void sendMessage(String message) {
        System.out.println("Email отправлен: " + message);
    }
}

class SmsSender implements IMessageSender {
    public void sendMessage(String message) {
        System.out.println("SMS отправлено: " + message);
    }
}


class NotificationService {
    private List<IMessageSender> senders;

    public NotificationService(List<IMessageSender> senders) {
        this.senders = senders;
    }

    public void sendNotification(String message) {
        for (IMessageSender sender : senders) {
            sender.sendMessage(message);
        }
    }
}