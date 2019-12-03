package com.mwaysolution.sapMock;

import com.mwaysolution.sapMock.model.*;
import com.mwaysolution.sapMock.service.AppointmentServiceImpl;
import com.mwaysolution.sapMock.service.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SapMockApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AppointmentServiceTest {

    private ZonedDateTime time = ZonedDateTime.now();
    private ZonedDateTime timeModificationDate = ZonedDateTime.now();


    private final String SAP_USERNAME = "SapUsername";
    private final String EXCHANGE_USERNAME = "ExchangeUsername";
    private final String EXCHANGE_DOMAIN = "ExchangeDomain";
    private final String TIME_ZONE = "+02:00";
    private final UserRegistrationStatus REGISTRATION_STATUS = UserRegistrationStatus.INIT;
    private final String FIRST_NAME = "John";
    private final String LAST_NAME = "Doe";

    private final String APPOINTMENT_TIME_ZONE = "+01:00";
    private final String APPOINTMENT_LOCATION = "Kosice";
    private final ZonedDateTime APPOINTMENT_DATE_TIME_FROM = time.plusDays(1);
    private final ZonedDateTime APPOINTMENT_DATE_TIME_TO = time.plusDays(2);
    private final AppointmentSyncStatus APPOINTMENT_SYNC_STATUS = AppointmentSyncStatus.NEW;
    private final ZonedDateTime APPOINTMENT_CREATION_DATE = ZonedDateTime.now();
    private final ZonedDateTime APPOINTMENT_MODIFICATION_DATE = ZonedDateTime.now();
    private final int APPOINTMENT_REMINDER = 10;
    private final String APPOINTMENT_TITLE = "TITLE";
    private final String APPOINTMENT_DESCRIPTION = "DESCRIPTION";
    private final boolean APPOINTMENT_DOMINANT = true;
    private final Sensitivity APPOINTMENT_SENSITIVITY = Sensitivity.NORMAL;
    private final ShowAs APPOINTMENT_SHOW_AS = ShowAs.FREE;

    private Appointment appointment;
    private User user;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    AppointmentServiceImpl appointmentServiceImpl;


    @Before
    public void setUp() {
        user = createUser(SAP_USERNAME, EXCHANGE_USERNAME, EXCHANGE_DOMAIN, TIME_ZONE,
                REGISTRATION_STATUS, FIRST_NAME, LAST_NAME);
        user = userService.save(user);

        appointment = createAppointment(APPOINTMENT_TIME_ZONE, APPOINTMENT_LOCATION, APPOINTMENT_DATE_TIME_FROM, APPOINTMENT_DATE_TIME_TO, APPOINTMENT_SYNC_STATUS,
                APPOINTMENT_CREATION_DATE, APPOINTMENT_MODIFICATION_DATE, APPOINTMENT_REMINDER, APPOINTMENT_TITLE, APPOINTMENT_DESCRIPTION,
                APPOINTMENT_DOMINANT, APPOINTMENT_SENSITIVITY, APPOINTMENT_SHOW_AS, user);
        appointment = appointmentServiceImpl.save(appointment);
    }

    @After
    public void tearDown() {
        appointmentServiceImpl.get().forEach(appointment -> appointmentServiceImpl.deleteById(appointment.getId()));
        userService.get().forEach(user -> userService.deleteById(user.getId()));
    }

    @Test
    public void testSaveAppointment() {
        assertThat(appointment.getId()).isNotNull();
        assertThat(appointment.getTimeZone()).isEqualTo(APPOINTMENT_TIME_ZONE);
        assertThat(appointment.getLocation()).isEqualTo(APPOINTMENT_LOCATION);
        assertThat(appointment.getDateTimeFrom()).isNotNull();
        assertThat(appointment.getDateTimeTo()).isNotNull();
        assertThat(appointment.getSyncStatus()).isEqualTo(APPOINTMENT_SYNC_STATUS);
        assertThat(appointment.getCreationDate()).isNotNull();
        assertThat(appointment.getModificationDate()).isNotNull();
        assertThat(appointment.getReminder()).isEqualTo(APPOINTMENT_REMINDER);
        assertThat(appointment.getTitle()).isEqualTo(APPOINTMENT_TITLE);
        assertThat(appointment.getDescription()).isEqualTo(APPOINTMENT_DESCRIPTION);
        assertThat(appointment.isDominant()).isEqualTo(APPOINTMENT_DOMINANT);
        assertThat(appointment.getSensitivity()).isEqualTo(APPOINTMENT_SENSITIVITY);
        assertThat(appointment.getShowAs()).isEqualTo(APPOINTMENT_SHOW_AS);
        assertThat(appointment.getUser().getId()).isEqualTo(appointment.getUser().getId());
    }

    @Test
    public void testUpdateAppointment() {
        appointment.setTimeZone("+04:00");
        appointment.setShowAs(ShowAs.BUSY);
        appointment.setDescription("DescriptionDescription");
        appointment.setModificationDate(timeModificationDate);
        appointment.setTitle("");
        appointment.setLocation("Bratislava");
        appointment = appointmentServiceImpl.save(appointment);

        assertThat(appointment.getId()).isNotNull();
        assertThat(appointment.getTimeZone()).isEqualTo("+04:00");
        assertThat(appointment.getLocation()).isEqualTo("Bratislava");
        assertThat(appointment.getDateTimeFrom()).isNotNull();
        assertThat(appointment.getDateTimeTo()).isNotNull();
        assertThat(appointment.getSyncStatus()).isEqualTo(APPOINTMENT_SYNC_STATUS);
        assertThat(appointment.getCreationDate()).isNotNull();
        assertThat(appointment.getModificationDate()).isNotNull();
        assertThat(appointment.getReminder()).isEqualTo(APPOINTMENT_REMINDER);
        assertThat(appointment.getTitle()).isEqualTo(APPOINTMENT_TITLE);
        assertThat(appointment.getDescription()).isEqualTo("DescriptionDescription");
        assertThat(appointment.isDominant()).isEqualTo(APPOINTMENT_DOMINANT);
        assertThat(appointment.getSensitivity()).isEqualTo(APPOINTMENT_SENSITIVITY);
        assertThat(appointment.getShowAs()).isEqualTo(ShowAs.BUSY);
        assertThat(appointment.getUser().getId()).isEqualTo(appointment.getUser().getId());
        assertThat(appointment.getCreationDate()).isNotEqualTo(timeModificationDate);
    }

    @Test
    public void testDeleteAppointment() {
        appointmentServiceImpl.deleteById(appointment.getId());
        assertThat(appointmentServiceImpl.getById(appointment.getId())).isNull();
    }

    private Appointment createAppointment(String timeZone, String location, ZonedDateTime dateTimeFrom, ZonedDateTime dateTimeTo, AppointmentSyncStatus syncStatus,
                                          ZonedDateTime creationDate, ZonedDateTime modificationDate, int reminder, String title, String description, boolean dominant,
                                          Sensitivity sensitivity, ShowAs showAs, User user) {

        Appointment appointment = new Appointment();

        appointment.setTimeZone(timeZone);
        appointment.setLocation(location);
        appointment.setDateTimeFrom(dateTimeFrom);
        appointment.setDateTimeTo(dateTimeTo);
        appointment.setSyncStatus(syncStatus);
        appointment.setCreationDate(creationDate);
        appointment.setModificationDate(modificationDate);
        appointment.setReminder(reminder);
        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setDominant(dominant);
        appointment.setSensitivity(sensitivity);
        appointment.setShowAs(showAs);
        appointment.setUser(user);
        return appointment;
    }

    private User createUser(String sapUsername, String exchangeUsername, String exchangeDomain,
                            String timeZone, UserRegistrationStatus registrationStatus, String firstName, String lastName) {
        User user = new User();
        user.setSapUsername(sapUsername);
        user.setExchangeUsername(exchangeUsername);
        user.setExchangeDomain(exchangeDomain);
        user.setTimeZone(timeZone);
        user.setRegistrationStatus(registrationStatus);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
