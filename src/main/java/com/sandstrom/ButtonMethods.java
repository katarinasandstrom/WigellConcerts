package com.sandstrom;
import com.sandstrom.Entities.*;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Date;
import com.sandstrom.Entities.Customer;

import java.time.LocalDate;
import java.util.List;

import static com.sandstrom.Main.selectedConcert;

public class ButtonMethods {

    public static boolean login(String email, String password) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            try {
                session.beginTransaction();

                // Kolla så att användarnamnet finns i databasen.
                Customer customer = session.byNaturalId(Customer.class).using("email", email).load();
                WC admin = session.createQuery("FROM WC WHERE userName = :userName AND passWord = :passWord", WC.class)
                        .setParameter("userName", email)
                        .setParameter("passWord", password)
                        .uniqueResult();

                // Kontrollera om användaren är en admin och logga in
                if (admin != null) {
                    session.getTransaction().commit();
                    return true; // Inloggning som admin lyckades
                } else if (customer != null && customer.getPassWord().equals(password)) {
                    session.getTransaction().commit();
                    return true; // Inloggning som kund lyckades
                } else {
                    return false; // Inloggning misslyckades eller något gick fel
                }
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public static boolean isAdmin(String email) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            WC admin = session.createQuery("FROM WC WHERE userName = :userName", WC.class)
                    .setParameter("userName", email)
                    .uniqueResult();

            session.getTransaction().commit();

            return admin != null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static void registerNewCustomer(Label labelDuplicateCustomer, String firstName, String lastName, Date dateOfBirth, String phoneNr,
                                           String streetAddress, int StreetNumber, String zipNr, String city,
                                           String email, String password) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            //Kontroll om emailadressen redan finns i databasen
            Query query = session.createQuery("FROM Customer WHERE email = :email");
            query.setParameter("email", email);
            Customer existingCustomer = (Customer) query.uniqueResult();

            if (existingCustomer != null) {
                //Det finns redan en kund med den mailen i databasen
                labelDuplicateCustomer.setText("En användare med den angivna e-postadressen " +
                        "finns redan i systemet.");
                return; //Regga ingen ny kund
            }

            // Skapa en ny adress och kund
            Address address = new Address(streetAddress, StreetNumber, zipNr, city);
            Customer customer = new Customer(firstName, lastName, dateOfBirth, phoneNr,
                    password, email, address);
            session.persist(address);
            session.persist(customer);

            transaction.commit(); // Utför transaktionscommit
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            sessionFactory.close();
        }
    }


    public static void registerNewConcert(Label labelErrorRegConcert, ObservableList<Concert> concertList, String artistName, String concertDateString, int ticketPrice, int age, String arenaName) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            LocalDate concertDate = LocalDate.parse(concertDateString);

            Arena arena = session.byNaturalId(Arena.class).using("arenaName", arenaName).load();

            if (arena == null) {
                labelErrorRegConcert.setText("Det finns ingen arena med det namnet registrerad.");
              return; // regga inte konsert
            }

            Concert concert = new Concert(artistName, concertDate, ticketPrice, age, arena);
            concertList.add(concert);
            session.persist(concert);

            session.getTransaction().commit();
            labelErrorRegConcert.setText("Konsert registrerad");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }

    }



    public static void deleteCustomer(String email) {


        Transaction transaction = null;
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Hämta kunden baserat på e-postadressen
            Query query = session.createQuery("FROM Customer WHERE email = :email");
            query.setParameter("email", email);
            Customer customer = (Customer) query.getSingleResult();

            if (customer != null) {
                session.delete(customer);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }


    public static void buyTicket(String email) {

        if (selectedConcert != null) {

            Transaction transaction = null;
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            try {
                Session session = sessionFactory.openSession();
                transaction = session.beginTransaction();

                // Hämta kunden baserat på e-postadressen
                Query query = session.createQuery("FROM Customer WHERE email = :email");
                query.setParameter("email", email);
                Customer customer = (Customer) query.getSingleResult();

                if (customer != null) {

                    Booking booking = new Booking();
                    booking.setConcertId(selectedConcert.getConcertId());
                    booking.setCustomer(customer);
                    customer.getBookings().add(booking);

                    WC wc = session.get(WC.class, 1);

                    selectedConcert = session.load(Concert.class, selectedConcert.getConcertId());

                    session.save(booking);
                    wc.getConcerts().add(selectedConcert);

                    transaction.commit();

                } else {
                    System.out.println("Kunden med e-postadressen " + email + " finns inte i databasen.");
                }

            }
            catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }


    public static void showConcertCustomer(TextArea textArea) {
        Transaction transaction = null;
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Hämta alla konserter
            List<Concert> concerts = session.createQuery("FROM Concert ORDER BY concertDate", Concert.class).list();

            // Loopa igenom varje konsert och skriv ut konsertinfo
            for (Concert concert : concerts) {
                textArea.appendText("Konsert: " + concert.getArtistName() + ", Datum: " + concert.getConcertDate() + "\n");

                // Hämta alla bokningar för den här konserten
                List<Booking> bookings = session.createQuery("FROM Booking WHERE concertId = :concertId", Booking.class)
                        .setParameter("concertId", concert.getConcertId())
                        .list();

                // Loopa igenom kunder som har köpt biljetter till den här konserten och lista
                for (Booking booking : bookings) {
                    Customer customer = booking.getCustomer();
                    textArea.appendText(" - Kund: " + customer.getFirstName() + " " + customer.getLastName() + "\n");
                }
                textArea.appendText("\n");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }

    public static void loadConcertsFromDatabase(List<Concert> concertList) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Concert> criteriaQuery = session.getCriteriaBuilder().createQuery(Concert.class);
            criteriaQuery.from(Concert.class);
            List<Concert> concerts = session.createQuery(criteriaQuery).getResultList();
            concertList.clear(); // Rensa befintliga konserter i listan
            concertList.addAll(concerts); // Lägg till konserter från databasen i listan
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}







