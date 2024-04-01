package com.sandstrom.Entities;



import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int concertId;
    @Column(name = "artistName", length = 50)
    private String artistName;
    @Column(name = "concertDate")
    private LocalDate concertDate;
    @Column(name = "ticketPrice")
    private int ticketPrice;
    @Column(name = "age")
    private int age;
    @ManyToOne
    @JoinColumn(name = "arenaId") // Namnet på kolumnen som lagrar arenans ID
    private Arena arena;

    /*@ManyToOne
    private WC wc;

*/
/*
    @ManyToOne
    @JoinColumn(
            name = "arenaId"
    )
*/


    @ManyToMany
    @JoinTable(name = "joinConcertIdBookingId",
            joinColumns = {@JoinColumn(name = "concertId")},
            inverseJoinColumns = {@JoinColumn(name = "bookingId")})
    private List<Booking> bookings = new ArrayList<>();

    public Concert() {
    }

    public Concert(String artistName, LocalDate concertDate, int ticketPrice, int age, Arena arena) {
        this.artistName = artistName;
        this.concertDate = concertDate;
        this.ticketPrice = ticketPrice;
        this.age = age;
        this.arena = arena;
    }

    public int getConcertId() {
        return this.concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public LocalDate getConcertDate() {
        return this.concertDate;
    }

    public void setConcertDate(LocalDate concertDate) {
        this.concertDate = concertDate;
    }

    public int getTicketPrice() {
        return this.ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public Arena getArena() {
        return this.arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    }

