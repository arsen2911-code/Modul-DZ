import java.util.ArrayList;
import java.util.List;


class Book {
    private String title;
    private String author;
    private String isbn;
    private int count;

    public Book(String title, String author, String isbn, int count) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.count = count;
    }


    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    @Override
    public String toString() {
        return "'" + title + "' (" + author + ") [ISBN: " + isbn + "]";
    }
}


class Reader {
    private String name;
    private String readerId;

    public Reader(String name, String readerId) {
        this.name = name;
        this.readerId = readerId;
    }

    public String getName() { return name; }
    public String getReaderId() { return readerId; }

    @Override
    public String toString() {
        return name + " (ID: " + readerId + ")";
    }
}


class Library {
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();


    public void addBook(Book book) {
        books.add(book);
        System.out.println("Книга добавлена: " + book.getTitle());
    }

    public void removeBook(String isbn) {
        boolean removed = books.removeIf(b -> b.getIsbn().equals(isbn));
        if (removed) {
            System.out.println("Книга с ISBN " + isbn + " удалена из каталога.");
        } else {
            System.out.println("Книга с таким ISBN не найдена.");
        }
    }

    public void registerReader(Reader reader) {
        readers.add(reader);
        System.out.println("Новый читатель зарегистрирован: " + reader.getName());
    }

    public void removeReader(String readerId) {
        readers.removeIf(r -> r.getReaderId().equals(readerId));
        System.out.println("Читатель " + readerId + " удален.");
    }

    public void lendBook(String isbn, String readerId) {
        Book book = findBook(isbn);
        Reader reader = findReader(readerId);

        if (book == null) {
            System.out.println("Ошибка: Книга не найдена.");
            return;
        }
        if (reader == null) {
            System.out.println("Ошибка: Читатель не найден.");
            return;
        }

        if (book.getCount() > 0) {
            book.setCount(book.getCount() - 1);
            System.out.println("Выдано: " + book.getTitle() + " -> читателю " + reader.getName());
        } else {
            System.out.println("Извините, экземпляры книги '" + book.getTitle() + "' закончились.");
        }
    }

    public void returnBook(String isbn) {
        Book book = findBook(isbn);
        if (book != null) {
            book.setCount(book.getCount() + 1);
            System.out.println("Книга возвращена: " + book.getTitle() + ". Теперь в наличии: " + book.getCount());
        } else {
            System.out.println("Ошибка: Эта книга не из нашей библиотеки.");
        }
    }


    private Book findBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) return b;
        }
        return null;
    }

    private Reader findReader(String id) {
        for (Reader r : readers) {
            if (r.getReaderId().equals(id)) return r;
        }
        return null;
    }
}

public class DZ1 {
    public static void main(String[] args) {
        Library myLibrary = new Library();

        System.out.println("--- Начало работы ---");

        Book b1 = new Book("Java. Эффективное программирование", "Дж. Блох", "978-0134685991", 2);
        Book b2 = new Book("Чистый код", "Р. Мартин", "978-5446100355", 1);

        myLibrary.addBook(b1);
        myLibrary.addBook(b2);

        Reader r1 = new Reader("Иван Петров", "R001");
        Reader r2 = new Reader("Анна Сидорова", "R002");
        myLibrary.registerReader(r1);
        myLibrary.registerReader(r2);

        System.out.println("\n--- Процесс выдачи ---");

        myLibrary.lendBook("978-5446100355", "R001");

        myLibrary.lendBook("978-5446100355", "R002");

        myLibrary.lendBook("978-0134685991", "R002");

        System.out.println("\n--- Процесс возврата ---");

        myLibrary.returnBook("978-5446100355");

        myLibrary.lendBook("978-5446100355", "R002");

        System.out.println("\n--- Удаление ---");
        myLibrary.removeBook("978-0134685991");
    }

}