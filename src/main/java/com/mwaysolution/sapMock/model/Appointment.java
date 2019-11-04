package com.mwaysolution.sapMock.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "APPOINTMENT")
public class Appointment {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "GUID", unique = true, nullable = false, length = 45)
    private String id;

    @Column(name = "TIMEZONE", nullable = false, length = 128)
    private String timeZone;
    @Column(name = "ACT_LOCATION", length = 256)
    private String location;
    @Column(name = "TIMESTMAP_FROM", nullable = false)
    private String timeWhen;
    @Column(name = "TIMESTMAP_TO", nullable = false)
    private String timeTill;

    @Column(name = "SYNC_STATUS", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private AppointmentSyncStatus syncStatus;
    @Column(name = "CREATION_DATE", nullable = false)
    private ZonedDateTime creationDate;
    @Column(name = "MODIFICATION_DATE", nullable = false)
    private ZonedDateTime modificationDate;
    @Column(name = "REMINDER")
    private int reminder;

    @Column(name = "TITLE", nullable = false, length = 128)
    private String title;
    @Column(name = "DESCRIPTION", nullable = false, length = 2048)
    private String description;
    @Column(name = "DOMINANT", nullable = false)
    private boolean dominant;

    @Column(name = "PRIVAT", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private Privat privat;
    @Column(name = "SHOW_AS", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private ShowAs showAs;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORGANIZER", referencedColumnName = "id", nullable = false)
    private User creator;


//    TODO: wtf
    @OneToMany()
    private Set<String> participants;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeWhen() {
        return timeWhen;
    }

    public void setTimeWhen(String timeWhen) {
        this.timeWhen = timeWhen;
    }

    public String getTimeTill() {
        return timeTill;
    }

    public void setTimeTill(String timeTill) {
        this.timeTill = timeTill;
    }

    public AppointmentSyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(AppointmentSyncStatus syncStatus) {
        this.syncStatus = syncStatus;
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

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDominant() {
        return dominant;
    }

    public void setDominant(boolean dominant) {
        this.dominant = dominant;
    }

    public Privat getPrivat() {
        return privat;
    }

    public void setPrivat(Privat privat) {
        this.privat = privat;
    }

    public ShowAs getShowAs() {
        return showAs;
    }

    public void setShowAs(ShowAs showAs) {
        this.showAs = showAs;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<String> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<String> participants) {
        this.participants = participants;
    }
}
