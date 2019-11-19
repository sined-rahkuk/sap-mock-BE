package com.mwaysolution.sapMock;

import com.mwaysolution.sapMock.model.*;
import com.mwaysolution.sapMock.service.AppointmentServiceImpl;
import com.mwaysolution.sapMock.service.UserServiceImpl;
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

    @Autowired
    UserServiceImpl userService;

    @Autowired
    AppointmentServiceImpl appointmentServiceImpl;


    @Test
    public void saveValidAppointment() {
        User user1 = createUser(null, "TestValidSapName", "TestValidExName",
                "TestValidDomain", "+02:00", null, null,
                UserRegistrationStatus.INIT, "TestValidName", "TestValidSurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();
        Appointment appointmentCreateValid = createAppointment(null, "+02:00", "Kosice", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(),ZonedDateTime.now(), 10, "Title", "Description", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment genericAppointment = appointmentServiceImpl.save(appointmentCreateValid);

        assertThat(genericAppointment.getId()).isNotNull();
        assertThat(genericAppointment.getTimeZone()).isEqualTo(appointmentCreateValid.getTimeZone());
        assertThat(genericAppointment.getLocation()).isEqualTo(appointmentCreateValid.getLocation());
        assertThat(genericAppointment.getDateTimeFrom()).isEqualTo(appointmentCreateValid.getDateTimeFrom());
        assertThat(genericAppointment.getDateTimeTo()).isEqualTo(appointmentCreateValid.getDateTimeTo());
        assertThat(genericAppointment.getSyncStatus()).isEqualTo(appointmentCreateValid.getSyncStatus());
        assertThat(genericAppointment.getCreationDate()).isEqualTo(appointmentCreateValid.getCreationDate());
        assertThat(genericAppointment.getModificationDate()).isEqualTo(appointmentCreateValid.getModificationDate());
        assertThat(genericAppointment.getReminder()).isEqualTo(appointmentCreateValid.getReminder());
        assertThat(genericAppointment.getTitle()).isEqualTo(appointmentCreateValid.getTitle());
        assertThat(genericAppointment.getDescription()).isEqualTo(appointmentCreateValid.getDescription());
        assertThat(genericAppointment.isDominant()).isEqualTo(appointmentCreateValid.isDominant());
        assertThat(genericAppointment.getSensitivity()).isEqualTo(appointmentCreateValid.getSensitivity());
        assertThat(genericAppointment.getShowAs()).isEqualTo(appointmentCreateValid.getShowAs());
        assertThat(genericAppointment.getUser()).isEqualTo(appointmentCreateValid.getUser());
    }

    @Test
    public void updateValidAppointment() {
        User user1 = createUser(null, "TestValidSapNameUpdate", "TestValidExNameUpdate",
                "TestValidDomain", "+02:00", null, null,
                UserRegistrationStatus.INIT, "TestValidName", "TestValidSurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentUpdateValidCreate = createAppointment(null, "+03:00", "Kiev", time.plusDays(3), time.plusDays(5), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "Description", true, Sensitivity.CONFIDENTIAL, ShowAs.BUSY, user1);

        Appointment appointmentUpdateValid = createAppointment(null, "+03:00", "Kiev", time.plusDays(3), time.plusDays(5), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "TitleTitle", "Description", true, Sensitivity.CONFIDENTIAL, ShowAs.BUSY, user1);

        Appointment genericValidCreateAppointment = appointmentServiceImpl.save(appointmentUpdateValidCreate);
        appointmentUpdateValid.setId(genericValidCreateAppointment.getId());
        Appointment genericValidUpdateAppointment = appointmentServiceImpl.save(appointmentUpdateValid);

        assertThat(genericValidUpdateAppointment.getTitle()).isEqualTo(appointmentUpdateValid.getTitle());
        assertThat(genericValidUpdateAppointment.getTitle()).isEqualTo(appointmentUpdateValid.getTitle());
    }

    @Test
    public void updateInvalidAppointmentTimeZone(){
        User user1 = createUser(null, "TestInvalidTimeZoneSapName", "TestInvalidTimeZoneExName",
                "TestInvalidTimeZoneDomain", "+01:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidTimeZoneName", "TestInvalidTimeZoneSurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();
        Appointment appointmentValidTimeZone = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "DescriptionTimeZone", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment appointmentInvalidTimeZone = createAppointment(null, "", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "DescriptionTimeZone", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment genericValidAppointment = appointmentServiceImpl.save(appointmentValidTimeZone);
        appointmentInvalidTimeZone.setId(genericValidAppointment.getId());
        Appointment genericValidUpdateAppointment = appointmentServiceImpl.save(appointmentInvalidTimeZone);

        assertThat(genericValidUpdateAppointment.getTimeZone()).isEqualTo(appointmentValidTimeZone.getTimeZone());
    }

    @Test
    public void updateInvalidAppointmentDateFrom(){
        User user1 = createUser(null, "TestInvalidDateFromSapName", "TestInvalidDateFromExName",
                "TestInvalidDateFromDomain", "+02:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidDateFromName", "TestInvalidDateFromSurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();
        Appointment appointmentValidDateFrom = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 4, "Title", "DescriptionDateFrom", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment appointmentInvalidDateFrom = createAppointment(null, "+02:00", "Bratislava", null, time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 4, "Title", "DescriptionDateFrom", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment genericValidAppointment = appointmentServiceImpl.save(appointmentValidDateFrom);
        appointmentInvalidDateFrom.setId(genericValidAppointment.getId());
        Appointment genericValidUpdateAppointment = appointmentServiceImpl.save(appointmentInvalidDateFrom);

        assertThat(genericValidUpdateAppointment.getDateTimeFrom()).isEqualTo(appointmentValidDateFrom.getDateTimeFrom());
    }

    @Test
    public void updateInvalidAppointmentDateTo(){
        User user1 = createUser(null, "TestInvalidDateToSapName", "TestInvalidDateToExName",
                "TestInvalidDateToDomain", "+03:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidDateToName", "TestInvalidDateToSurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();
        Appointment appointmentValidDateTo = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 30, "Title", "DescriptionDateTo", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment appointmentInvalidDateTo = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), null, AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 30, "Title", "DescriptionDateTo", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment genericValidAppointment = appointmentServiceImpl.save(appointmentValidDateTo);
        appointmentInvalidDateTo.setId(genericValidAppointment.getId());
        Appointment genericValidUpdateAppointment = appointmentServiceImpl.save(appointmentInvalidDateTo);

        assertThat(genericValidUpdateAppointment.getDateTimeTo()).isEqualTo(appointmentValidDateTo.getDateTimeTo());
    }

    @Test
    public void updateInvalidAppointmentReminder(){
        User user1 = createUser(null, "TestInvalidReminderSapName", "TestInvalidReminderExName",
                "TestInvalidReminderDomain", "+04:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidName", "TestInvalidReminderSurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();
        Appointment appointmentValidReminder = createAppointment(null, "+04:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 20, "Title", "DescriptionReminder", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment appointmentInvalidReminder = createAppointment(null, "+04:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 0, "Title", "DescriptionReminder", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment genericValidAppointment = appointmentServiceImpl.save(appointmentValidReminder);
        appointmentInvalidReminder.setId(genericValidAppointment.getId());
        Appointment genericValidUpdateAppointment = appointmentServiceImpl.save(appointmentInvalidReminder);

        assertThat(genericValidUpdateAppointment.getReminder()).isEqualTo(appointmentValidReminder.getReminder());
    }

    @Test
    public void updateInvalidAppointmentTitle(){
        User user1 = createUser(null, "TestInvalidTitleSapName", "TestInvalidTitleExName",
                "TestInvalidTitleDomain", "+05:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidTitleName", "TestInvalidTitleSurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();
        Appointment appointmentValidTitle = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "Description", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment appointmentInvalidTitle = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "", "Description", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment genericValidAppointment = appointmentServiceImpl.save(appointmentValidTitle);
        appointmentInvalidTitle.setId(genericValidAppointment.getId());
        Appointment genericValidUpdateAppointment = appointmentServiceImpl.save(appointmentInvalidTitle);

        assertThat(genericValidUpdateAppointment.getTitle()).isEqualTo(appointmentValidTitle.getTitle());
    }

    @Test
    public void updateInvalidAppointmentDescription(){
        User user1 = createUser(null, "TestInvalidDescriptionSapName", "TestInvalidDescriptionExName",
                "TestInvalidDescriptionDomain", "-01:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidDescriptionName", "TestInvalidDescriptionSurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();
        Appointment appointmentValidDescription = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "Description", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment appointmentInvalidDescription = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment genericValidAppointment = appointmentServiceImpl.save(appointmentValidDescription);
        appointmentInvalidDescription.setId(genericValidAppointment.getId());
        Appointment genericValidUpdateAppointment = appointmentServiceImpl.save(appointmentInvalidDescription);

        assertThat(genericValidUpdateAppointment.getDescription()).isEqualTo(appointmentValidDescription.getDescription());

    }

    @Test
    public void updateInvalidAppointmentSensitivity(){
        User user1 = createUser(null, "TestInvalidSensitivitySapName", "TestInvalidSensitivityExName",
                "TestInvalidSensitivityDomain", "-02:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidSensitivityName", "TestInvalidSensitivitySurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();
        Appointment appointmentValidSensitivity = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "Description", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment appointmentInvalidSensitivity = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "Description", true, null, ShowAs.FREE, user1);

        Appointment genericValidAppointment = appointmentServiceImpl.save(appointmentValidSensitivity);
        appointmentInvalidSensitivity.setId(genericValidAppointment.getId());
        Appointment genericValidUpdateAppointment = appointmentServiceImpl.save(appointmentInvalidSensitivity);

        assertThat(genericValidUpdateAppointment.getSensitivity()).isEqualTo(appointmentValidSensitivity.getSensitivity());

    }

    @Test
    public void updateInvalidAppointmentShowAs(){
        User user1 = createUser(null, "TestInvalidShowAsSapName", "TestInvalidShowAsExName",
                "TestInvalidShowAsDomain", "-03:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidShowAsName", "TestInvalidShowAsSurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentValidShowAs = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "Description", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment appointmentInvalidShowAs = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "Description", true, Sensitivity.NORMAL, null, user1);

        Appointment genericValidAppointment = appointmentServiceImpl.save(appointmentValidShowAs);
        appointmentInvalidShowAs.setId(genericValidAppointment.getId());
        Appointment genericValidUpdateAppointment = appointmentServiceImpl.save(appointmentInvalidShowAs);

        assertThat(genericValidUpdateAppointment.getShowAs()).isEqualTo(appointmentValidShowAs.getShowAs());
    }

    @Test
    public void updateInvalidAppointmentLocation(){
        User user1 = createUser(null, "TestInvalidLocationSapName", "TestInvalidLocationExName",
                "TestInvalidLocationDomain", "-04:00", null, null,
                UserRegistrationStatus.INIT, "TestInvalidLocationName", "TestInvalidLocationSurname");
        userService.save(user1);
        ZonedDateTime time = ZonedDateTime.now();

        Appointment appointmentValidLocation = createAppointment(null, "+02:00", "Bratislava", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "Description", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment appointmentInvalidLocation = createAppointment(null, "+02:00", "", time.plusDays(1), time.plusDays(2), AppointmentSyncStatus.NEW,
                ZonedDateTime.now(), ZonedDateTime.now(), 15, "Title", "Description", true, Sensitivity.NORMAL, ShowAs.FREE, user1);

        Appointment genericValidAppointment = appointmentServiceImpl.save(appointmentValidLocation);
        appointmentInvalidLocation.setId(genericValidAppointment.getId());
        Appointment genericValidUpdateAppointment = appointmentServiceImpl.save(appointmentInvalidLocation);

        assertThat(genericValidUpdateAppointment.getLocation()).isEqualTo(appointmentValidLocation.getLocation());
    }

    private Appointment createAppointment(String id, String timeZone, String location, ZonedDateTime dateTimeFrom, ZonedDateTime dateTimeTo, AppointmentSyncStatus syncStatus,
                                   ZonedDateTime creationDate, ZonedDateTime modificationDate, int reminder, String title, String description, boolean dominant,
                                   Sensitivity sensitivity, ShowAs showAs, User user) {

        Appointment appointment = new Appointment();
        appointment.setId(id);
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
