import java.util.ArrayList;

public class Book {
    public int book_copy_id;
    public int book_id;
    public String title;
    public ArrayList<String>authors;
    public ArrayList<String>publishers;

    public Book(int book_id, String title, ArrayList<String> authors, ArrayList<String> publishers,int book_copy_id)
    {
        this.book_copy_id = book_copy_id;
        this.book_id = book_id;
        this.title = title;
        this.authors = authors;
        this.publishers = publishers;
    }
}
