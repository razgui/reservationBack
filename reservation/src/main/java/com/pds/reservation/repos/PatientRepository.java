package com.pds.reservation.repos;

import com.pds.reservation.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {
}
