package com.mwaysolution.sapMock.model;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "APPOINTMENT")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "SYNC_STATUS", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private AppointmentSyncStatus syncStatus;
    @Column(name = "CREATION_DATE", nullable = false)
    private ZonedDateTime creationDate;
    @Column(name = "MODIFICATION_DATE", nullable = false)
    private ZonedDateTime modificationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORGANIZER", referencedColumnName = "id", nullable = false)
    private User creator;
//    HOW TO INSERT A LIST OF PARTICIPANTS? god knows
}
