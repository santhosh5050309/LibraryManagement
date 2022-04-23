import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class UserBookInfo {
    private Integer numberOfBooks = 0;
    private Map<BookCopy, Date> mapOfBooksDueDate = new TreeMap<>();

    public UserBookInfo() {
    }

    public UserBookInfo(Integer numberOfBooks, Map<BookCopy, Date> mapOfBooksDueDate) {
        this.numberOfBooks = numberOfBooks;
        this.mapOfBooksDueDate = mapOfBooksDueDate;
    }

    public Integer getNumberOfBooks() {
        return numberOfBooks;
    }

    public void addBook(BookCopy bookCopy, Date date) {
        mapOfBooksDueDate.put(bookCopy, date);
        numberOfBooks++;
    }

    public void returnBook(BookCopy bookCopy) {
        mapOfBooksDueDate.remove(bookCopy);
        numberOfBooks--;
    }

    public void printBorrowedBooks() {
        mapOfBooksDueDate.forEach((key, value) -> System.out.println("BookCopy: " + key + ",Due Date: " + value));
    }

    public Map<BookCopy, Date> getMapOfBooksDueDate() {
        return mapOfBooksDueDate;
    }
}
