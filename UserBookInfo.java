import java.util.*;

public class UserBookInfo {
    private int numberOfBooks;
    public TreeMap<Integer, Date> mapOfBooksDueDate;

    public UserBookInfo()
    {
        numberOfBooks=0;
        mapOfBooksDueDate = new TreeMap<>();
    }

    public int getNumberOfBooks()
    {
        return numberOfBooks;
    }

    public void addBook(int book_copy_id,Date date)
    {
        mapOfBooksDueDate.put(book_copy_id,date);
        numberOfBooks++;
    }

    public void returnBook(int book_copy_id)
    {
        mapOfBooksDueDate.remove(book_copy_id);
        numberOfBooks--;
    }

    public void printBorrowedBooks()
    {
        for (Map.Entry<Integer,Date> entry : mapOfBooksDueDate.entrySet())
        {
            System.out.println("BookCopy: " + entry.getKey() + ",Due Date: "+ entry.getValue());
        }
    }
}
