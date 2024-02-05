package com.pds.reservation.service;

import com.pds.reservation.domain.Patient;
import com.pds.reservation.domain.Reservation;
import com.pds.reservation.domain.Soins;
import com.pds.reservation.model.ReservationDTO;
import com.pds.reservation.repos.PatientRepository;
import com.pds.reservation.repos.ReservationRepository;
import com.pds.reservation.repos.SoinsRepository;
import com.pds.reservation.util.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PatientRepository patientRepository;
    private final SoinsRepository soinsRepository;

    public ReservationService(final ReservationRepository reservationRepository,
            final PatientRepository patientRepository,final SoinsRepository soinsRepository) {
        this.reservationRepository = reservationRepository;
        this.patientRepository = patientRepository;
        this.soinsRepository = soinsRepository;
    }

    public List<ReservationDTO> findAll() {
        final List<Reservation> reservations = reservationRepository.findAll(Sort.by("id"));
        return reservations.stream()
                .map(reservation -> mapToDTO(reservation, new ReservationDTO()))
                .toList();
    }

    public ReservationDTO get(final Long id) {
        return reservationRepository.findById(id)
                .map(reservation -> mapToDTO(reservation, new ReservationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ReservationDTO reservationDTO) {
        final Reservation reservation = new Reservation();
        mapToEntity(reservationDTO, reservation);
        return reservationRepository.save(reservation).getId();
    }

    public void update(final Long id, final ReservationDTO reservationDTO) {
        final Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(reservationDTO, reservation);
        reservationRepository.save(reservation);
    }

    public void delete(final Long id) {
        reservationRepository.deleteById(id);
    }

    public int nmbrOfResrvationToday(){
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return reservationRepository.findAllByDateBetween(startOfDay, endOfDay).size();
    }

    public List<Integer> getNumberOfReservationsPerMonth() {
        return reservationRepository.findNumberOfReservationsPerMonth();
    }
    public int nmbrOfResrvationBookedToday(){
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return reservationRepository.findAllBycreationDateBetween(startOfDay, endOfDay).size();
    }

    private ReservationDTO mapToDTO(final Reservation reservation,
            final ReservationDTO reservationDTO) {
        reservationDTO.setId(reservation.getId());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setDescription(reservation.getDescription());
        reservationDTO.setPatient(reservation.getPatient() == null ? null : reservation.getPatient().getId());
        reservationDTO.setSoin(reservation.getSoins() == null ? null : reservation.getSoins().getId());
        return reservationDTO;
    }

    private Reservation mapToEntity(final ReservationDTO reservationDTO,
            final Reservation reservation) {
        reservation.setDate(reservationDTO.getDate());
        reservation.setDescription(reservationDTO.getDescription());
        reservation.setCreationDate(LocalDateTime.now());
        final Patient patient = reservationDTO.getPatient() == null ? null : patientRepository.findById(reservationDTO.getPatient())
                .orElseThrow(() -> new NotFoundException("patient not found"));
        reservation.setPatient(patient);
        final Soins soins = reservationDTO.getPatient() == null ? null : soinsRepository.findById(reservationDTO.getSoin())
                .orElseThrow(() -> new NotFoundException("patient not found"));
        reservation.setSoins(soins);
        return reservation;
    }

}
