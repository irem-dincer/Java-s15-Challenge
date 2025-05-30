/* Interface Özellikleri:

 Contract Definition → Library sınıfının hangi metodları implement etmesi gerektiği
Abstract Methods → Sadece signature, implementation yok
 public by default → Tüm metodlar otomatik public */


package com.workintech.Services;

import com.workintech.Model.Book;
import com.workintech.Model.Librarian;
import com.workintech.Model.User;

import java.util.List;
import java.util.Scanner;

public interface BookServices {       // <- "Interface Abstraction"

    // <- " Methodlar"
    void addBook(Librarian librarian); //access e librarian sahip
    void updateBook(String id, Scanner scanner);
    void removeBook(Librarian librarian); //access e librarian sahip
    Book getBookById(String id);


    List<Book> getBookByName(String name);
    List<Book> getBookByAuthor(String authorName);
    List<Book> getBooksByCategory(String category);
    boolean borrowBook(User user, String idD);
    boolean  returnBook(User user, String id);


}
