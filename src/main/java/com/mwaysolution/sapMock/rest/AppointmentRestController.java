package com.mwaysolution.sapMock.rest;

import com.mwaysolution.sapMock.model.Appointment;
import com.mwaysolution.sapMock.service.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("appointments")
public class AppointmentRestController {

    @Value("${syncItem.notification.url}")
    private String notificationURL;

    @Value("${syncItem.hostname}")
    private String hostname;

    @Autowired
    private AppointmentServiceImpl appointmentService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public List<Appointment> get() {
        return appointmentService.get();
    }

    @GetMapping("{id}")
    public Appointment getById(@PathVariable("id") String id) {
        return appointmentService.getById(id);
    }

    @PostMapping
    public Appointment create(@RequestBody Appointment appointment) {
        sendNotification(appointment, "CREATE");
        return appointmentService.save(appointment);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") String id) {
        sendNotification(appointmentService.getById(id), "DELETE");
        appointmentService.deleteById(id);
    }

    @PutMapping("{id}")
    public Appointment update(@PathVariable("id") String appointmentID, @RequestBody Appointment appointment) {
        appointment.setId(appointmentID);
        sendNotification(appointment, "UPDATE");
        return appointmentService.save(appointment);
    }

    private void sendNotification(Appointment appointment, String operation) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Appointment> entity = new HttpEntity<>(appointment, headers);
        if (operation.equals("UPDATE") || operation.equals("DELETE")) {
            if (restTemplate.exchange(
                    hostname + notificationURL +
                            "?operation=" + operation +
                            "&header_guid=" + appointment.getId(),
                    HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200)
                System.out.println("Notification for appointment" + appointment.getTitle() + "was send");
        } else {
            if (restTemplate.exchange(
                    hostname + notificationURL +
                            "?operation=" + operation +
                            "&header_guid=" + appointment.getId() +
                            "&username=" + appointment.getUser().getSapUsername() +
                            "&timestamp=" + DateTimeFormatter.ofPattern("yyyyMMddhhmmss").format(ZonedDateTime.now()),
                    HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200)
                System.out.println("Notification for appointment" + appointment.getTitle() + "was send");
        }
    }
}