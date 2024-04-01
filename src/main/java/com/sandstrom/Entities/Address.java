
package com.sandstrom.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    @Column(name = "street", length = 30)
    private String street;
    @Column(name = "streetNumber", length = 10)
    private int streetNumber;
    @Column(name = "zip", length = 10)
    private String zip;
    @Column(name = "city", length = 20)
    private String city;

    public Address() {
    }

    public Address(String street, int streetNumber, String zip, String city) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.zip = zip;
        this.city = city;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return this.streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
