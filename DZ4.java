import java.util.Scanner;

interface IVehicle {
    void drive();
    void refuel();
}

class Car implements IVehicle {
    private String make;
    private String model;
    private String fuelType;

    public Car(String make, String model, String fuelType) {
        this.make = make;
        this.model = model;
        this.fuelType = fuelType;
    }

    @Override
    public void drive() {
        System.out.println("Автомобиль " + make + " " + model + " едет по дороге.");
    }

    @Override
    public void refuel() {
        System.out.println("Заправка автомобиля топливом: " + fuelType);
    }
}

class Motorcycle implements IVehicle {
    private String type;
    private double engineVolume;

    public Motorcycle(String type, double engineVolume) {
        this.type = type;
        this.engineVolume = engineVolume;
    }

    @Override
    public void drive() {
        System.out.println("Мотоцикл (" + type + ") мчится с объемом двигателя " + engineVolume + "л.");
    }

    @Override
    public void refuel() {
        System.out.println("Заправка мотоцикла бензином.");
    }
}

class Truck implements IVehicle {
    private double capacity;
    private int axles;

    public Truck(double capacity, int axles) {
        this.capacity = capacity;
        this.axles = axles;
    }

    @Override
    public void drive() {
        System.out.println("Грузовик везет " + capacity + " тонн на " + axles + " осях.");
    }

    @Override
    public void refuel() {
        System.out.println("Заправка грузовика дизелем.");
    }
}

class Bus implements IVehicle {
    private int passengerCount;

    public Bus(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    @Override
    public void drive() {
        System.out.println("Автобус перевозит " + passengerCount + " пассажиров.");
    }

    @Override
    public void refuel() {
        System.out.println("Заправка автобуса газом/дизелем.");
    }
}

abstract class VehicleFactory {
    public abstract IVehicle createVehicle();
}

class CarFactory extends VehicleFactory {
    private String make;
    private String model;
    private String fuelType;

    public CarFactory(String make, String model, String fuelType) {
        this.make = make;
        this.model = model;
        this.fuelType = fuelType;
    }

    @Override
    public IVehicle createVehicle() {
        return new Car(make, model, fuelType);
    }
}

class MotorcycleFactory extends VehicleFactory {
    private String type;
    private double engineVolume;

    public MotorcycleFactory(String type, double engineVolume) {
        this.type = type;
        this.engineVolume = engineVolume;
    }

    @Override
    public IVehicle createVehicle() {
        return new Motorcycle(type, engineVolume);
    }
}

class TruckFactory extends VehicleFactory {
    private double capacity;
    private int axles;

    public TruckFactory(double capacity, int axles) {
        this.capacity = capacity;
        this.axles = axles;
    }

    @Override
    public IVehicle createVehicle() {
        return new Truck(capacity, axles);
    }
}

class BusFactory extends VehicleFactory {
    private int passengerCount;

    public BusFactory(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    @Override
    public IVehicle createVehicle() {
        return new Bus(passengerCount);
    }
}

public class DZ4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Транспортная система ---");
            System.out.println("1. Автомобиль");
            System.out.println("2. Мотоцикл");
            System.out.println("3. Грузовик");
            System.out.println("4. Автобус");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine();

            if (choice.equals("0")) break;

            VehicleFactory factory = null;

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Введите марку: ");
                        String make = scanner.nextLine();
                        System.out.print("Введите модель: ");
                        String model = scanner.nextLine();
                        System.out.print("Введите тип топлива: ");
                        String fuel = scanner.nextLine();
                        factory = new CarFactory(make, model, fuel);
                        break;

                    case "2":
                        System.out.print("Введите тип: ");
                        String motoType = scanner.nextLine();
                        System.out.print("Введите объем двигателя: ");
                        double volume = Double.parseDouble(scanner.nextLine());
                        factory = new MotorcycleFactory(motoType, volume);
                        break;

                    case "3":
                        System.out.print("Введите грузоподъемность: ");
                        double capacity = Double.parseDouble(scanner.nextLine());
                        System.out.print("Введите количество осей: ");
                        int axles = Integer.parseInt(scanner.nextLine());
                        factory = new TruckFactory(capacity, axles);
                        break;

                    case "4":
                        System.out.print("Введите количество пассажиров: ");
                        int passengers = Integer.parseInt(scanner.nextLine());
                        factory = new BusFactory(passengers);
                        break;

                    default:
                        System.out.println("Неверный выбор.");
                        continue;
                }

                if (factory != null) {
                    IVehicle vehicle = factory.createVehicle();
                    vehicle.drive();
                    vehicle.refuel();
                }

            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода чисел.");
            }
        }
        scanner.close();
    }
}