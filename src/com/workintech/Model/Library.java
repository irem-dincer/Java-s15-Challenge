package com.workintech.Model;

import com.workintech.Enum.BookCategories;
import com.workintech.Services.BookServices;

import java.util.*;

public class Library implements BookServices { //<-"Interface implementation"

    private Set<String> borrowedBooks; // Odunc alinan kitaplarin ID'leri (Benzersiz Kitap ID'si. Ayni kitap ID'si tekrar edemez!)
    private Map<String, Book> bookList; //Tum kitaplar (ID ile hizli kitap search islemi);
    private double balance; //Kutuphane total bakiye

    public Library(Set<String> borrowedBooks, Map<String, Book> bookList, double balance) {
        this.borrowedBooks = borrowedBooks;
        this.bookList = bookList;
        this.balance = balance;
    }

    public Set<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public Map<String, Book> getBookList() {
        return bookList;
    }

    public double getBalance() {
        return balance;
    }

//borrowBook() - Kitap Ödünç Alma;

    // <- "Implementation Override Methodlar"
    public boolean borrowBook(User user, String id) {
        if (user != null && user.isLoggedIn()) { //Kullanici var mi, giris yapmis mi?
            Book book = getBookById(id); //Id ile kitabi bul
            if (borrowedBooks.size() >= 5) {
                System.out.println("Uzgunuz, kitap odunc alma limiti asildi!");
                return false;

            } else if (borrowedBooks.contains(id)) { //Kullanici zaten bu kitabi almis mi?
                System.out.println("Bu kitabi daha once odunc aldiniz!");
                return false;

            } else if (book.isBorrowed()) { //Kitap baskasi tarafindan odunc alinmis mi?
                System.out.println("Kitap, baskasi tarafindan odunc alinmis!");
                return false;

            } else { //Set'e ekle, durumu update et, bakiye kes!
                borrowedBooks.add(id); //Odunc listesine ekle
                book.setBorrowed(true); //Kitabi odunc alinmis olarak isaretle
                user.setBalance(user.getBalance() - 50); //Kullanicidan 30TL kes
                System.out.println("You borrowed this book: " + book.getName() + " ||  Your Balance: " + user.getBalance() + "TL");
                return true;
            }

        }
        return false;

    }

    //addBook() - Kitap Ekleme (Sadece Librarian);

    public void addBook(Librarian librarian) {
        if (librarian != null && librarian.isLoggedIn()) { //Kutuphaneci giris yapmis mi?
            Scanner scanner = new Scanner(System.in); //Kullanicidan input al

            try {
                System.out.println("Book ID giriniz: ");
                String bookId = scanner.nextLine();  // Kitap ID'si

                if (bookList.containsKey(bookId)) {  // Bu ID zaten var mı?
                    System.out.println("Bu ID ile kitap zaten mevcut!");
                    return;
                }

                //Kitap bilgilerini al!
                System.out.println("Kitap adini giriniz: ");
                String title = scanner.nextLine();

                System.out.println("Kitap ödünç alındı mı? (dogru/yanlis): ");
                boolean isBorrowed = scanner.nextBoolean();
                scanner.nextLine();

                //Kategori Secimi;
                System.out.println("These are book categories: " + Arrays.toString(BookCategories.values()));
                System.out.println("Lutfen bir kitap kategorisi girin (Sadece Büyük Harf!) : ");
                String categoryInput = scanner.nextLine();

                if (!isValidCategory(categoryInput)) { //Gecerli kategori mi?
                    System.out.println("Gecersiz kategori girisi!! Lütfen gecerli bir kategori girin...");
                    return;
                }
                BookCategories category = BookCategories.valueOf(categoryInput);

                //Yazar Bilgileri
                System.out.println("Yazar adini giriniz: ");
                String authorName = scanner.nextLine();
                System.out.println("Yazar ID'sini giriniz:  ");
                String authorId = scanner.nextLine();

                Author author = new Author(authorName, authorId, new ArrayList<>());  // Yazar olustur
                Book newBook = new Book(bookId, title, author, isBorrowed, category);  // Kitap olustur

                bookList.put(newBook.getId(), newBook);  // Kitap listesine ekle
                System.out.println("Kitap basarili sekilde eklendi! : " + newBook.getAuthor() + " - " + newBook.getName());

            } catch (InputMismatchException e) { //Hatali input yakalarsa
                System.out.println("Gecersiz giris. Lutfen dogru veri turunu girin!");

            } catch (IllegalArgumentException e) { //Enum donusturme hatasi
                System.out.println("Gecersiz giris: " + e.getMessage());

            }

        } else {
            System.out.println("Yalnizca kutuphaneciler kitap ekleyebilir. Lutfen kütüphaneci olarak giris yapin.");
        }

    }

    //Helper Method - Kategori Kontrolü:
    private boolean isValidCategory(String categoryInput) {
        for (BookCategories category : BookCategories.values()) { //Tum kategorileri dolas
            if (category.name().equals(categoryInput)) { //Girilen kategori var mi?
                return true;
            }

        }
        return false;

    }

    //removeBook() - Kitap Silme:
    public void removeBook(Librarian librarian) {
        if (librarian != null && librarian.isLoggedIn()) {  // Kütüphaneci kontrolü
            Scanner scanner = new Scanner(System.in);

            try {
                System.out.println("Lutfen veritabanindan silmek istediginiz kitap kodunu giriniz.");
                String bookId = scanner.nextLine();

                if (bookList.containsKey(bookId)) { //Kitap var mi?
                    Book removedBook = bookList.remove(bookId);//Kitabi sil

                    if (removedBook != null) {
                        System.out.println("Kitap kutuphane veritabanindan kaldirildi." + removedBook.getAuthor() + " - " + removedBook.getName());

                    } else {
                        System.out.println("Hata: Kitap kutuphane veritabanindan kaldirilamadi.");
                    }

                } else {
                    System.out.println("Girdiginiz ID'ye sahip bir kitap bulunamadi. Lutfen emin oldugunuz bir kitap ID'si girin.");

                }
            } catch (InputMismatchException e) {
                System.out.println("Gecersiz giris. Lutfen dogru veri turunu girin.");
                scanner.nextLine();
            }

        } else {
            System.out.println("Yalnizca kutuphaneciler kitap cikartabilir. Eğer bir kutuphaneciyseniz, lutfen kutuphaneci olarak giris yapin.");
        }
    }

//updateBook() - Kitap Güncelleme: (ID ve Scanner nesnesi almakta)

    public void updateBook(String id, Scanner scanner) {

        if (bookList.containsKey(id)) { //Verilen id ile kitap listesinde kitabin olamdigi kontrolu
            System.out.println("Lutfen yeni kitap basligi giriniz: ");
            String newTitle = scanner.nextLine(); //Yeni baslik alimi

            System.out.println("Kitap odunc alindi mi (Dogru/Yanlis): ");
            boolean isBorrowed = scanner.nextBoolean(); //dogru/yanlis alimi
            scanner.nextLine(); //(Buffer temizligi icin ekledim)

            System.out.println("Lutfen yeni kategori secin");
            String categoryInput = scanner.nextLine().toUpperCase(); //Kategori buyuk harfe cevrilir

            if (!isValidCategory(categoryInput)) { //Kategori gecerli mi kontrolu
                System.out.println("Bu kategori gecersiz, lutfen gecerli bir kategori secin!");
                return; //Gecersiz durumunda method sonlanir
            }

            BookCategories newCategory = BookCategories.valueOf(categoryInput); //String --->Kategori enum'ina cevrildi

            System.out.println("Lutfen yeni yazar adini giriniz: ");
            String newAuthorName = scanner.nextLine(); //Yeni yazar adi

            System.out.println("Lutfen yeni yazarin ID bilgisini giriniz: ");
            String newAuthorId = scanner.nextLine(); //Yeni yazar ID

            Author newAuthor = new Author(newAuthorName, newAuthorId, new ArrayList<>()); //Yeni yazar nesnesi olusturulur

            Book updatedBook = new Book(newTitle, id, newAuthor, isBorrowed, newCategory); //Yeni kitap nesnesi olustur.

            bookList.put(id, updatedBook); //Eski kitabin ustune yeni kitap adi(ID ile)

            System.out.println("Kitap basarili sekilde guncellendi! " + updatedBook.getAuthor() + " -" + updatedBook.getName()); //Basarili guncelleme mesaji!
        } else {
            System.out.println("Bu ID ile kitap bulunamadi! Lutfen gecerli bir ID giriniz: "); //Kitap bulunamadigi takdirde hata mesaji
        }
    }

    //getBookById() Metodu: ID ile kitap arama,
    public Book getBookById(String id) { //ID alip, book nesnesi dondururum
        if (bookList.containsKey(id)) {

            Book foundBook = bookList.get(id); //bookList'te ID li kitap var ise ID sini al

            if (!foundBook.isBorrowed()) { //Kitap odunc alinmadi ise
                System.out.println("Kitap bulundu " + foundBook.getName() + " -" + foundBook.getAuthor() + " Kitap odunc alinmamis, kitabi odunc alabilirsin");
            } else {
                System.out.println("Kitap bulundu: " + foundBook.getName() + " -" + foundBook.getAuthor() + "Kitap odunc alinmis, maalesef bu kitabi alamazsin");

            }

            return foundBook; //Bulunan kitap nesnesi

        }
        System.out.println("Kitap bulunamadi, Lutfen tekrar dene!");
        return null;
    }

    //getBookByName() Metodu:
    public List<Book> getBookByName(String name) { //Isim ile kitap arama methodu,

        List<Book> foundBooks = new ArrayList<>(); //Bulunan kitaplari tutan ArrayList

        for (Book book : bookList.values()) { //BookList'teki tum kitaplari geziyorum
            if (book.getName().equalsIgnoreCase(name)) {
                //(Buyuk-kucuk harf duyarsiz) Girilen kitap ismini girilen isimle karsilastir
                foundBooks.add(book); //kitap eklendi (eslesiyor ise)
                System.out.println("Kitap bulundu: " + book.getName() + " -" + book.getAuthor());
            }

        }

        if (foundBooks.isEmpty()) {
            System.out.println("Kitap bulunamadi!");

        }
        return foundBooks;
    }
    //getBookByAuthor() Metodu: Yazar adi ile kitap arama methodu;
    public List<Book> getBookByAuthor(String authorName){

        List<Book> bookByAuthor=new ArrayList<>(); //Yazara ait kitaplari tutan ArrayList

        for(Book book:bookList.values()){ //bookList Map'indeki tum kitaplari gez
            if(book.getAuthor().getName().equalsIgnoreCase(authorName)){ //Her kitabin yazarin adi ile girilen yazar adini karsilastir.

                bookByAuthor.add(book); //Yazar adi eslesiyor ise yazarin kitaplarina ekle
            }
        }
        if(bookByAuthor.isEmpty()){ //Yazara ait kitap bulunamaz ise
            System.out.println("Uzgunum, yazar bulunamadi");
        }
        return  bookByAuthor;

    }

    //getBooksByCategory() Metodu: Kategoriye gore kitap arama methodu
    public List<Book> getBooksByCategory(String category){
        List<Book> booksByCategory= new ArrayList<>(); //Bu kategorideki kitaplari tutacak ArrayList

        for(Book book: bookList.values()){ //bookList Map'indeki kitaplari dolas

            if(book.getBookCategories().name().equalsIgnoreCase(category)){ //Kitap kategorisi girilen kategoriyle eslesiyor ise
                booksByCategory.add(book); //Kategori eslesir ise kitabi ekle
            }


        }
if(booksByCategory.isEmpty()){
    System.out.println("Bu kategoride kitap bulunamadi");
}
return booksByCategory;
    }

    // returnBook() Metodu: Kitap iade methodu (User ve kitap ID si alir)
    public boolean returnBook(User user,String id){

        if(user==null){ //Kullanici guvenlik kontrolu

            System.out.println("Gecersiz Kullanici!"); //Gecersiz kullanici hatasi
            return  false;

        }

        Book book= bookList.get(id); //Verilen id ile kitabi bookList'ten alirim

        if(book==null){ //???
            System.out.println("Gecersiz Kitap!");
            return false;

        }

        if(borrowedBooks.contains(id)){ //Id, borrowedBooks Set'inde var mi kontrolu (Kitap odunc alinmis mi)
            user.returnBook(book); //Kullanicinin borrowedList'inden bu kitabi cikar
book.setBorrowed(false); //Kitabin durumunu odunc alinmamis sekilde guncelledim
            user.setBalance(user.getBalance()+50); //Kullaniciya +50TL para iadesi
            borrowedBooks.remove(id); //borrowedBooks Set'inden bu ID'li kitabi cikar
            System.out.println("Kitap iadesi basarili bir sekilde gerceklesti!: "+ book.getName()+ " Bakiyeniz: "+user.getBalance()+" TL.");
            return  true;

        }else {
            System.out.println("Kitap iade islemi basarisiz oldu! ");
            return  false;
        }

    }


    }




