package com.workintech.Model;

import java.util.Objects;

public class Librarian extends Person {
    private String password; //Kutuphaneci sifresi
    private boolean isLoggedIn=false; //Kutuphaneci giris durumu

    public Librarian(String name, int age, int reader_id, String address, String phoneNumber, String email, String password){
        super(name,age,reader_id,address,phoneNumber,email);
        this.password=password; //Kutuphaneciye ozel sifre
    }

    public String getPassword() { //SILEBILIRIM!!
        return password;
    }

    public void setLoggedIn(boolean loggedIn) {  // Giris durumunu degistir
        isLoggedIn = loggedIn;
    }

    public boolean isLoggedIn() { // Giris durumunu kontrol et
        return this.isLoggedIn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), password);
    }

    @Override
    public String toString() {
        return "Librarian{password='" + password + "'}";  // Şifreyi göster
    }

    @Override
    public boolean equals(Object object) { // EKLEDIM!!!!
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Librarian librarian = (Librarian) object;
        return Objects.equals(password, librarian.password);
    }
}
