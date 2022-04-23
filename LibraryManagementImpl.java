import java.util.ArrayList;
import java.util.List;

public class LibraryManagementImpl implements ILibraryManagement {

    private final List<ILibrary> libraryList = new ArrayList<>();

    public LibraryManagementImpl() {}

    @Override
    public ILibrary createLibrary(Integer numRacks, Integer rackCapacity) {
        ILibrary library = new Library(numRacks, rackCapacity);
        libraryList.add(library);
        return library;
    }

    @Override
    public ILibrary getLibrary(Integer index) {
        return libraryList.get(index);
    }
}
