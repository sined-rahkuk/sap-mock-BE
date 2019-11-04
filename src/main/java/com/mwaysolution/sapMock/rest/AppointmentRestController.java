package com.mwaysolution.sapMock.rest;

import com.mwaysolution.sapMock.model.Appointment;
import com.mwaysolution.sapMock.service.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointments")
public class AppointmentRestController {


    @Autowired
    private AppointmentServiceImpl appointmentService;

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
        return appointmentService.save(appointment);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") String id) {
        appointmentService.deleteById(id);
    }

    @PutMapping("{id}")
    public Appointment update(@PathVariable("id") String appointmentID, @RequestBody Appointment appointment)
            throws IllegalArgumentException {
        if (appointmentID.equals(appointment.getId()))
            return appointmentService.save(appointment);
        else throw new IllegalArgumentException("PATH ID DOES NOT MATCH ID IN BODY!");

    }

}