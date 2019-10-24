package com.mwaysolution.sapMock.model;


import javax.persistence.*;
import java.time.ZonedDateTime;

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
    @Column(name = "EMAIL", nullable = false, length = 128)
    private String email;
    @Column(name = "TIMEZONE", nullable = false, length = 128)
    private String timeZone;
    @Column(name = "CREATION_DATE", nullable = false, length = 128)
    private ZonedDateTime creationDate;
    @Column(name = "MODIFICATION_DATE", nullable = false, length = 128)
    private ZonedDateTime modificationDate;

    @Column(name = "FIRST_NAME", length = 45)
    private String firstName;
    @Column(name = "LAST_NAME", length = 45)
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

    public String getTimeZone() {
        return timeZone;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(ZonedDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setTimeZone(String timeZone) {
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
