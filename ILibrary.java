import java.util.Date;
import java.util.List;

public interface ILibrary {
    void addBook(Integer book_id, String title, List<String> authors,
                 List<String> publishers, List<Integer> book_copy_ids) throws Exception;

    /*
    void removeBookCopy(Integer book_copy_id); */

    void borrowBook(Integer book_id, Integer user_id, Date date) throws Exception;

    void borrowBookCopy(Integer book_copy_id, Integer user_id, Date date) throws Exception;

    void returnBookCopy(Integer user_id, Integer book_copy_id);

    void printBorrowed(Integer user_id);

    void searchByBookCopyId(Integer book_copy_id);

    void searchByBookId(Integer book_id);

    void searchByTitle(String title);

    void searchByAuthors(List<String> authors);

    void searchByPublishers(List<String> publishers);

    void searchByDueDate(Integer user_id, Date date);

}
