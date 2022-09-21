import java.util.List;

public interface LibraryService {
    public List<Book> getAllBooks();
    public void returnBook(String barcode);
    public Book findBook(String barcode);
    public void borrowBook(int bookId);
    public void sendMessageToStudent(String studentId);
}
