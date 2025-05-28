package com.workintech.Model;

import java.util.Objects;

// Bu sınıf abstract --> doğrudan new Person() ile nesne oluşturamam.
// Yalnızca inheritance amacıyla kullanıcam;  User ve Librarian sınıfları bu sınıftan türeyecek.
public abstract  class Person {

    private String name;
    private int age;
    private int reader_id;

    private String phoneNumber;
    private String address;
    private  String email;

    public Person(String name, int age, int reader_id, String phoneNumber,String address,String email){ //Tum alanlari parametre olarak aldim.
        this.name=name;
        this.age=age;
        this.reader_id=reader_id;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.email=email;

    }
    //Sadece okuma izni var, degistirme yok! (Encapsulation)

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getReader_id() {
        return reader_id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

//equals methodu: Person'larin phoneNumber ve email esit ise ayni kabul et.
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Person person = (Person) object;
        return Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, email);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", reader_id=" + reader_id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
