package com.sandstrom.Entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class WC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wcId;

    @Column(name = "wcName", length = 50)
    private String wcName;
    @Column(name = "passWord", length = 20)
    private String passWord;
    @Column(name = "userName", length = 20)
    private String userName;
    @OneToMany(
            targetEntity = Concert.class,
            mappedBy = "concertId",
            cascade = {CascadeType.ALL}
    )
    private List<Concert> concerts;
    @OneToMany(
            targetEntity = Customer.class,
            mappedBy = "customerId",
            cascade = {CascadeType.ALL}
    )
    private List<Customer> customers;
    /*@ManyToOne
    @JoinColumn(
            name = "customerId"
    )
    private Customer customer;
    @ManyToOne
    @JoinColumn(
            name = "concertId"
    )
    private Concert concert;
*/

    public WC() {
    }

    public WC(String wcName, String passWord, String userName) {
        this.wcName = wcName;
        this.passWord = passWord;
        this.userName = userName;
    }

    public int getWcId() {
        return this.wcId;
    }

    public void setWcId(int wcId) {
        this.wcId = wcId;
    }

    public String getWcName() {
        return this.wcName;
    }

    public void setWcName(String wcName) {
        this.wcName = wcName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Concert> getConcerts() {
        return this.concerts;
    }

    public void setConcerts(List<Concert> concerts) {
        this.concerts = concerts;
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

   /* public Concert getConcert() {
        return this.concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }*/
}
