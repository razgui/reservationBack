package com.pds.reservation.service;

import com.pds.reservation.domain.Patient;
import com.pds.reservation.domain.Reservation;
import com.pds.reservation.model.ReservationDTO;
import com.pds.reservation.repos.PatientRepository;
import com.pds.reservation.repos.ReservationRepository;
import com.pds.reservation.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PatientRepository patientRepository;

    public ReservationService(final ReservationRepository reservationRepository,
            final PatientRepository patientRepository) {
        this.reservationRepository = reservationRepository;
        this.patientRepository = patientRepository;
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

    private ReservationDTO mapToDTO(final Reservation reservation,
            final ReservationDTO reservationDTO) {
        reservationDTO.setId(reservation.getId());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setDescription(reservation.getDescription());
        reservationDTO.setPatient(reservation.getPatient() == null ? null : reservation.getPatient().getId());
        return reservationDTO;
    }

    private Reservation mapToEntity(final ReservationDTO reservationDTO,
            final Reservation reservation) {
        reservation.setDate(reservationDTO.getDate());
        reservation.setDescription(reservationDTO.getDescription());
        final Patient patient = reservationDTO.getPatient() == null ? null : patientRepository.findById(reservationDTO.getPatient())
                .orElseThrow(() -> new NotFoundException("patient not found"));
        reservation.setPatient(patient);
        return reservation;
    }

}
