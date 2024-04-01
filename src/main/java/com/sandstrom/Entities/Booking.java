package com.sandstrom.Entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @JoinColumn(name = "concertId")
    private int concertId;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "joinConcertIdBookingId",
            joinColumns = {@JoinColumn(name = "bookingId")},
            inverseJoinColumns = {@JoinColumn(name = "concertId")})
    private List<Concert>concerts = new ArrayList<>();

    public Booking(){

    }

    public Booking(int bookingId, int concertId, Customer customer, List<Concert> concerts) {
        this.bookingId = bookingId;
        this.concertId = concertId;
        this.customer = customer;
        this.concerts = concerts;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Concert> concerts) {
        this.concerts = concerts;
    }
}