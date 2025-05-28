package com.workintech.Model;

import java.util.List;
import java.util.Objects;

public class Author {
    private String name; //Yazar adi
    private String id; // Yazarin kimligi
     private List<Book> books; //Yazarin kitaplari (Book class'i ile composition iliskisi)

    public Author(String name, String id, List<Book> books){
        this.name=name;
        this.id=id;
        this.books=books;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public boolean equals(Object object) { // Sadece ID'ye bakarak esitlik kontrolu
        if (object == null || getClass() != object.getClass()) return false;
        Author author = (Author) object;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    @Override
    public String toString() {
        return "Author: " +
                name + '\'' +
                ", id=" + id;
    }

}
