import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;

public interface ILibrary {
     void addBook(int book_id, String title, ArrayList<String> authors,
                        ArrayList<String> publishers, ArrayList<Integer> book_copy_ids);

     void removeBookCopy(int book_copy_id);

     void borrowBook(int book_id, int user_id, Date date);

     void borrowBookCopy(int book_copy_id, int user_id, Date date);

     void returnBookCopy(int user_id,int book_copy_id);

     void printBorrowed(int user_id);

     void searchByBookCopyId(int book_copy_id);

     void searchByBookId(int book_id);

     void searchByTitle(String title);

     void searchByAuthors(ArrayList<String> authors);

     void searchByPublishers(ArrayList<String>publishers);

     void searchByDueDate(int user_id,Date date);

}
