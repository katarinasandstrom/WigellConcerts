package com.sandstrom.Entities;

import org.hibernate.annotations.NaturalId;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Arena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int arenaId;

    @NaturalId
    @Column(name = "arenaName")
    private String arenaName;

    @Column(name = "indoorOrOutdoor", length = 10)
    private String indoorOrOutdoor;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "arenaAddress")
    private Address address;

    @OneToMany(mappedBy = "arena", cascade = CascadeType.ALL)
    private List<Concert> concerts;

    public Arena() {
    }

    public Arena(String arenaName, String indoorOrOutdoor, Address address) {
        this.arenaName = arenaName;
        this.indoorOrOutdoor = indoorOrOutdoor;
        this.address = address;
    }

    public int getArenaId() {
        return this.arenaId;
    }

    public void setArenaId(int arenaId) {
        this.arenaId = arenaId;
    }

    public String getArenaName() {
        return this.arenaName;
    }

    public void setArenaName(String name) {
        this.arenaName = name;
    }

    public String getIndoorOrOutdoor() {
        return this.indoorOrOutdoor;
    }

    public void setIndoorOrOutdoor(String indoorOrOutdoor) {
        this.indoorOrOutdoor = indoorOrOutdoor;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

