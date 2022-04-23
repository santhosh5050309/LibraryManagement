import org.omg.PortableInterceptor.INACTIVE;

import java.lang.reflect.Array;
import java.util.*;

public class Library implements ILibrary{

    public ArrayList<Integer> rack;
    public int free;
    public int free_slots;
    public TreeMap<Integer,ArrayList<Integer>> mapOfBooks; // book_id to book_copies
    public HashMap<Integer,Integer> mapOfBooksToRack;
    public HashMap<Integer,Integer> mapOfBookCopyToBookId;
    public HashMap<Integer,BookCopy> globalBookCopyInfo;
    private int size;
    private HashMap<Integer,UserBookInfo> mapOfUserBookInfo;
    private int maxBorrowLimit;

    Library(int size)
    {
        this.size =size;
        this.free_slots = size;
        rack = new ArrayList<>(size);
        for(int i=0;i<size;i++)
        {
            rack.add(i,i+1);
        }
        this.free=0;
        maxBorrowLimit = 5;
        mapOfBooks = new TreeMap<>();
        mapOfBooksToRack = new HashMap<>();
        mapOfBookCopyToBookId = new HashMap<>();
        globalBookCopyInfo = new HashMap<>();
        mapOfUserBookInfo = new HashMap<>();
    }

    private int getRack()
    {
        if(free == size)
        {
            return -1;
        }
        int rack_no = free;
        this.free = rack.get(free);
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
                int free_rack = getRack();
                mapOfBooksToRack.put(copy,free_rack);
                mapOfBookCopyToBookId.put(copy,book_id);
                this.free_slots--;
                globalBookCopyInfo.put(copy,new BookCopy(book_id,title,authors,publishers,copy,free_rack));

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
            mapOfBooksToRack.remove(book_copy_id);
            rack.set(index_to_rack,free);
            free  = index_to_rack;
            free_slots++;

            int book_id = mapOfBookCopyToBookId.get(book_copy_id);
            ArrayList<Integer> book_copies = mapOfBooks.get(book_id);
            //Can be optimised with Doubly Linked list
            mapOfBookCopyToBookId.remove(book_copy_id);
            book_copies.remove(new Integer(book_copy_id));
            mapOfBooks.put(book_id,book_copies);

            globalBookCopyInfo.remove(book_copy_id);
        }
    }

    @Override
    public void borrowBook(int book_id, int user_id, Date date) {
        if(!mapOfUserBookInfo.containsKey(user_id))
        {
            mapOfUserBookInfo.put(user_id,new UserBookInfo());
        }
        if(!mapOfBooks.containsKey(book_id))
        {
            System.out.println("Invalid Book ID");
        }
        else if(mapOfBooks.containsKey(book_id) && mapOfBooks.get(book_id).size() == 0)
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
        if(mapOfUserBookInfo.containsKey(user_id))
        {
            UserBookInfo user = mapOfUserBookInfo.get(user_id);
            user.printBorrowedBooks();
        }
    }

    @Override
    public void returnBookCopy(int user_id,int book_copy_id) {
        if(!globalBookCopyInfo.containsKey(book_copy_id))
        {
            System.out.println("Invalid Book Copy ID");
        }
        else
        {
            Book book = globalBookCopyInfo.get(book_copy_id);
            addBook(book.book_id,book.title,book.authors, book.publishers, new ArrayList<>(Arrays.asList(book_copy_id)));
            UserBookInfo user =  mapOfUserBookInfo.get(user_id);
            user.returnBook(book_copy_id);
        }
    }

    @Override
    public void searchByBookCopyId(int book_copy_id) {
        if(globalBookCopyInfo.containsKey(book_copy_id))
        {
            globalBookCopyInfo.get(book_copy_id).printDetails();
        }
    }

    @Override
    public void searchByBookId(int book_id) {
        /*
        if(mapOfBooks.containsKey(book_id)){
            ArrayList<BookCopy> book_copies_list = new ArrayList<>();
            for(Integer book_copy_id : mapOfBooks.get(book_id))
            {
                book_copies_list.add(globalBookCopyInfo.get(book_copy_id));
                searchByBookCopyId(book_copy_id);
            }
            Collections.sort(book_copies_list,BookCopy.getCompByRack());
            for(BookCopy bookCopy: book_copies_list)
            {
                bookCopy.printDetails();
            }
            System.out.println();
        }
        */
    }

    @Override
    public void searchByAuthors(ArrayList<String> authors) {
        ArrayList<BookCopy> book_copies_list = new ArrayList<>();
        for(Map.Entry<Integer,ArrayList<Integer>> entry: mapOfBooks.entrySet())
        {
            int book_copy_id  =  entry.getValue().get(0);
            BookCopy bookCopy =  globalBookCopyInfo.get(book_copy_id);
            if(bookCopy.searchByAuthors(authors))
            {
                book_copies_list.add(bookCopy);
            }
        }

        Collections.sort(book_copies_list,BookCopy.getCompByRack());
        for(BookCopy bookCopy: book_copies_list)
        {
            bookCopy.printDetails();
        }
        System.out.println();
    }

    @Override
    public void searchByPublishers(ArrayList<String> publishers) {
        ArrayList<BookCopy> book_copies_list = new ArrayList<>();
        for(Map.Entry<Integer,ArrayList<Integer>> entry: mapOfBooks.entrySet())
        {
            int book_copy_id  =  entry.getValue().get(0);
            BookCopy bookCopy =  globalBookCopyInfo.get(book_copy_id);
            if(bookCopy.searchByPublishers(publishers))
            {
                book_copies_list.add(bookCopy);
            }
        }

        Collections.sort(book_copies_list,BookCopy.getCompByRack());
        for(BookCopy bookCopy: book_copies_list)
        {
            bookCopy.printDetails();
        }
        System.out.println();
    }


    @Override
    public void searchByTitle(String title) {

        ArrayList<BookCopy> book_copies_list = new ArrayList<>();
        for(Map.Entry<Integer,ArrayList<Integer>> entry: mapOfBooks.entrySet())
        {
            int book_copy_id  =  entry.getValue().get(0);
            BookCopy bookCopy =  globalBookCopyInfo.get(book_copy_id);
            if(bookCopy.searchByTitle(title))
            {
                book_copies_list.add(bookCopy);
            }
        }

        Collections.sort(book_copies_list,BookCopy.getCompByRack());
        for(BookCopy bookCopy: book_copies_list)
        {
            bookCopy.printDetails();
        }
        System.out.println();
    }

    @Override
    public void searchByDueDate(int user_id,Date date) {
        ArrayList<BookCopy> book_copies_list = new ArrayList<>();
        UserBookInfo userBookInfo = mapOfUserBookInfo.get(user_id);
        TreeMap<Integer, Date> mapOfBooksDueDate = userBookInfo.mapOfBooksDueDate;
        for(Map.Entry<Integer, Date> entry: mapOfBooksDueDate.entrySet())
        {
            if(entry.getValue().equals(date))
            {
                BookCopy bookCopy =  globalBookCopyInfo.get(entry.getKey());
                book_copies_list.add(bookCopy);
            }
        }

        Collections.sort(book_copies_list,BookCopy.getCompByRack());
        for(BookCopy bookCopy: book_copies_list)
        {
            bookCopy.printDetails();
        }
        System.out.println();
    }
}
