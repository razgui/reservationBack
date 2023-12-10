package com.pds.reservation.repos;

import com.pds.reservation.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, Long> {
}
