import java.util.*;

// 1. Интерфейс стратегии
interface IPaymentStrategy {
    void pay(double amount);
}

// 2. Конкретные стратегии

class CreditCardPayment implements IPaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Оплата " + amount + " с банковской карты: " + cardNumber);
    }
}

class PayPalPayment implements IPaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Оплата " + amount + " через PayPal: " + email);
    }
}

class CryptoPayment implements IPaymentStrategy {
    private String walletAddress;

    public CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Оплата " + amount + " криптовалютой: " + walletAddress);
    }
}

// 3. Контекст
class PaymentContext {
    private IPaymentStrategy strategy;

    public void setStrategy(IPaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executePayment(double amount) {
        if (strategy == null) {
            System.out.println("Стратегия оплаты не выбрана!");
            return;
        }
        strategy.pay(amount);
    }
}

// 1. Интерфейс наблюдателя
interface IObserver {
    void update(String currency, double rate);
}

// 2. Интерфейс субъекта
interface ISubject {
    void attach(IObserver observer);
    void detach(IObserver observer);
    void notifyObservers(String currency, double rate);
}

// 3. Субъект — Биржа валют
class CurrencyExchange implements ISubject {

    private List<IObserver> observers = new ArrayList<>();
    private Map<String, Double> rates = new HashMap<>();

    @Override
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String currency, double rate) {
        for (IObserver observer : observers) {
            observer.update(currency, rate);
        }
    }

    public void setRate(String currency, double rate) {
        rates.put(currency, rate);
        System.out.println("\nКурс обновлён: " + currency + " = " + rate);
        notifyObservers(currency, rate);
    }
}

// 4. Конкретные наблюдатели

class BankObserver implements IObserver {
    @Override
    public void update(String currency, double rate) {
        System.out.println("Банк получил обновление: " + currency + " = " + rate);
    }
}

class MobileAppObserver implements IObserver {
    @Override
    public void update(String currency, double rate) {
        System.out.println("Мобильное приложение отправило уведомление: " + currency + " = " + rate);
    }
}

class InvestorObserver implements IObserver {
    @Override
    public void update(String currency, double rate) {
        if (rate > 100) {
            System.out.println("Инвестор: Курс высокий! Рассмотреть продажу.");
        } else {
            System.out.println("Инвестор: Курс низкий. Возможно купить.");
        }
    }
}


public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите способ оплаты:");
        System.out.println("1 - Банковская карта");
        System.out.println("2 - PayPal");
        System.out.println("3 - Криптовалюта");

        int choice = scanner.nextInt();
        scanner.nextLine();

        PaymentContext context = new PaymentContext();

        switch (choice) {
            case 1:
                context.setStrategy(new CreditCardPayment("1111-2222-3333-4444"));
                break;
            case 2:
                context.setStrategy(new PayPalPayment("user@mail.com"));
                break;
            case 3:
                context.setStrategy(new CryptoPayment("0xABC123XYZ"));
                break;
            default:
                System.out.println("Неверный выбор!");
        }

        context.executePayment(500);
        

        CurrencyExchange exchange = new CurrencyExchange();

        IObserver bank = new BankObserver();
        IObserver app = new MobileAppObserver();
        IObserver investor = new InvestorObserver();

        exchange.attach(bank);
        exchange.attach(app);
        exchange.attach(investor);

        exchange.setRate("USD", 95.5);
        exchange.setRate("EUR", 102.3);

        // Удаляем одного наблюдателя
        exchange.detach(app);

        exchange.setRate("BTC", 120.0);
    }
}