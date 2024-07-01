import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import comparators.BookComparator;
import comparators.UserComparator;
import enums.BookGenre;
import enums.TransactionType;
import enums.UserRole;
import exceptions.BookNotAvailableException;
import exceptions.UserNotFoundException;
import models.Book;
import models.Transaction;
import models.User;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.System.exit;

public class Main {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static ObjectMapper objectMapper = new ObjectMapper();
    static Scanner scan = new Scanner(System.in);
    static File usersData = new File("src/datas/users.json");
    static File libraryData = new File("src/datas/library.json");
    static File transactionsData = new File("src/datas/transactions.json");
    static HashMap<String, User> users = new HashMap<>();
    static HashMap<String, Book> library = new HashMap<>();
    static HashMap<String, Transaction> transactions = new HashMap<>();

    static String generateId() {
        return UUID.randomUUID().toString().substring(0,8);
    }
    static String register() {
        System.out.println("Welcome to the Register menu");
        String id;
        String name;
        String phone;
        String address;
        String email;
        String password;
        UserRole userRole=UserRole.MEMBER;
        User user;
        scan.nextLine();
        while (true) {
            System.out.println("Please enter your name");
            name = scan.nextLine();
            if(name.isBlank()) {
                System.out.println("Name cannot be blank");
                continue;
            }
            System.out.println("Please enter your phone number as 0901234567");
            phone = scan.nextLine();
            if(phone.isBlank() ||
                    phone.length() != 10 ||
                    !phone.matches("[0-9]+") ||
                    phone.charAt(0) != '0'
            ) {
                System.out.println("Please enter a valid phone number");
                continue;
            }
            System.out.println("Please enter your address");
            address = scan.nextLine();
            if(address.isBlank()) {
                System.out.println("Address cannot be blank");
                continue;
            }
            System.out.println("Please enter your email");
            email = scan.nextLine();
            if(email.isBlank() ||
                    !email.endsWith("@gmail.com")
            ) {
                System.out.println("Please enter a valid email");
                continue;
            }
            System.out.println("Please enter your password");
            password = scan.nextLine();
            if(password.isBlank()) {
                System.out.println("Password cannot be blank");
                continue;
            }
            System.out.println("Please enter your role as 1. Librarian or 2. Member");
            int response= checkResponse( 2);
            if(response == 1) {
                userRole = UserRole.LIBRARIAN;
            }
            break;
        }
        id = generateId();
        user = new User(id, name, phone, address, email, password, userRole);
        users.put(id, user);
        try {
            objectMapper.writeValue(usersData, users);
            System.out.println("models.User Account successfully created");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }
    static String login() throws UserNotFoundException {
        System.out.println("Welcome to the Login menu");
        String username;
        String password;
        String userId;
        scan.nextLine();
        System.out.println("Please enter your username");
        username = scan.nextLine();
        System.out.println("Please enter your password");
        password = scan.nextLine();
        userId = checkData(username, password);
        if(userId == null) throw new UserNotFoundException("User not found");
        System.out.println("Login successfully");
        return userId;
    }
    static int checkResponse( int limit ) {
        while (true) {
            try {
                int number = scan.nextInt();
                if(number < 1 || number > limit) {
                    System.out.println("A number must be between 1 and " + limit);
                    scan.nextLine();
                    continue;
                }
                return number;
            } catch (Exception e) {
                System.out.println("Input must be a number");
                scan.nextLine();
            }
        }
    }
    static String checkData(String username, String password) {
        for(User user : users.values()) {
            if(user.getName().equals(username) && user.getPassword().equals(password)) {
                return user.getUserId();
            }
        }
        return null;
    }
    static void updateAccount(String userId) {
        System.out.println("Welcome to the Update Account menu");
        System.out.println("Dear " + users.get(userId).getName());
        String name;
        String phone;
        String address;
        String email;
        String password;
        UserRole userRole=UserRole.MEMBER;
        User user;
        scan.nextLine();
        while (true) {
            System.out.println("Please enter your name");
            System.out.println("Your previous name is " + users.get(userId).getName());
            name = scan.nextLine();
            if(name.isBlank()) {
                System.out.println("Name cannot be blank");
                continue;
            }
            System.out.println("Please enter your phone number as 0901234567");
            System.out.println("Your previous phone number is " + users.get(userId).getPhone());
            phone = scan.nextLine();
            if(phone.isBlank() ||
                    phone.length() != 10 ||
                    !phone.matches("[0-9]+") ||
                    phone.charAt(0) != '0'
            ) {
                System.out.println("Please enter a valid phone number");
                continue;
            }
            System.out.println("Please enter your address");
            System.out.println("Your previous address is " + users.get(userId).getAddress());
            address = scan.nextLine();
            if(address.isBlank()) {
                System.out.println("Address cannot be blank");
                continue;
            }
            System.out.println("Please enter your email");
            System.out.println("Your previous email is " + users.get(userId).getEmail());
            email = scan.nextLine();
            if(email.isBlank() ||
                    !email.endsWith("@gmail.com")
            ) {
                System.out.println("Please enter a valid email");
                continue;
            }
            System.out.println("Please enter your password");
            System.out.println("Your previous password is " + users.get(userId).getPassword());
            password = scan.nextLine();
            if(password.isBlank()) {
                System.out.println("Password cannot be blank");
                continue;
            }
            System.out.println("Please enter your role as 1. Librarian or 2. Member");
            System.out.println("Your previous role is " + users.get(userId).getUserRole());
            int response= checkResponse( 2);
            if(response == 1) {
                userRole = UserRole.LIBRARIAN;
            }
            break;
        }
        user = new User(userId, name, phone, address, email, password, userRole);
        users.put(userId, user);
        try {
            objectMapper.writeValue(usersData, users);
            System.out.println("models.User Account successfully updated");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void deleteAccount(String userId) {
        System.out.println("Welcome to the Delete Account menu");
        System.out.println("Dear " + users.get(userId).getName());
        users.remove(userId);
        try {
            objectMapper.writeValue(usersData, users);
            System.out.println("models.User Account successfully deleted");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        exit(0);
    }
    static void addBook() {
        System.out.println("Welcome to the Add Book menu");
        String bookId;
        String title;
        String author;
        BookGenre genre;
        String publicationDate;
        boolean isAvailable;
        scan.nextLine();
        while (true) {
            System.out.println("Please enter the title");
            title = scan.nextLine();
            if(title.isBlank()) {
                System.out.println("Title cannot be blank");
                continue;
            }
            System.out.println("Please enter the author");
            author = scan.nextLine();
            if(author.isBlank()) {
                System.out.println("Author cannot be blank");
                continue;
            }
            System.out.println("Please enter the genre as 1. Fiction, 2. Non-fiction, 3. Science, 4. Art");
            int response = checkResponse(4);
            genre = BookGenre.values()[response - 1];
            scan.nextLine();
            System.out.println("Please enter the publication date as yyyy-MM-dd");
            try{
                publicationDate = scan.nextLine();
                LocalDate.parse(publicationDate,formatter);
            } catch (Exception e) {
                System.out.println("Please enter a valid date");
                continue;
            }
            System.out.println("Please enter the availability as 1. Yes or 2. No");
            response = checkResponse(2);
            isAvailable = response == 1;
            break;
        }
        bookId =generateId();
        Book book = new Book(bookId, title, author, genre, publicationDate, isAvailable);
        library.put(bookId, book);
        try {
            objectMapper.writeValue(libraryData, library);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Book successfully added");
    }
    static void deleteBook() {
        if(library.isEmpty()) {
            System.out.println("Library is empty");
            return;
        }
        System.out.println("Welcome to the Delete Book menu");
        System.out.println("""
                You must enter the book ID to delete,
                if you dont know the ID, please enter 1,
                if you know the ID, please enter 2""");
        int choice = checkResponse(2);
        if(choice==1) searchBook();
        scan.nextLine();
        System.out.println("Please enter the book ID");
        String bookId = scan.nextLine();

        if(library.remove(bookId)==null){
            System.out.println("Book not found");
        }else{
            System.out.println("Book successfully deleted");
            try {
                objectMapper.writeValue(libraryData, library);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static void updateBook() {
        System.out.println("Welcome to the Update Book menu");
        System.out.println("""
                You must enter the book ID to update,
                if you dont know the ID, please enter 1,
                if you know the ID, please enter 2""");
        int choice = checkResponse(2);
        if(choice==1) searchBook();
        System.out.println("Please enter the book ID");
        scan.nextLine();
        String bookId = scan.nextLine();
        if(library.containsKey(bookId)){
            String title;
            String author;
            BookGenre genre;
            String publicationDate;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            boolean isAvailable;
            while (true) {
                System.out.println("Please enter the title");
                System.out.println("The book's previous title is " + library.get(bookId).getTitle());
                title = scan.nextLine();
                if(title.isBlank()) {
                    System.out.println("Title cannot be blank");
                    continue;
                }
                System.out.println("Please enter the author");
                System.out.println("The book's previous author is " + library.get(bookId).getAuthor());
                author = scan.nextLine();
                if(author.isBlank()) {
                    System.out.println("Author cannot be blank");
                    continue;
                }
                System.out.println("Please enter the genre as 1. Fiction, 2. Non-fiction, 3. Science, 4. Art");
                System.out.println("The book's previous genre is " + library.get(bookId).getGenre());
                int response = checkResponse(4);
                genre = BookGenre.values()[response - 1];
                scan.nextLine();
                System.out.println("Please enter the publication date as yyyy-MM-dd");
                System.out.println("The book's previous publication date is " + library.get(bookId).getPublicationDate());
                try{
                    publicationDate = scan.nextLine();
                    LocalDate.parse(publicationDate,formatter);
                } catch (Exception e) {
                    System.out.println("Please enter a valid date");
                    continue;
                }
                System.out.println("Please enter the availability as 1. Yes or 2. No");
                System.out.println("The book's previous availability is " + library.get(bookId).getIsAvailable());
                response = checkResponse(2);
                isAvailable = response == 1;
                break;
            }

            Book book = new Book(bookId, title, author, genre, publicationDate, isAvailable);
            library.put(bookId, book);
            try {
                objectMapper.writeValue(libraryData, library);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Book successfully updated");
        }
        else{
            System.out.println("Book not found");
        }
    }
    static void searchBook() {
        System.out.println("Welcome to the Search Book menu");
        System.out.println("""
                Please enter specific number to do
                the operation you want to perform:
                1.View all books
                2.Search book by title
                3.Search book by author
                4.Search book by genre
                """);
        int choice = checkResponse(4);
        scan.nextLine();
        switch (choice) {
            case 1 :
                library.forEach((k, v) -> System.out.println(v));
                break;
            case 2 :
                System.out.println("Please enter the title");
                String title;
                while (true) {
                    title = scan.nextLine();
                    if(title.isBlank()) {
                        System.out.println("Title cannot be blank, please try again");
                        continue;
                    }
                    break;
                }
                final String  tempTitle = title;
                library.forEach((k, v) -> {
                    if(v.getTitle().equals(tempTitle)) {
                        System.out.println(v);
                    }
                });
                break;
            case 3 :
                System.out.println("Please enter the author");
                String author;
                while (true) {
                    author = scan.nextLine();
                    if(author.isBlank()) {
                        System.out.println("Author cannot be blank, please try again");
                        continue;
                    }
                    break;
                }
                final String  tempAuthor = author;
                library.forEach((k, v) -> {
                    if(v.getAuthor().equals(tempAuthor)) {
                        System.out.println(v);
                    }
                });
                break;
            case 4 :
                System.out.println("Please enter the genre as 1. Fiction, 2. Non-fiction, 3. Science, 4. Art");
                int response = checkResponse(4);
                BookGenre genre = BookGenre.values()[response - 1];
                library.forEach((k, v) -> {
                    if(v.getGenre() == genre) {
                        System.out.println(v);
                    }
                });
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
    static void searchUser(){
        System.out.println("Welcome to the Search User menu");
        System.out.println("""
                Please enter specific number to do
                the operation you want to perform:
                1.View all users
                2.Search user by name
                3.Search user by ID
                """);
        int choice = checkResponse(3);
        scan.nextLine();
        switch (choice) {
            case 1 :
                users.forEach((k, v) -> System.out.println(v));
                break;
            case 2 :
                System.out.println("Please enter the name");
                String name;
                while (true) {
                    name = scan.nextLine();
                    if(name.isBlank()) {
                        System.out.println("Name cannot be blank, please try again");
                        continue;
                    }
                    break;
                }
                final String  tempName = name;
                users.forEach((k, v) -> {
                    if(v.getName().equals(tempName)) {
                        System.out.println(v);
                    }
                });
                break;
            case 3 :
                System.out.println("Please enter the ID");
                String id;
                while (true) {
                    id = scan.nextLine();
                    if(id.isBlank()) {
                        System.out.println("ID cannot be blank, please try again");
                        continue;
                    }
                    break;
                }
                final String  tempId = id;
                users.forEach((k, v) -> {
                    if(v.getUserId().equals(tempId)) {
                        System.out.println(v);
                    }
                });
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
    static void borrowBook(String userId) throws BookNotAvailableException{
        System.out.println("Welcome to the Borrow Book menu");
        System.out.println("""
                You must enter the book ID to borrow,
                if you dont know the ID, please enter 1,
                if you know the ID, please enter 2""");
        int choice = checkResponse(2);
        if(choice==1) searchBook();
        System.out.println("Please enter the book ID");
        String bookId = scan.nextLine();
        if(library.containsKey(bookId)) {
            if(isBorrowed(userId, bookId)) {
                System.out.println("Book already borrowed");
                return;
            }
            if(!library.get(bookId).getIsAvailable()) {
                throw new BookNotAvailableException("Book not available");
            }
            String transactionId = generateId();
            Transaction transaction = new Transaction(transactionId, userId, bookId,LocalDate.now().format(formatter));
            transactions.put(transactionId, transaction);
            try {
                objectMapper.writeValue(transactionsData, transactions);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Book successfully borrowed");
        }
        else {
            System.out.println("Book not found");
        }
    }
    static void returnBook(String userId) {
        System.out.println("Welcome to the Return Book menu");
        if(numBorrows(userId)==0) {
            System.out.println("You have not borrowed any book");
            return;
        }
        System.out.println("""
                You must enter the transaction ID to return,
                if you dont know the ID, please enter 1,
                if you know the ID, please enter 2""");
        int choice = checkResponse(2);
        if(choice==1) userBorrowedBooks(userId);
        scan.nextLine();
        System.out.println("Please enter the Transaction ID");
        String transactionId = scan.nextLine();
        if(transactions.containsKey(transactionId)) {
            Transaction trans = transactions.get(transactionId);
            trans.setTransactionType(TransactionType.RETURN);
            trans.setReturnDate(LocalDate.now().format(formatter));
            transactions.put(transactionId, trans);
            try {
                objectMapper.writeValue(transactionsData, transactions);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Book successfully returned");
        }
        else {
            System.out.println("Transaction not found");
        }
    }
    static int numBorrows(String userId) {
        int counter=0;
        for(Transaction transaction : transactions.values()) {
            if(transaction.getUserId().equals(userId) &&
                    transaction.getTransactionType().equals(TransactionType.BORROW)) {
                counter++;
            }
        }
        return counter;
    }
    static int numTransactions(String userId) {
        int counter=0;
        for(Transaction transaction : transactions.values()) {
            if(transaction.getUserId().equals(userId)) {
                counter++;
            }
        }
        return counter;
    }
    static void userBorrowedBooks(String userId) {
        int counter=0;
        for(Transaction transaction : transactions.values()) {
            if(transaction.getUserId().equals(userId) &&
                    transaction.getTransactionType().equals(TransactionType.BORROW)) {
                counter++;
                System.out.println(counter+". " + transaction);
            }
        }
        if(counter==0) {
            System.out.println("No books borrowed");
        }
    }
    static void userTransactions(String userId) {
        int counter=0;
        for(Transaction transaction : transactions.values()) {
            if(transaction.getUserId().equals(userId)) {
                counter++;
                System.out.println(counter+". " + transaction);
            }
        }
        if(counter==0) {
            System.out.println("No transactions found");
        }
    }
    static boolean isBorrowed(String userId, String bookId) {
        for(Transaction transaction : transactions.values()) {
            if(transaction.getUserId().equals(userId) &&
                    transaction.getBookId().equals(bookId) &&
                    transaction.getTransactionType().equals(TransactionType.BORROW)) {
                return true;
            }
        }
        return false;
    }
    static void sortBooks(){
        System.out.println("Welcome to the Sort Books menu");
        System.out.println("""
                Please enter specific
                number to do perform:
                1. Sort by title
                2. Sort by author
                3. Sort by genre
                4. Sort by publication date
                5. Sort by availability
                """);
        int choice = checkResponse(5);
        BookComparator bookComparator = new BookComparator();
        switch (choice) {
            case 1 :
                ArrayList<Book> sortedByTitle = new ArrayList<>(library.values());
                sortedByTitle.sort(bookComparator::compareByTitle);
                sortedByTitle.forEach(System.out::println);
                break;
            case 2 :
                ArrayList<Book> sortedByAuthor = new ArrayList<>(library.values());
                sortedByAuthor.sort(bookComparator::compareByAuthor);
                sortedByAuthor.forEach(System.out::println);
                break;
            case 3 :
                ArrayList<Book> sortedByGenre = new ArrayList<>(library.values());
                sortedByGenre.sort(bookComparator::compareByGenre);
                sortedByGenre.forEach(System.out::println);
                break;
            case 4 :
                ArrayList<Book> sortedByPublicationDate = new ArrayList<>(library.values());
                sortedByPublicationDate.sort(bookComparator::compareByPublicationDate);
                sortedByPublicationDate.forEach(System.out::println);
                break;
            case 5 :
                ArrayList<Book> sortedByAvailability = new ArrayList<>(library.values());
                sortedByAvailability.sort(bookComparator::compareByAvailability);
                sortedByAvailability.forEach(System.out::println);
                break;
            default :
                System.out.println("Invalid choice");
                break;
        }
    }
    static void sortUsers() {
        System.out.println("Welcome to the Sort Users menu");
        System.out.println("""
                Please enter specific
                number to do perform:
                1. Sort by name
                2. Sort by address
                3. Sort by user role
                """);
        int choice = checkResponse(3);
        UserComparator userComparator = new UserComparator();
        switch (choice) {
            case 1:
                ArrayList<User> sortedByName = new ArrayList<>(users.values());
                sortedByName.sort(userComparator::compareByName);
                sortedByName.forEach(System.out::println);
                break;
            case 2:
                ArrayList<User> sortedByAddress = new ArrayList<>(users.values());
                sortedByAddress.sort(userComparator::compareByAddress);
                sortedByAddress.forEach(System.out::println);
                break;
            case 3:
                ArrayList<User> sortedByUserRole = new ArrayList<>(users.values());
                sortedByUserRole.sort(userComparator::compareByUserRole);
                sortedByUserRole.forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
    static void generateReportsMember(String userId) {
        if(numTransactions(userId)==0) {
            System.out.println("No transactions found");
            return;
        }
        System.out.println("Welcome to the Generate Reports menu");
        System.out.println("\nDear " + users.get(userId).getName());
        if(numBorrows(userId)!=0) {
            System.out.println("Total number of books borrowed : " + numBorrows(userId));
        }
        System.out.println("""
                
                Please enter specific number to do perform:
                1.View all my transactions
                2.View my borrowed books""");
        int choice = checkResponse(2);
        switch (choice) {
            case 1 -> userTransactions(userId);
            case 2 -> userBorrowedBooks(userId);
            default -> System.out.println("Invalid choice");
        }
    }
    static void generateReportsLibrarian() {
        if(transactions.isEmpty()) {
            System.out.println("No transactions found");
            return;
        }
        System.out.println("Welcome to the Generate Reports menu");
        System.out.println("""
                Please enter specific number to do perform:
                1.View transaction history
                2.View overdue books with specific date
                3.View specific user transactions""");
        int choice = checkResponse(3);
        scan.nextLine();
        switch (choice) {
            case 1 :
                for(Transaction transaction : transactions.values()) {
                    System.out.println(transaction);
                }
                break;
            case 2 :
                System.out.println("Please enter the date (YYYY-MM-DD) we search for after it: ");
                String date = scan.nextLine();
                try{
                    LocalDate.parse(date,formatter);
                } catch (Exception e) {
                    System.out.println("Invalid date");
                    break;
                }
                for(Transaction transaction : transactions.values()) {
                    if(transaction.getBorrowDate().compareTo(date)>0) {
                        System.out.println(transaction);
                    }
                }
                break;
            case 3 :
                System.out.println("Please enter the user ID");
                String userId = scan.nextLine();
                if(!users.containsKey(userId)) {
                    System.out.println("User not found");
                    break;
                }
                for(Transaction transaction : transactions.values()) {
                    if(transaction.getUserId().equals(userId)) {
                        System.out.println(transaction);
                    }
                }
                break;
            default :
                System.out.println("Invalid choice");
                break;
        }
    }
    static void favoriteBooks(String userId) {
        System.out.println("Welcome to the Favorite Books menu");
        int counter =1;
        for(Book book : users.get(userId).getFavoriteBooks()) {
            System.out.println(counter + ". " +book);
            counter++;
        }
        System.out.println("""
                If you want to add a book please enter 1,
                if you want to remove a book please enter 2,
                if you want to exit please enter 3""");
        int choice = checkResponse(3);

        switch (choice) {
            case 1 :
                searchBook();
                System.out.println("Please enter the book ID");
                String bookId = scan.nextLine();
                if(!library.containsKey(bookId)) {
                    System.out.println("Book not found");
                    break;
                }
                users.get(userId).getFavoriteBooks().add(library.get(bookId));
                try {
                    objectMapper.writeValue(usersData,users);
                }catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Book added successfully");
                break;
            case 2 :
                if(users.get(userId).getFavoriteBooks().isEmpty()) {
                    System.out.println("No favorite books found");
                    break;
                }
                System.out.println("Please enter the book ID");
                String bookid = scan.nextLine();
                if(!library.containsKey(bookid)) {
                    System.out.println("Book not found");
                    break;
                }
                if(!users.get(userId).getFavoriteBooks().contains(library.get(bookid))) {
                    System.out.println("Book not found in your favorite books");
                    break;
                }
                users.get(userId).getFavoriteBooks().remove(library.get(bookid));
                try {
                    objectMapper.writeValue(usersData,users);
                }catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Book removed successfully");
                break;
            case 3 :
                break;
            default :
                System.out.println("Invalid choice");
                break;
        }
    }
    static void reserveBook(String userId) {
        System.out.println("Welcome to the Reserve Book menu");
        int counter =1;
        for(Book book : users.get(userId).getReservedBooks()) {
            if(book.getIsAvailable()) {
                System.out.println(counter+". "+book+" is available");
            } else {
                System.out.println(counter+". "+book+" is not available");
            }
            counter++;
        }
        System.out.println("""
                If you want to reserve a book please enter 1,
                if you want remove a book from reserved books please enter 2,
                if you want to exit please enter 3""");
        int choice = checkResponse(3);

        switch (choice) {
            case 1 :
                searchBook();
                System.out.println("Please enter the book ID");
                String bookId = scan.nextLine();
                if(!library.containsKey(bookId)) {
                    System.out.println("Book not found");
                    break;
                }
                if(library.get(bookId).getIsAvailable()) {
                    System.out.println("Book is available you can borrow it");
                    break;
                }
                users.get(userId).getReservedBooks().add(library.get(bookId));
                try {
                    objectMapper.writeValue(usersData,users);
                }catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Book reserved successfully");
                break;
            case 2 :
                if(users.get(userId).getReservedBooks().isEmpty()) {
                    System.out.println("No reserved books found");
                    break;
                }
                System.out.println("Please enter the book ID");
                String bookid = scan.nextLine();
                if(!library.containsKey(bookid)) {
                    System.out.println("Book not found");
                    break;
                }
                if(!users.get(userId).getReservedBooks().contains(library.get(bookid))) {
                    System.out.println("Book not found in your reserved books");
                    break;
                }
                users.get(userId).getReservedBooks().remove(library.get(bookid));
                try {
                    objectMapper.writeValue(usersData,users);
                }catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Book removed successfully");
                break;
            case 3 :
                break;
            default :
                System.out.println("Invalid choice");
                break;
        }
    }
    static void totalFees(String userId) {
        for(Transaction transaction : transactions.values()) {
            if(transaction.getUserId().equals(userId)) {
                transaction.transactionFees();
            }
        }
    }

    static void memberMenu(String userId) {
        while(true) {
            System.out.println("Dear " + users.get(userId).getName());
            System.out.println("""
                    Welcome to Member Menu
                    Please enter the specific number
                    for the operation you want to perform:
                    1. Update account
                    2. Delete account
                    3. Borrow book
                    4. Return book
                    5. Search book
                    6. Sort books
                    7. Generate reports
                    8. Favorite books
                    9. Reserve book
                    10. Total fees
                    11. Exit""");
            int choice = checkResponse(11);
            try {
                switch (choice) {
                    case 1 -> updateAccount(userId);
                    case 2 -> deleteAccount(userId);
                    case 3 -> borrowBook(userId);
                    case 4 -> returnBook(userId);
                    case 5 -> searchBook();
                    case 6 -> sortBooks();
                    case 7 -> generateReportsMember(userId);
                    case 8 -> favoriteBooks(userId);
                    case 9 -> reserveBook(userId);
                    case 10 -> totalFees(userId);
                    default -> exit(0);
                }
            }catch (BookNotAvailableException e) {
                System.out.println(e.getMessage());
            }

        }
    }
    static void librarianMenu(String userId) {
        while(true) {
            System.out.println("Dear " + users.get(userId).getName());
            System.out.println("""
                    Welcome to Librarian Menu
                    Please enter the specific number
                    for the operation you want to perform:
                    1. Update account
                    2. Delete account
                    3. Add book
                    4. Delete book
                    5. Update book
                    6. Search book
                    7. Search user
                    8. Sort books
                    9. Sort users
                    10. Generate reports
                    11. Exit""");
            int choice = checkResponse(11);
            switch (choice) {
                case 1 -> updateAccount(userId);
                case 2 -> deleteAccount(userId);
                case 3 -> addBook();
                case 4 -> deleteBook();
                case 5 -> updateBook();
                case 6 -> searchBook();
                case 7 -> searchUser();
                case 8 -> sortBooks();
                case 9 -> sortUsers();
                case 10 -> generateReportsLibrarian();
                default -> exit(0);
            }
        }
    }
    public static void main(String[] args) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            users = objectMapper.readValue(usersData, new TypeReference<HashMap<String, User>>() {});
            library = objectMapper.readValue(libraryData, new TypeReference<HashMap<String, Book>>() {});
            transactions = objectMapper.readValue(transactionsData, new TypeReference<HashMap<String, Transaction>>() {});
        } catch (IOException e) {
            e.getMessage();
        }

        System.out.println("""
                    Welcome to Library Management System
                    Please enter the specific number
                    Do you have an account?
                    1. Yes
                    2. No""");
        int choice = checkResponse( 2);
        String currentUserId=null;
        if(choice == 1) {
            try {
                currentUserId = login();
            }catch (UserNotFoundException e){
                System.out.println("Login failed");
                exit(0);
            }

        } else if(choice == 2) {
            currentUserId = register();
        }
        if(currentUserId == null) {
            System.out.println("Login failed");
            exit(0);
        }
        if(users.get(currentUserId).getUserRole() == UserRole.MEMBER) {
            memberMenu(currentUserId);
        } else if(users.get(currentUserId).getUserRole() == UserRole.LIBRARIAN) {
            librarianMenu(currentUserId);
        }
    }
}