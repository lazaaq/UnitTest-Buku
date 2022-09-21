import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LibraryHelperTest{

    @Test
    public void testSearchBook() {
        LibraryServiceImplementation service = mock(LibraryServiceImplementation.class);
        LibraryHelper helper = new LibraryHelper(service);

        Book book1 = new Book(1, "Self Improvement", "01");
        Book book2 = new Book(2, "Investasi Bodong", "02");
        Book book3 = new Book(3, "Investasi Riil", "03");

        List<Book> dummyBooks = new ArrayList<>();
        dummyBooks.add(book1);
        dummyBooks.add(book2);
        dummyBooks.add(book3);

        when(service.getAllBooks()).thenReturn(dummyBooks);
        Assert.assertEquals(2, helper.searchBook("Investasi").size());
    }

    @Test
    public void testReturnBook() {
        LibraryServiceImplementation service = mock(LibraryServiceImplementation.class);
        LibraryHelper helper = new LibraryHelper(service);

        Book book1 = new Book(1, "Investasi Tips", "01");
        book1.shelfId = 3;

        helper.returnToShelf(book1, 3);
        verify(service).returnBook(book1.barcode);
    }

    @Test
    public void testTotalBookAvailability(){
        LibraryServiceImplementation service = mock(LibraryServiceImplementation.class);
        LibraryHelper helper = new LibraryHelper(service);

        Book book1 = new Book(1, "Self Improvement", "01");
        Book book2 = new Book(2, "Investasi Bodong", "02");
        Book book3 = new Book(3, "Investasi Riil", "03");
        Book book4 = new Book(3, "Dunia Konspirasi Rafa", "03");

        List<Book> dummyBooks = new ArrayList<>();
        dummyBooks.add(book1);
        dummyBooks.add(book2);
        dummyBooks.add(book3);
        dummyBooks.add(book4);

        when(service.getAllBooks()).thenReturn(dummyBooks);
        Assert.assertEquals(2, helper.totalBookAvailability(3));
    }

    @Test
    public void testIsOverdue(){
        LibraryServiceImplementation service = mock(LibraryServiceImplementation.class);
        LibraryHelper helper = new LibraryHelper(service);

        Book book1 = new Book(1, "Self Improvement", "01");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        Date tglKembali;

        try {
            book1.setTanggalPeminjaman(dateFormat.parse("14/09/2022"));
            tglKembali = dateFormat.parse("20/09/2022");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        book1.setLoanPeriod(5);

        when(service.findBook(book1.barcode)).thenReturn(book1);
        Assert.assertEquals(true, helper.isOverdue("01", tglKembali));
    }

    @Test
    public void testTotalFine() {
        LibraryServiceImplementation service = mock(LibraryServiceImplementation.class);
        LibraryHelper helper = new LibraryHelper(service);

        Book book1 = new Book(1, "Judul Buku", "01");
        book1.setFine(5000);
        when(service.findBook(book1.barcode)).thenReturn(book1);
        Assert.assertEquals(50000, helper.totalFine("01", 10));
    }

    @Test
    public void testIsNeedRestock() {
        LibraryServiceImplementation service = mock(LibraryServiceImplementation.class);
        LibraryHelper helper = new LibraryHelper(service);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

        Book book1 = new Book(1, "Judul Buku", "01");
        Book book2 = new Book(1, "Judul Buku", "02");
        Book book3 = new Book(1, "Judul Buku", "03");
        book1.setIsBorrowed(true);
        book2.setIsBorrowed(true);
        book3.setIsBorrowed(true);
        List<Book> dummyBooks = new ArrayList<>();
        dummyBooks.add(book1);
        dummyBooks.add(book2);
        dummyBooks.add(book3);

        when(service.getAllBooks()).thenReturn(dummyBooks);
        Assert.assertEquals(true, helper.isNeedRestock(1));
    }

    @Test
    public void testLoanBook() {
        LibraryServiceImplementation service = mock(LibraryServiceImplementation.class);
        LibraryHelper helper = new LibraryHelper(service);

        Book book1 = new Book(1, "Judul Buku", "01");
        Book book2 = new Book(1, "Judul Buku", "02");
        Book book3 = new Book(1, "Judul Buku", "03");
        book1.setIsBorrowed(true);
        book2.setIsBorrowed(true);
        book3.setIsBorrowed(false);
        List<Book> dummyBooks = new ArrayList<>();
        dummyBooks.add(book1);
        dummyBooks.add(book2);
        dummyBooks.add(book3);

        when(service.getAllBooks()).thenReturn(dummyBooks);
        helper.loanBook(1);
        verify(service).borrowBook(1);
    }

    @Test
    public void testReminderReturnBook() {
        LibraryServiceImplementation service = mock(LibraryServiceImplementation.class);
        LibraryHelper helper = new LibraryHelper(service);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

        Date todayDate, borrowDate;
        try {
            todayDate = dateFormat.parse("20/09/2022");
            borrowDate = dateFormat.parse("10/09/2022");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Book book1 = new Book(1, "Judul Buku", "01");
        Book book2 = new Book(1, "Judul Buku", "02");
        book1.setIsBorrowed(true);
        book2.setIsBorrowed(true);
        book1.setTanggalPeminjaman(borrowDate);
        book2.setTanggalPeminjaman(borrowDate);
        book1.setLoanPeriod(11);
        book2.setLoanPeriod(5);
        book1.setStudentId("1");
        book2.setStudentId("2");
        List<Book> dummyBooks = new ArrayList<>();
        dummyBooks.add(book1);
        dummyBooks.add(book2);

        when(service.getAllBooks()).thenReturn(dummyBooks);
        helper.reminderReturnBook(todayDate);
        verify(service).sendMessageToStudent("2");
    }

}