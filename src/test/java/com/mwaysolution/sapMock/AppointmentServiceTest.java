package com.mwaysolution.sapMock;

import com.mwaysolution.sapMock.model.*;
import com.mwaysolution.sapMock.service.AppointmentService;
import com.mwaysolution.sapMock.service.AppointmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = SapMockApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")

public class AppointmentServiceTest {

    @Autowired
    AppointmentServiceImpl appointmentServiceImpl;

    @MockBean
    AppointmentService appointmentService;


    @Test
    public void saveValidAppointment() {
        User user1 = createUser(null, "TestValidSapName", "TestValidExName",
                "TestValidDomain", "+02:00", null, null,
                UserRegistrationStatus.INIT, "TestValidName", "TestValidSurname");

        ZonedDateTime time = ZonedDateTime.now();
        Appointment appointmentCreateValid = createAppointment(null, "+02:00", "Kosice", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), 10, "Title", "Description", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        when(appointmentService.save(refEq(appointmentCreateValid))).thenReturn(appointmentCreateValid);
        appointmentServiceImpl.save(appointmentCreateValid);

        verify(appointmentService).save(any(Appointment.class));
        verify(appointmentService, times(1)).save(any());

    }

    @Test
    public void updateValidAppointment() {
        User user1 = createUser(null, "TestValidSapName", "TestValidExName",
                "TestValidDomain", "+02:00", null, null,
                UserRegistrationStatus.INIT, "TestValidName", "TestValidSurname");

        ZonedDateTime time = ZonedDateTime.now();
        Appointment appointmentUpdateValid = createAppointment(null, "+03:00", "Kiev", time.plusDays(3), time.plusDays(5), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), 15, "Title", "Description", true, Sensitivity.CONFIDENTIAL, ShowAs.BUSY, user1);

        when(appointmentService.save(refEq(appointmentUpdateValid))).thenReturn(appointmentUpdateValid);

    }

    @Test
    public void updateInvalidAppointmentTimeZone(){
        User user1 = createUser(null, "TestInvalidTimeZoneSapName", "TestInvalidTimeZoneExName",
                "TestInvalidTimeZoneDomain", "+01:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidTimeZoneName", "TestInvalidTimeZoneSurname");
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentInvalidTimeZone = createAppointment("66", "", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), 15, "Title", "DescriptionTimeZone", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        ArgumentCaptor<Appointment> argument = ArgumentCaptor.forClass(Appointment.class);
        appointmentService.save(appointmentInvalidTimeZone);
        verify(appointmentService).save(argument.capture());
        assertEquals("", argument.getValue().getTimeZone());


    }

    @Test
    public void updateInvalidAppointmentDateFrom(){
        User user1 = createUser(null, "TestInvalidDateFromSapName", "TestInvalidDateFromExName",
                "TestInvalidDateFromDomain", "+02:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidDateFromName", "TestInvalidDateFromSurname");
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentInvalidDateFrom = createAppointment("666", "+02:00", "Bratislava", null, time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), 4, "Title", "DescriptionDateFrom", true, Sensitivity.NORMAL, null, user1);

        ArgumentCaptor<Appointment> argument = ArgumentCaptor.forClass(Appointment.class);
        appointmentService.save(appointmentInvalidDateFrom);
        verify(appointmentService).save(argument.capture());
        assertNull(argument.getValue().getDateTimeFrom());
    }

    @Test
    public void updateInvalidAppointmentDateTo(){
        User user1 = createUser(null, "TestInvalidDateToSapName", "TestInvalidDateToExName",
                "TestInvalidDateToDomain", "+03:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidDateToName", "TestInvalidDateToSurname");
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentInvalidDateTo = createAppointment("45", "+01:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), 30, "Title", "DescriptionDateTo", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        ArgumentCaptor<Appointment> argument = ArgumentCaptor.forClass(Appointment.class);
        appointmentService.save(appointmentInvalidDateTo);
        verify(appointmentService).save(argument.capture());
        assertEquals(time.plusDays(2), argument.getValue().getDateTimeTo());
    }

    @Test
    public void updateInvalidAppointmentReminder(){
        User user1 = createUser(null, "TestInvalidReminderSapName", "TestInvalidReminderExName",
                "TestInvalidReminderDomain", "+04:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidName", "TestInvalidReminderSurname");
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentInvalidReminder = createAppointment("65", "+04:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), 0, "", "DescriptionReminder", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        ArgumentCaptor<Appointment> argument = ArgumentCaptor.forClass(Appointment.class);
        appointmentService.save(appointmentInvalidReminder);
        verify(appointmentService).save(argument.capture());
        assertEquals(0, argument.getValue().getReminder());
    }

    @Test
    public void updateInvalidAppointmentTitle(){
        User user1 = createUser(null, "TestInvalidTitleSapName", "TestInvalidTitleExName",
                "TestInvalidTitleDomain", "+05:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidTitleName", "TestInvalidTitleSurname");
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentInvalidTitle = createAppointment("", "+03:00", "Bratislava", time.plusDays(1), time.plusDays(2), null,
                ZonedDateTime.now(), 40, "Title", "", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        ArgumentCaptor<Appointment> argument = ArgumentCaptor.forClass(Appointment.class);
        appointmentService.save(appointmentInvalidTitle);
        verify(appointmentService).save(argument.capture());
        assertEquals("Title", argument.getValue().getTitle());
    }

    @Test
    public void updateInvalidAppointmentDescription(){
        User user1 = createUser(null, "TestInvalidDescriptionSapName", "TestInvalidDescriptionExName",
                "TestInvalidDescriptionDomain", "-01:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidDescriptionName", "TestInvalidDescriptionSurname");
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentInvalidDescription = createAppointment("666", "+03:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), 35, "", "", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        ArgumentCaptor<Appointment> argument = ArgumentCaptor.forClass(Appointment.class);
        appointmentService.save(appointmentInvalidDescription);
        verify(appointmentService).save(argument.capture());
        assertEquals("", argument.getValue().getDescription());
    }

    @Test
    public void updateInvalidAppointmentSensitivity(){
        User user1 = createUser(null, "TestInvalidSensitivitySapName", "TestInvalidSensitivityExName",
                "TestInvalidSensitivityDomain", "-02:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidSensitivityName", "TestInvalidSensitivitySurname");
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentInvalidSensitivity = createAppointment("", "+03:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), 25, "Title", "DescriptionSens", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        ArgumentCaptor<Appointment> argument = ArgumentCaptor.forClass(Appointment.class);
        appointmentService.save(appointmentInvalidSensitivity);
        verify(appointmentService).save(argument.capture());
        assertEquals(Sensitivity.NORMAL, argument.getValue().getSensitivity());
    }

    @Test
    public void updateInvalidAppointmentShowAs(){
        User user1 = createUser(null, "TestInvalidShowAsSapName", "TestInvalidShowAsExName",
                "TestInvalidShowAsDomain", "-03:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidShowAsName", "TestInvalidShowAsSurname");
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentInvalidShowAs = createAppointment("32", "+03:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), 12, "Title", "DescriptionShow", true, Sensitivity.NORMAL, null, user1);

        ArgumentCaptor<Appointment> argument = ArgumentCaptor.forClass(Appointment.class);

        appointmentService.save(appointmentInvalidShowAs);
        verify(appointmentService).save(argument.capture());
        assertNull(argument.getValue().getShowAs());
    }

    @Test
    public void updateInvalidAppointmentLocation(){
        User user1 = createUser(null, "TestInvalidLocationSapName", "TestInvalidLocationExName",
                "TestInvalidLocationDomain", "-04:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidLocationName", "TestInvalidLocationSurname");
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentInvalidLocation = createAppointment("86", "+03:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), 10, "Title", "", true, Sensitivity.NORMAL, ShowAs.FREE, user1);
        ArgumentCaptor<Appointment> argument = ArgumentCaptor.forClass(Appointment.class);

        appointmentService.save(appointmentInvalidLocation);
        verify(appointmentService).save(argument.capture());
        assertEquals("Bratislava", argument.getValue().getLocation());
    }


    private Appointment createAppointment(String id, String timeZone, String location, ZonedDateTime dateTimeFrom, ZonedDateTime dateTimeTo, AppointmentSyncStatus syncStatus,
                                   ZonedDateTime creationDate, int reminder, String title, String description, boolean dominant,
                                   Sensitivity sensitivity, ShowAs showAs, User user) {

        Appointment appointment = new Appointment();
        appointment.setId(id);
        appointment.setTimeZone(timeZone);
        appointment.setLocation(location);
        appointment.setDateTimeFrom(dateTimeFrom);
        appointment.setDateTimeTo(dateTimeTo);
        appointment.setSyncStatus(syncStatus);
        appointment.setCreationDate(creationDate);
        appointment.setReminder(reminder);
        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setDominant(dominant);
        appointment.setSensitivity(sensitivity);
        appointment.setShowAs(showAs);
        appointment.setUser(user);

        return appointment;
    }

    private User createUser(Integer id, String sapUsername, String exchangeUsername, String exchangeDomain,
                            String timeZone, ZonedDateTime creationDate, ZonedDateTime modificationDate,
                            UserRegistrationStatus registrationStatus, String firstName, String lastName) {
        User user = new User();
        user.setId(id);
        user.setSapUsername(sapUsername);
        user.setExchangeUsername(exchangeUsername);
        user.setExchangeDomain(exchangeDomain);
        user.setTimeZone(timeZone);
        user.setCreationDate(creationDate);
        user.setModificationDate(modificationDate);
        user.setRegistrationStatus(registrationStatus);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

}
