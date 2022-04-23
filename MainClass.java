import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainClass {
    public static void main(String args[])
    {
        ILibraryManagement iLibraryManagement;
        iLibraryManagement = new LibraryManagementImpl();
        ILibrary library = iLibraryManagement.createLibrary(10);
        //Test
        //add_book 1 book1 author1,author2 publisher1 book_copy1,book_copy2,book_copy3
        //add_book 2 book2 author2,author3 publisher2,publisher3 book_copy4,book_copy5,book_copy6,book_copy7,book_copy8,book_copy9,book_copy10
        //add_book 3 book3 author2 publisher2 book_copy11,book_copy12,book_copy13

        library.addBook(1,"book1",
                new ArrayList<>(Arrays.asList("author1","author2")),
                new ArrayList<>(Arrays.asList("publisher1")),
                new ArrayList<>(Arrays.asList(1,2,3)));

        library.addBook(2,"book2",
                new ArrayList<>(Arrays.asList("author2","author3")),
                new ArrayList<>(Arrays.asList("publisher2","publisher3")),
                new ArrayList<>(Arrays.asList(4,5,6,7,8,9,10)));

        library.addBook(3,"book3",
                new ArrayList<>(Arrays.asList("author2")),
                new ArrayList<>(Arrays.asList("publisher2")),
                new ArrayList<>(Arrays.asList(11,12,13)));

        //search book_id 3
        library.searchByBookId(1);
        library.searchByBookId(3);

        // search author_id author2
        library.searchByAuthors(new ArrayList<>(Arrays.asList("author2")));
        library.searchByAuthors(new ArrayList<>(Arrays.asList("author3")));

        //remove_book_copy book_copy6
        library.removeBookCopy(6);
        library.removeBookCopy(13);

        //add_book 3 book3 author2 publisher2 book_copy13
        library.addBook(3,"book3",
                new ArrayList<>(Arrays.asList("author2")),
                new ArrayList<>(Arrays.asList("publisher2")),
                new ArrayList<>(Arrays.asList(13)));

        //search book_id 2
        library.searchByBookId(2);

        //print_borrowed user1
        library.printBorrowed(1);

        //borrow_book 1 user1 2020-12-31
        //borrow_book 1 user1 2020-12-31
        //borrow_book 1 user1 2020-12-31
        //borrow_book 1 user1 2020-12-31
        library.borrowBook(1,1,new Date(2020,12,31));
        library.borrowBook(1,1,new Date(2020,12,31));
        library.borrowBook(1,1,new Date(2020,12,31));
        library.borrowBook(1,1,new Date(2020,12,31));
        library.borrowBook(1,1,new Date(2020,12,31));

        //search book_id 1
        library.searchByBookId(1);

        //search author_id author1
        library.searchByAuthors(new ArrayList<>(Arrays.asList("author1")));

        //borrow_book 4 user1 2020-12-31
        //borrow_book 2 user1 2020-12-31
        //borrow_book 2 user1 2020-12-31
        //borrow_book 2 user1 2020-12-31
        library.borrowBook(4,1,new Date(2020,12,31));
        library.borrowBook(2,1,new Date(2020,12,31));
        library.borrowBook(2,1,new Date(2020,12,31));
        library.borrowBook(2,1,new Date(2020,12,31));
        library.borrowBook(2,1,new Date(2020,12,31));

        //print_borrowed user1
        library.printBorrowed(1);

        //return_book_copy book_copy1
        //return_book_copy book_copy2
        library.removeBookCopy(1);
        library.removeBookCopy(2);

        //borrow_book_copy book_copy1
        //borrow_book_copy book_copy1
        //borrow_book_copy book_copy2
        //borrow_book_copy book_copy10
        library.borrowBook(1,1,new Date(2020,12,31));
        library.borrowBook(1,1,new Date(2020,12,31));
        library.borrowBook(2,1,new Date(2020,12,31));
        library.borrowBook(10,1,new Date(2020,12,31));

        //print_borrowed user1
        library.printBorrowed(1);



    }
}
