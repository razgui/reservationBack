package com.pds.reservation.service;

import com.pds.reservation.domain.Reservation;
import com.pds.reservation.domain.Soins;
import com.pds.reservation.model.SoinsDTO;
import com.pds.reservation.repos.ReservationRepository;
import com.pds.reservation.repos.SoinsRepository;
import com.pds.reservation.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SoinsService {

    private final SoinsRepository soinsRepository;
    private final ReservationRepository reservationRepository;

    public SoinsService(final SoinsRepository soinsRepository,
            final ReservationRepository reservationRepository) {
        this.soinsRepository = soinsRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<SoinsDTO> findAll() {
        final List<Soins> soinses = soinsRepository.findAll(Sort.by("id"));
        return soinses.stream()
                .map(soins -> mapToDTO(soins, new SoinsDTO()))
                .toList();
    }

    public SoinsDTO get(final Long id) {
        return soinsRepository.findById(id)
                .map(soins -> mapToDTO(soins, new SoinsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SoinsDTO soinsDTO) {
        final Soins soins = new Soins();
        mapToEntity(soinsDTO, soins);
        return soinsRepository.save(soins).getId();
    }

    public void update(final Long id, final SoinsDTO soinsDTO) {
        final Soins soins = soinsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(soinsDTO, soins);
        soinsRepository.save(soins);
    }

    public void delete(final Long id) {
        soinsRepository.deleteById(id);
    }

    private SoinsDTO mapToDTO(final Soins soins, final SoinsDTO soinsDTO) {
        soinsDTO.setId(soins.getId());
        soinsDTO.setName(soins.getName());
        soinsDTO.setPrice(soins.getPrice());
        soinsDTO.setDescription(soins.getDescription());
        return soinsDTO;
    }

    private Soins mapToEntity(final SoinsDTO soinsDTO, final Soins soins) {
        soins.setName(soinsDTO.getName());
        soins.setPrice(soinsDTO.getPrice());
        soins.setDescription(soinsDTO.getDescription());
        return soins;
    }

}
