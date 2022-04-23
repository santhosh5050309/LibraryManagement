import java.util.ArrayList;
import java.util.Comparator;

public class BookCopy extends Book {
    public int book_copy_id;
    public int rack_id;
    public BookCopy(int book_id, String title, ArrayList<String> authors, ArrayList<String> publishers,int book_copy_id,int rack_id)
    {
        super(book_id,title,authors,publishers);
        this.book_copy_id = book_copy_id;
        this.rack_id = rack_id;
    }

    public void printDetails()
    {
        System.out.println("BookCopyId:"+book_copy_id);
        super.printDetails();
        System.out.println("Rack_no:"+rack_id);
    }

    public static Comparator<BookCopy> getCompByRack()
    {
        Comparator comp = new Comparator<BookCopy>(){
            @Override
            public int compare(BookCopy book1, BookCopy book2)
            {
                return book1.rack_id-book2.rack_id;
            }
        };
        return comp;
    }
}
