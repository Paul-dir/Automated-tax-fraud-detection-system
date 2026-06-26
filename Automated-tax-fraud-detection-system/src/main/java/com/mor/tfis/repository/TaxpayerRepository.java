package com.mor.tfis.repository;

import com.mor.tfis.entity.Taxpayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaxpayerRepository extends JpaRepository<Taxpayer, UUID> {
    Optional<Taxpayer> findByTin(String tin);
    boolean existsByTin(String tin);
}