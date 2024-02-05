package com.pds.reservation.service;

import com.pds.reservation.domain.Patient;
import com.pds.reservation.model.PatientDTO;
import com.pds.reservation.repos.PatientRepository;
import com.pds.reservation.util.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(final PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> findAll() {
        final List<Patient> patients = patientRepository.findAll(Sort.by("id"));
        return patients.stream()
                .map(patient -> mapToDTO(patient, new PatientDTO()))
                .toList();
    }

    public List<Integer> getNumberOfPatientAddPerMonth() {
        return patientRepository.findNumberOfPatientAddedPerMonth();
    }

    public PatientDTO get(final Long id) {
        return patientRepository.findById(id)
                .map(patient -> mapToDTO(patient, new PatientDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PatientDTO patientDTO) {
        final Patient patient = new Patient();
        mapToEntity(patientDTO, patient);
        return patientRepository.save(patient).getId();
    }

    public int getNbreOfPatients() {
        return patientRepository.findAll().size();
    }

    public void update(final Long id, final PatientDTO patientDTO) {
        final Patient patient = patientRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(patientDTO, patient);
        patientRepository.save(patient);
    }

    public void delete(final Long id) {
        patientRepository.deleteById(id);
    }

    private PatientDTO mapToDTO(final Patient patient, final PatientDTO patientDTO) {
        patientDTO.setId(patient.getId());
        patientDTO.setName(patient.getName());
        patientDTO.setLastName(patient.getLastName());
        patientDTO.setMail(patient.getMail());
        patientDTO.setTelephone(patient.getTelephone());
        patientDTO.setCreationDate(patient.getCreationDate());
        return patientDTO;
    }

    private Patient mapToEntity(final PatientDTO patientDTO, final Patient patient) {
        patient.setName(patientDTO.getName());
        patient.setLastName(patientDTO.getLastName());
        patient.setMail(patientDTO.getMail());
        patient.setTelephone(patientDTO.getTelephone());
        patient.setCreationDate(LocalDateTime.now());
        return patient;
    }

}
