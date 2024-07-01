package comparators;

import models.Book;

import java.time.LocalDate;
import java.util.Comparator;

public class BookComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        return 0;
    }
    public int compareByTitle(Book o1, Book o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }

    public int compareByAuthor(Book o1, Book o2) {
        return o1.getAuthor().compareTo(o2.getAuthor());
    }

    public int compareByGenre(Book o1, Book o2) {
        return o1.getGenre().compareTo(o2.getGenre());
    }

    public int compareByPublicationDate(Book o1, Book o2) {
        return LocalDate.parse(o1.getPublicationDate()).compareTo(LocalDate.parse(o2.getPublicationDate()));
    }

    public int compareByAvailability(Book o1, Book o2) {
        return o1.getIsAvailable() ? -1 : 1;
    }
}
