import java.util.Comparator;

//ThreadSafe
public class BookCopy implements Comparable<BookCopy> {
    private final Integer book_id;
    private final Integer book_copy_id;
    private final Integer rack_id;
    private Boolean available;

    public BookCopy(Integer book_id, Integer book_copy_id, Integer rack_id, Boolean available) {
        this.book_id = book_id;
        this.book_copy_id = book_copy_id;
        this.rack_id = rack_id;
        this.available = available;
    }

    public void printDetails() {
        System.out.println("BookCopyId:" + book_copy_id);
        System.out.println("Rack_no:" + rack_id);
    }

    public int getRack_id() {
        return rack_id;
    }

    public Integer getBook_copy_id() {
        return book_copy_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public int compareTo(BookCopy a) {
        return Integer.compare(this.rack_id, a.getRack_id());
    }
}
