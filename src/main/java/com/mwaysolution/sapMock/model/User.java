package com.mwaysolution.sapMock.model;


import javax.persistence.*;
import java.util.TimeZone;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "SAP_USERNAME", nullable = false, length = 45)
    private String sapUsername;
    @Column(name = "EXCHANGE_USERNAME", nullable = false, length = 45)
    private String exchangeUsername;
    @Column(name = "EXCHANGE_DOMAIN", nullable = false, length = 45)
    private String exchangeDomain;
    @Column(name = "EMAIL", nullable = false, length = 45)
    private String email;
    @Column(name = "TIMEZONE", nullable = false)
    private TimeZone timeZone;

    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
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
