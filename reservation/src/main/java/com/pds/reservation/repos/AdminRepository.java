package com.pds.reservation.repos;

import com.pds.reservation.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findBymail(String mail);
}
