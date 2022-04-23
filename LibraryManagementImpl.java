import java.util.ArrayList;

public class LibraryManagementImpl implements ILibraryManagement {

    private ArrayList<ILibrary> libraryList;
    public LibraryManagementImpl()
    {
        libraryList = new ArrayList<>();
    }

    @Override
    public ILibrary createLibrary(int size) {
        ILibrary library = new Library(size);
        libraryList.add(library);
        return library;
    }

    @Override
    public ILibrary getLibrary(int index) {
        return libraryList.get(index);
    }
}
