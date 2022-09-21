import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LibraryHelper {
    private LibraryService service;
    public LibraryHelper(LibraryService service) {
        this.service = service;
    }
    public List<Book> searchBook(String keyword) {
        List<Book> allBooks = service.getAllBooks();
        List<Book> result = new ArrayList<>();
        
        for(int i=0; i<allBooks.size(); i++) {
            Book book = allBooks.get(i);
            if(book.title.contains(keyword)) {
                result.add(book);
            }
        }
        return result;
    }
    public void returnToShelf(Book book, int shelfId) {
        if(book.shelfId == shelfId) {
            this.service.returnBook(book.barcode);
        }
    }

    public int totalBookAvailability(int bookId) {
        List<Book> allBooks = service.getAllBooks();
        int count = 0;
        for(int i=0; i<allBooks.size(); i++) {
            Book book = allBooks.get(i);
            if(book.getBookId() == bookId) {
                count++;
            }
        }
        return count;
    }

    public boolean isOverdue(String barcode, Date tanggalKembali){
        Book bookInfo = service.findBook(barcode);
        Date tanggalPeminjaman = bookInfo.tanggalPeminjaman;
        int loanPeriod = bookInfo.loanPeriod;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tanggalPeminjaman);
        calendar.add(Calendar.DATE, loanPeriod);

        Date tanggalHarusKembali = calendar.getTime();
        return tanggalHarusKembali.before(tanggalKembali);
    }

    public int totalFine(String barcode, int days) {
        Book book = service.findBook(barcode);
        int fine = book.fine;
        return fine * days;
    }

    public boolean isNeedRestock(int bookId) {
        List<Book> allBooks = service.getAllBooks();
        boolean isRestock = true;
        for(int i=0; i<allBooks.size(); i++) {
            if(allBooks.get(i).bookId == bookId && !allBooks.get(i).isBorrowed) {
                isRestock = false;
                break;
            }
        }
        return isRestock;
    }

    public void loanBook(int bookId) {
        List<Book> allBooks = service.getAllBooks();
        boolean isExist = false;
        for(int i=0; i<allBooks.size(); i++) {
            if(!allBooks.get(i).getIsBorrowed()) {
                this.service.borrowBook(bookId);
                isExist = true;
                break;
            }
        }
        if(!isExist) {
            System.out.println("gaada buku");
        }
    }

    public void reminderReturnBook(Date todayDate) {
        List<Book> allBooks = service.getAllBooks();
        for(int i=0; i<allBooks.size(); i++) {
            if(allBooks.get(i).getIsBorrowed()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(allBooks.get(i).tanggalPeminjaman);
                calendar.add(Calendar.DATE, allBooks.get(i).loanPeriod);

                if(calendar.getTime().before(todayDate)) {
                    this.service.sendMessageToStudent(allBooks.get(i).studentId);
                }
            }
        }
    }


}
