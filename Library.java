import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Library implements ILibrary {

    // Should replace with Rack class
    private final Map<Integer, Integer> rackMap;
    private Integer free_slots;
    private Integer firstFreeRack = 0;
    private final Map<Integer, Book> mapOfBookIdBook = new HashMap<>();
    private final Map<Integer, UserBookInfo> mapOfUserBookInfo = new HashMap<>();
    private final Map<Integer, Integer> invertedIndexofBookCopyIdBookId = new HashMap<>();
    private final Integer maxBorrowLimit = 5;
    private final Integer rackCapacity;
    private final Integer numRacks;

    public Library(Integer numRacks, Integer rackCapacity) {
        this.rackCapacity = rackCapacity;
        this.numRacks = numRacks;
        this.free_slots = numRacks*rackCapacity;
        rackMap = new HashMap<>(numRacks);
    }

    private Integer getRack() throws Exception {
        if (free_slots.equals(0)) {
            throw new Exception("Out of capacity");
        }

        if(rackMap.get(firstFreeRack).equals(rackCapacity)) {
            firstFreeRack++;
        }

        rackMap.put(firstFreeRack, rackMap.get(firstFreeRack)+1);
        free_slots--;
        return firstFreeRack;
    }

    @Override
    public void addBook(Integer book_id, String title, List<String> authors, List<String> publishers,
                        List<Integer> book_copy_ids) throws Exception {

        if (free_slots < book_copy_ids.size()) {
            throw new IllegalStateException("Out of free slots");
        } else {
            if (!mapOfBookIdBook.containsKey(book_id)) {
                mapOfBookIdBook.put(book_id, new Book(book_id, title, authors, publishers));
            }
            Book book = mapOfBookIdBook.get(book_id);
            for (Integer book_copy : book_copy_ids) {
                // Lets assume book_copy_ids are always unique
                if(!book.getBookCopies().containsKey(book_copy)) {
                    Integer free_rack = getRack();
                    this.free_slots--;
                    book.addBookCopy(book.getBook_id(), book_copy, free_rack);
                    invertedIndexofBookCopyIdBookId.put(book_copy, book_id);
                }
            }
        }
    }

    /*
    @Override
    public void removeBookCopy(Integer book_copy_id) {
        if (!invertedIndexofBookCopyIdBookId.containsKey(book_copy_id)) {
            System.out.println("Invalid Book Copy ID");
        } else {
            Book book = mapOfBookIdBook.get(invertedIndexofBookCopyIdBookId.get(book_copy_id));
            BookCopy bookCopy = book.getBookCopies().get(book_copy_id);
            Integer rackIndex = bookCopy.getRack_id();
            mapOfUserBookInfo.values().forEach(userBookInfo -> {
                if(userBookInfo.getMapOfBooksDueDate().containsKey(bookCopy)) {
                    throw new IllegalStateException("User must return copy first");
                }
            });
            //Free rack space
            rackMap.put(rackIndex, rackMap.get(rackIndex) -1);
            book.getBookCopies().remove(book_copy_id);
            invertedIndexofBookCopyIdBookId.remove(book_copy_id);
            free_slots++;
        }
    } */

    @Override
    public void borrowBook(Integer book_id, Integer user_id, Date date) throws Exception {
        if (!mapOfUserBookInfo.containsKey(user_id)) {
            mapOfUserBookInfo.put(user_id, new UserBookInfo());
        }
        if (!mapOfBookIdBook.containsKey(book_id)) {
            throw new IllegalArgumentException("Invalid Book ID");
        } else if (mapOfUserBookInfo.get(user_id).getNumberOfBooks().equals(maxBorrowLimit)) {
            throw new Exception("Book borrow limit reached");
        } else {
            UserBookInfo user = mapOfUserBookInfo.get(user_id);
            Book book = mapOfBookIdBook.get(book_id);
            book.getBookCopies().forEach((integer, bookCopy) -> {
                if(bookCopy.getAvailable()) {
                    try {
                        borrowBookCopy(bookCopy.getBook_copy_id(), user_id, date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            throw new Exception("No Book Available");
        }
    }

    @Override
    public void borrowBookCopy(Integer book_copy_id, Integer user_id, Date date) throws Exception {
        if (!mapOfUserBookInfo.containsKey(user_id)) {
            mapOfUserBookInfo.put(user_id, new UserBookInfo());
        }
        if (!invertedIndexofBookCopyIdBookId.containsKey(book_copy_id)) {
            throw new IllegalArgumentException("Invalid Book Copy ID");
        } else if (mapOfUserBookInfo.get(user_id).getNumberOfBooks().equals(maxBorrowLimit)) {
            throw new Exception("Book borrow limit reached");
        } else {
            Integer book_id = invertedIndexofBookCopyIdBookId.get(book_copy_id);
            mapOfBookIdBook.get(book_id).getBookCopies().get(book_copy_id).setAvailable(false);
            UserBookInfo user = mapOfUserBookInfo.get(user_id);
            user.addBook(mapOfBookIdBook.get(book_id).getBookCopies().get(book_copy_id), date);
        }
    }

    @Override
    public void printBorrowed(Integer user_id) {
        if (mapOfUserBookInfo.containsKey(user_id)) {
            mapOfUserBookInfo.get(user_id).printBorrowedBooks();
        }
    }

    @Override
    public void returnBookCopy(Integer user_id, Integer book_copy_id) {
        if (!invertedIndexofBookCopyIdBookId.containsKey(book_copy_id)) {
            throw new IllegalArgumentException("Invalid Book Copy ID");
        } else {
            Integer book_id = invertedIndexofBookCopyIdBookId.get(book_copy_id);
            mapOfBookIdBook.get(book_id).getBookCopies().get(book_copy_id).setAvailable(true);
        }
    }

    @Override
    public void searchByBookCopyId(Integer book_copy_id) {
        mapOfBookIdBook.values().forEach(book -> {
            if(book.getBookCopies().containsKey(book_copy_id)) {
                book.printDetails();
                book.getBookCopies().get(book_copy_id).printDetails();
            }
        });
    }

    @Override
    public void searchByBookId(Integer book_id) {
        throw new NotImplementedException();
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
    public void searchByAuthors(List<String> authors) {
        genericSearch(authors, SearchType.AUTHORS);
    }

    @Override
    public void searchByPublishers(List<String> publishers) {
        genericSearch(publishers, SearchType.PUBLISHERS);
    }


    @Override
    public void searchByTitle(String title) {
        genericSearch(Collections.singletonList(title), SearchType.TITLE);
    }

    @Override
    public void searchByDueDate(Integer user_id, Date date) {
        ArrayList<BookCopy> book_copies_list = new ArrayList<>();
        UserBookInfo userBookInfo = mapOfUserBookInfo.get(user_id);
        Map<BookCopy, Date> mapOfBooksDueDate = userBookInfo.getMapOfBooksDueDate();
        for (Map.Entry<BookCopy, Date> entry : mapOfBooksDueDate.entrySet()) {
            if (entry.getValue().before(date) || entry.getValue().equals(date)) {
                BookCopy bookCopy = entry.getKey();
                book_copies_list.add(bookCopy);
            }
        }

        book_copies_list.sort(BookCopy::compareTo);
        for (BookCopy bookCopy : book_copies_list) {
            bookCopy.printDetails();
        }
        System.out.println();
    }

    private void genericSearch(List<String> query, SearchType searchType) {
        ArrayList<BookCopy> book_copies_list = new ArrayList<>();
        mapOfBookIdBook.values().forEach(book ->  {
            if(searchType.equals(SearchType.TITLE)) {
                if (book.searchByTitle(query.get(0))) {
                    book_copies_list.addAll(book.getBookCopies().values());
                }
            }

            else if(searchType.equals(SearchType.AUTHORS)) {
                if (book.searchByAuthors(query)) {
                    book_copies_list.addAll(book.getBookCopies().values());
                }
            }

            else if(searchType.equals(SearchType.PUBLISHERS)) {
                if (book.searchByPublishers(query)) {
                    book_copies_list.addAll(book.getBookCopies().values());
                }
            }
        });
        book_copies_list.sort(BookCopy::compareTo);
        for (BookCopy bookCopy : book_copies_list) {
            bookCopy.printDetails();
        }
        System.out.println();
    }
}
