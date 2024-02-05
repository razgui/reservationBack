package com.pds.reservation.repos;

import com.pds.reservation.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(value = "SELECT COALESCE(COUNT(r.id), 0) AS num_patient " +
            "FROM " +
            "(SELECT 1 AS month " +
            "UNION SELECT 2 " +
            "UNION SELECT 3 " +
            "UNION SELECT 4 " +
            "UNION SELECT 5 " +
            "UNION SELECT 6 " +
            "UNION SELECT 7 " +
            "UNION SELECT 8 " +
            "UNION SELECT 9 " +
            "UNION SELECT 10 " +
            "UNION SELECT 11 " +
            "UNION SELECT 12) m " +
            "LEFT JOIN patient r ON MONTH(r.creation_date) = m.month " +
            "GROUP BY m.month ORDER BY m.month", nativeQuery = true)
    List<Integer> findNumberOfPatientAddedPerMonth();
}
