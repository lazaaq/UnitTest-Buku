import java.util.Date;

public class Book {
    public Book(int bookId, String title, String barcode) {
        this.bookId = bookId;
        this.title = title;
        this.barcode = barcode;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public int getLoanPeriod() {
        return this.loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public Date getTanggalPeminjaman() {
        return this.tanggalPeminjaman;
    }

    public void setTanggalPeminjaman(Date tanggalPeminjaman) {
        this.tanggalPeminjaman = tanggalPeminjaman;
    }

    public int getFine() {
        return this.fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public boolean getIsBorrowed() {
        return this.isBorrowed;
    }

    public void setIsBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    int bookId;
    String title;
    String barcode;
    int shelfId;
    int fine;
    boolean isBorrowed;
    int loanPeriod;
    String studentId;
    Date tanggalPeminjaman;

}

