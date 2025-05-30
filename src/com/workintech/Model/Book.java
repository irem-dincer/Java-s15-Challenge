package com.workintech.Model;

import com.workintech.Enum.BookCategories;

import java.util.Objects;

public class Book {
    private String name; //Kitabin adi           // <- "Private Fields"
    private  String id; //Kitabin kimlik numarasi
    private Author author; //Kitabin yazari (Author class'indan)   // <- "Composition relationship"
    private boolean isBorrowed; //Kitap suan odunc alinmis mi       // <-  "State management icin"
    private BookCategories bookCategories; //Kitabin kategorisi (Enum'dan gelir)

public Book(String name, String id, Author author, boolean isBorrowed, BookCategories bookCategories){
    this.name=name;
    this.id=id;
    this.author=author;
    this.isBorrowed=isBorrowed;
    this.bookCategories=bookCategories;
}

// "Getter&Setter Methodlari"

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return this.author;
    }

    // isBorrowed icin getter/setter (User sinifinda kullaniliyor!)
    public void setBorrowed(boolean borrowed) {
        this.isBorrowed = borrowed; // Odunc durumunu degistir (User.returnBook() bunu kullaniyor!)
    }

    public boolean isBorrowed() {
        return this.isBorrowed; // Odunc durumunu döndür
    }

    public void setBookCategories(BookCategories bookCategories) {
        this.bookCategories = bookCategories; //Kategoriyi degistir.
    }

    public BookCategories getBookCategories() {
        return this.bookCategories; //Kategoriyi dondur (Enums'taki kategori)
    }

    @Override
    public boolean equals(Object object) { //  Sadece ID'ye bakarak esitlik kontrolu
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Book book = (Book) object;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", author=" + author +
                ", isBorrowed=" + isBorrowed +
                ", bookCategories=" + bookCategories +
                '}';
    }
}
