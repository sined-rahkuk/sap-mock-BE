package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.Appointment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl {
    @Autowired
    private AppointmentService appointmentService;

    public List<Appointment> get() {
        return appointmentService.findAll();
    }


    public Appointment getById(Integer id) {
        return appointmentService.findById(id);
    }


    public Appointment create(Appointment appointment) {
        return appointmentService.save(appointment);
    }


    public void deleteById(Integer id) {
        appointmentService.delete(appointmentService.findById(id));
    }


    public Appointment update(Integer appointmentID, Appointment appointment) {
//        TODO: ake fieldy sa smu nastavovat?
        BeanUtils.copyProperties(appointment, appointmentService.findById(appointmentID),
                "id", "creationDate", "syncStatus");
        return appointmentService.save(appointmentService.findById(appointmentID));
    }

}
