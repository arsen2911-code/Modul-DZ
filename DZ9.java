import java.util.*;

class TV {
    public void on() {
        System.out.println("TV включен");
    }

    public void off() {
        System.out.println("TV выключен");
    }

    public void setChannel(String channel) {
        System.out.println("TV переключен на канал: " + channel);
    }

    public void setInput(String input) {
        System.out.println("TV вход установлен на: " + input);
    }
}

class AudioSystem {
    public void on() {
        System.out.println("Аудиосистема включена");
    }

    public void off() {
        System.out.println("Аудиосистема выключена");
    }

    public void setVolume(int level) {
        System.out.println("Громкость установлена на: " + level);
    }
}

class DVDPlayer {
    public void play() {
        System.out.println("DVD воспроизведение начато");
    }

    public void pause() {
        System.out.println("DVD на паузе");
    }

    public void stop() {
        System.out.println("DVD остановлен");
    }
}

class GameConsole {
    public void on() {
        System.out.println("Игровая консоль включена");
    }

    public void startGame(String game) {
        System.out.println("Запуск игры: " + game);
    }
}

class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audio;
    private DVDPlayer dvd;
    private GameConsole console;

    public HomeTheaterFacade(TV tv, AudioSystem audio, DVDPlayer dvd, GameConsole console) {
        this.tv = tv;
        this.audio = audio;
        this.dvd = dvd;
        this.console = console;
    }

    public void watchMovie() {
        System.out.println("\n=== Режим: Просмотр фильма ===");
        tv.on();
        tv.setInput("DVD");
        audio.on();
        audio.setVolume(15);
        dvd.play();
    }

    public void playGame(String game) {
        System.out.println("\n=== Режим: Игра ===");
        tv.on();
        tv.setInput("Console");
        console.on();
        console.startGame(game);
    }

    public void listenMusic() {
        System.out.println("\n=== Режим: Музыка ===");
        tv.on();
        tv.setInput("Audio");
        audio.on();
        audio.setVolume(20);
    }

    public void shutdown() {
        System.out.println("\n=== Выключение системы ===");
        dvd.stop();
        audio.off();
        tv.off();
    }

    public void setVolume(int level) {
        audio.setVolume(level);
    }
}


abstract class FileSystemComponent {
    protected String name;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public abstract void display(String indent);
    public abstract int getSize();
}

class FileComponent extends FileSystemComponent {
    private int size;

    public FileComponent(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- Файл: " + name + " (" + size + " KB)");
    }

    @Override
    public int getSize() {
        return size;
    }
}

class Directory extends FileSystemComponent {
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    public void add(FileSystemComponent component) {
        if (!components.contains(component)) {
            components.add(component);
        } else {
            System.out.println("Компонент уже существует: " + component.name);
        }
    }

    public void remove(FileSystemComponent component) {
        if (components.contains(component)) {
            components.remove(component);
        } else {
            System.out.println("Компонент не найден: " + component.name);
        }
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ Папка: " + name);

        for (FileSystemComponent component : components) {
            component.display(indent + "   ");
        }
    }

    @Override
    public int getSize() {
        int total = 0;
        for (FileSystemComponent component : components) {
            total += component.getSize();
        }
        return total;
    }
}

public class Main {
    public static void main(String[] args) {

        TV tv = new TV();
        AudioSystem audio = new AudioSystem();
        DVDPlayer dvd = new DVDPlayer();
        GameConsole console = new GameConsole();

        HomeTheaterFacade homeTheater =
                new HomeTheaterFacade(tv, audio, dvd, console);

        homeTheater.watchMovie();
        homeTheater.playGame("FIFA 25");
        homeTheater.listenMusic();
        homeTheater.setVolume(10);
        homeTheater.shutdown();

        System.out.println("\n==========================");
        System.out.println("ФАЙЛОВАЯ СИСТЕМА");
        System.out.println("==========================");

        Directory root = new Directory("Root");

        Directory documents = new Directory("Documents");
        Directory images = new Directory("Images");

        FileComponent file1 = new FileComponent("resume.pdf", 120);
        FileComponent file2 = new FileComponent("photo.jpg", 200);
        FileComponent file3 = new FileComponent("notes.txt", 50);

        documents.add(file1);
        documents.add(file3);

        images.add(file2);

        root.add(documents);
        root.add(images);

        root.display("");

        System.out.println("\nОбщий размер: " + root.getSize() + " KB");
    }
}