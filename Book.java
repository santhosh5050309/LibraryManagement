import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class Book {
    public int book_id;
    public String title;
    public ArrayList<String>authors;
    public ArrayList<String>publishers;

    public Book(int book_id, String title, ArrayList<String> authors, ArrayList<String> publishers)
    {
        this.book_id = book_id;
        this.title = title;
        this.authors = authors;
        this.publishers = publishers;
        authors = new ArrayList<>();
        publishers = new ArrayList<>();
    }

    public void printDetails()
    {
        System.out.println("BookId:"+book_id);
        System.out.println("Authors: ");
        for(String author: authors)
        {
            System.out.print(author+" ");
        }
        System.out.println();
        for(String publisher: publishers)
        {
            System.out.print(publisher+" ");
        }
    }

    public Boolean searchByAuthors(ArrayList<String> authors)
    {
        for(String author : authors)
        {
            if(!this.authors.contains(author))
            {
                return false;
            }
        }
        return true;
    }


    public Boolean searchByPublishers(ArrayList<String> publishers)
    {
        for(String publisher : publishers)
        {
            if(!this.publishers.contains(publisher))
            {
                return false;
            }
        }
        return true;
    }

    public Boolean searchByTitle(String title)
    {
        return this.title.equals(title);
    }



}
