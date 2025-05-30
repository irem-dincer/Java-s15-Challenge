package com.workintech.Main;

import com.workintech.Enum.BookCategories;
import com.workintech.Model.Book;
import com.workintech.Model.Librarian;
import com.workintech.Model.Library;
import com.workintech.Model.User;

import java.util.*;

public class Main {
    private static Map<Integer, User> users = new HashMap<>();// Kayıtlı kullanıcılar (ID → User eşleşmesi)
    private static User currentUser;//Suan giris yapmis kullanici
    private static Librarian currentLibrarian; //giris yapmis kutuphaneci


    public static void main(String[] args) {

        Library library = new Library(new HashSet<>(), new HashMap<>(), 1000);//Kutuphane nesnesi olusturdum (1000TL baslangic bakiyesi)
        Scanner scanner = new Scanner(System.in); //Konsol girisi icin tarayici
        Librarian librarian = new Librarian("irem", 24, 0121, "yenimahalle", "5435718709", "irem.dincer02@gamil.com", "0121abc");
        Map<String, Book> bookList = new HashMap<>(); //Kitap koleksiyonu
        User user = new User("irem", 24, 01, "05435718709", "yenimahalle", "irem.dncr@gmail.com", 5, null, 0.0);


        while (true) {  //Sonsuz dongu-surekli menu gosterir
            System.out.println("Lutfen bir secenek secin:");
            System.out.println("1. Kullanici Olarak Kaydol"); //Kullanici kaydi
            System.out.println("2. Kullanici Girisi"); //Kullanici girisi
            System.out.println("3. Kutuphaneci Girisi"); //Kutuphaneci girisi
            System.out.println("4. Kitap Ekle (Yalnizca Kutuphaneci)"); //Kitap ekleme(Sadece kutuphaneci)
            System.out.println("5. Kitap Odunc Al");  //Kitap odunc alma
            System.out.println("6. Kitabi Iade Et"); //Kitap iade etme
            System.out.println("7. Kitaplari Listele"); //Kitap arama islemleri
            System.out.println("8. Kitabi Kaldir (Yalnizca Kutuphaneci)"); //Kitap silme(Sadece kutuphaneci)
            System.out.println("9. Kitabi Guncelle (Yalnizca Kutuphaneci)"); //Kitap guncelleme (Sadece kutuphaneci)
            System.out.println("10. Cikis"); //Programdan cikis

            int choice = scanner.nextInt(); //Kullanici secimini al
            scanner.nextLine(); //Buffer temizligi


            switch (choice) {
                case 1:
                    registerUser(scanner); //registerUser methodunu cagir

                    break;

                case 2:
                    user = loginUser(scanner); //loginUser methodunu cagir, donus degeri user'a atansin
                    break;


                case 3:
                    librarian = loginLibrarian(scanner, librarian); //Kutuphaneci dogrulamasi
                    break;

                case 4:
                    library.addBook(librarian); //library sinifinin addBook methodunu cagir. (Sadece kutuphaneci kitap ekleyebilir)
                    break;

                case 5:
                    if (user != null && user.isLoggedIn()) {    //Yetkilendirme Kontrolu
                        System.out.println("Odunc almak istediginiz kitap ID'sini giriniz: ");
                        String bookId = scanner.nextLine(); //Kitap ID'si ile giris
                        boolean success = library.borrowBook(user, bookId); //Library method cagirisi
                        if (success) {
                            System.out.println("Kitap basarili bir sekilde odunc alindi!");
                        } else {
                            System.out.println("Kitap odunc alma islemi basarisiz!");

                        }
                    } else {
                        System.out.println("Lutfen once kullanici olarak giris yapin. Kayitli degilseniz, giris yapmadan once lutfen kayit olun.");

                    }
                    break;

                case 6:
                    System.out.println("İade etmek istediginiz kitabın ID'sini giriniz:");
                    String returningBookId = scanner.nextLine();
                    library.returnBook(user, returningBookId);//Direkt method cagrisi
                    break;

                case 7:
                    System.out.println("Lutfen yapmak istediginiz islemi seciniz: ");
                    System.out.println("1. Kitaplari kimlige gore listele"); // ID'ye göre arama
                    System.out.println("2. Kitaplari isme gore listele"); // İsme göre arama
                    System.out.println("3. Kitaplari yazar adina gore listele"); // Yazara göre arama
                    System.out.println("4. Kitaplari kategorilerine gore listele"); // Kategoriye göre arama
                    int bookListingCriteria = scanner.nextInt();
                    scanner.nextLine();  //Buffer temizligi


                    switch (bookListingCriteria) {
                        case 1:
                            System.out.println("Lutfen kitap ID'sini giriniz: ");
                            String bookId = scanner.nextLine();
                            library.getBookById(bookId);//Direkt method cagrisi
                            break;


                        case 2:
                            System.out.println("Lutfen kitabin adini giriniz: ");
                            String bookName = scanner.nextLine();
                            library.getBookByName(bookName);//Direkt method cagrisi
                            break;

                        case 3:
                            System.out.println("Lutfen yazarin adini giriniz: ");
                            String authorName = scanner.nextLine();
                            List<Book> booksByAuthor = library.getBookByAuthor(authorName);//Donus degeri isleme
                            if (!booksByAuthor.isEmpty()) { //Sonuc kontrolu
                                System.out.println(authorName + " adli yazarin kitaplari: ");
                                for (Book book : booksByAuthor) {
                                    System.out.println(book.getName());//Sonuclari goster
                                }
                            }

                            break;

                        case 4:
                            System.out.println("Bunlar kitap kategorileridir: " + Arrays.toString(BookCategories.values()));
                            System.out.println("Lutfen kitap kategorisini giriniz");
                            String bookCategory = scanner.nextLine();
                            List<Book> booksByCategories = library.getBooksByCategory(bookCategory);

                            if (booksByCategories.isEmpty()) {
                                System.out.println("Bu kategoride kitap bulunamadi: " + bookCategory);

                            } else {
                                System.out.println("Kategoriye gore kitap listesi: " + bookCategory);
                                for (Book book : booksByCategories) {
                                    System.out.println(book.toString());
                                }
                            }
                            break;


                        default:
                            System.out.println("Gecersiz secenek!");


                    }
                    break;

                case 8:
                    library.removeBook(librarian);//Kutuphaneci yetkilendirmesi gerekli
                    break;

                case 9:
                    System.out.println("Lütfen güncellemek istediğiniz kitabın ID'sini giriniz:");
                    String bookIdToUpdate = scanner.nextLine();
                    library.updateBook(bookIdToUpdate, scanner);//Scanner nesnesini parametre olarak gecir
                    break;

                case 10:
                    System.out.println("Hoscakal!");
                    scanner.close();//Kaynak temizligi
                    System.exit(0);//Program sonlandirilmasi
                    break;

                default:
                    System.out.println("Gecersiz secim. Lutfen bir daha secim yapin!");

            }
        }
    }

    public static void registerUser(Scanner scanner) {
        String name = null;
        int reader_id = 0;
        String email;


        while (true) {//Giris dogrulama dongusu

            try {
                System.out.println("Isminizi giriniz: ");//Iism dogrulamasi
                name = scanner.nextLine();

                if (!name.matches("^[a-zA-Z\\s]+$")) {//regex dogrulamasi
                    throw new IllegalArgumentException("Gecersiz isim, Lutfen gecerli bir isim giriniz!");
                }
                System.out.println("ID'sini giriniz:");
                String idInput = scanner.nextLine();
                reader_id = Integer.parseInt(idInput); //String'ten integer'a donusum

                if (reader_id <= 0) {//Is kurali dogrulamasi
                    throw new NumberFormatException("Gecersiz ID. Lutfen gecerli bir ID giriniz!");

                }

                System.out.println("Email'i giriniz.");
                email = scanner.nextLine();

                if (!email.contains("@")) {//Basic email dogrulamasi
                    throw new IllegalArgumentException("Gecersiz email adresi. Lutfen gecerli bir email adresi giriniz!");

                }
                break;//Tum dogrulamalar basarili ise gonduden cikiyorum


            } catch (NumberFormatException e) {//HATA ISLEME
                System.err.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }

        //Kullanici olusturma
        User newUser = new User(name, 0, reader_id, "", "", email, 5, new ArrayList<>(), 1000);
        users.put(reader_id, newUser);//Static map'e ekleme
        System.out.println("Kullanici kaydi basarili!");

    }

    public static User loginUser(Scanner scanner) {
        System.out.println("Adinizi giriniz: ");
        String name = scanner.nextLine();
        System.out.println("ID' nizi giriniz: ");
        int reader_id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Email adresinizi giriniz: ");
        String email = scanner.nextLine();


        User user = users.get(reader_id);//Map aramasi
        if (user != null && user.getName().equals(name) && user.getEmail().equals(email)) {
            System.out.println("Kullanici girisi basarili!");
            user.setLoggedIn(true);//Oturum durumu guncelleme

            return user;//Dogrulanmis kullanici donusu


        } else {
            System.out.println("Kullanici girisi basarisiz! Hatali kullanici adi,kimlik veya email");
            return null;//Dogrulama basarisizligi
        }
    }

    public static Librarian loginLibrarian(Scanner scanner, Librarian librarian) {
        System.out.println("Adinizi giriniz: ");
        String enteredUsername = scanner.nextLine();
        System.out.println("Sifrenizi giriniz: ");
        String enteredPassword = scanner.nextLine();


        if (librarian != null && librarian.getName().toLowerCase().equals(enteredUsername) && librarian.getPassword().equals(enteredPassword)) {
            System.out.println("Kutuphaneci girisi basarili!");
            librarian.setLoggedIn(true);//Dogrulama basarisi
            return librarian;


        } else {
            System.out.println("Kutuphaneci girisi basarisiz! Hatali kullanici adi veya sifre");
            librarian.setLoggedIn(false);//Dogrulama basarisizligi
        }
        return librarian;// // Her durumda librarian nesnesi dondur
    }
}



