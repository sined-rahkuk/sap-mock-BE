package com.mwaysolution.sapMock.model;


import javax.persistence.*;
import java.util.TimeZone;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String sapUsername;
    @Column(nullable = false)
    private String exchangeUsername;
    @Column(nullable = false)
    private String exchangeDomain;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private TimeZone timeZone;


    private String firstName;
    private String lastName;

    public Integer getId() {
        return id;
    }

    public String getSapUsername() {
        return sapUsername;
    }

    public void setSapUsername(String sapUsername) {
        this.sapUsername = sapUsername;
    }

    public String getExchangeUsername() {
        return exchangeUsername;
    }

    public void setExchangeUsername(String exchangeUsername) {
        this.exchangeUsername = exchangeUsername;
    }

    public String getExchangeDomain() {
        return exchangeDomain;
    }

    public void setExchangeDomain(String exchangeDomain) {
        this.exchangeDomain = exchangeDomain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
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
}
