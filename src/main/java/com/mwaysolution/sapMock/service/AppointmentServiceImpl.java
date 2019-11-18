package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.Appointment;
import com.mwaysolution.sapMock.model.AppointmentSyncStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class AppointmentServiceImpl {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${syncItem.notification.url}")
    private String notificationURL;

    @Value("${syncItem.hostname}")
    private String hostname;

    private static final Logger logger = LogManager.getLogger(AppointmentServiceImpl.class);

    public List<Appointment> get() {
        return appointmentService.findAll();
    }

    public Appointment getById(String id) {
//        TODO: throw exception in case of null
        return appointmentService.findAppointmentById(id);
    }

    public void deleteById(String id) {
        appointmentService.delete(appointmentService.findAppointmentById(id));
    }


    public Appointment save(Appointment appointment) {

        if (appointment.getId() == null) {
            return create(appointment);
        }
        return update(appointment);
    }

    private Appointment create(Appointment appointment) {
        ZonedDateTime time = ZonedDateTime.now();
        appointment.setCreationDate(time);
        appointment.setModificationDate(time);
        appointment.setSyncStatus(AppointmentSyncStatus.NEW);

        return appointmentService.save(appointment);
    }


    private Appointment update(Appointment appointment) {
        Appointment appointmentSaved = getById(appointment.getId());
        appointmentSaved.setModificationDate(ZonedDateTime.now());


        if (!appointment.getTimeZone().equals("") && appointment.getTimeZone() != null)
            appointmentSaved.setTimeZone(appointment.getTimeZone());
        if (!appointment.getLocation().equals("") && appointment.getTimeZone() != null)
            appointmentSaved.setLocation(appointment.getLocation());
        if (appointment.getDateTimeFrom() != null)
            appointmentSaved.setDateTimeFrom(appointment.getDateTimeFrom());
        if (appointment.getDateTimeTo() != null)
            appointmentSaved.setDateTimeTo(appointment.getDateTimeTo());
        if (appointment.getReminder() != 0)
            appointmentSaved.setReminder(appointment.getReminder());
        if (!appointment.getTitle().equals("") && appointment.getTimeZone() != null)
            appointmentSaved.setTitle(appointment.getTitle());
        if (!appointment.getDescription().equals("") && appointment.getTimeZone() != null)
            appointmentSaved.setDescription(appointment.getDescription());
        if (appointment.getSensitivity() != null)
            appointmentSaved.setSensitivity(appointment.getSensitivity());
        if (appointment.getShowAs() != null)
            appointmentSaved.setShowAs(appointment.getShowAs());
        appointmentSaved.setDominant(appointment.isDominant());

//        may creator be changed?

//        appointmentSaved.setParticipants(appointment.getParticipants());


        return appointmentService.save(appointmentSaved);
    }

    public void sendNotification(Appointment appointment, String operation) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        operation = operation.toUpperCase();
        if (operation.equals("UPDATE") || operation.equals("DELETE")) {
            if (restTemplate.exchange(
                    hostname + notificationURL +
                            "?operation=" + operation +
                            "&header_guid=" + appointment.getId(),
                    HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200)
                logger.info("Notification for appointment " + operation + " " + appointment.getTitle().toUpperCase() + " was sent");
            else
                logger.error("Something went wrong. Notification for appointment " + operation + " " + appointment.getTitle().toUpperCase() + " wasn't sent");
        } else {
            if (restTemplate.exchange(
                    hostname + notificationURL +
                            "?operation=" + operation +
                            "&header_guid=" + appointment.getId() +
                            "&username=" + appointment.getUser().getSapUsername() +
                            "&timestamp=" + DateTimeFormatter.ofPattern("yyyyMMddhhmmss").format(ZonedDateTime.now()),
                    HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200)
                logger.info("Notification for appointment " + operation + " " + appointment.getTitle().toUpperCase() + " was sent");
            else
                logger.error("Something went wrong. Notification for appointment " + operation + " " + appointment.getTitle().toUpperCase() + " wasn't sent");
        }
    }

}
