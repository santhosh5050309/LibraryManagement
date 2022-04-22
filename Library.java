import org.omg.PortableInterceptor.INACTIVE;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Library implements ILibrary{

    public ArrayList<Integer> rack;
    public int free;
    public int free_slots;
    public HashMap<Integer,ArrayList<Integer>> mapOfBooks;
    public HashMap<Integer,Integer> mapOfBooksToRack;
    public HashMap<Integer,Integer> mapOfBookCopyToBookId;
    public HashMap<Integer,Book> globalBookCopyInfo;
    private int size;
    private HashMap<Integer,UserBookInfo> mapOfUserBookInfo;
    Library(int size)
    {
        this.size =size;
        this.free_slots = size;
        rack = new ArrayList<Integer>(size);
        for(int i=0;i<size;i++)
        {
            rack.add(i,i+1);
        }
        free=0;
    }

    private int getRack()
    {
        if(free == size)
        {
            return -1;
        }
        int rack_no = free;
        free = rack.get(free);
        return rack_no;
    }

    @Override
    public void addBook(int book_id, String title, ArrayList<String> authors, ArrayList<String> publishers, ArrayList<Integer> book_copy_ids) {

        if (free_slots < book_copy_ids.size())
        {
            System.out.println("Rack not available");
        }
        else
        {
            if (mapOfBooks.containsKey(book_id)) {
                ArrayList<Integer> copies = mapOfBooks.get(book_id);
                copies.addAll(book_copy_ids);
                mapOfBooks.put(book_id, copies);
            }
            else {
                mapOfBooks.put(book_id,book_copy_ids);
            }
            for(Integer copy : book_copy_ids)
            {
                mapOfBooksToRack.put(copy,free);
                mapOfBookCopyToBookId.put(copy,book_id);

                globalBookCopyInfo.put(copy,new Book(book_id,title,authors,publishers,copy));
                free = getRack();
            }
        }
    }

    @Override
    public void removeBookCopy(int book_copy_id) {
        if(!mapOfBookCopyToBookId.containsKey(book_copy_id))
        {
            System.out.println("Invalid Book Copy ID");
        }
        else
        {
            int index_to_rack = mapOfBooksToRack.get(book_copy_id);
            rack.set(index_to_rack,free);
            free  = index_to_rack;
            free_slots++;
            int book_id = mapOfBookCopyToBookId.get(book_copy_id);
            ArrayList<Integer> book_copies = mapOfBooks.get(book_id);
            //Can be optimised with Doubly Linked list
            mapOfBookCopyToBookId.remove(book_copy_id);
            book_copies.remove(new Integer(book_copy_id));
            mapOfBooks.put(book_id,book_copies);
        }
    }

    @Override
    public void borrowBook(int book_id, int user_id, Date date) {
        if(!mapOfBooks.containsKey(book_id))
        {
            System.out.println("Invalid Book ID");
        }
        else if(mapOfBooks.containsKey(book_id) && mapOfBooks.get(book_id) == null)
        {
            System.out.println("Not available");
        }
        else if(mapOfUserBookInfo.get(user_id).getNumberOfBooks() == 5)
        {
            System.out.println("Overlimit");
        }
        else
        {
            UserBookInfo user = mapOfUserBookInfo.get(user_id);
            int book_copy_id = mapOfBooks.get(book_id).get(0);
            removeBookCopy(book_copy_id);
            user.addBook(book_copy_id,date);
        }
    }

    @Override
    public void borrowBookCopy(int book_copy_id, int user_id, Date date) {
        if(!mapOfBookCopyToBookId.containsKey(book_copy_id))
        {
            System.out.println("Invalid Book Copy ID");
        }
        else if(mapOfUserBookInfo.get(user_id).getNumberOfBooks() == 5)
        {
            System.out.println("Overlimit");
        }
        else
        {
            UserBookInfo user = mapOfUserBookInfo.get(user_id);
            removeBookCopy(book_copy_id);
            user.addBook(book_copy_id,date);
        }
    }

    @Override
    public void printBorrowed(int user_id) {
        UserBookInfo user = mapOfUserBookInfo.get(user_id);
        user.printBorrowedBooks();
    }

    @Override
    public void returnBookCopy(int book_copy_id) {
        if(!globalBookCopyInfo.containsKey(book_copy_id))
        {
            System.out.println("Invalid Book Copy ID");
        }
        else
        {
            Book book = globalBookCopyInfo.get(book_copy_id);
            addBook(book.book_id,book.title,book.authors, book.publishers, new ArrayList<>(Arrays.asList(book_copy_id)));
        }
    }
}
