package com.pds.reservation.repos;

import com.pds.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByDateBetween(LocalDateTime stdate,LocalDateTime enddate);
    List<Reservation> findAllBycreationDateBetween(LocalDateTime stdate,LocalDateTime enddate);
    @Query(value = "SELECT COALESCE(COUNT(r.id), 0) AS num_reservations " +
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
            "LEFT JOIN reservation r ON MONTH(r.date) = m.month " +
            "GROUP BY m.month ORDER BY m.month", nativeQuery = true)
    List<Integer> findNumberOfReservationsPerMonth();
}
