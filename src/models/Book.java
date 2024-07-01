package models;
import enums.BookGenre;
import java.time.LocalDate;

public class Book {

    public Book(){}

    public Book(String bookId, String title, String author, BookGenre genre, String publicationDate, boolean isAvailable) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.isAvailable = isAvailable;
    }
    String bookId;
    String title;
    String author;
    BookGenre genre;
    String publicationDate;
    public boolean isAvailable;

    public void setAuthor(String author) {this.author = author;}
    public void setIsAvailable(boolean isAvailable) {this.isAvailable = isAvailable;}
    public void setBookId(String bookId) {this.bookId = bookId;}
    public void setTitle(String title) {this.title = title;}
    public void setGenre(BookGenre genre) {this.genre = genre;}
    public BookGenre getGenre() {return genre;}
    public String getAuthor() {return author;}
    public String getBookId() {return bookId;}
    public String getTitle() {return title;}
    public boolean getIsAvailable() {return isAvailable;}
    public String getPublicationDate() {return publicationDate;}
    public void setPublicationDate(String publicationDate) {this.publicationDate = publicationDate;}

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre=" + genre +
                ", publicationDate='" + publicationDate + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
