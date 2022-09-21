import java.util.List;
public class LibraryServiceImplementation implements LibraryService {
    @Override
    public List<Book> getAllBooks() { return null; }
    @Override
    public void returnBook(String barcode) { }
    @Override
    public Book findBook(String barcode) { return null; }
    @Override
    public void borrowBook(int bookId) {}
    @Override
    public void sendMessageToStudent(String studentId) { }
}
