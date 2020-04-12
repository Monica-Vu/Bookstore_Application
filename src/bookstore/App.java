package bookstore;

import java.sql.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class App {
    Scanner keyboard = new Scanner(System.in);

    public static List<Book> getBooks(String query) {
        ArrayList<Book> answer = new ArrayList<Book>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project", "monicavu",
                "jason"); Statement statement = connection.createStatement();) {

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) { // Loop through the result set, and create Book object and add it to the list
                Book newBook = new Book(resultSet.getInt("isbn"), resultSet.getString("title"),
                        resultSet.getString("author"), resultSet.getString("genre"), resultSet.getFloat("price"),
                        resultSet.getInt("page_number"), resultSet.getFloat("percentage_sale"),
                        resultSet.getString("publisher_name"), resultSet.getInt("stock"));
                answer.add(newBook);
            }
        } catch (SQLException sqle) {
            System.out.println("Exception_getBooks: " + sqle);
        }
        return answer;
    }

    public void bookStoreMainMenu() {
        System.out.println("Please select one of the options: ");
        System.out.println("View Books (V) ");
        System.out.println("Search Books (S) ");
        System.out.println("Add Book to Cart (A) ");
        System.out.println("Cart (C) -> Buy Books Here ");
        System.out.println("Orders (O)");
        System.out.println("PLEASE ENTER YOUR CHOICE: ");

        char option = keyboard.nextLine().charAt(0);

        if (Character.toUpperCase(option) == 'V') {
            // Convert the resultSet into a list of books
            List<Book> allBooks = App.getBooks("SELECT * FROM book");

            // Print out the list of books
            System.out.println(String.format("%20s | %20s | %20s | %20s | %7s | %6s | %20s | %20s | %10s", "isbn",
                    "title", "author", "genre", "price", "pages", "percentage sale", "publisher name", "stock"));
            for (Book b : allBooks)
                System.out.println(b.toString());

        } else if (Character.toUpperCase(option) == 'S') {
            System.out.println("How would you like to search by?");
            System.out.println("Select which one you are selecting for book: ");
            System.out.println("ISBN (I)");
            System.out.println("Genre (G)");
            System.out.println("Author (A)");
            System.out.println("Title (T)");

            char searchOption = keyboard.nextLine().charAt(0);
            searchBookOptions(searchOption);
        } else {
            System.out.println("This is an invalid choice or the author did not implement this feature. Sorry about the inconvenience. "); 
        }
    }

    public void searchBookOptions(char option) {
        if (Character.toUpperCase(option) == 'I') {
            System.out.println("Enter ISBN: ");
            int isbn = keyboard.nextInt();
            keyboard.nextLine();
            System.out.println(String.format("%20s | %20s | %20s | %20s | %7s | %6s | %20s | %20s | %10s", "isbn",
                    "title", "author", "genre", "price", "pages", "percentage sale", "publisher name", "stock"));

            String ISBNquery = String.format("SELECT * FROM book WHERE isbn = %d", isbn);

            List<Book> allBooks = App.getBooks(ISBNquery);

            for (Book b : allBooks)
                System.out.println(b.toString());

        } else if (Character.toUpperCase(option) == 'G') {
            System.out.println("Enter genre: ");
            String genre = keyboard.nextLine();
            System.out.println(String.format("%20s | %20s | %20s | %20s | %7s | %6s | %20s | %20s | %10s", "isbn",
                    "title", "author", "genre", "price", "pages", "percentage sale", "publisher name", "stock"));

            String genreQuery = String.format("SELECT * FROM book WHERE genre = '%s'", genre);

            List<Book> allBooks = App.getBooks(genreQuery);

            for (Book b : allBooks)
                System.out.println(b.toString());
        } else if (Character.toUpperCase(option) == 'A') {
            System.out.println("Enter author: ");
            String author = keyboard.nextLine();
            System.out.println(String.format("%20s | %20s | %20s | %20s | %7s | %6s | %20s | %20s | %10s", "isbn",
                    "title", "author", "genre", "price", "pages", "percentage sale", "publisher name", "stock"));

            String authorQuery = String.format("SELECT * FROM book WHERE author = '%s'", author);

            List<Book> allBooks = App.getBooks(authorQuery);

            for (Book b : allBooks)
                System.out.println(b.toString());
        } else if (Character.toUpperCase(option) == 'T') {
            System.out.println("Enter title: ");
            String title = keyboard.nextLine();
            System.out.println(String.format("%20s | %20s | %20s | %20s | %7s | %6s | %20s | %20s | %10s", "isbn",
                    "title", "author", "genre", "price", "pages", "percentage sale", "publisher name", "stock"));

            String titleQuery = String.format("SELECT * FROM book WHERE title = '%s'", title);

            List<Book> allBooks = App.getBooks(titleQuery);

            for (Book b : allBooks)
                System.out.println(b.toString());

            System.out.println("Here are some books with containing the title: ");

            String simTitleQuery = String.format("SELECT * FROM book WHERE title LIKE '%s' AND NOT title = '%s'", "%" + title + "%", title);

            List<Book> similiarBooks = App.getBooks(simTitleQuery);

            for (Book b : similiarBooks)
                System.out.println(b.toString());

        } else {
            System.out.println("Please enter a valid option: ");
        }
        bookStoreMainMenu();
    }

    public void signUp() {
        System.out.println("Enter your username: ");
        String username = keyboard.nextLine();
        System.out.println("Enter your password: ");
        String password = keyboard.nextLine();
        System.out.println("Enter your email_address: ");
        String emailAddress = keyboard.nextLine();
        System.out.println("Enter your address: ");
        String address = keyboard.nextLine();
        System.out.println("Enter your phone number (no spaces): ");
        long phoneNumber = keyboard.nextLong();
        System.out.println("Enter your credit card number (no spaces): ");
        long creditCardNumber = keyboard.nextLong();
        keyboard.nextLine();
        System.out.println("Enter your card type: ");
        String cardType = keyboard.nextLine();
        System.out.println("Enter the expiration month of your credit card: ");
        int creditCardMonthExpiration = keyboard.nextInt();
        System.out.println("Enter the expiration year of your credit card: ");
        int creditCardYearExpiration = keyboard.nextInt();

        if (creditCardYearExpiration < 2020) {
            System.out.println("Your credit card is not valid. It has expired.");
        } else if ((creditCardYearExpiration == 2020) && (creditCardMonthExpiration < 4)) {
            System.out.println("Your credit card has expired recently.");
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project",
                    "monicavu", "jason"); Statement statement = connection.createStatement();) {
                String creditCardInfo = String.format(
                        "INSERT INTO credit_card_information (credit_card_number, credit_card_month_expiration, credit_card_year_expiration, card_type) VALUES (%d, %d, %d, '%s')",
                        creditCardNumber, creditCardMonthExpiration, creditCardYearExpiration, cardType);
                statement.execute(creditCardInfo);
                String userInfo = String.format(
                        "INSERT INTO customer (username, user_password, address, email_address, phone_number, credit_card_number) VALUES ('%s', '%s', '%s', '%s', %d, %d)",
                        username, password, address, emailAddress, phoneNumber, creditCardNumber);

                statement.execute(userInfo);
            } catch (Exception sqle) {
                System.out.println("Exception_signUp: " + sqle);
            } finally {
                System.out.println("Leaving the sign up!");
                System.out.println("Please log in to continue shopping the bookstore!");
            }
        }
    }

    public void logIn() {
        System.out.println("Enter your username: ");
        String username = keyboard.nextLine();
        System.out.println("Enter your password: ");
        String password = keyboard.nextLine();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project", "monicavu",
                "jason"); Statement statement = connection.createStatement();) {
            String verifiedUser = String.format(
                    "SELECT COUNT(*) FROM customer WHERE username = '%s' AND user_password = '%s'", username, password);
            ResultSet result = statement.executeQuery(verifiedUser);
            result.next();
            int numMatches = result.getInt("count"); // The number of users with that username and password

            if (numMatches == 1) {
                System.out.println("Success!");
                bookStoreMainMenu();
            } else {
                System.out.println("Failure!");
            }
        }

        catch (Exception sqle) {
            System.out.println("Exception_logIn: " + sqle);
        } finally {
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        final App app = new App();

        System.out.println("Welcome to Look Inna Book! Here are your options: ");
        System.out.println("Log In (L)");

        System.out.println("Sign Up (S)");
        System.out.println("Any other key to exit.");
        System.out.println("PLEASE ENTER YOUR CHOICE: ");
        char option = app.keyboard.nextLine().charAt(0);

        if (Character.toUpperCase(option) == 'S') {
            app.signUp();
        } else if (Character.toUpperCase(option) == 'L') {
            app.logIn();
        } else {
            System.out.println("Thank you for visiting Look Inna Book!");
        }
    }
}