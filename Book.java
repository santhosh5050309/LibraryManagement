import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

//ThreadSafe
public class Book {
    private final Integer book_id;
    private final String title;
    private final List<String> authors;
    private final List<String> publishers;
    private Map<Integer, BookCopy> bookCopies = new HashMap<>();
    private final StringBuilder stringBuilder = new StringBuilder();

    public Book(Integer book_id, String title, List<String> authors, List<String> publishers) {
        this.book_id = book_id;
        this.title = title;
        this.authors = authors;
        this.publishers = publishers;
    }

    public void printDetails() {
        stringBuilder.setLength(0);
        stringBuilder.append("BookId:").append(book_id).append("Authors: ");;
        for (String author : authors) {
            stringBuilder.append(author).append(" ");
        }
        stringBuilder.append("\n");
        for (String publisher : publishers) {
            stringBuilder.append(publisher).append(" ");
        }
        System.out.println(stringBuilder.toString());
        bookCopies.forEach((integer, bookCopy) -> bookCopy.printDetails());
    }

    public Boolean searchByAuthors(List<String> authors) {
        return generalSearch(authors, this.authors);
    }

    public Boolean searchByPublishers(List<String> publishers) {
        return generalSearch(publishers, this.publishers);
    }

    public Boolean searchByTitle(String title) {
        return generalSearch(Collections.singletonList(title),
                Collections.singletonList(this.title));
    }

    public void addBookCopy(Integer book_id, Integer book_copy_id, Integer rack_id) {
        this.bookCopies.put(book_copy_id, new BookCopy(book_id, book_copy_id, rack_id, true));
    }

    public Map<Integer, BookCopy> getBookCopies() {
        return bookCopies;
    }

    public Integer getBook_id() {
        return book_id;
    }

    private Boolean generalSearch(List<String> query, List<String> field) {
        for (String string : field) {
            if (!query.contains(string)) {
                return false;
            }
        }
        return true;
    }

}
