package com.mwaysolution.sapMock.rest;


import com.mwaysolution.sapMock.model.Appointment;
import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.service.AppointmentServiceImpl;
import com.mwaysolution.sapMock.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Controller
public class GuiAppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${syncItem.notification.url}")
    private String notificationURL;

    @Value("${syncItem.hostname}")
    private String hostname;

    private User user;

    @RequestMapping("/gui/appointments")
    public String viewHomePage(Model model) {
        List<Appointment> listAppointments = appointmentService.get();
        model.addAttribute("listAppointments", listAppointments);

        return "indexAppointment";
    }

    @RequestMapping("/gui/appointments/create/{id}")
    public String createAppointment(Model model, @PathVariable("id") Integer userId) {
        Appointment appointment = new Appointment();
        user = userService.getById(userId);
        appointment.setUser(user);
        model.addAttribute("appointment", appointment);

        return "createAppointment";
    }

    @RequestMapping(value = "/gui/appointments/save", method = RequestMethod.POST)
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment,
                                  @RequestParam("dateTimeFROM") String dateTimeFROM,
                                  @RequestParam("dateTimeTO") String dateTimeTO) {
        appointment.setUser(user);
        appointment.setDateTimeFrom(ZonedDateTime.parse(dateTimeFROM + appointment.getTimeZone()));
        appointment.setDateTimeTo(ZonedDateTime.parse(dateTimeTO + appointment.getTimeZone()));
        Appointment appointmentSaved = appointmentService.save(appointment);
        sendNotification(appointmentSaved, "CREATE");

        return "redirect:/gui/appointments";
    }

    @RequestMapping("/gui/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable("id") String id) {
        sendNotification(appointmentService.getById(id), "DELETE");
        appointmentService.deleteById(id);

        return "redirect:/gui/appointments";
    }

    @RequestMapping("/gui/appointments/edit/{id}")
    public ModelAndView updateAppointmentPage(@PathVariable("id") String id) {
        ModelAndView model = new ModelAndView("updateAppointment");

        Appointment appointment = appointmentService.getById(id);
        model.addObject("appointment", appointment);

        return model;
    }

    @RequestMapping(value = "/gui/appointments/update", method = RequestMethod.POST)
    public String updateAppointment(@ModelAttribute("appointment") Appointment appointment,
                                    @RequestParam("dateTimeFROM") String dateTimeFROM,
                                    @RequestParam("dateTimeTO") String dateTimeTO) {
        appointment.setDateTimeFrom(ZonedDateTime.parse(dateTimeFROM + appointment.getTimeZone()));
        appointment.setDateTimeTo(ZonedDateTime.parse(dateTimeTO + appointment.getTimeZone()));
        appointmentService.save(appointment);
        sendNotification(appointment, "UPDATE");

        return "redirect:/gui/appointments";
    }

    private void sendNotification(Appointment appointment, String operation) {
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
                System.out.println("Notification for appointment " + operation + " " + appointment.getTitle().toUpperCase() + " was sent");
        } else {
            if (restTemplate.exchange(
                    hostname + notificationURL +
                            "?operation=" + operation +
                            "&header_guid=" + appointment.getId() +
                            "&username=" + appointment.getUser().getSapUsername() +
                            "&timestamp=" + DateTimeFormatter.ofPattern("yyyyMMddhhmmss").format(ZonedDateTime.now()),
                    HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200)
                System.out.println("Notification for appointment " + operation + " " +  appointment.getTitle().toUpperCase() + " was sent");
        }
    }
}
