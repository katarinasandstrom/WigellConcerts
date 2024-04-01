

package com.sandstrom.Entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import java.sql.Date;
import java.util.List;


@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private int customerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerAddress")

    private Address address;

    @Column (name = "firstName", length = 20)
    private String firstName;
    @Column (name = "lastName", length = 20)
    private String lastName;
    @Column
    private Date dateOfBirth;
    @Column
    private String phoneNr;
    @Column
    private String passWord;
    @Column
    @NaturalId
    private String email;
/*
    ag gjorde email till @NaturalId för att kunna använda som användarnamn. Chatgpt säger
   att det kan vara smidigt att använda naturalId som en typ av affärsnyckel som kan användas
   för att identifiera entiteter snabbt och enkelt utan att använda den genererade
   primärnyckeln.
    */
    /*@ManyToOne
    @JoinColumn(name = "wcId")
    private WC wc;
*/
    @OneToMany (targetEntity = Booking.class, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public Customer(){
    }

    public Customer(String firstName, String lastName, Date dateOfBirth, String phoneNr, String passWord, String email, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNr = phoneNr;
        this.passWord = passWord;
        this.email = email;
        this.address = address;
    }




    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public Date getDateOfBirth() {
        return dateOfBirth;
    }


    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public String getPhoneNr() {
        return phoneNr;
    }


    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }


    public String getPassWord() {
        return passWord;
    }


    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email ) {
        this.email = email;
    }
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}




