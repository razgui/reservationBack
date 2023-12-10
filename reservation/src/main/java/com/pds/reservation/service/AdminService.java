package com.pds.reservation.service;

import com.pds.reservation.domain.Admin;
import com.pds.reservation.model.AdminDTO;
import com.pds.reservation.repos.AdminRepository;
import com.pds.reservation.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(final AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<AdminDTO> findAll() {
        final List<Admin> admins = adminRepository.findAll(Sort.by("id"));
        return admins.stream()
                .map(admin -> mapToDTO(admin, new AdminDTO()))
                .toList();
    }

    public AdminDTO get(final Long id) {
        return adminRepository.findById(id)
                .map(admin -> mapToDTO(admin, new AdminDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AdminDTO adminDTO) {
        final Admin admin = new Admin();
        mapToEntity(adminDTO, admin);
        return adminRepository.save(admin).getId();
    }

    public void update(final Long id, final AdminDTO adminDTO) {
        final Admin admin = adminRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(adminDTO, admin);
        adminRepository.save(admin);
    }

    public void delete(final Long id) {
        adminRepository.deleteById(id);
    }

    private AdminDTO mapToDTO(final Admin admin, final AdminDTO adminDTO) {
        adminDTO.setId(admin.getId());
        adminDTO.setFirstName(admin.getFirstName());
        adminDTO.setLastName(admin.getLastName());
        adminDTO.setRole(admin.getRole());
        adminDTO.setMail(admin.getMail());
        adminDTO.setPassword(admin.getPassword());
        return adminDTO;
    }

    private Admin mapToEntity(final AdminDTO adminDTO, final Admin admin) {
        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setRole(adminDTO.getRole());
        admin.setMail(adminDTO.getMail());
        admin.setPassword(adminDTO.getPassword());
        return admin;
    }

}
