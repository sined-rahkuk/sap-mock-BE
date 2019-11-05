package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl {
    @Autowired
    private AppointmentService appointmentService;

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
        appointment.setCreationDate(ZonedDateTime.now());
        appointment.setModificationDate(ZonedDateTime.now());
        return appointmentService.save(appointment);
    }


    private Appointment update(Appointment appointment) {
        Appointment appointmentSaved = getById(appointment.getId());
        appointmentSaved.setModificationDate(ZonedDateTime.now());


        if (!appointment.getTimeZone().equals("") && appointment.getTimeZone() != null)
            appointmentSaved.setTimeZone(appointment.getTimeZone());
        if (!appointment.getLocation().equals("") && appointment.getTimeZone() != null)
            appointmentSaved.setLocation(appointment.getLocation());
        if (!appointment.getDateTimeFrom().equals("") && appointment.getTimeZone() != null)
            appointmentSaved.setDateTimeFrom(appointment.getDateTimeFrom());
        if (!appointment.getDateTimeTo().equals("") && appointment.getTimeZone() != null)
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

        appointmentSaved.setParticipants(appointment.getParticipants());


        return appointmentService.save(appointmentSaved);
    }


}
