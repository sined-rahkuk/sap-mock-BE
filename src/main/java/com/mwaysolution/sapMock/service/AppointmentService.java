package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentService extends JpaRepository<Appointment, Long> {
    Appointment findById(Integer id);
}
