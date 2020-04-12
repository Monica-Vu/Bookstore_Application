package bookstore;

public class Book {
    int isbn;
    String title;
    String author;
    String genre;
    float price;
    int page_number;
    float percentage_sale;
    String publisher_name;
    int stock;

    public Book(int isbn, String title, String author, String genre, float price, int page_number,
            float percentage_sale, String publisher_name, int stock) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.page_number = page_number;
        this.percentage_sale = percentage_sale;
        this.publisher_name = publisher_name;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("%20d | %20s | %20s | %20s | %7.2f | %6d | %20.2f | %20s | %10d", isbn, title, author,
                genre, price, page_number, percentage_sale, publisher_name, stock);
    }
}