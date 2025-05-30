package com.workintech.Model;

import java.util.List;
import java.util.Objects;

public class User extends  Person{    // <-"Inheritance iliskisi"


    private int borrowedBooksLimit; // Kac kitap ödünç alabilir (max 5)     // <-"User'a ozel field atamalari"
    private List<Book> borrowedBooks;  // Su an odunc aldığı kitaplar
    private double balance; // Hesap bakiyesi (ceza vs.)
    private boolean isLoggedIn=false; // Giris yapmıs mi?

    public User(String name, int age, int reader_id, String phoneNumber, String address, String email, int borrowedBooksLimit, List<Book> borrowedBooks, double balance){

        // <- "Super() çağrısı ile parent class'ın constructor'ını çağırıp, ortak özellikleri initialize ediyorum."
    super( name,  age,  reader_id,  phoneNumber,  address,  email);     // <- "Parent Constructor"
        this.borrowedBooksLimit=borrowedBooksLimit;
        this.borrowedBooks=borrowedBooks;                            // <- "Child'a ozel atamalar"
        this.balance=0.0;


    }
    //Kullanici girisi yapmak icin  authenticate methodu, Kullanici ismi ve id'si kontrol ediliyor.
    public boolean authenticate(String enteredName, int enteredId){

        if(this.getName().equals(enteredName) && this.getReader_id()==enteredId){ //getName() ve getReader_id() methodlari Person sinifindan alindi.
            isLoggedIn=true; //Bilgiler dogruysa login olarak isaretle
            return  true;

        }
        else {
            isLoggedIn=false; //Bilgiler yanlis, giris yapmamis sekilde.
            return false;
        }

    }

    public void setLoggedIn(boolean loggedIn) { // Kullanıcının giriş durumunu değiştiren setter method
        isLoggedIn = loggedIn;
    }


    public boolean isLoggedIn(){ //Kullanicinin giris yapip yapmadigini kontrol eden getter method.
        return this.isLoggedIn;
    }

    public void setBorrowedBooksLimit(int borrowedBooksLimit) { //Odunc alinabilir kitap limitini degistiren setter method
        this.borrowedBooksLimit = borrowedBooksLimit;
    }

    public int getBorrowedBooksLimit() { //Odunc alinabilir kitap limitini degistiren getter method
        return this.borrowedBooksLimit;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) { // Odunc alinan kitapların listesini degistiren setter method
        this.borrowedBooks = borrowedBooks;
    }

    public List<Book> getBorrowedBooks() { // Odunc alinan kitapların listesini degistiren getter method
        return this.borrowedBooks;
    }

    public void setBalance(double balance) {   // Kullanicinin hesap bakiyesini degistiren setter method
        this.balance = balance;
    }

    public double getBalance() { // Kullanicinin hesap bakiyesini degistiren getter method
        return this.balance;
    }
    //Kitap iade etme methodu;

    public void returnBook(Book book){
        //Once kullanicinin bu kitabi alip almadigi kontrol edilir.

        if(borrowedBooks.contains(book)){ //kitap, odunc alinanlar listesinde ise;
            borrowedBooks.remove(book); //Kitap odunc alinanlar listesinden cikarilir
            book.setBorrowed(false); //Kitap "odunc alinmamis" sekilde isaretlenir. (Books sinifinda tanimlanan setBorrowed(), composition)
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "borrowedBooksLimit=" + borrowedBooksLimit +
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        User user = (User) object;
        return borrowedBooksLimit == user.borrowedBooksLimit && Objects.equals(borrowedBooks, user.borrowedBooks); /// Odunc kitap limiti ve ödunc alınan kitaplar ayniysa esit kabul et
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), borrowedBooksLimit, borrowedBooks);
    }
}
