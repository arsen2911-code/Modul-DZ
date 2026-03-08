import java.util.*;

interface ICommand {
    void execute();
    void undo();
}

class Light {
    public void on() {
        System.out.println("Свет включен");
    }

    public void off() {
        System.out.println("Свет выключен");
    }
}

class Door {
    public void open() {
        System.out.println("Дверь открыта");
    }

    public void close() {
        System.out.println("Дверь закрыта");
    }
}

class Thermostat {
    private int temperature = 22;

    public void increase() {
        temperature++;
        System.out.println("Температура увеличена до " + temperature);
    }

    public void decrease() {
        temperature--;
        System.out.println("Температура уменьшена до " + temperature);
    }
}

class TV {
    public void on() {
        System.out.println("Телевизор включен");
    }

    public void off() {
        System.out.println("Телевизор выключен");
    }
}

// ---------- COMMANDS ----------

class LightOnCommand implements ICommand {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }

    public void undo() {
        light.off();
    }
}

class LightOffCommand implements ICommand {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.off();
    }

    public void undo() {
        light.on();
    }
}

class DoorOpenCommand implements ICommand {
    private Door door;

    public DoorOpenCommand(Door door) {
        this.door = door;
    }

    public void execute() {
        door.open();
    }

    public void undo() {
        door.close();
    }
}

class DoorCloseCommand implements ICommand {
    private Door door;

    public DoorCloseCommand(Door door) {
        this.door = door;
    }

    public void execute() {
        door.close();
    }

    public void undo() {
        door.open();
    }
}

class TempIncreaseCommand implements ICommand {
    private Thermostat thermostat;

    public TempIncreaseCommand(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    public void execute() {
        thermostat.increase();
    }

    public void undo() {
        thermostat.decrease();
    }
}

class TempDecreaseCommand implements ICommand {
    private Thermostat thermostat;

    public TempDecreaseCommand(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    public void execute() {
        thermostat.decrease();
    }

    public void undo() {
        thermostat.increase();
    }
}

class TVOnCommand implements ICommand {
    private TV tv;

    public TVOnCommand(TV tv) {
        this.tv = tv;
    }

    public void execute() {
        tv.on();
    }

    public void undo() {
        tv.off();
    }
}
class SmartHomeController {

    private Stack<ICommand> history = new Stack<>();

    public void pressButton(ICommand command) {
        command.execute();
        history.push(command);
    }

    public void undo() {

        if (history.isEmpty()) {
            System.out.println("Нет команд для отмены");
            return;
        }

        ICommand command = history.pop();
        command.undo();
    }
}



abstract class Beverage {

    // TEMPLATE METHOD
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();

        if (customerWantsCondiments()) {
            addCondiments();
        }
    }

    void boilWater() {
        System.out.println("Кипятим воду");
    }

    void pourInCup() {
        System.out.println("Наливаем в чашку");
    }

    abstract void brew();
    abstract void addCondiments();

    // HOOK
    boolean customerWantsCondiments() {
        return true;
    }
}

class Tea extends Beverage {

    void brew() {
        System.out.println("Завариваем чай");
    }

    void addCondiments() {
        System.out.println("Добавляем лимон");
    }
}

class Coffee extends Beverage {

    void brew() {
        System.out.println("Завариваем кофе");
    }

    void addCondiments() {
        System.out.println("Добавляем молоко и сахар");
    }

    boolean customerWantsCondiments() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Добавить молоко и сахар? (yes/no)");

        String answer = scanner.nextLine();

        return answer.toLowerCase().startsWith("y");
    }
}

class HotChocolate extends Beverage {

    void brew() {
        System.out.println("Готовим горячий шоколад");
    }

    void addCondiments() {
        System.out.println("Добавляем маршмеллоу");
    }
}

interface IMediator {
    void sendMessage(String message, User sender);
    void addUser(User user);
    void privateMessage(String message, User sender, String receiverName);
}

class ChatRoom implements IMediator {

    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        System.out.println(user.getName() + " присоединился к чату");
    }

    public void sendMessage(String message, User sender) {

        for (User user : users) {

            if (user != sender) {
                user.receive(message, sender.getName());
            }
        }
    }

    public void privateMessage(String message, User sender, String receiverName) {

        for (User user : users) {

            if (user.getName().equals(receiverName)) {
                user.receive("(Личное) " + message, sender.getName());
                return;
            }
        }

        System.out.println("Пользователь не найден");
    }
}

class User {

    private String name;
    private IMediator mediator;

    public User(String name, IMediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public String getName() {
        return name;
    }

    public void send(String message) {
        mediator.sendMessage(message, this);
    }

    public void sendPrivate(String message, String receiver) {
        mediator.privateMessage(message, this, receiver);
    }

    public void receive(String message, String sender) {
        System.out.println(sender + " -> " + name + ": " + message);
    }
}



public class DZ7 {

    public static void main(String[] args) {


        System.out.println("===== COMMAND PATTERN =====");

        Light light = new Light();
        Door door = new Door();
        Thermostat thermostat = new Thermostat();
        TV tv = new TV();

        SmartHomeController controller = new SmartHomeController();

        controller.pressButton(new LightOnCommand(light));
        controller.pressButton(new DoorOpenCommand(door));
        controller.pressButton(new TempIncreaseCommand(thermostat));
        controller.pressButton(new TVOnCommand(tv));

        controller.undo();
        controller.undo();


        System.out.println("\n===== TEMPLATE METHOD =====");

        Beverage tea = new Tea();
        tea.prepareRecipe();

        System.out.println();

        Beverage coffee = new Coffee();
        coffee.prepareRecipe();

        System.out.println();

        Beverage chocolate = new HotChocolate();
        chocolate.prepareRecipe();

        System.out.println("\n===== MEDIATOR =====");

        ChatRoom chat = new ChatRoom();

        User user1 = new User("Alice", chat);
        User user2 = new User("Bob", chat);
        User user3 = new User("Charlie", chat);

        chat.addUser(user1);
        chat.addUser(user2);
        chat.addUser(user3);

        user1.send("Всем привет!");
        user2.send("Привет!");

        user3.sendPrivate("Это личное сообщение", "Alice");
    }
}